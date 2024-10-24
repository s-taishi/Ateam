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
	
	private final BookRepository bookRepository;  // BookRepositoryのインスタンス
    private final UserRepository userRepository;  // UserRepositoryのインスタンス


    // Bookに関するメソッド
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
        return bookRepository.bookSelectBybookdate(date);
    }

    public List<Book> bookFindByMonth(LocalDate localDate) {
        return bookRepository.bookSelectByMonth(localDate);
    }
    
    public List<Book> bookFindByUserName(String userName){
    	return bookRepository.bookSelectByName(userName);
    }
    
    public String displayNameFindByUserName(String userName){
    	return bookRepository.displayNameSelectByUsername(userName);
    }

    // Userに関するメソッド


    public User userFindByUserName(String username) {
        return userRepository.userSelectByUsername(username);
    }
    public User userFindById(int id) {
    	return userRepository.userSelectById(id);
    }
    public List<User> userFindAll() {
    	return userRepository.userSelectAll();
    }

    public void userInsert(UserForm user) {
        userRepository.userInsert(user);
    }
    
    public boolean userExistsByUserName(String username) {
    	return userRepository.userExistsByUsername(username);
    }
    
    public void userUpdate(UserForm user) {
    	userRepository.userUpdate(user);
    }
}




