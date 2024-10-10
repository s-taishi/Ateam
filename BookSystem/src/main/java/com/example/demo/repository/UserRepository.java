package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.User;

@Mapper
public interface UserRepository {
//usernameによるユーザー検索
	User userSelectByUsername(@Param("userName")String userName);
	
	//ユーザー新規登録
	void userInsert(User user);
}
