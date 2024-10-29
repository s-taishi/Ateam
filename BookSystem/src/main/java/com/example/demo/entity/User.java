package com.example.demo.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * ユーザー情報を表すエンティティクラス
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
	
	// ユーザーID
	private int id; 
	
	// ユーザーネーム
	private String username; 
	
	// パスワード
	private String password; 
	
	// 表示名
	private String displayName; 
	
	// 電話番号
	private String tellNumber;
	
	// 権限
	private Role authority; 
}
