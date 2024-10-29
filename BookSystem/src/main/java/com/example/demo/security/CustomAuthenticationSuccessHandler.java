package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.User;
import com.example.demo.service.BookService;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler{

	//DI
	private final BookService service;
	
	//ログイン成功時に実行されるメソッド
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		//ログインしたユーザー情報をログイン中、静的に保持し続ける
		String username = authentication.getName();
		User user = service.userFindByUserName(username);
		ConnectUser.id = user.getId();
		ConnectUser.username = user.getUsername();
		ConnectUser.password = user.getPassword();
		ConnectUser.displayName = user.getDisplayName();
		ConnectUser.tellNumber = user.getTellNumber();
		ConnectUser.authority = user.getAuthority();
		response.sendRedirect("/mypage");
	}
	
}
