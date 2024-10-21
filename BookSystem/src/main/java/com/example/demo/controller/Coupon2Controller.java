package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.CouponType;
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

	@GetMapping("/couponcreate")
	public String couponCreate(Model model, HttpSession session) {
		CouponType couponType = CouponRouletteService.spinRoulette();

		// CouponFormのインスタンスを作成
		CouponForm couponForm = new CouponForm();
		couponForm.setCouponType(couponType);

		// セッションから現在のユーザーを取得
		User currentUser = (User) session.getAttribute("currentUser");
		couponForm.setUser(currentUser);

		model.addAttribute("couponForm", couponForm);

		return "coupondetail";
	}
}