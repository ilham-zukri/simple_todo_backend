package com.dreamdev.simple_todo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.dreamdev.simple_todo.model.User;
import com.dreamdev.simple_todo.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }

    public User createUser(User user){
        return userRepository.save(user);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }
}
