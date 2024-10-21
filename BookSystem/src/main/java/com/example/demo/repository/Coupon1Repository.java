package com.example.demo.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Coupon;

//ユーザーネームでの検索
@Mapper
public interface Coupon1Repository {
	 List<Coupon> couponSelectByUserId(@Param("userId") int userId);
}