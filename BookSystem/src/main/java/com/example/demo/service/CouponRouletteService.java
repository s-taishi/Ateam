package com.example.demo.service;

import java.util.Random;

import com.example.demo.entity.CouponType;

public class CouponRouletteService {

	//Randomクラスをインスタンス化
	private static final Random random = new Random();

	public static CouponType spinRoulette() {
		/* 
		*nextDoubleメソッドは0.0以上1.0未満の
		*double型の数値をランダムで生成する
		*/
		double randomValue = random.nextDouble();

		double cumulativeProbability = 0.0;//確率を累積する変数(初期値は0)
		/*
		 * //各クーポンの確率を順に加算し、
		 * ランダム生成された確率が、累積された各クーポンの確率の合計を
		 * 下回った時点のCouponType型のインスタンスを返す
		 */
		for (CouponType type : CouponType.values()) {
			cumulativeProbability += type.getProbability();
			if (randomValue < cumulativeProbability) {
				return type;
			}

		}
		//50%の確率でハズレ
		return null;
	}
}
