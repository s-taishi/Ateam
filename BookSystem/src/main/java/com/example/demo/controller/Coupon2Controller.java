package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.form.CouponForm;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.CouponRouletteService;
import com.example.demo.service.impl.LoginUserDetailsServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	private final Coupon2Service coupon2service;
	private final LoginUserDetailsServiceImpl userService;
	private final CouponRouletteService couponRouletteService;

	@GetMapping("/couponcreate")
	public String couponCreate(Model model, HttpSession session) {
		// セッションから現在のユーザーを取得
		User currentUser = (User) session.getAttribute("currentUser");
		
		 // ユーザーが存在しない場合の処理
	    if (currentUser == null) {
	        return "redirect:/login"; // 適切なリダイレクト先を設定
	    }
	    
		Coupon coupon = couponRouletteService.spinRoulette(currentUser);

		// CouponFormのインスタンスを作成
		CouponForm couponForm = new CouponForm();
		couponForm.setCouponType(coupon.getCouponType());

		
		couponForm.setUser(currentUser);

		model.addAttribute("couponForm", couponForm);

		return "coupondetail";
	}
}