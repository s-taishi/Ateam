package com.example.demo.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.Coupon;
import com.example.demo.service.Coupon1Service;	

@Controller
public class Coupon1Controller {

	private final Coupon1Service coupon1Service;

	public Coupon1Controller(Coupon1Service coupon1Service) {
		this.coupon1Service = coupon1Service;
	}
		
	@GetMapping("/couponlist")
	public String getCouponsByUserId(Model model) {
	    List<Coupon> coupons = coupon1Service.couponFindByUserId(ConnectUser.id);
	    model.addAttribute("coupons", coupons);
	    return "couponlist"; // couponlist.htmlを返す
	}
}
