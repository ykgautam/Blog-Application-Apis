package com.yash.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.yash.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
