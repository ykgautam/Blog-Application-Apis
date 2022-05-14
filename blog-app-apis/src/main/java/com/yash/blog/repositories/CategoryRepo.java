package com.yash.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.blog.entities.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}
