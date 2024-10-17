package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.service.Coupon2Service;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	//DI
	private final Coupon2Service coupon2service;
	
	@GetMapping("/couponlot")
	public String couponLot() {
		return "couponlot";
	}
	
	@GetMapping("/couponcreate")
	public String couponCreate(Model model) {
		/*
		 * ここにサービスでの抽選結果のデータを
		 * addAttributeでmodelに格納
		 */
		return "coupondetail";
	}
}
