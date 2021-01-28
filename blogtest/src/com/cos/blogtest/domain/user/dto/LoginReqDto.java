package com.cos.blogtest.domain.user.dto;

import lombok.Data;

@Data
public class LoginReqDto {
	private String username;
	private String password;
	private String userRole;
}
