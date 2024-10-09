package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.Data;

	@Data
	public class Book {
		private String username; // ユーザー名
		private long id; // 予約ID（serialはlong型で表現）
		private LocalDate date; // 日付
		private LocalTime time; // 時間
		private LocalDateTime dateTime; // 日付と時間
		private int count; // 人数
		private String memo; // 特記事項

}
