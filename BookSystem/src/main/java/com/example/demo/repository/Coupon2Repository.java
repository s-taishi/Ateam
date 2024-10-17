package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.demo.entity.Coupon;
@Mapper
public interface Coupon2Repository {

	void couponInsert(Coupon coupon);
}
