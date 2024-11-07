package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.PlayTime;

@Mapper
public interface PlayTimeRepository {
	//useridからplaytimeを検索
	PlayTime playTimeSelectById(int id);
	//新規登録
	void playTimeInsert(PlayTime playTime);
	//更新
	void playTimeUpdate(PlayTime playTime);

}
