package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Coupon {
    private int id;
    private User user; // Userオブジェクト
    private CouponType couponType; // プロパティ名を修正
}
