package com.yash.blog.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.blog.entities.Comment;
import com.yash.blog.entities.Post;
import com.yash.blog.exceptions.ResourceNotFoundException;
import com.yash.blog.payloads.CommentDto;
import com.yash.blog.repositories.CommentRepo;
import com.yash.blog.repositories.PostRepo;
import com.yash.blog.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private CommentRepo commentRepo;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "postId", postId));

		Comment comment = this.modelMapper.map(commentDto, Comment.class);
		comment.setPost(post);

		Comment savedComment = this.commentRepo.save(comment);
		return this.modelMapper.map(savedComment, CommentDto.class);
	}

	@Override
	public void deleteComment(Integer commentd) {
		Comment comment = this.commentRepo.findById(commentd)
				.orElseThrow(() -> new ResourceNotFoundException("Comment  ", "commentId", commentd));
		this.commentRepo.delete(comment);
	}

}
