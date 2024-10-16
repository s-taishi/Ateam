package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	private int id; // 予約ID
	private LocalDate bookdate; // 日付
	private LocalTime booktime; // 時間
	private int bookcount; // 人数
	private String memo; // 特記事項
	private User username; // bookとuser の１：１の関係

}
