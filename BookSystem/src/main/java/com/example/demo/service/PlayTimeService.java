package com.example.demo.service;

import org.springframework.stereotype.Service;

import com.example.demo.entity.PlayTime;
import com.example.demo.repository.PlayTimeRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlayTimeService {

	private final PlayTimeRepository playTimeRepository;
	
	public PlayTime playTimeFindById(int id) {
		return playTimeRepository.playTimeSelectById(id);
	}
	
	public void playTimeInsert(PlayTime playTime) {
		playTimeRepository.playTimeInsert(playTime);
	}
	
	public void playTimeUpdate(PlayTime playTime) {
		playTimeRepository.playTimeUpdate(playTime);
	}
}
