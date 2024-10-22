package com.example.demo.service;

import java.util.Random;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;
import com.example.demo.repository.Coupon2Repository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class CouponRouletteService {

	private final Coupon2Repository coupon2Repository ;
	
    private static final Random random = new Random();

    

    public  Coupon spinRoulette(User user) {
    	//sessionが切れている場合はエラーを返す
    	if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        double randomValue = random.nextDouble();
        
        //確率の初期値
        double cumulativeProbability = 0.0;
        Coupon coupon = new Coupon();

        for (CouponType type : CouponType.values()) {
            cumulativeProbability += type.getProbability();
            if (randomValue < cumulativeProbability) {
                coupon.setCouponType(type);
                coupon.setUser(user);
             // couponType を文字列に変換して挿入
                coupon2Repository.couponInsert(type.name(), (long) user.getId());
                return coupon;
            }
        }

        return null; // 50%の確率でハズレ
    }
}