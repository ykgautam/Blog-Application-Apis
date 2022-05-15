package com.yash.blog.payloads;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {

	private int id;

	@NotEmpty // used for validation restricting empty / null values
	@Size(min = 4, message = "Username must be minimum of 4 characters")
	private String name;

	@Email(message = "Email addres is not valid !!")
	private String email;

	@NotEmpty
	@Size(min = 3, max = 10, message = "password must be minimum 3 chars and max 10 chars")
	private String password;

	@NotEmpty
	private String about;
}
