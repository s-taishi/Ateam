package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.Coupon3Service;
import com.example.demo.service.CouponRouletteService;
import com.example.demo.service.impl.LoginUserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	private final Coupon2Service coupon2Service;
	private final Coupon3Service coupon3Service;
	private final LoginUserDetailsServiceImpl userService;
	private final CouponRouletteService couponRouletteService;
	private final LoginUserDetailsServiceImpl loginUserDetailsServiceImpl;

	//ルーレットの画面を表示
	@GetMapping("/couponlot")
	public String couponLot(@AuthenticationPrincipal UserDetails userDetails, Model model) {

		return "couponlot"; // couponlot.htmlを返す
	}

	@GetMapping("/couponcreate")
	public ResponseEntity<Map<String, Object>> couponCreate(@AuthenticationPrincipal UserDetails userDetails) {
	    User currentUser = coupon2Service.userSelectByUsername(userDetails);
	    Coupon coupon = couponRouletteService.spinRoulette(userDetails);

	    Map<String, Object> response = new HashMap<>();
	    if (coupon == null || coupon.getCouponType() == null) {
	        response.put("coupon", null);
	        response.put("couponType", null);
	    } else {
	        response.put("coupon", coupon); // クーポンオブジェクトを返す
	        response.put("couponType", coupon.getCouponType().name());
	        response.put("couponId", coupon.getId()); // クーポンIDを返す
	    }

	    return ResponseEntity.ok(response);
	}
	@GetMapping("/couponresult")
	public String couponResult(
	        @AuthenticationPrincipal UserDetails userDetails,
	        @RequestParam int id,
	        Model model) {
	    // クーポンIDを使ってデータベースからクーポンを取得
	    Coupon coupon = coupon3Service.couponFindById(id); // サービスクラスにメソッドを追加

	    if (coupon == null) {
	        // クーポンが見つからない場合の処理
	        model.addAttribute("error", "クーポンが見つかりません。");
	        return "error"; // エラーページにリダイレクト
	    }

	    // クーポンのタイプをモデルに追加
	    CouponType couponType = coupon.getCouponType();

	    model.addAttribute("couponId", id);//クーポンID
	    model.addAttribute("couponType", couponType);//クーポンタイプ
	    model.addAttribute("coupons", coupon); //クーポンインスタンス

	    return "coupondetail"; // クーポン詳細ページを返す
	}
}