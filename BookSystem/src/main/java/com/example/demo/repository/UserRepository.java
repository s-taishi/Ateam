package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.User;

@Mapper
public interface UserRepository {
//userNameによるユーザー検索
	User userSelectByUserName(@Param("userName")String userName);
	
	//ユーザー新規登録
	void userInsert(User user);
	
	//ユーザー情報が既に存在するか判定
	boolean userExistsByUserName(@Param("userName")String userName);
}
