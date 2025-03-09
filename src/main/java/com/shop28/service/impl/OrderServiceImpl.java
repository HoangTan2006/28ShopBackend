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
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final ProductDetailRepository productDetailRepository;
    private final AddressRepository addressRepository;
    private final AddressMapper addressMapper;
    private final CartItemRepository cartItemRepository;

    //Lấy danh sách order, dành cho admin
    @Override
    public List<OrderResponse> getOrders(Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = orderRepository.findAll(pageable).getContent();
        log.info("Get list order from database");

        return orders.stream().map(order -> OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .address(addressMapper.toDTO(order.getAddress()))
                .price(order.getTotalPrice())
                .status(order.getStatus())
                .build()).toList();
    }

    //Lấy danh sách các order của người dùng hiện tại
    @Override
    public List<OrderResponse> getOrdersByUserId(Integer userId, Integer pageNumber, Integer pageSize) {

        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<Order> orders = orderRepository.findByUserId(userId, pageable);
        log.info("Get list order by user ID: {}", userId);

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

        //Tạo order
        Order order = Order.builder()
                .user(user)
                .status(TypeStatus.PENDING.name())
                .address(address)
                .build();

        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        List<Integer> productDetailsId = cartItems.stream().map(cartItem ->
                cartItem.getProductDetail().getId()).toList();

        Map<Integer, Integer> cart = new HashMap<>();

        cartItems.forEach(cartItem ->
                cart.put(cartItem.getProductDetail().getId(), cartItem.getQuantity()));


        List<ProductDetail> productDetails = productDetailRepository.findAllById(productDetailsId);
        Set<OrderDetail> orderDetails = new HashSet<>();

        StringJoiner title = new StringJoiner(", ");
        int totalPrice = 0;

        /*Thực hiện lặp qua danh sách sản phẩm dựa trên giỏ hàng hiện tại:
          Kiểm tra và cập nhật số lượng trong kho nếu sản phẩm còn đủ số lượng
          Tính số tiền cần thanh toán của order, nối chuỗi title bằng tên các sản phẩm*/
        for (ProductDetail productDetail : productDetails) {

            Integer stock = productDetail.getStockQuantity();
            Integer quantity = cart.get(productDetail.getId());

            if (stock >= quantity) {
                productDetail.setStockQuantity(stock - quantity);

                title.add(productDetail.getProduct().getName());

                totalPrice += productDetail.getPrice() * quantity;

                orderDetails.add(OrderDetail.builder()
                                .order(order)
                                .productDetail(productDetail)
                                .quantity(quantity)
                                .price(productDetail.getPrice() * quantity)
                                .status(TypeStatus.PENDING.name())
                        .build());
            } else {
                log.error("The product {} is out of stock", productDetail.getId());
                throw new RuntimeException("The product" + productDetail.getProduct().getName() + "is out of stock");
            }
        }

        //Cập nhật title, totalPrice, orderDetail
        order.setTitle(title.toString());
        order.setTotalPrice(totalPrice);
        order.setOrderDetails(orderDetails);

        productDetailRepository.saveAll(productDetails);
        cartItemRepository.deleteAll(cartItems);
        order = orderRepository.save(order);
        log.info("Created order ID: {} by user ID: {}", order.getId(), user.getId());

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
        log.info("Order ID: {} status updated to {}", order.getId(), order.getStatus());

        return OrderResponse.builder()
                .id(order.getId())
                .userId(order.getUser().getId())
                .address(addressMapper.toDTO(order.getAddress()))
                .price(order.getTotalPrice())
                .status(order.getStatus())
                .build();
    }
}
