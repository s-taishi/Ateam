package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.Coupon;

@Mapper
@Repository

public interface Coupon2Repository {

	// クーポンをデータベースに登録
	void couponInsert(Coupon coupon);

	// 発行されているクーポンIDの最大値を取得する
	Coupon couponSelectByMaxId();
}