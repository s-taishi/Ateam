package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.repository.Coupon2Repository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Coupon2Service {
	private final Coupon2Repository coupon2Repository;

	public void couponInsert(String couponType, Long userId) {
		coupon2Repository.couponInsert(couponType, userId);
	}

}