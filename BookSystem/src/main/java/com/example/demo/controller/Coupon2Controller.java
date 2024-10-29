package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.service.Coupon2Service;
import com.example.demo.service.CouponRouletteService;
import com.example.demo.service.impl.LoginUserDetailsServiceImpl;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class Coupon2Controller {

	private final Coupon2Service coupon2Service;
	private final LoginUserDetailsServiceImpl userService;
	private final CouponRouletteService couponRouletteService;
	private final LoginUserDetailsServiceImpl loginUserDetailsServiceImpl;

	@GetMapping("/couponlot")
	public String couponLot(@AuthenticationPrincipal UserDetails userDetails, Model model) {

		return "couponlot"; // couponlot.htmlを返す
	}

	@GetMapping("/couponcreate")
	@ResponseBody//returnで値をJSON形式で直接返すアノテーション(Modelよりも動的な値を処理するのに適するらしい)
	public Map<String,String> couponCreate(@AuthenticationPrincipal UserDetails userDetails){
		
		//@AuthenticationPrincipalでログインユーザーのユーザー名を取得
		String username = userDetails.getUsername();
		//ログインユーザーのユーザー名を使ってデータベースからユーザー情報を取得
		User currentUser = coupon2Service.userSelectByUsername(userDetails);
		//ルーレットをまわしてクーポンを取得
		Coupon coupon = couponRouletteService.spinRoulette(userDetails);
		
		/*取得したクーポンのクーポンタイプの名前をString型で取得
		 * (クーポンタイプがnullの時は"はずれ"の文字列)
		 */
		String couponType = (coupon != null) ? coupon.getCouponType().name() : "はずれ";
		
		//オブジェクト登録名とcouponTypeの名前をMapに登録
		Map<String,String> response = new HashMap<>();
		response.put("couponType", couponType);
		
		return response;
				
	}
	

}