package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface Coupon2Repository {

	void couponInsert(String couponType, int userId);
}