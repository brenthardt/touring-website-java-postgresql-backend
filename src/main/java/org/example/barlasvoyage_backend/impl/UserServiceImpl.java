package org.example.barlasvoyage_backend.impl;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.dto.LoginDto;
import org.example.barlasvoyage_backend.dto.LoginResponse;
import org.example.barlasvoyage_backend.dto.UserDto;
import org.example.barlasvoyage_backend.entity.User;
import org.example.barlasvoyage_backend.jwt.JwtUtil;
import org.example.barlasvoyage_backend.repository.RoleRepository;
import org.example.barlasvoyage_backend.repository.UserRepository;
import org.example.barlasvoyage_backend.role.Role;
import org.example.barlasvoyage_backend.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Override
    public ResponseEntity<?> findAll(int page,int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);
        return ResponseEntity.ok(usersPage);
    }

    @Override
    public User save(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            Role userRole = roleRepository.findByName("ROLE_USER")
                    .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
            user.setRoles(List.of(userRole));
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public ResponseEntity<?> delete(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            return ResponseEntity.ok("User deleted");
        }
        return ResponseEntity.notFound().build();
    }

    @Override
    public ResponseEntity<?> update(UUID id, User user) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setName(user.getName());
                    existingUser.setPassword(user.getPassword());
                    existingUser.setRoles(user.getRoles());
                    return ResponseEntity.ok(userRepository.save(existingUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<?> signUp(UserDto userDto) {
        if (userRepository.existsByPhone(userDto.getPhone())) {
            return ResponseEntity.badRequest().body("User already exists with this phone");
        }

        User user = new User();
        user.setName(userDto.getName());
        user.setPhone(userDto.getPhone());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));

        user.setRoles(List.of(userRole));

        User savedUser = userRepository.save(user);
        String token = jwtUtil.generateToken(savedUser.getPhone(), "ROLE_USER");

        LoginResponse response = new LoginResponse(
                savedUser.getId(),
                savedUser.getName(),
                savedUser.getPhone(),
                "ROLE_USER",
                token
        );

        return ResponseEntity.ok(response);


    }

    @Override
    public ResponseEntity<?> login(LoginDto loginDTO) {
        return userRepository.findByPhone(loginDTO.getPhone())
                .map(user -> {
                    if (passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
                        String mainRole = user.getRoles().stream()
                                .findFirst()
                                .map(Role::getName)
                                .orElse("UNKNOWN");


                        String token = jwtUtil.generateToken(user.getPhone(), mainRole);

                        LoginResponse response = new LoginResponse(
                                user.getId(),
                                user.getName(),
                                user.getPhone(),
                                mainRole,
                                token
                        );
                        return ResponseEntity.ok(response);
                    } else {
                        return ResponseEntity.badRequest().body("Invalid password");
                    }
                })
                .orElse(ResponseEntity.badRequest().body("User not found"));
    }



    @Override
    public ResponseEntity<?> deleteByPhone(String phone) {
        return userRepository.findByPhone(phone)
                .map(user -> {
                    userRepository.delete(user);
                    return ResponseEntity.ok("✅ User deleted");
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("❌ User not found"));
    }

    @Override
    public ResponseEntity<?> quickCreate(String name, String phone) {
        if (userRepository.existsByPhone(phone)) {
            return ResponseEntity.badRequest().body("User already exists with this phone");
        }

        User user = new User();
        user.setName(name);
        user.setPhone(phone);
        user.setPassword("");

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        user.setRoles(List.of(userRole));

        userRepository.save(user);
        return ResponseEntity.ok("User created successfully");
    }

    @Override
    public ResponseEntity<?> getWannabuyers() {
        List<User> wannabuyers = userRepository.findByPassword("");
        return ResponseEntity.ok(wannabuyers);
    }

    @Override
    public ResponseEntity<?> saveComment(User user) {
        if (user.getName() == null || user.getPhone() == null || user.getComment() == null) {
            return ResponseEntity.badRequest().body("Name, phone, and comment are required");
        }

        User newUser = new User();
        newUser.setName(user.getName());
        newUser.setPhone(user.getPhone());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setComment(user.getComment());

        Role userRole = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new RuntimeException("ROLE_USER not found"));
        newUser.setRoles(List.of(userRole));

        userRepository.save(newUser);
        return ResponseEntity.ok("Comment submitted successfully");
    }

    @Override
    public ResponseEntity<?> getAllComments() {
        List<User> commenters = userRepository.findByCommentIsNotNull();
        return ResponseEntity.ok(commenters);
    }





}
