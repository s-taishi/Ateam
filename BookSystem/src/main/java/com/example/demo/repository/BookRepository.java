package com.example.demo.repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.demo.entity.Book;

@Mapper
public interface BookRepository {
	//IDでの検索
	Book bookSelectById(int id);

	//usernameでの予約検索
	List<Book> bookSelectByName(@Param("userName") String userName);

	//月毎の予約検索
	List<Book> bookSelectByMonth(@Param("localDate") LocalDate localDate);

	//日毎の予約検索
	List<Book> bookSelectByDate(@Param("localDate") LocalDate localDate);

	//時間ごとの予約検索
	List<Book> bookSelectByHour(@Param("localTime") LocalTime localTime);

	//新規予約登録
	void bookInsert(Book book);

	//特定のIDを持つ予約削除
	void bookDelete(int id);

}
