package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Coupon;


@Mapper
public interface Coupon1Repository {

	// 指定したユーザーIDのクーポンを全件表示
	List<Coupon> couponSelectByUserId(@Param("userId") int userId);
}