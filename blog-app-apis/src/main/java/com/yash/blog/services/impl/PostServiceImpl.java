package com.yash.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.yash.blog.entities.Category;
import com.yash.blog.entities.Post;
import com.yash.blog.entities.User;
import com.yash.blog.exceptions.ResourceNotFoundException;
import com.yash.blog.payloads.PostDto;
import com.yash.blog.payloads.PostResponse;
import com.yash.blog.repositories.CategoryRepo;
import com.yash.blog.repositories.PostRepo;
import com.yash.blog.repositories.UserRepo;
import com.yash.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private UserRepo userRepo;

	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {

		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " user Id", userId));

		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", " category Id", categoryId));

		Post post = this.modelMapper.map(postDto, Post.class);

		post.setImageName("default.png");
		post.setAddedDate(new Date());

		post.setUser(user);
		post.setCategory(category);

		Post savedPost = this.postRepo.save(post);
		return this.modelMapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {

		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));

		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post updatedPOst = this.postRepo.save(post);
		return this.modelMapper.map(updatedPOst, PostDto.class);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {

		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageable);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());

		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setTotalElement(pagePost.getTotalElements());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());

		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post ", "Post Id", postId));
		PostDto postDto = this.modelMapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", " Post Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category ", "category Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User ", "User Id", userId));

		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map((post) -> this.modelMapper.map(post, PostDto.class))
				.collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
