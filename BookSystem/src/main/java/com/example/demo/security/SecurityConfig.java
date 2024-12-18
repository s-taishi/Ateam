package com.example.demo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final UserDetailsService userDetailsService;
	private final PasswordEncoder passwordEncoder;
	private final CustomAuthenticationSuccessHandler successHandler;
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http
		//セキュリティ設定
		.authorizeHttpRequests(authz -> authz
		//ログインに関わる画面へのアクセスは誰でも可能
		.requestMatchers("/login/**").permitAll()
		//管理者画面へのアクセスはADMIN権限を持つ者のみ可能
		.requestMatchers("/adminmenu","/adminlist/**").hasAuthority("ADMIN")
		//他はログインすればアクセス可能
		.anyRequest().authenticated())
		//ログイン設定
		.formLogin(form -> form
		//ログインページに使うurlはlogin
		.loginPage("/login")
		//ログイン処理に使用するurl
		.loginProcessingUrl("/authentication")
		//usernameに使う変数はusername
		.usernameParameter("username")
		//passwordに使う変数はpassword
		.passwordParameter("password")
		//ログイン成功時に実行するメソッド
		.successHandler(successHandler)
		//ログイン失敗時に飛ばすurl
		.failureUrl("/login?error"))
		//ログアウト設定
		.logout(logout -> logout
		//ログアウトに使うurl
		.logoutUrl("/logout")
		//ログアウトに成功した時に飛ばすurl
		.logoutSuccessUrl("/login?logout")
		//ログアウト時にセッションを無効化
		.invalidateHttpSession(true)
		//Cokkieの削除
		.deleteCookies("JSESSIONID"));
		
		return http.build();
	}

}
