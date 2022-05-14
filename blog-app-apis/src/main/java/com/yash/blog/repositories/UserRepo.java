package com.yash.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.blog.entities.User;

public interface UserRepo extends JpaRepository<User, Integer> {

}
