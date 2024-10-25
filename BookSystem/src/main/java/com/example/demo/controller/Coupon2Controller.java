package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
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

	//ルーレットの画面を表示
	@GetMapping("/couponlot")
	public String couponLot(@AuthenticationPrincipal UserDetails userDetails, Model model) {

		return "couponlot"; // couponlot.htmlを返す
	}

	//ルーレットを回す処理

	@GetMapping("/couponcreate")
	@ResponseBody //取得したクーポンの情報をJSON形式で返すアノテーション
	public ResponseEntity<Map<String, String>> couponCreate(@AuthenticationPrincipal UserDetails userDetails,
			Model model) {

		/*@AuthenticationPrincipalを付加したUserDetailsから
			ログイン中のユーザー名を取得*/
		String username = userDetails.getUsername();

		//ログイン中のユーザーのユーザー名を使ってデータベースからUser情報を取得
		User currentUser = coupon2Service.userSelectByUsername(userDetails);

		//ルーレットを回してクーポンを取得
		Coupon coupon = couponRouletteService.spinRoulette(userDetails);

		//取得したクーポン名を取得
		String couponType = (coupon != null) ? coupon.getCouponType().name() : "はずれ";

		//クーポン情報をJSON形式で返すためのマップを作成
		Map<String, String> response = new HashMap<>();
		response.put("couponType", couponType);//Mapにクーポン名を追加

		return ResponseEntity.ok(response);//JSONを返す

	}
}