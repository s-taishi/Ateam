package com.example.demo.security;

import java.io.IOException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
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
		User user = service.userFindByUserName(username);//入力したユーザー名の情報を取得
		//静的なフィールドを持つクラスにユーザーの全情報を格納
		ConnectUser.id = user.getId();
		ConnectUser.username = user.getUsername();
		ConnectUser.password = user.getPassword();
		ConnectUser.displayName = user.getDisplayName();
		ConnectUser.tellNumber = user.getTellNumber();
		ConnectUser.authority = user.getAuthority();
		if(user.getUsername().matches("guest\\d*")) {//guestアカウントでログインを試みた場合
			new SecurityContextLogoutHandler().logout(request, response, authentication);
			response.sendRedirect("/login?guest");
		}else {
			response.sendRedirect("/mypage");//格納処理終了後、mypageをリクエスト
		}
	}
	
}
