package com.example.demo.service;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.repository.Coupon2Repository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class Coupon2Service {
	private final Coupon2Repository coupon2Repository;
private final UserRepository userRepository;
	public void couponInsert(Coupon coupon) {
		coupon2Repository.couponInsert(coupon);
	}

	//データベースからログイン中のUserインスタンスを取得
	public User userSelectByUsername(@AuthenticationPrincipal UserDetails userDetails) {
		
		//現在ログイン中のユーザーのusernameを取得
		String username =userDetails.getUsername();
		
		//データベースで該当するusernameを持つユーザーデータを取得
	User currentUser = userRepository.userSelectByUsername(username);
	
	return currentUser;
	}
}