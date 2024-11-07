package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.repository.Coupon1Repository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class Coupon1Service {

	/** DI */
	private final Coupon1Repository coupon1Repository;

	// 指定したユーザーIDに基づいてクーポンを取得するメソッド
	public List<Coupon> couponFindByUserId(int userId) {
		return coupon1Repository.couponSelectByUserId(userId);
	}
}
