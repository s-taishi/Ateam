package com.example.demo.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.repository.BookRepository;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {
	
	private final BookRepository bookRepository;  // BookRepositoryのインスタンス
    private final UserRepository userRepository;  // UserRepositoryのインスタンス

//    public Service(BookRepository bookRepository, UserRepository userRepository) {
//        this.bookRepository = bookRepository;
//        this.userRepository = userRepository;
//    }

    // Bookに関するメソッド
    public List<Book> bookFindAll() {
        return bookRepository.bookSelectByName(null); // すべてのBookを取得するメソッドを呼び出す
    }

    public Book bookFindById(int id) {
        return bookRepository.bookSelectById(id);
    }

    public void bookInsert(Book book) {
        bookRepository.bookInsert(book);
    }

    public void bookDelete(int id) {
        bookRepository.bookDelete(id);
    }

    public List<Book> bookFindByDate(LocalDate date) {
        return bookRepository.bookSelectByDate(date);
    }

    public List<Book> bookFindByMonth(LocalDate localDate) {
        return bookRepository.bookSelectByMonth(localDate);
    }
    
    public List<Book> bookFindByUserName(String userName){
    	return bookRepository.bookSelectByName(userName);
    }

    // Userに関するメソッド
//    public List<User> userFindAll() {
//        return userRepository.findAll(null); // UserRepositoryからすべてのユーザーを取得
//    }

    public User userFindByUserName(String username) {
        return userRepository.userSelectByUserName(username);
    }

    public void userInsert(User user) {
        userRepository.userInsert(user);
    }
}




