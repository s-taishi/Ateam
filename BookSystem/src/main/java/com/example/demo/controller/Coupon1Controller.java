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

	/** DI */
	private final Coupon1Service coupon1Service; 
	private final PlayTimeService playTimeService;


	// ユーザーのクーポンリストを取得するメソッド
	@GetMapping("/couponlist")
	public String getCouponsByUserId(Model model) {

		// ConnectUserからユーザーIDを取得して、クーポンを取得
		List<Coupon> coupons = coupon1Service.couponFindByUserId(ConnectUser.id);

		// 有効期限切れのクーポンを削除する処理
		Iterator<Coupon> iterator = coupons.iterator();
		while (iterator.hasNext()) {
			Coupon coupon = iterator.next();

			// expirationDateがnullでないかをチェックし、現在の日付より前なら削除
			if (coupon.getExpirationDate() != null && coupon.getExpirationDate().isBefore(LocalDate.now())) {
				iterator.remove();  // 条件に合うクーポンをリストから削除
			}
		}

		/* ルーレットを回せるのが1日3回までにする処理 **/

		// ログイン中のユーザー情報がPlayTimeのDBに保存されているか検索
		PlayTime playTime = playTimeService.playTimeFindById(ConnectUser.id);

		// 情報がない場合ログイン中のユーザーIDと現在の時間、回数（0）の情報を保存
		if(playTime == null) {
			playTimeService.playTimeInsert(new PlayTime(ConnectUser.id,LocalDate.now(),0));		
			playTime = playTimeService.playTimeFindById(ConnectUser.id);
		}

		// Lastplay（前回の実施時間）が現在日時より以前であれば
		if(playTime.getLastplay().isBefore(LocalDate.now())) {

			// 今の日時に情報を変更
			playTime.setLastplay(LocalDate.now());

			// Playtimeを0に変更
			playTime.setPlaycount(0);

			// DBに変更を更新
			playTimeService.playTimeUpdate(playTime);
		}


		// Playtimeが３以上であればcountとして回数の情報を渡す。渡さなければcouponlistで抽選へのリンクが表示される
		if(playTime.getPlaycount() >= 3) {			
			model.addAttribute("count",playTime.getPlaycount());								
		}

		// 取得したクーポンリストをモデルに追加
		model.addAttribute("coupons", coupons);

		// 抽選可能回数を計算
		int p = 3-playTime.getPlaycount();

		// couponlistへ情報を渡す
		model.addAttribute("playCount", p);

		return "couponlist"; // couponlist.htmlを返す
	}
}
