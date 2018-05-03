package com.process.dao;

import com.process.entity.User;

public interface UserDao {
	public User selectUserById(Integer userId);
}