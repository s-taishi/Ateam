package com.example.demo.controller;



import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.Book;
import com.example.demo.entity.User;
import com.example.demo.form.BookForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.Service;

import lombok.RequiredArgsConstructor;


@Controller
@RequiredArgsConstructor
public class BookController {

	/** DI */
	private final Service service;
	

	//ログイン画面の表示
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	//新規登録ボタンを押したときの処理
	@PostMapping("/user")
	public String save(@Validated UserForm userForm, BindingResult bindingResult, RedirectAttributes attributes) {
		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "login";
		}

		User user = new User();
		user.setUsername(userForm.getUsername());
		user.setPassword(userForm.getPassword());
		user.setDisplayName(userForm.getDisplayName());
		user.setTellNumber(userForm.getTellNumber());
		service.insertUser(user);
		attributes.addFlashAttribute("message", "新規アカウントを作成しました");
		return "redirect/login";
	}

	//予約情報を確認画面に送る
	@PostMapping("/form")
	public String form(@Validated BookForm BookForm, BindingResult bindingResult, RedirectAttributes attributes) {
		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "form";
		}

		Book book = new Book();
		book.setUsername(book.getUsername());
		book.setId(book.getId());
		book.setDate(book.getDate());
		book.setTime(book.getTime());
		book.setDatetime(book.getDatetime());
		book.setCount(book.getCount());
		book.setMemo(book.getMemo());

		service.insertBook(book);

		// リダイレクト時にBook情報をフラッシュ属性として追加
		attributes.addFlashAttribute("book", book); // Bookオブジェクトを追加

		return "redirect:/check";
	}

	//予約確認画面
	@GetMapping("/check")
	public String check(@ModelAttribute("book") Book book, Model model) {


		model.addAttribute("book", book);

		return "check"; // 予約確認画面を表示

	}



	//予約完了画面
	@GetMapping("/comp")
	public String comp(@ModelAttribute("book") Book book, Model model){

		model.addAttribute("book", book);

		return "comp"; // 予約完了画面を表示

	}
	
	//マイページを表示
	@PostMapping("/mypage/{username}")
	public String myPage(@PathVariable("username") String username, Model model) {
		User user = service.findByUserName(username);

	    // ユーザーに関連する予約一覧を取得
	    List<Book> books = service.findBookByUserName(username);

	    // 取得したユーザー情報と予約情報をモデルに追加
	    model.addAttribute("user", user);
	    model.addAttribute("books", books);

	    // 一覧画面のテンプレート名を返す
	    return "mypage";
		
		
	}
	
	//マイページからの予約詳細画面
	@GetMapping("/check2/{id}")
	public String check2(@PathVariable("id") int id, Model model) {
		// 指定されたIDの予約詳細を取得
		Book book = service.findBookById(id);

		if (book == null) {
			return "error"; // 予約が見つからない場合のエラーハンドリング
		}

		// 取得した予約情報をモデルに追加
		model.addAttribute("book", book);

		// 予約詳細画面を表示
		return "check"; // 予約詳細画面のテンプレート名
	}
	
	//予約の削除
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attributes) {
		service.deleteBook(id);
		attributes.addFlashAttribute("message", "予約を削除しました");
		return "redirect:/mypage";
	}
	
	
	//管理者予約確認
	@GetMapping("/booklist")
	public String bookList() {
		
	}
	
	

}
