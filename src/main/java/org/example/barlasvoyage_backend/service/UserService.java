package org.example.barlasvoyage_backend.service;


import org.example.barlasvoyage_backend.dto.LoginDto;
import org.example.barlasvoyage_backend.dto.UserDto;
import org.example.barlasvoyage_backend.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public interface UserService {
    ResponseEntity<?> findAll(int page,int size);
    User save(User user);
    ResponseEntity<?> delete(UUID id);
ResponseEntity<?> update(UUID id, User user);
    ResponseEntity<?> signUp(UserDto userDto);
    ResponseEntity<?> login(LoginDto loginDto);
    ResponseEntity<?> deleteByPhone(String phone);
    ResponseEntity<?> quickCreate(String name, String phone);
    ResponseEntity<?> getWannabuyers();
    ResponseEntity<?> saveComment(User user);
    ResponseEntity<?> getAllComments();



}
