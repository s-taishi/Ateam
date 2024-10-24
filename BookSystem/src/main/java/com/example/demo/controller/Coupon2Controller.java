package com.example.demo.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.User;
import com.example.demo.form.CouponForm;
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
		public String couponCreate(@AuthenticationPrincipal UserDetails userDetails ,Model model) {
			//@AuthenticationPrincipalを付加したUserDetailsから
			
			//ログイン中のユーザー名を取得
			String username = userDetails.getUsername();
	
			//ログイン中のユーザーのユーザー名を使ってデータベースからUser情報を取得
			User currentUser = coupon2Service.userSelectByUsername(userDetails);
		    
			//ルーレットを回す
			Coupon coupon = couponRouletteService.spinRoulette(userDetails);
			
			//取得したクーポン名を取得
			String couponType = (coupon != null)?coupon.getCouponType().name():"はずれ";
			
			// CouponFormのインスタンスを作成
			CouponForm couponForm = new CouponForm();
			couponForm.setCouponType(coupon!= null?coupon.getCouponType():null);//nullの場合はCouponTypeにnullを設定
			couponForm.setUser(currentUser);
	
			//Modelに格納
			model.addAttribute("couponForm", couponForm );
			model.addAttribute("couponResult",couponType);
			return "coupondetail"; // coupondetail.htmlを返す
		}
}