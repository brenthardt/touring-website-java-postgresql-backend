package org.example.barlasvoyage_backend.controller;

import lombok.RequiredArgsConstructor;
import org.example.barlasvoyage_backend.dto.LoginDto;
import org.example.barlasvoyage_backend.dto.UserDto;
import org.example.barlasvoyage_backend.entity.User;
import org.example.barlasvoyage_backend.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> findAll(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return userService.findAll(page,size);
    }

    @PostMapping
    public User save(@RequestBody User user) {
        return userService.save(user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        return userService.delete(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id,@RequestBody User user) {
        return userService.update(id, user);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) {
        return userService.signUp(userDto);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginDto LoginDto) {
        return userService.login(LoginDto);
    }

    @PostMapping("/quick")
    public ResponseEntity<?> quickCreate(@RequestBody Map<String, String> req) {
        String name = req.get("name");
        String phone = req.get("phone");
        return userService.quickCreate(name, phone);
    }

    @GetMapping("/wannabuyers")
    public ResponseEntity<?> getWannabuyers() {
        return userService.getWannabuyers();
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createComment(@RequestBody User user) {
        return userService.saveComment(user);
    }

    @GetMapping("/comments")
    public ResponseEntity<?> getAllComments() {
        return userService.getAllComments();
    }





}
