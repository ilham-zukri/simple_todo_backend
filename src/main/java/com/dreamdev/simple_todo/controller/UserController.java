package com.dreamdev.simple_todo.controller;

import com.dreamdev.simple_todo.model.User;
import com.dreamdev.simple_todo.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    @DeleteMapping
    public ResponseEntity<Map<String, String>> deleteUserByUsername(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        userService.deleteByUsername(username);
        return ResponseEntity.ok(Map.of("message", "User deleted successfully"));
    }
    
}
