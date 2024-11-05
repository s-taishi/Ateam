package com.example.demo.entity;

/**
 * ユーザーの権限を表す列挙型クラス
 * 管理者または一般ユーザーを指定
 */
public enum Role {
    
    ADMIN, // 管理者権限を持つユーザー（システム全体の管理が可能）
    
    USER // 一般ユーザー権限を持つユーザー（通常の操作が可能）
}