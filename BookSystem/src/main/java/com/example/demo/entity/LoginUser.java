package com.example.demo.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;

/**
 * Spring Securityの認証時にユーザー情報を構築・保持するクラス
 */

public class LoginUser extends org.springframework.security.core.userdetails.User{

	public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}


}
