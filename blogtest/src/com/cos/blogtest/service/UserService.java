package com.cos.blogtest.service;

import java.util.List;

import com.cos.blogtest.domain.user.User;
import com.cos.blogtest.domain.user.UserDao;
import com.cos.blogtest.domain.user.dto.JoinReqDto;
import com.cos.blogtest.domain.user.dto.LoginReqDto;

public class UserService {
	private UserDao userDao;
	
	public UserService() {
		userDao = new UserDao();
	}
	
	public int 회원가입(JoinReqDto dto) {
		return userDao.save(dto);
	}
	
	public User 로그인(LoginReqDto dto) {
		return userDao.findByUsernameAndPassword(dto);
	}
	
	public List<User> 목록보기(){
		return userDao.findAll();
	}
	
	public int 유저삭제(int id) {
		return userDao.deleteById(id);
	}
}
