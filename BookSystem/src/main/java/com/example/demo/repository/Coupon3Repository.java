package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Coupon;

@Mapper
public interface Coupon3Repository {

	// 取得したクーポンを表示
	Coupon couponSelectById(@Param("id") int id);

	// クーポンを使用済みにする
	void couponDelete(@Param("id") int id);
}