package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.Coupon3Service;
import com.example.demo.service.CouponRouletteService;
import com.example.demo.service.impl.LoginUserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	private final Coupon2Service coupon2Service;
	private final Coupon3Service coupon3Service;
	private final LoginUserDetailsServiceImpl userService;
	private final CouponRouletteService couponRouletteService;
	private final LoginUserDetailsServiceImpl loginUserDetailsServiceImpl;

	//ルーレットの画面を表示
	@GetMapping("/couponlot")
	public String couponLot(@AuthenticationPrincipal UserDetails userDetails, Model model) {

		return "couponlot"; // couponlot.htmlを返す
	}

	//ルーレットを回す処理
	@GetMapping("/couponcreate")
	public String couponCreate(
	        @AuthenticationPrincipal UserDetails userDetails,
	        Model model) {
	    // ユーザー情報を取得
	    User currentUser = coupon2Service.userSelectByUsername(userDetails);
	    // ルーレットを回してクーポンを取得
	    Coupon coupon = couponRouletteService.spinRoulette(userDetails);

	    // クーポン情報をモデルに追加
	    model.addAttribute("coupon", coupon); // Couponオブジェクトをモデルに追加

	    return "couponlot"; // couponlot.htmlを返す
	}

	@GetMapping("/couponresult")
	public String couponResult(
	        @AuthenticationPrincipal UserDetails userDetails,
	        @RequestParam int id,
	        Model model) {
	    CouponType couponType = null; 
	    System.out.println("遷移ボタンを押下した時のID: " + id);

	    // IDに応じてcouponTypeを設定
	    switch (id) {
	        case 1:
	            couponType = CouponType.COUPON_TYPE1;
	            break;
	        case 2:
	            couponType = CouponType.COUPON_TYPE2;
	            break;
	        case 3:
	            couponType = CouponType.COUPON_TYPE3;
	            break;
	        case 4:
	            couponType = CouponType.COUPON_TYPE4;
	            break;
	        default:
	            System.out.println("無効なクーポンID: " + id);
	            break;
	    }

	    System.out.println("取得したクーポンタイプ: " + couponType); // couponTypeをログに出力

	    model.addAttribute("couponId", id); // クーポンIDをモデルに追加
	    model.addAttribute("couponType", couponType); // couponTypeをモデルに追加

	    return "coupondetail"; // IDを含むパスで遷移
	}

}