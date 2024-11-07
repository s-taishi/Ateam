package com.example.demo.controller;

import java.time.LocalDate;
import java.util.Iterator;
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
       
        Iterator<Coupon> iterator = coupons.iterator();
        while (iterator.hasNext()) {
            Coupon coupon = iterator.next();
            
            // expirationDateがnullでないかをチェックし、現在の日付より前なら削除
            if (coupon.getExpirationDate() != null && coupon.getExpirationDate().isBefore(LocalDate.now())) {
                iterator.remove();  // 条件に合うクーポンをリストから削除
            }
        }
        
        PlayTime playTime = playTimeService.playTimeFindById(ConnectUser.id);					//ログイン中のユーザー情報がPlayTimeのDBに保存されているか検索
        if(playTime == null) {																	//情報がない場合
        	playTimeService.playTimeInsert(new PlayTime(ConnectUser.id,LocalDate.now(),0));		//ログイン中のユーザーIDと現在の時間、回数（0）の情報を保存
        	playTime = playTimeService.playTimeFindById(ConnectUser.id);
        }
        if(playTime.getLastplay().isBefore(LocalDate.now())) {									//Lastplay（前回の実施時間）が現在日時より以前であれば
        	playTime.setLastplay(LocalDate.now());												//今の日時に情報を変更
    		playTime.setPlaycount(0);															//Playtimeを0に変更
    		playTimeService.playTimeUpdate(playTime);											//DBに変更を更新
        }
        if(playTime.getPlaycount() >= 3) {														//Playtimeが３以上であれば
        	model.addAttribute("count",playTime.getPlaycount());								//countとして回数の情報を渡す。渡さなければcouponlistで抽選へのリンクが表示される
        }
        // 取得したクーポンリストをモデルに追加
        model.addAttribute("coupons", coupons);
        int p = 3-playTime.getPlaycount();														//抽選可能回数を計算
        model.addAttribute("playCount", p);														//couponlistへ情報を渡す
        return "couponlist"; // couponlist.htmlを返す
    }
}
