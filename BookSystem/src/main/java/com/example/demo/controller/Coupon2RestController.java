package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.service.CouponRouletteService;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2RestController {

    private final CouponRouletteService couponRouletteService;

    @GetMapping("/couponlot")
    public String couponCreate(Model model, HttpSession session) {
        // Userをセッションから取得（現在は固定のユーザー名を使用）
        User currentUser = couponRouletteService.getUserByUsername("user");
        Coupon coupon = couponRouletteService.spinRoulette(currentUser);
        
        // クーポンがnullの場合は、loss属性を追加しない
        if (coupon != null) {
            model.addAttribute("couponForm", coupon);
        } else {
            model.addAttribute("loss", true);  // はずれの場合
        }
        
        return "couponlot"; // couponlot.htmlを返す
    }
}
