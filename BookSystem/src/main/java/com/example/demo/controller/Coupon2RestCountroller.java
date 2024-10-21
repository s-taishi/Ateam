package com.example.demo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;
import com.example.demo.form.CouponForm;
import com.example.demo.service.CouponRouletteService;

import jakarta.servlet.http.HttpSession;

/*
 * 画面遷移なしにHTMLを動的に動かすため
 * このメソッドだけrestコントローラに分けました
 */
@RestController
public class Coupon2RestCountroller {

	@GetMapping("/couponlot")
	public ResponseEntity<CouponForm> couponLot(HttpSession session) {
		CouponType couponType = CouponRouletteService.spinRoulette();

		// CouponFormのインスタンスを作成
		CouponForm couponForm = new CouponForm();
		couponForm.setCouponType(couponType);

		// セッション情報から現在ログインしているユーザーを取得
		User currentUser = (User) session.getAttribute("currentUser");
		
		//取得したユーザー情報をformのインスタンスに設定する
		couponForm.setUser(currentUser);
		
		/*
		 * ResponseEntityクラスのokメソッドはオブジェクト(ここではCouponForm couponForm)
		 * をhttp200（リクエストが成功したことを示すステータスコード）
		 * のレスポンスボディに含めて返す。
		 * 
		 */
		return ResponseEntity.ok(couponForm);
	}
}
