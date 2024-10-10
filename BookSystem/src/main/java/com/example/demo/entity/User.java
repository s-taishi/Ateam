package com.example.demo.entity;


import lombok.Data;

@Data
public class User {
	private String userName;
	private String password;
	private String displayName;
	private String tellNumber;
	private Role authority;
}
