package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Coupon;
import com.example.demo.repository.Coupon2Repository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Coupon2Service {
	private final Coupon2Repository coupon2Repository;

	public void couponInsert(Coupon coupon) {
		coupon2Repository.couponInsert(coupon);
	}
	public Coupon couponFindByMaxId() {
		return coupon2Repository.couponSelectByMaxId();
	}
}