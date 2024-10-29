package com.example.demo.controller;

import java.time.LocalDate;
import java.util.List;

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

import com.example.demo.entity.Book;
import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.form.BookForm;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class BookController {

	/** DI */
	private final BookService service;

//	//ログイン画面の表示
//	@GetMapping("/login")
//	public String login(@ModelAttribute UserForm userForm) {
//		return "login";
//	}
	

	//新規登録ボタンを押したときの処理 アカウント作成
//	@PostMapping("/user")
//	public String save(@Validated UserForm userForm,HttpSession session, BindingResult bindingResult, RedirectAttributes attributes) {
//
//		
//
//		// ユーザー名の存在チェック
//		if (service.userExistsByUserName(userForm.getUsername())) {
//			bindingResult.rejectValue("userName", "error.userName", "このユーザー名は既に使用されています");
//		}
//
//		//バリデーションチェック
//		if(bindingResult.hasErrors()) {
//			return "login";
//		}
//
//		// カタカナかどうかをチェック
//		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
//		if (!userForm.getDisplayName().matches(katakanaPattern)) {
//			bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
//		}

//		User user = new User();
//		user.setUsername(userForm.getUsername());
//		user.setPassword(userForm.getPassword());
//		user.setDisplayName(userForm.getDisplayName());
//		user.setTellNumber(userForm.getTellNumber());
//		service.userInsert(userForm);
//		attributes.addFlashAttribute("message", "新規アカウントを作成しました");
//		return "redirect:/login";
//	}
	
	//予約情報入力画面
	@GetMapping("/entry")
	public String entry(@ModelAttribute BookForm bookForm,Model model) {
		
		return "form";
	}
	
	
	
	//予約情報を確認画面に送る
	@PostMapping("/form")
	public String form(@Validated BookForm bookForm, BindingResult bindingResult, RedirectAttributes attributes,Model model) {
		
			User user = service.userFindByUserName(ConnectUser.username);
			bookForm.setUser(user);
	
		
		// 日付と時間が未来かどうかをチェック
		LocalDate currentDate = LocalDate.now();

		// まず日付をチェック
		if (bookForm.getBookdate().isBefore(currentDate)) {
			bindingResult.rejectValue("bookdate", "error.bookdate", "過去の日付は選べません");
		}

		// 日付が同じ場合
		if (bookForm.getBookdate().isEqual(currentDate)) {
			bindingResult.rejectValue("booktime", "error.booktime", "本日は選べません");
		}


		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "form";
		}
		
		

		Book book = new Book();
		book.setBookdate(bookForm.getBookdate());
		book.setBooktime(bookForm.getBooktime());
		book.setBookcount(bookForm.getBookcount());
		book.setMemo(bookForm.getMemo());
		book.setUserid(bookForm.getUser());

		model.addAttribute("book",book);
		model.addAttribute("check","check");

		return "/check";
	}


	//予約完了画面
	@PostMapping("/comp")
	public String comp(@ModelAttribute Book book, Model model){
		if(book.getUserid() != null) {
			 User user = book.getUserid();
		        book.setUserid(user);
		}
		service.bookInsert(book);
		model.addAttribute("book", book);

		return "comp"; // 予約完了画面を表示

	}

	//マイページを表示
	@GetMapping("/mypage")
	public String myPage(Model model) {
		if(ConnectUser.authority.equals(Role.ADMIN)) {
			return "redirect:/adminmenu";
		}
		User user = service.userFindByUserName(ConnectUser.username);

		// ユーザーに関連する予約一覧を取得
		List<Book> books = service.bookFindByUserName(ConnectUser.username);

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
		model.addAttribute("detail","detail");

		// 予約詳細画面を表示
		return "check"; // 予約詳細画面のテンプレート名
	}

	//予約の削除
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attributes) {
		service.bookDelete(id);
		attributes.addFlashAttribute("message", "予約を削除しました");
		return "redirect:/mypage";
	}
	
	//管理者メニュー
	@GetMapping("/adminmenu")
	public String adminmenu() {
		return "adminmenu";
	}



	//管理者予約確認
	@GetMapping("/adminlist")
	public String adminlist(@RequestParam("date") LocalDate date,Model model) {
		List<Book> list = service.bookFindByDate(date);
//		for(Book a : list) {
//			System.out.println(a.getId());
//			System.out.println(a.getBookdate());
//			System.out.println(a.getBooktime());
//			System.out.println(a.getBookcount());
//			System.out.println(a.getMemo());
//			System.out.println(a.getUserid());
//		}
		model.addAttribute("list", list);
		model.addAttribute("selectedDate", date); //伊藤追記
		return "adminlist";

	}
	
	//伊藤追加部分
	
	// 管理者予約削除
	@PostMapping("/delete-admin/{id}")
	public String adminDelete(@PathVariable int id, @RequestParam("date") LocalDate date, RedirectAttributes attributes) {
	    service.bookDelete(id);
	    attributes.addFlashAttribute("message", "予約を削除しました");
	    return "redirect:/adminlist?date=" + date; // 日付をクエリパラメーターとして追加
	}
	
	
	//ここまで伊藤
	
	
	
//	//新規登録
//	@GetMapping("/login/createform")
//	public String createUser(@ModelAttribute UserForm userForm,Model model) {
//		model.addAttribute("userForm",userForm);
//		return "create";
//	}
//	
//	//新規登録情報保存
//	@PostMapping("/login/create")
//	public String create(@Validated UserForm userForm,BindingResult bindingResult,RedirectAttributes attributes, Model model) {
//		// ユーザー名の存在チェック
//		if(userForm.getUsername() != null) {
//			User user = service.userFindByUserName(userForm.getUsername());
//			if(user != null) {
//				if (userForm.getUsername().equals(user.getUsername())) {
//					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");
//				}
//			}
//		}
//		
//		// カタカナかどうかをチェック
//		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
//		if(userForm.getDisplayName() != null) {
//			if (!(userForm.getDisplayName().matches(katakanaPattern))) {
//				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
//			}
//		}
//
//		//バリデーションチェック
//		if(bindingResult.hasErrors()) {
//			return "create";
//		}
//		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());
//		userForm.setPassword(hashpass);
//		service.userInsert(userForm);
//		attributes.addFlashAttribute("message", "新規アカウントを作成しました");
//		return "redirect:/login";
//	}



}
