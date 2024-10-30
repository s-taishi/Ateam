package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.PlayTime;

@Mapper
public interface PlayTimeRepository {
	
	PlayTime playTimeSelectById(int id);
	
	void playTimeInsert(PlayTime playTime);
	
	void playTimeUpdate(PlayTime playTime);

}
