package com.example.demo.controller;



import java.time.LocalDate;
import java.time.LocalTime;
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
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;



@Controller
@RequiredArgsConstructor
public class BookController {

	/** DI */
	private final BookService service;


	//ログイン画面の表示
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	//新規登録ボタンを押したときの処理
	@PostMapping("/user")
	public String save(@Validated UserForm userForm, BindingResult bindingResult, RedirectAttributes attributes) {


		// ユーザー名の存在チェック
		if (service.userExistsByUserName(userForm.getUserName())) {
			bindingResult.rejectValue("userName", "error.userName", "このユーザー名は既に使用されています");
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "login";
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if (!userForm.getDisplayName().matches(katakanaPattern)) {
			bindingResult.rejectValue("userName", "error.userName", "名前はカタカナで入力してください");
		}

		User user = new User();
		user.setUserName(userForm.getUserName());
		user.setPassword(userForm.getPassword());
		user.setDisplayName(userForm.getDisplayName());
		user.setTellNumber(userForm.getTellNumber());
		service.userInsert(user);
		attributes.addFlashAttribute("message", "新規アカウントを作成しました");
		return "redirect:/login";
	}

	//予約情報を確認画面に送る
	@PostMapping("/form")
	public String form(@Validated BookForm bookForm, BindingResult bindingResult, RedirectAttributes attributes) {

		// 日付と時間が未来かどうかをチェック
		LocalDate currentDate = LocalDate.now();
		LocalTime currentTime = LocalTime.now();

		// まず日付をチェック
		if (bookForm.getDate().isBefore(currentDate)) {
			bindingResult.rejectValue("date", "error.date", "過去の日付は選べません");
		}

		// 日付が同じ場合、時間をチェック
		if (bookForm.getDate().isEqual(currentDate) && bookForm.getTime().isBefore(currentTime)) {
			bindingResult.rejectValue("time", "error.time", "過去の時間は選べません");
		}


		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "form";
		}

		Book book = new Book();
		book.setUserName(bookForm.getUserName());
		book.setId(bookForm.getId());
		book.setDate(bookForm.getDate());
		book.setTime(bookForm.getTime());
		book.setCount(bookForm.getCount());
		book.setMemo(bookForm.getMemo());

		service.bookInsert(book);

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
	@GetMapping("/mypage/{userName}")
	public String myPage(@PathVariable("userName") User UserName, String userName, Model model) {
		User user = service.userFindByUserName(userName);

		// ユーザーに関連する予約一覧を取得
		List<Book> books = service.bookFindByUserName(userName);

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
		Book book = service.bookFindById(id);

		if (book == null) {
			model.addAttribute("errorMessage", "予約が見つかりません");
		}

		// 取得した予約情報をモデルに追加
		model.addAttribute("book", book);

		// 予約詳細画面を表示
		return "check"; // 予約詳細画面のテンプレート名
	}

	//予約の削除
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attributes) {
		service.bookDelete(id);
		attributes.addFlashAttribute("message", "予約を削除しました");
		return "redirect:/mypage/{userName}";
	}



	//管理者予約確認
	@GetMapping("/adminlist")
	public String adminlist(LocalDate date, Model model) {
		model.addAttribute("list", service.bookFindByDate(date));
		return "adminlist";

	}



}
