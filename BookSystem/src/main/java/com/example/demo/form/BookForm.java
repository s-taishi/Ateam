package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.entity.User;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 予約情報を格納し、バリデーションを行うためのフォームクラス
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookForm {
	
	// ユーザー名
	private User user; 
	
	// 予約ID
	private int id; 

	// 日付（必須フィールド）
	@NotNull(message = "日付は必須です")
	private LocalDate bookdate; 
	
	// 時間（必須フィールド）
	@NotNull(message = "時間は必須です")
	private LocalTime booktime;
	
	// 人数（必須フィールド、1人以上）
	@NotNull(message = "人数は必須です")
	@Min(value = 1, message = "人数は１以上でなければいけません")
	private Integer bookcount; 

	// 特記事項（100文字以内の制限）
	@Size(max = 100, message = "特記事項は100字以内でなければいけません")
	private String memo; 

}
