package com.example.demo.controller;

import java.time.LocalDate;
import java.util.ArrayList;
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

	//予約情報入力画面
	@GetMapping("/entry")
	public String entry(@ModelAttribute BookForm bookForm,Model model) {
		return "form";//予約フォームへ遷移
	}
	
	//予約情報を確認画面に送る
	@PostMapping("/form")
	public String form(@Validated BookForm bookForm, BindingResult bindingResult, RedirectAttributes attributes,Model model) {
		//現在ログイン中のユーザー情報を取得
		User user = service.userFindByUserName(ConnectUser.username);
		bookForm.setUser(user);
		
		// 日付が未来かどうかをチェック
		LocalDate currentDate = LocalDate.now();

		// まず日付が過去の場合
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
		
		List<Book> books = service.bookFindByDate(bookForm.getBookdate());
		int count = bookForm.getBookcount();
		for(Book b : books) {
			if(b.getBooktime().equals(bookForm.getBooktime())) {
				count += b.getBookcount();
			}
		}
		if(count > 50) {
			attributes.addFlashAttribute("message","選択された時間帯は満席です。別の時間をご指定ください。");
			return "redirect:/entry";
		}
		
		//入力フォームをデータベースに入れられるように型変更
		Book book = new Book();
		book.setBookdate(bookForm.getBookdate());
		book.setBooktime(bookForm.getBooktime());
		book.setBookcount(bookForm.getBookcount());
		book.setMemo(bookForm.getMemo());
		book.setUserid(bookForm.getUser());
		
		model.addAttribute("book",book);
		model.addAttribute("check","check");
		
		return "/check";//確認画面へ遷移
	}


	//予約完了画面
	@PostMapping("/comp")
	public String comp(RedirectAttributes attributes,@ModelAttribute Book book, Model model){
		//データベースに入力内容を登録
		service.bookInsert(book);
		model.addAttribute("book", book);
		attributes.addFlashAttribute("book",book);
		return "redirect:/complete"; // 予約完了画面を表示

	}
	
	@GetMapping("/complete")
	public String complete(@ModelAttribute Book book,Model model) {
	
		return "/comp";
	}

	//マイページを表示
	@GetMapping("/mypage")
	public String myPage(Model model) {
		//ADMIN権限を持つ管理者は管理者用画面へ遷移
		if(ConnectUser.authority.equals(Role.ADMIN)) {
			return "redirect:/adminmenu";
		}
		//現在ログイン中のユーザー情報を取得
		User user = service.userFindByUserName(ConnectUser.username);

		// ユーザーに関連する予約一覧を取得
		List<Book> books = service.bookFindByUserName(ConnectUser.username);
		List<Integer> removes = new ArrayList<Integer>();
		int roop = 0;
		for(Book b : books) {
			if(b.getBookdate().isBefore(LocalDate.now())) {
				removes.add(roop);
			}
			roop++;
		}
		int deletes = 0;
		
		for(int i : removes) {
			books.remove(i-deletes);
			deletes++;
		}

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

		// 取得した予約情報をモデルに追加
		model.addAttribute("book", book);
		model.addAttribute("detail","detail");

		// 予約詳細画面を表示
		return "check"; // 予約詳細画面のテンプレート名
	}

	//予約の削除
	@PostMapping("/delete/{id}")
	public String delete(@PathVariable int id, RedirectAttributes attributes) {
		//予約IDをもとに予約情報を削除
		service.bookDelete(id);
		attributes.addFlashAttribute("message", "予約を削除しました");
		return "redirect:/mypage";
	}
	
	//管理者メニュー
	@GetMapping("/adminmenu")
	public String adminmenu() {
		return "adminmenu";//管理者専用画面へ遷移
	}



	//管理者予約確認
	@GetMapping("/adminlist")
	public String adminlist(@RequestParam("date") LocalDate date,Model model) {
		//指定した日付の予約情報を取得
		List<Book> list = service.bookFindByDate(date);

		model.addAttribute("list", list);
		model.addAttribute("selectedDate", date); //伊藤追記
		return "adminlist";//指定日ごとの予約情報画面へ遷移
	}
	
	//伊藤追加部分
	
	// 管理者予約削除
	@PostMapping("/delete-admin/{id}")
	public String adminDelete(@PathVariable int id, @RequestParam("date") LocalDate date, RedirectAttributes attributes) {
		//指定した予約情報のIDを見て削除
	    service.bookDelete(id);
	    attributes.addFlashAttribute("message", "予約を削除しました");
	    return "redirect:/adminlist?date=" + date; // 日付をクエリパラメーターとして追加
	}
	
	//ここまで伊藤

}
