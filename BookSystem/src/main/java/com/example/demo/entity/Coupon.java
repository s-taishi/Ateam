package com.example.demo.entity;

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

	// クーポンの種類
	private CouponType couponType; 
	
	// Userオブジェクト
	private User user; 
}
