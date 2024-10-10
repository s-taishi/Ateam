package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRepository {
//usernameによるNameユーザー検索
	User userSelectByUsername(@Param("username")String username);
	
	//ユーザー新規登録
	void userInsert(User user);
}
