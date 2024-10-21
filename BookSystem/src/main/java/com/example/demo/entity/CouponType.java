package com.example.demo.entity;

public enum CouponType {
	//各定数にフィールドとして確率の情報を付加
	COUPON_TYPE1(0.05),//5%
	COUPON_TYPE2(0.05),//5%
	COUPON_TYPE3(0.20),//20%
	COUPON_TYPE4(0.20);//20%

	
	private final double probability;
	
	//コンストラクタ
	CouponType(double probability) {
	this.probability=probability;
}

	public double getProbability() {
		return probability;
	}
	


}