package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Coupon;
@Mapper
@Repository
public interface Coupon2Repository {

	void couponInsert(Coupon coupon);
}