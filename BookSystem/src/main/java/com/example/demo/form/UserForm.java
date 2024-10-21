package com.example.demo.form;

import com.example.demo.entity.Role;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
	private String username; // ユーザー名

	@NotBlank(message = "パスワードは必須です")
	@Size(min = 8, max = 12, message = "パスワードは8文字以上12文字以下である必要があります")
	private String password; // パスワード

	@NotBlank(message = "氏名は必須です")
	private String displayName; // 氏名

	@NotBlank(message = "電話番号は必須です")
	@Pattern(regexp = "^\\\\d{3}-\\\\d{4}-\\\\d{4}$",message="電話番号は「123-4567-8912」の形式で入力してください")
	private String tellNumber; // 電話番号

	private Role authority = Role.USER; // 権限
	
	public String getConvertAuthority() {
		String auth = authority.name();
		return auth;
	}
}