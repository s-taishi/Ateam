package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.Coupon;
import com.example.demo.entity.PlayTime;
import com.example.demo.service.Coupon1Service;
import com.example.demo.service.PlayTimeService;

import lombok.RequiredArgsConstructor;	

@Controller
@RequiredArgsConstructor
public class Coupon1Controller {

    private final Coupon1Service coupon1Service; // クーポンサービスのインスタンス
    private final PlayTimeService playTimeService;

		
    // ユーザーのクーポンリストを取得するメソッド
    @GetMapping("/couponlist")
    public String getCouponsByUserId(Model model) {
        // ConnectUserからユーザーIDを取得して、クーポンを取得
        List<Coupon> coupons = coupon1Service.couponFindByUserId(ConnectUser.id);
        
        
        PlayTime playTime = playTimeService.playTimeFindById(ConnectUser.id);
        if(playTime == null) {
        	playTimeService.playTimeInsert(new PlayTime(ConnectUser.id,LocalDate.now(),0));
        	playTime = playTimeService.playTimeFindById(ConnectUser.id);
        }
        if(playTime.getLastplay().isBefore(LocalDate.now())) {
        	playTime.setLastplay(LocalDate.now());
    		playTime.setPlaycount(0);
    		playTimeService.playTimeUpdate(playTime);
        }
        if(playTime.getPlaycount() >= 3) {
        	model.addAttribute("count",playTime.getPlaycount());
        }
        // 取得したクーポンリストをモデルに追加
        model.addAttribute("coupons", coupons);
        
        return "couponlist"; // couponlist.htmlを返す
    }
}
