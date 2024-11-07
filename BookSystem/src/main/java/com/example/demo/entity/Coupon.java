package com.example.demo.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * クーポン情報を格納するエンティティ
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coupon {

	// クーポンID
	private int id;

	// クーポンタイプ
	private CouponType couponType;

	// Userオブジェクト
	private User user;

	// クーポンの取得日
	private LocalDate issueDate;

	// 有効期限
	private LocalDate expirationDate;


	// 有効期限を設定するためのコンストラクタ
	public Coupon(CouponType couponType, User user) {
		this.couponType = couponType;
		this.user = user;
		this.issueDate = LocalDate.now(); // クーポン取得日を現在日付に設定
		this.expirationDate = issueDate.plusMonths(3); // 有効期限を3か月後に設定
	}
}