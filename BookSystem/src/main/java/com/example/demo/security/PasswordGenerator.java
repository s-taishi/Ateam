package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	//入力内容をハッシュ値に変換するメソッド
	public static String hashGenerate(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String rawPassword = password;
		String encodedPassword = encoder.encode(rawPassword);
//		System.out.println("ハッシュ化されたパスワード: "+ encodedPassword);
		return encodedPassword;
	}

}
