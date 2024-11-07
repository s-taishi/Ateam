package com.example.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Coupon;
import com.example.demo.repository.Coupon3Repository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Coupon3Service {

	/** DI */
	private final Coupon3Repository coupon3Repository;

	// 取得したクーポンを表示
	public	Coupon couponFindById(int id) {
		return coupon3Repository.couponSelectById(id);
	}

	// クーポンを使用済みにする
	public void couponDelete(int id) {
		coupon3Repository.couponDelete(id);
	}

}
