package com.example.demo.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ルーレットのプレイ回数を設定するためのエンティティ
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlayTime {

	// 主キー
	private int id;

	// 前回のプレイ日
	private LocalDate lastplay;

	// プレイ回数
	private int playcount;

}
