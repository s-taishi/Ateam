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

    private final Coupon1Service coupon1Service; // クーポンサービスのインスタンス

    // コンストラクタでクーポンサービスを初期化
    public Coupon1Controller(Coupon1Service coupon1Service) {
        this.coupon1Service = coupon1Service;
    }
		
    // ユーザーのクーポンリストを取得するメソッド
    @GetMapping("/couponlist")
    public String getCouponsByUserId(Model model) {
        // ConnectUserからユーザーIDを取得して、クーポンを取得
        List<Coupon> coupons = coupon1Service.couponFindByUserId(ConnectUser.id);
        
        // 取得したクーポンリストをモデルに追加
        model.addAttribute("coupons", coupons);
        
        return "couponlist"; // couponlist.htmlを返す
    }
}
