package com.dreamdev.simple_todo.service;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.dreamdev.simple_todo.model.User;
import com.dreamdev.simple_todo.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository) {
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        //encrypt password before saving the user data
        user.setPassword(passwordEncoder.encode(user.getPassword()));

    
        return userRepository.save(user);
    }

    public void deleteByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        userRepository.delete(user);
    }

    private void updateIfPresent(String value, Consumer<String> setter) {
        Optional.ofNullable(value).filter(s -> !s.isBlank()).ifPresent(setter);
    }

    public void updateUser(User updatedUser) {
        User existingUser = userRepository.findByUsername(updatedUser.getUsername())
                .orElseThrow(() -> new EntityNotFoundException("User Not Found"));

        updateIfPresent(updatedUser.getUsername(), existingUser::setUsername);
        updateIfPresent(updatedUser.getEmail(), existingUser::setEmail);
        updateIfPresent(updatedUser.getFirstName(), existingUser::setFirstName);
        updateIfPresent(updatedUser.getLastName(), existingUser::setLastName);
        Optional.ofNullable(updatedUser.getRole()).ifPresent(existingUser::setRole);


        userRepository.save(existingUser);
    }
}
