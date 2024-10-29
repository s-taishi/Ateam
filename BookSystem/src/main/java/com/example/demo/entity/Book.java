package com.example.demo.entity;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 予約情報を格納するエンティティ
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
	
	// 予約ID
	private int id; 
	
	// 日付
	private LocalDate bookdate; 
	
	// 時間
	private LocalTime booktime; 
	
	 // 人数
	private int bookcount;
	
	// 特記事項
	private String memo; 
	
	// Userオブジェクト
	private User userid; 

}
