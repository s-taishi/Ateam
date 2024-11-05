package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Coupon;

//ユーザーネームでの検索
@Mapper
public interface Coupon1Repository {
	// 指定したユーザーIDに基づいてクーポンを検索するメソッド
	 List<Coupon> couponSelectByUserId(@Param("userId") int userId);
}