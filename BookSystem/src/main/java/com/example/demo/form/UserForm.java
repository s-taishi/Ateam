package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー情報の入力フォームとして使用されるクラス
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserForm {
	
	//ユーザーID
	private int id;
	
	// ユーザー名（必須フィールド）
	@NotBlank(message="ユーザーネームは必須です")
	private String username; 

	// パスワード（必須フィールド、8～12文字）
	@NotBlank(message = "パスワードは必須です")
	@Size(min = 8, max = 12, message = "パスワードは8文字以上12文字以下である必要があります")
	private String password; 

	// 表示名（必須フィールド）
	@NotBlank(message = "氏名は必須です")
	private String displayName; 

	// 電話番号（必須フィールド、123-4567-8912の形式）
	@NotBlank(message = "電話番号は必須です")
	@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$",message="電話番号は「123-4567-8900」の形式で入力してください")
	private String tellNumber; 

	

}