package com.example.demo.form;

import com.example.demo.entity.Role;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserForm {
	private String username; // ユーザー名

	@NotBlank(message = "パスワードは必須です")
	private String password; // パスワード

	@NotBlank(message = "氏名は必須です")
	private String displayName; // 氏名

	@NotBlank(message = "電話番号は必須です")
	private String tellNumber; // 電話番号

	private Role authority; // 権限
}