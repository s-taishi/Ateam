package com.example.demo.form;

import java.time.LocalDate;
import java.time.LocalTime;

import com.example.demo.entity.User;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class BookForm {
	private User userName; // ユーザー名
	private int id; // 予約ID

	@NotNull(message = "日付は必須です")
	private LocalDate date; // 日付
	@NotNull(message = "時間は必須です")
	private LocalTime time; // 時間
	@NotNull(message = "人数は必須です")
	@Min(value = 1, message = "人数は１以上でなければいけません")
	private Integer count; // 人数

	@Size(max = 100, message = "100字以内でなければいけません")
	private String memo; // 特記事項

}
