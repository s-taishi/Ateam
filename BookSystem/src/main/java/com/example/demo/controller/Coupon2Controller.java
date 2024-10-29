package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

<<<<<<< HEAD
import org.springframework.http.ResponseEntity;
=======
>>>>>>> branch 'master' of https://github.com/s-taishi/Ateam.git
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
<<<<<<< HEAD
import org.springframework.web.bind.annotation.RequestParam;
=======
import org.springframework.web.bind.annotation.ResponseBody;
>>>>>>> branch 'master' of https://github.com/s-taishi/Ateam.git

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
<<<<<<< HEAD

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
=======
>>>>>>> branch 'master' of https://github.com/s-taishi/Ateam.git

<<<<<<< HEAD
	    return ResponseEntity.ok(response);
	}
	@GetMapping("/couponresult")
	public String couponResult(
	        @AuthenticationPrincipal UserDetails userDetails,
	        @RequestParam int id,
	        Model model) {
	    // クーポンIDを使ってデータベースからクーポンを取得
	    Coupon coupon = coupon3Service.couponFindById(id); // サービスクラスにメソッドを追加
=======
	@GetMapping("/couponlot")
	public String couponLot(@AuthenticationPrincipal UserDetails userDetails, Model model) {
>>>>>>> branch 'master' of https://github.com/s-taishi/Ateam.git

<<<<<<< HEAD
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
=======
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
	

>>>>>>> branch 'master' of https://github.com/s-taishi/Ateam.git
}