package com.example.demo.controller;

import java.awt.print.Book;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.form.BookForm;
import com.example.demo.form.UserForm;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {

	/** DI */
	private final BookService bookService;

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
		bookService.insertUser(user);
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
		book.setUsername(userBook.getUsername());
		book.setId(userBook.getId());
		book.setDate(userBook.getDate());
		book.setTime(userBook.getTime());
		book.setDatetime(userBook.getDatetime());
		book.setCount(userBook.getCount());
		book.setMemo(userBook.getMemo());

		bookService.insertBook(book);

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
	@PostMapping("/mypage")
	public String myPage() {
		
	}
	
	//マイページからの予約詳細画面
	@GetMapping("/check2")
	public String check2(@RequestParam("id") Integer id, Model model) {
		// 指定されたIDの予約詳細を取得
		Book book = bookService.findBookById(id);

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
	public String delete(@PathVariable Integer id, RedirectAttributes attributes) {
		bookService.deleteBook(id);
		attributes.addFlashAttribute("message", "予約を削除しました");
		return "redirect:/mypage";
	}
	
	
	//管理者予約確認
	@GetMapping("/booklist")
	public String bookList() {
		
	}
	
	

}
