package com.example.demo.service;

import java.util.Random;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;
import com.example.demo.repository.Coupon2Repository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public  class CouponRouletteService {

	private final Coupon2Repository coupon2Repository ;
public final UserRepository userRepository;	
    private static final Random random = new Random();
    
  
	

//※テスト用機能ここまで


    public  Coupon spinRoulette(@AuthenticationPrincipal UserDetails userDetails) {
    	//UserDetailsからユーザー名を取得
    	String username = userDetails.getUsername(); 
    	
    	
    	//ユーザー名を使ってuser情報を取得
        User curretUser = userRepository.userSelectByUsername(username);
      
    	double randomValue = random.nextDouble();
        //確率の初期値
        double cumulativeProbability = 0.0;
        Coupon coupon = new Coupon();

        for (CouponType type : CouponType.values()) {
            cumulativeProbability += type.getProbability();
            if (randomValue < cumulativeProbability) {
                coupon.setCouponType(type);
                coupon.setUser(curretUser);
                
             // couponType を文字列に変換して挿入
                try {
                	System.out.println(type);
                	System.out.println(curretUser.getId());
                	coupon2Repository.couponInsert(type.name(), curretUser.getId());
                } catch (Exception e) {
                    e.printStackTrace(); // または適切なロギングを行う
                }
                return coupon;
            }
        }
        
    	System.out.println(curretUser.getId());
        return null; // 50%の確率でハズレ
    }
}