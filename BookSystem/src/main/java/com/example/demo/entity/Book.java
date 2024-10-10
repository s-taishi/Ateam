package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.Data;

	@Data
	public class Book {
		private User user; // bookとuser の１：１の関係
		private int id; // 予約ID
		private LocalDate date; // 日付
		private LocalTime time; // 時間
		private int count; // 人数
		private String memo; // 特記事項

}
