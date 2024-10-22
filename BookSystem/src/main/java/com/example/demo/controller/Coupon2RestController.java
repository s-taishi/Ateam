package com.example.demo.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.CouponRouletteService;
import com.example.demo.service.impl.LoginUserDetailsServiceImpl;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

/*
 * 画面遷移なしにHTMLを動的に動かすため
 * このメソッドだけrestコントローラに分けました
 */
@RestController
@RequiredArgsConstructor
public class Coupon2RestController {
    private final Coupon2Service coupon2service;
    private final LoginUserDetailsServiceImpl userService;
    private final CouponRouletteService couponRouletteService;
    
    

    @GetMapping("/couponlot")
    public String couponCreate(Model model, HttpSession session) {
//        User currentUser = (User) session.getAttribute("currentUser");
    	User currentUser = couponRouletteService.getUserByUsername("user");
        Coupon coupon = couponRouletteService.spinRoulette(currentUser);

        model.addAttribute("couponForm", coupon);
        return "couponlot"; // couponlot.htmlを返す
    }
}