package com.example.demo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LoginUserDetailsServiceImpl implements UserDetailsService{
	
	private final UserMapper userMapper;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO 自動生成されたメソッド・スタブ
		User user = userMapper.selectByUserName(username);
		
		if(user != null) {
			return new User(user.getName(),
					user.getPassword(),
					getAuthorityList(user.getRole());
		}else {
			throw new UsernameNotFoundException(username +" => 指定しているユーザー名は存在しません");
		}
	}
	
	List<GrantedAuthority> getAuthorityList(Role role) {
		List<GrantedAuthority> rolelist = new ArrayList<>();
		if(role == Role.ADMIN) {
			rolelist.add(new SimpleGrantedAuthority(Role.USER.toString()));
			rolelist.add(new SimpleGrantedAuthority(Role.ADMIN.toString()));
		}else {
			rolelist.add(new SimpleGrantedAuthority(Role.USER.toString()));
		}
		return rolelist;
	}
	
	
	

}
