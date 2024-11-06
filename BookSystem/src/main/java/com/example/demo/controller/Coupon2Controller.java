package com.example.demo.controller;

import java.util.Random;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.Coupon;
import com.example.demo.entity.CouponType;
import com.example.demo.entity.PlayTime;
import com.example.demo.service.BookService;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.Coupon3Service;
import com.example.demo.service.PlayTimeService;
import com.example.demo.service.RouletteCount;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	private final BookService bookService;
	private final Coupon2Service coupon2Service;
	private final Coupon3Service coupon3service;
	private final RouletteCount rouletteCount;
	 private final PlayTimeService playTimeService;

	@GetMapping("/couponlot")
	public String couponLot(Model model) {
	    rouletteCount.playCount();						//playcountを追加していく
	    PlayTime playTime = playTimeService.playTimeFindById(ConnectUser.id);
	    Random rand = new Random();
	    int p = rand.nextInt(100);
	   
	    // クーポンIDをランダムに決定
	    int couponId = 5; // デフォルトは「はずれ」
	    if(playTime.getPlaycount()==4) {				//playcountの最終値は４のため４であれば外れに設定
	    	couponId=5;
	    	 model.addAttribute("num", couponId);
	 	    return "couponlot2";
	    }
	    
		if (p < 5) {
	        coupon2Service.couponInsert(new Coupon(CouponType.COUPON_TYPE1, bookService.userFindByUserName(ConnectUser.username)));
	        couponId = 1;
	    } else if (p < 10) {
	        coupon2Service.couponInsert(new Coupon(CouponType.COUPON_TYPE2, bookService.userFindByUserName(ConnectUser.username)));
	        couponId = 2;
	    } else if (p < 30) {
	        coupon2Service.couponInsert(new Coupon(CouponType.COUPON_TYPE3, bookService.userFindByUserName(ConnectUser.username)));
	        couponId = 3;
	    } else if (p < 50) {
	        coupon2Service.couponInsert(new Coupon(CouponType.COUPON_TYPE4, bookService.userFindByUserName(ConnectUser.username)));
	        couponId = 4;
	    }
	    // クーポンIDをモデルに追加してHTMLに渡す
	    model.addAttribute("num", couponId);
	   
	    return "couponlot2";
	}

	
	@GetMapping("/detail/{id}")
	public String couponResult(@PathVariable int id, Model model) {
		if(id == 5) {
			model.addAttribute("miss","残念、はずれ");
			return "coupondetail";
		}
		Coupon coupon = coupon2Service.couponFindByMaxId();
		Coupon coupon2 = coupon3service.couponFindById(coupon.getId());
		model.addAttribute("coupons", coupon2);
		model.addAttribute("couponType", coupon2.getCouponType());
		return "coupondetail";
	}
}