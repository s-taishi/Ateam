package com.example.demo.form;

import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * クーポン情報を保持するためのフォームクラス
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CouponForm {

	// クーポンID
	private int id; 

	// ユーザー情報を格納するフィールド
	private User user;     
	
	 // クーポンタイプを格納するフィールド
	private CouponType couponType;

	// ユーザーIDを文字列として取得するためのメソッド
	public String getCouponType() {
		return couponType != null ? couponType.name() : "No Coupon";
	}
}