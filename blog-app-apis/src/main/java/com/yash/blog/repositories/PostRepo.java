package com.yash.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.blog.entities.Category;
import com.yash.blog.entities.Post;
import com.yash.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {

	List<Post> findByUser(User user);

	List<Post> findByCategory(Category category);

}
