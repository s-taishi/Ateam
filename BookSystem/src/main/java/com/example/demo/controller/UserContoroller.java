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
public class UserContoroller {
	/** DI */
	private final BookService service;

	//ログイン画面の表示
	@GetMapping("/login")
	public String login(@ModelAttribute UserForm userForm) {
		return "login";
	}

	//新規登録
	@GetMapping("/login/createform")
	public String createUser(@ModelAttribute UserForm userForm,Model model) {
		model.addAttribute("userForm",userForm);
		return "create";
	}



	//新規登録情報保存
	@PostMapping("/login/create")
	public String create(@Validated UserForm userForm,BindingResult bindingResult,RedirectAttributes attributes, Model model) {
		// ユーザー名の存在チェック														userFormが新規登録画面で入力されたデータ。userがデータベース内にあるデータ
		if(userForm.getUsername() != null) {											//userFormが空出なければ以下のコードを実行
			User user = service.userFindByUserName(userForm.getUsername());				//データベースの情報をユーザー名で検索して一致したデータをuserに格納
			if(user != null) {
				if (userForm.getUsername().equals(user.getUsername())) {				//ユーザーに格納されている名前とuserFormに格納されている名前が等しいか確認
					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");
				}
			}
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {											//displaynameが入っていることを確認
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {				//displaynameがカタカナでなければエラー表示
				bindingResult.rejectValue("displayName", "error.displayName", "氏名はカタカナで入力してください");//表示するメッセージ内容
			}
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {													//エラーメッセージがあればcreate.htmlにエラーメッセージを送る
			return "create";
		}
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());		//パスワードのハッシュ化
		userForm.setPassword(hashpass);													//ハッシュ化したパスワードをuserFormに登録
		service.userInsert(userForm);													//ユーザーを新規登録
		attributes.addFlashAttribute("message", "新規アカウントを作成しました");		//作成できたことを表示するメッセージ
		return "redirect:/login";
	}
	
	
	//ユーザーの登録情報変更（ConnectUserは現在ログイン中のユーザー）
	@GetMapping("/custom")
	public String customUser(@ModelAttribute UserForm userForm,Model model) {	//ページを表示した際に元情報を表示したいため、データベースに登録されている情報をFormに格納する
		userForm.setUsername(ConnectUser.username);								//ユーザー名を格納
																				//パスワードは表示されないため省略
		userForm.setDisplayName(ConnectUser.displayName);						//氏名を格納
		userForm.setTellNumber(ConnectUser.tellNumber);						//電話番号を格納
		model.addAttribute("userForm", userForm);								//userFormとしてcustomへ情報を渡す
		return "custom";
	}
	
	
	
	//ユーザーの登録情報より変更した情報がバリデーションエラーないかチェックし登録
	@PostMapping("/update")
	public String updateUser(@Validated UserForm userForm, BindingResult bindingResult, RedirectAttributes attributes) {
		// ユーザー名の存在チェック
		if(userForm.getUsername() != null) {
			User user = service.userFindByUserName(userForm.getUsername());		//データベースの情報をユーザー名で検索して一致したデータをuserに格納
			if(user != null) {
				if (userForm.getUsername().equals(user.getUsername())&&!(userForm.getUsername().equals(ConnectUser.username))) {	//入力されたユーザーネームとデータベース内に登録されたユーザーネームが同じか
					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");					//入力したユーザーネームと現在ログインしているユーザーネームが違えば
				}																													//エラーメッセージを渡す
			}
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";						//正規表現でカタカナであるかチェック
		if(userForm.getDisplayName() != null) {									//入力された氏名が空でなければ
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {		//入力された氏名がカタカナでない場合
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");//バリデーションエラーで表示されるメッセージ
			}
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {												//エラーがあればcustum.htmlを表示
			return "custom";
		}
		userForm.setId(ConnectUser.id);												//現在ログインしているユーザーのidを入力している情報に追加
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());	//パスワードをハッシュ化
		userForm.setPassword(hashpass);												//ハッシュ化したパスワードを格納
		service.userUpdate(userForm);												//入力された情報を使用してデータベースの情報を更新
		ConnectUser.username = userForm.getUsername();								//ログインしているユーザーの各情報を更新する
		ConnectUser.password = userForm.getPassword();
		ConnectUser.displayName = userForm.getDisplayName();
		ConnectUser.tellNumber = userForm.getTellNumber();
		attributes.addFlashAttribute("message", "情報を更新しました");				//フラッシュメッセージとして更新完了したことを表示する
		return "redirect:/mypage";
	}


	//ゲストアカウント作成画面
	@GetMapping("/adminentry")
	public String adminEntry(@ModelAttribute WrapForm wrapForm, Model model) {
		model.addAttribute("wrapForm",wrapForm);									//wrapFormは管理者側で登録するためのフォーム
		return "admincreate";														//wrapForm:氏名と電話番号の情報を持つ
	}

	//ゲスト登録情報保存
	@PostMapping("/guest/create")
	public String adminCreate(@Validated WrapForm wrapForm,BindingResult bindingResult,RedirectAttributes attributes, Model model,@ModelAttribute BookForm bookForm) {
		UserForm userForm = new UserForm();											//データベースに登録するためuserFormを作成
		userForm.setDisplayName(wrapForm.getDisplayName());							//管理者より入力された氏名をuserFormに格納	
		userForm.setTellNumber(wrapForm.getTellNumber());							//管理者より入力された電話番号をuserFormに格納
						
		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
			}
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "admincreate";
		}

		//パスワードを12345678で固定.
		userForm.setPassword("12345678");											//ゲストユーザーのパスワードを固定							
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());	//パスワードをハッシュ化
		userForm.setPassword(hashpass);												//ハッシュ化したパスワードを登録

		//userNameを重複しないように自動生成
		String guest = "guest";														
		int num = 0;																//重複しないために追加する数字
		while(true) {
			num++;																	//ゲスト１から作成する
			String guestNum = guest + num;											
			User user = service.userFindByUserName(guestNum);						//データベースを検索して自動生成したユーザーネームが重複してないか確認しuserに登録

			if(user == null) {														//ユーザーが空であれば自動生成した情報を登録
				userForm.setUsername(guestNum);
				
				service.userInsert(userForm);
				
				ConnectUser.username = guestNum;									//予約する際に必要となるためログイン名を変更
				break;
			}
		}
		
		return "redirect:/entry";
	}
}
