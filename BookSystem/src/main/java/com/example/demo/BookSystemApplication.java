package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.demo.entity.CouponType;
import com.example.demo.service.CouponRouletteService;

@SpringBootApplication

public class BookSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSystemApplication.class, args);
		//ルーレットを10回回して結果を表示
		for(int i =0;i<10;i++) {
			CouponType result = CouponRouletteService.spinRoulette();
			System.out.println("結果"+(i+1)+":"+(result != null ?  result : "No Coupon"));
		}
	}

}
