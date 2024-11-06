package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	//DI
	private final BookRepository bookRepository;  // BookRepositoryのインスタンス
    private final UserRepository userRepository;  // UserRepositoryのインスタンス


    // Bookに関するメソッド
    
    //ID別予約情報取得
    public Book bookFindById(int id) {
        return bookRepository.bookSelectById(id);
    }
    //予約情報登録
    public void bookInsert(Book book) {
        bookRepository.bookInsert(book);
    }
    //指定IDの予約情報削除
    public void bookDelete(int id) {
        bookRepository.bookDelete(id);
    }
    //日付別予約情報取得
    public List<Book> bookFindByDate(LocalDate date) {
        return bookRepository.bookSelectBybookdate(date);
    }
    //ユーザー名別予約情報取得
    public List<Book> bookFindByUserName(String userName){
    	return bookRepository.bookSelectByName(userName);
    }
    
    
    
    // Userに関するメソッド

    //ユーザー名別ユーザー情報取得
    public User userFindByUserName(String username) {
        return userRepository.userSelectByUsername(username);
    }
    //ユーザー情報登録
    public void userInsert(UserForm user) {
        userRepository.userInsert(user);
    }
    //ユーザー情報更新
    public void userUpdate(UserForm user) {
    	userRepository.userUpdate(user);
    }
}




