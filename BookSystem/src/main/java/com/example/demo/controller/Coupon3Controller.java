package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.entity.Coupon;
import com.example.demo.entity.CouponType;
import com.example.demo.service.Coupon3Service;

import lombok.RequiredArgsConstructor;

/**
 * クーポン詳細ボタンとクーポン使用のコントローラー
 */

@Controller
@RequiredArgsConstructor
public class Coupon3Controller {

	private final Coupon3Service coupon3Service;

	// クーポン詳細を表示
	@GetMapping("/coupondetail/{id}")
	public String couponDetail(@PathVariable int id, Model model) {

		//クーポンidで検索
		Coupon coupon = coupon3Service.couponFindById(id);

		//クーポン情報とクーポンタイプをmodelに格納
		model.addAttribute("coupons", coupon);
		model.addAttribute("couponType", coupon.getCouponType());
		
		return "coupondetail"; //詳細画面

	}

	//クーポンを使用する
	@PostMapping("/coupondelete/{id}")
	public String delete(@PathVariable int id,Model model) {
		
		//クーポンidで検索
		Coupon coupon=coupon3Service.couponFindById(id);
		
		//クーポンを使用（削除）
		coupon3Service.couponDelete(id);
		
		//クーポンタイプによってバーコードを表示
		if(coupon.getCouponType()==CouponType.COUPON_TYPE1) {
			model.addAttribute("image","/images/coupon1.png");
		}else if(coupon.getCouponType()==CouponType.COUPON_TYPE2) {
			model.addAttribute("image","/images/coupon2.png");
		}else if(coupon.getCouponType()==CouponType.COUPON_TYPE3) {
			model.addAttribute("image","/images/coupon3.png");
		}else if(coupon.getCouponType()==CouponType.COUPON_TYPE4) {
			model.addAttribute("image","/images/coupon4.png");
		}

		return "used" ; //使用済み画面
	}
}
