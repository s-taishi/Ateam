package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.ConnectUser;
import com.example.demo.entity.User;
import com.example.demo.form.BookForm;
import com.example.demo.form.UserForm;
import com.example.demo.form.WrapForm;
import com.example.demo.security.PasswordGenerator;
import com.example.demo.service.BookService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class UserController {
	/** DI */
	private final BookService service;

	// ログイン画面の表示
	@GetMapping("/login")
	public String login(@ModelAttribute UserForm userForm) {
		return "login";
	}

	// 新規登録
	@GetMapping("/login/createform")
	public String createUser(@ModelAttribute UserForm userForm,Model model) {
		model.addAttribute("userForm",userForm);
		return "create";
	}



	// 新規登録情報保存
	@PostMapping("/login/create")
	public String create(@Validated UserForm userForm,BindingResult bindingResult,RedirectAttributes attributes, Model model) {

		// ユーザー名の存在チェック							
		if(userForm.getUsername() != null) {		

			//データベースの情報をユーザー名で検索して一致したデータをuserに格納
			User user = service.userFindByUserName(userForm.getUsername());

			//ユーザーに格納されている名前とuserFormに格納されている名前が一致すればエラー
			if(user != null) {
				if (userForm.getUsername().equals(user.getUsername())) {		

					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");
				}
			}

			// ゲスト登録で使用するユーザー名はエラーを出す
			if(userForm.getUsername().matches("guest\\d*")) {

				bindingResult.rejectValue("username","error.username","このユーザー名は使用できません");
			}
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {											
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {

				//displaynameがカタカナでなければエラー表示
				bindingResult.rejectValue("displayName", "error.displayName", "氏名はカタカナで入力してください");
			}
		}

		// バリデーションチェック
		if(bindingResult.hasErrors()) {						

			//エラーメッセージがあればcreate.htmlにエラーメッセージを送る
			return "create";
		}


		//パスワードのハッシュ化
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());	
		//ハッシュ化したパスワードをuserFormに登録
		userForm.setPassword(hashpass);										
		//ユーザー情報をデータベースに格納
		service.userInsert(userForm);										
		//作成できたことを表示するメッセージ
		attributes.addFlashAttribute("message", "新規アカウントを作成しました");	

		return "redirect:/login";
	}


	// ユーザーの登録情報変更（ConnectUserは現在ログイン中のユーザー）
	@GetMapping("/custom")
	public String customUser(@ModelAttribute UserForm userForm,Model model) {

		//ページを表示した際に元情報を表示したいため、データベースに登録されている情報をFormに格納する
		userForm.setUsername(ConnectUser.username);
		//パスワードは表示されないため省略
		userForm.setDisplayName(ConnectUser.displayName);
		userForm.setTellNumber(ConnectUser.tellNumber);	

		//userFormとしてcustomへ情報を渡す
		model.addAttribute("userForm", userForm);	

		return "custom";
	}


	// ユーザー情報変更処理
	@PostMapping("/update")
	public String updateUser(@Validated UserForm userForm, BindingResult bindingResult, RedirectAttributes attributes) {

		// ユーザー名の存在チェック
		if(userForm.getUsername() != null) {

			//データベースの情報をユーザー名で検索して一致したデータをuserに格納
			User user = service.userFindByUserName(userForm.getUsername());	

			//ユーザーに格納されている名前とuserFormに格納されている名前が一致すればエラー
			if(user != null) {
				if (userForm.getUsername().equals(user.getUsername())&&!(userForm.getUsername().equals(ConnectUser.username))) {	

					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");					
				}
			}

			// ゲスト登録で使用するユーザー名はエラーを出す
			if(userForm.getUsername().matches("guest\\d*")) {

				bindingResult.rejectValue("username","error.username","このユーザー名は使用できません");
			}
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {	
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {

				//displaynameがカタカナでなければエラー表示
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
			}
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {	
			//エラーがあればcustum.htmlを表示
			return "custom";
		}


		//現在ログインしているユーザーのidを入力している情報に追加
		userForm.setId(ConnectUser.id);	
		
		//パスワードをハッシュ化
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());	
		//ハッシュ化したパスワードを格納
		userForm.setPassword(hashpass);												
		
		//入力された情報を使用してデータベースの情報を更新
		service.userUpdate(userForm);	
		
		//ログインしているユーザーの各情報を更新する
		ConnectUser.username = userForm.getUsername();								
		ConnectUser.password = userForm.getPassword();
		ConnectUser.displayName = userForm.getDisplayName();
		ConnectUser.tellNumber = userForm.getTellNumber();
		
		//フラッシュメッセージとして更新完了したことを表示する
		attributes.addFlashAttribute("message", "情報を更新しました");
		
		return "redirect:/mypage";
	}


	//ゲストアカウント作成画面
	@GetMapping("/adminentry")
	public String adminEntry(@ModelAttribute WrapForm wrapForm, Model model) {
		
		//wrapFormは管理者側で登録するためのフォーム
		//氏名と電話番号の情報を持つ
		model.addAttribute("wrapForm",wrapForm);
		
		return "admincreate";									
	}

	//ゲスト登録情報保存
	@PostMapping("/guest/create")
	public String adminCreate(@Validated WrapForm wrapForm,BindingResult bindingResult,RedirectAttributes attributes, Model model,@ModelAttribute BookForm bookForm) {
		
		//データベースに登録するためuserFormを作成
		UserForm userForm = new UserForm();
		
		//管理者より入力された氏名をuserFormに格納	
		userForm.setDisplayName(wrapForm.getDisplayName());	
		//管理者より入力された電話番号をuserFormに格納
		userForm.setTellNumber(wrapForm.getTellNumber());

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
			}
		}

		// バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "admincreate";
		}

		//パスワードを12345678で固定
		userForm.setPassword("12345678");
							
		//パスワードをハッシュ化
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());
		//ハッシュ化したパスワードをuserFormに格納
		userForm.setPassword(hashpass);	
		

		//userNameを重複しないように自動生成
		String guest = "guest";	
		
		//重複しないために追加する数字
		int num = 0;
		
		while(true) {
			
			//ゲスト１から作成する
			num++;	
			String guestNum = guest + num;
			
			//データベースを検索して自動生成したユーザーネームが重複してないか確認しuserに登録
			User user = service.userFindByUserName(guestNum);
			if(user == null) {	
				//生成したユーザーネームをuserFormに格納
				userForm.setUsername(guestNum);

				//ユーザーが空であれば自動生成した情報を登録
				service.userInsert(userForm);

				//予約する際に必要となるためログイン名を変更
				ConnectUser.username = guestNum;
				break;
			}
		}

		return "redirect:/entry";
	}
}
