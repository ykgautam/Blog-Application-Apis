package com.yash.blog.services;

import java.util.List;

import com.yash.blog.entities.Post;
import com.yash.blog.payloads.PostDto;
import com.yash.blog.payloads.PostResponse;

public interface PostService {

	//	create
	PostDto createPost(PostDto postDto,Integer userId,Integer categoryId);

	// update
	PostDto updatePost(PostDto postDto, Integer postId);

	//	get all Posts
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize,String sortBy, String sortDir);

	//	get
	PostDto getPostById(Integer postId);

	//	delete
	void deletePost(Integer postId);
	
	//	get all posts by category
	List<PostDto> getPostsByCategory(Integer categoryId);
	
	//	get all posts by user
	List<PostDto> getPostsByUser(Integer userId);
	
	//	search posts
	List<PostDto> searchPosts(String keyword);
}
