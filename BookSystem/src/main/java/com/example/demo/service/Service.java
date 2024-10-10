package com.example.demo.service;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import com.example.demo.form.UserForm;
import com.example.webapp.service.Book;
import com.example.webapp.service.BookRepository;
import com.example.webapp.service.User;
import com.example.webapp.service.UserRepository;

public class Service {
	
	private final BookRepository bookRepository;  // BookRepositoryのインスタンス
    private final UserRepository userRepository;  // UserRepositoryのインスタンス

    public Service(BookRepository bookRepository, UserRepository userRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    // Bookに関するメソッド
    public List<Book> findAllBook() {
        return bookRepository.bookSelectByName(null); // すべてのBookを取得するメソッドを呼び出す
    }

    public Book findById(long id) {
        return bookRepository.bookfindById(id);
    }

    public void insertBook(Book book) {
        bookRepository.bookInsert(book);
    }

    public void deleteBook(long id) {
        bookRepository.bookDelete(id);
    }

    public List<Book> findDate(LocalDate date) {
        return bookRepository.bookSelectByDate(date);
    }

    public List<Book> findMonth(Month month) {
        return bookRepository.bookSelectByMonth(month);
    }

    // Userに関するメソッド
    public List<User> findAllUser() {
        return userRepository.findAll(); // UserRepositoryからすべてのユーザーを取得
    }

    public User findByUserName(String username) {
        return userRepository.findByUserName(username);
    }

    public void insertUser(UserForm user) {
        userRepository.insert(user);
    }
}



}
