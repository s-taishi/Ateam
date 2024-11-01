package com.example.demo.service;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.PlayTime;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RouletteCount {
	
	//DI
	private final PlayTimeService playTimeService;
	
	//ルーレット回数計測　計測以外できないため、他はコントローラーでやってね
	public void playCount() {
		//現在ログイン中のユーザーのルーレットアクセス情報を取得
		PlayTime playTime = playTimeService.playTimeFindById(ConnectUser.id);
		//現在日付を取得
		LocalDate now = LocalDate.now();
		
		//ルーレット情報がないなら
		if(playTime == null) {
			//データベースに新規登録
			PlayTime create = new PlayTime(ConnectUser.id,now,0);
			playTimeService.playTimeInsert(create);
		}else {//情報があれば
			//最後に利用した日が前日なら
			if(playTime.getLastplay().isBefore(now)) {
				playTime.setPlaycount(1);
				playTimeService.playTimeUpdate(playTime);
			}else if(playTime.getPlaycount() <= 3) {//今日かつ利用回数が３を超えないなら
				playTime.setPlaycount(playTime.getPlaycount() + 1);
				playTimeService.playTimeUpdate(playTime);
			}
			
		}
	}

}
