package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.form.CouponForm;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.CouponRouletteService;
import com.example.demo.service.impl.LoginUserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	private final Coupon2Service coupon2Service;
	private final LoginUserDetailsServiceImpl userService;
	private final CouponRouletteService couponRouletteService;
	private final LoginUserDetailsServiceImpl loginUserDetailsServiceImpl;
	
	
	 @GetMapping("/couponlot")
	    public String couponLot(@AuthenticationPrincipal UserDetails userDetails, Model model) {
		 
	        return "couponlot"; // couponlot.htmlを返す
	    }
	 


//クーポン
		@GetMapping("/couponcreate")
		public String couponCreate(@AuthenticationPrincipal UserDetails userDetails ,Model model) {
			//@AuthenticationPrincipalを付加したUserDetailsから
			//ログイン中のユーザー名を取得
			String username = userDetails.getUsername();
	
			//ユーザー名を使ってUser情報を取得
			User currentUser = coupon2Service.userSelectByUsername(userDetails);
		    
			//ルーレットを回す
			Coupon coupon = couponRouletteService.spinRoulette(userDetails);
	
			// CouponFormのインスタンスを作成
			CouponForm couponForm = new CouponForm();
			couponForm.setCouponType(coupon.getCouponType());
			couponForm.setUser(currentUser);
	
			//Modelに格納
			model.addAttribute("couponForm", couponForm);
	
			return "coupondetail"; // coupondetail.htmlを返す
		}
}