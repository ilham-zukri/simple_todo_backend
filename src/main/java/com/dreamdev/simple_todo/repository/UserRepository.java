package com.dreamdev.simple_todo.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dreamdev.simple_todo.model.User;

public interface UserRepository extends JpaRepository<User, UUID> {

}
