package com.yash.blog.payloads;

import java.util.Date;

import javax.validation.constraints.NotEmpty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {

	private Integer postId;
	
	@NotEmpty
	private String title;

	@NotEmpty
	private String content;

	private String imageName;
	
	private Date addedDate;
	
	private CategoryDto category;
	
	private UserDto user;
}
