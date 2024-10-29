package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Coupon;
import com.example.demo.repository.Coupon1Repository;

import lombok.RequiredArgsConstructor;

// サービスクラスとしてアノテーションを付与
@Service
@RequiredArgsConstructor
public class Coupon1Service {

    // Coupon1Repositoryを自動注入するためのフィールド
    private final Coupon1Repository coupon1Repository;

    // 指定したユーザーIDに基づいてクーポンを取得するメソッド
    public List<Coupon> couponFindByUserId(int userId) {
        // リポジトリを呼び出して、クーポンリストを取得
        return coupon1Repository.couponSelectByUserId(userId);
    }
}
