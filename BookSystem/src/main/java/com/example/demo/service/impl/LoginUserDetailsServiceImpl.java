package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsServiceImpl implements UserDetailsService{
	
	//DIコンテナ
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		//ログイン画面で入力されたユーザー名を元に鍵となるデータの呼び出し
		User user = userRepository.userSelectByUserName(username);
		
		//呼び出したデータがあるかないか
		if(user != null) {//あればそのデータが持つ情報をログイン処理用インスタンスに格納
			return new org.springframework.security.core.userdetails.User(user.getUserName(),
					user.getPassword(),
					getAuthorityList(user.getAuthority()));
		}else {//なければエラーを表示する
			throw new UsernameNotFoundException(username +" => 指定しているユーザー名は存在しません");
		}
	}
	
	//管理者が一般の権限も持つようにするメソッド
	List<GrantedAuthority> getAuthorityList(Role role) {
		List<GrantedAuthority> rolelist = new ArrayList<>();
		if(role == Role.ADMIN) {//メソッド引数の中身がADMINなら全ての権限を持たせる
			rolelist.add(new SimpleGrantedAuthority(Role.USER.toString()));
			rolelist.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		}else {//違うならUSER権限のみ持たせる
			rolelist.add(new SimpleGrantedAuthority(Role.USER.toString()));
		}
		return rolelist;
	}
	
	
	

}
