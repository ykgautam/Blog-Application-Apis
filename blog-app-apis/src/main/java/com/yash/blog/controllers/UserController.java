package com.yash.blog.controllers;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yash.blog.payloads.ApiResponse;
import com.yash.blog.payloads.UserDto;
import com.yash.blog.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	@PostMapping("/")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
		UserDto createdUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createdUserDto, HttpStatus.CREATED);
	}

	@PutMapping("/{userId}")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable("userId") Integer uid) {
		UserDto updateUser = this.userService.updateUser(userDto, uid);
		return ResponseEntity.ok(updateUser);
	}

	// access for  only ADMIN user
	@DeleteMapping("/{userId}")
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid) {
		this.userService.deleteUser(uid);
//		return  ResponseEntity.ok(Map.of("message","User Deleted Successfully"));
//		return new ResponseEntity(Map.of("message","User Deleted Successfully"),HttpStatus.OK);
		return new ResponseEntity<ApiResponse>(new ApiResponse("User deleted Successfully", true), HttpStatus.OK);
	}

	@GetMapping("/")
	public ResponseEntity<List<UserDto>> getAllUser() {
		return ResponseEntity.ok(this.userService.getAllUsers());
	}

	@GetMapping("/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable("userId") Integer userId) {
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}

}
