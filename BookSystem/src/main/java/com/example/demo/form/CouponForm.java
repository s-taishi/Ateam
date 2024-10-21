package com.example.demo.form;

import com.example.demo.entity.CouponType;
import com.example.demo.entity.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CouponForm {
    private int id;        // データベースから取得したクーポンのID
    private User user;     // ユーザー情報を格納するフィールド
    private CouponType couponType; // クーポンタイプを格納するフィールド

    // ユーザーIDを文字列として取得するためのメソッド
    public String getCouponType() {
        return couponType != null ? couponType.name() : "No Coupon";
    }
}