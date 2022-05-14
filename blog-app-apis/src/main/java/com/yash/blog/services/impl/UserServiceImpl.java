package com.yash.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yash.blog.entities.User;
import com.yash.blog.exceptions.ResourceNotFoundException;
import com.yash.blog.payloads.UserDto;
import com.yash.blog.repositories.UserRepo;
import com.yash.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepo userRepo;

	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.dtoToUser(userDto);
		User saveUser = this.userRepo.save(user);
		return this.userToDto(saveUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id", userId));
		user.setEmail(userDto.getEmail());
		user.setName(userDto.getName());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User updatedUser = this.userRepo.save(user);
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id", userId));
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " Id", userId));
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user=this.modelMapper.map(userDto, User.class);
//		User user = new User();
//		user.setId(userDto.getId());
//		user.setEmail(userDto.getEmail());
//		user.setName(userDto.getName());
//		user.setPassword(userDto.getPassword());
//		user.setAbout(userDto.getAbout());
		return user;
	}

	private UserDto userToDto(User user) {
		UserDto userDto=this.modelMapper.map(user, UserDto.class); // used to map one model to another model
//		UserDto userDto = new UserDto();
//		userDto.setId(user.getId());
//		userDto.setEmail(user.getEmail());
//		userDto.setName(user.getName());
//		userDto.setPassword(user.getPassword());
//		userDto.setAbout(user.getAbout());
		return userDto;
	}

}
