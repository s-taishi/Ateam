
package com.example.demo.repository;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;
@Mapper
@Repository
public interface Coupon2Repository {
//データベースに格納する際は列挙型CouponTypeクラスをStringに
//キャストする必要があるらしい
	void couponInsert(String couponType, Long userId);
}
