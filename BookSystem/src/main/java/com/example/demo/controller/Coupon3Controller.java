package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.service.Coupon3Service;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon3Controller {

	private final Coupon3Service coupon3Service;

	// クーポン詳細を表示
	@GetMapping("/coupondetail/{id}")
	public String couponDetail(@PathVariable int id, Model model) {
		Coupon coupon = coupon3Service.couponFindById(id);
		if (coupon != null) {
			model.addAttribute("coupon", coupon);
			model.addAttribute("couponType", coupon.getCouponType());
			return "coupondetail"; 
		} else {
			return "lose"; 
		}
	}

	//クーポンを使用する
	@PostMapping("/coupondelete/{id}")
	public String delete(@PathVariable int id) {

		coupon3Service.couponDelete(id);

		return "used" ; 
	}
}
