package com.shop28.service.impl;

import com.shop28.dto.request.AddressRequest;
import com.shop28.dto.request.OrderStatusUpdateRequest;
import com.shop28.dto.response.OrderResponse;
import com.shop28.entity.*;
import com.shop28.mapper.AddressMapper;
import com.shop28.repository.*;
import com.shop28.service.OrderService;
import com.shop28.util.TypeStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductVariantRepository productVariantRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CartItemRepository cartItemRepository;

    @Override
    public List<OrderResponse> getOrders(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = orderRepository.findAll(pageable).getContent();

        return orders.stream().map(order -> OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .address(addressMapper.toDTO(order.getAddress()))
                .price(order.getTotalPrice())
                .status(order.getStatus())
                .build()).toList();
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Integer userId, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = orderRepository.findByUserId(userId, pageable);

        return orders.stream().map(order -> OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .address(addressMapper.toDTO(order.getAddress()))
                .price(order.getTotalPrice())
                .status(order.getStatus())
                .build()).toList();
    }

    @Override
    public OrderResponse createOrder(Integer userId, AddressRequest addressRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Address address = addressRepository.save(addressMapper.toEntity(addressRequest));

        Order order = Order.builder()
                .user(user)
                .status(TypeStatus.PENDING.name())
                .address(address)
                .build();

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<Integer> productVariantsId = cartItems.stream().map(cartItem ->
                cartItem.getProductVariant().getId()).toList();

        Map<Integer, Integer> cart = new HashMap<>();

        cartItems.forEach(cartItem ->
                cart.put(cartItem.getProductVariant().getId(), cartItem.getQuantity()));


        List<ProductVariant> productVariants = productVariantRepository.findAllById(productVariantsId);
        Set<OrderDetail> orderDetails = new HashSet<>();

        StringJoiner title = new StringJoiner(", ");
        int totalPrice = 0;

        for (ProductVariant productVariant : productVariants) {

            Integer stock = productVariant.getStockQuantity();
            Integer quantity = cart.get(productVariant.getId());

            if (stock >= quantity) {
                productVariant.setStockQuantity(stock - quantity);

                title.add(productVariant.getProduct().getName());

                totalPrice += productVariant.getPrice() * quantity;

                orderDetails.add(OrderDetail.builder()
                                .order(order)
                                .productVariant(productVariant)
                                .quantity(quantity)
                                .price(productVariant.getPrice() * quantity)
                                .status(TypeStatus.PENDING.name())
                        .build());
            }
            else throw new RuntimeException("The product" + productVariant.getProduct().getName() + "is out of stock");
        }


        order.setTitle(title.toString());
        order.setTotalPrice(totalPrice);
        order.setOrderDetails(orderDetails);

        productVariantRepository.saveAll(productVariants);
        cartItemRepository.deleteAll(cartItems);
        order = orderRepository.save(order);

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .address(addressMapper.toDTO(order.getAddress()))
                .price(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }

    @Override
    public OrderResponse updateStatusOder(Integer id, OrderStatusUpdateRequest orderStatusUpdateRequest) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        order.setStatus(orderStatusUpdateRequest.getStatus().toUpperCase());

        order = orderRepository.save(order);

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .address(addressMapper.toDTO(order.getAddress()))
                .price(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
