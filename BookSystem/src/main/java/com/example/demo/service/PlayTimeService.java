package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PlayTime;
import com.example.demo.repository.PlayTimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayTimeService {

	/** DI */
	private final PlayTimeRepository playTimeRepository;

	// useridからplaytimeを検索
	public PlayTime playTimeFindById(int id) {
		return playTimeRepository.playTimeSelectById(id);
	}

	// プレイ回数の新規登録
	public void playTimeInsert(PlayTime playTime) {
		playTimeRepository.playTimeInsert(playTime);
	}

	// プレイ回数の更新
	public void playTimeUpdate(PlayTime playTime) {
		playTimeRepository.playTimeUpdate(playTime);
	}
}
