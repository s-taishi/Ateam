package com.example.demo.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordGenerator {
	//入力内容をハッシュ値に変換するメソッド
	public static String hashGenerate(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();//ハッシュ化に利用する変数
		String rawPassword = password;//ハッシュ化前のパスワード
		String encodedPassword = encoder.encode(rawPassword);//ハッシュ化したパスワード
//		System.out.println("ハッシュ化されたパスワード: "+ encodedPassword);
		return encodedPassword;
	}

}
