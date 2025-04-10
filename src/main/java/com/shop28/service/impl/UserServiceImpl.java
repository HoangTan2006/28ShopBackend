package com.shop28.service.impl;

import com.shop28.dto.request.UserCreateRequest;
import com.shop28.dto.request.UserUpdateRequest;
import com.shop28.dto.response.UserResponse;
import com.shop28.entity.Role;
import com.shop28.entity.User;
import com.shop28.entity.UserHasRole;
import com.shop28.mapper.UserMapper;
import com.shop28.repository.UserRepository;
import com.shop28.service.RoleService;
import com.shop28.service.UserService;
import com.shop28.util.TypeUser;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.Query;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final UserMapper userMapper;

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("Not found"));
    }

//    @Override
//    public UserResponse SearchUser(String keyword) {
//        String regex = "^(username|phone|email):.*$";
//        if (!searchMethod.matches(regex)) throw new ValidationException("Search by username or phone or email");
//
//        String sql = "SELECT * FROM `users` WHERE :searchMethod = :keyword";
//        Query query = entityManager.createQuery(sql);
//        query.setParameter("searchMethod", searchMethod);
//        query.setParameter("keyword", keyword);
//
//        User user = (User) query.getSingleResult();
//
//        return userMapper.toUserDTO(user);
//    }

    @Override
    public List<UserResponse> getUsers(Integer pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10);

        List<User> users = userRepository.findAll(page).getContent();

        return users.stream().map(userMapper::toUserDTO).toList();
    }

    @Override
    public UserResponse createUser(UserCreateRequest userCreate) {

        if (userRepository.existsByUsername(userCreate.getUsername())) throw new EntityExistsException("Username existed");

        Role role = roleService.findByName(TypeUser.USER.name());

        User user = User.builder()
                .firstName(userCreate.getFirstName())
                .lastName(userCreate.getLastName())
                .username(userCreate.getUsername())
                .password(passwordEncoder.encode(userCreate.getPassword()))
                .email(userCreate.getEmail())
                .phone(userCreate.getPhone())
                .isLock(Boolean.FALSE)
                .build();

        UserHasRole userHasRole = UserHasRole.builder()
                .user(user)
                .role(role)
                .build();

        user.setRoles(Set.of(userHasRole));

        user = userRepository.save(user);
        log.info("Created user ID: {}", user.getId());

        return userMapper.toUserDTO(user);
    }

    @Override
    public UserResponse updateUser(Integer id, UserUpdateRequest userUpdate) {

        User user = userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        user.setFirstName(userUpdate.getFirstName());
        user.setLastName(userUpdate.getLastName());
        user.setEmail(userUpdate.getEmail());
        user.setPhone(userUpdate.getPhone());

        user = userRepository.save(user);

        return userMapper.toUserDTO(user);
    }
}
