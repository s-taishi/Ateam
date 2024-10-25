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
		// ユーザー名の存在チェック
		if(userForm.getUsername() != null) {
			User user = service.userFindByUserName(userForm.getUsername());
			if(user != null) {
				if (userForm.getUsername().equals(user.getUsername())) {
					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");
				}
			}
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
			}
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "create";
		}
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());
		userForm.setPassword(hashpass);
		service.userInsert(userForm);
		attributes.addFlashAttribute("message", "新規アカウントを作成しました");
		return "redirect:/login";
	}

	@GetMapping("/custom")
	public String customUser(@ModelAttribute UserForm userForm,Model model) {
		userForm.setUsername(ConnectUser.username);
		userForm.setPassword(ConnectUser.password);
		userForm.setDisplayName(ConnectUser.displayName);
		userForm.setTellNumber(ConnectUser.tellNumber);
		model.addAttribute("userForm", userForm);
		return "custom";
	}
	@PostMapping("/update")
	public String updateUser(@Validated UserForm userForm, BindingResult bindingResult, RedirectAttributes attributes) {
		// ユーザー名の存在チェック
		if(userForm.getUsername() != null) {
			User user = service.userFindByUserName(userForm.getUsername());
			if(user != null) {
				if (userForm.getUsername().equals(user.getUsername())&&!(userForm.getUsername().equals(ConnectUser.username))) {
					bindingResult.rejectValue("username","error.username","このユーザー名は既に使用されています");
				}
			}
		}

		// カタカナかどうかをチェック
		String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
		if(userForm.getDisplayName() != null) {
			if (!(userForm.getDisplayName().matches(katakanaPattern))) {
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
			}
		}

		//バリデーションチェック
		if(bindingResult.hasErrors()) {
			return "create";
		}
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());
		userForm.setPassword(hashpass);
		service.userUpdate(userForm);
		ConnectUser.username = userForm.getUsername();
		ConnectUser.password = userForm.getPassword();
		ConnectUser.displayName = userForm.getDisplayName();
		ConnectUser.tellNumber = userForm.getTellNumber();
		attributes.addFlashAttribute("message", "情報を更新しました");
		return "redirect:/mypage";
	}


	//ゲストアカウント作成画面
	@GetMapping("/adminentry")
	public String adminEntry(@ModelAttribute WrapForm wrapForm, Model model) {
		model.addAttribute("wrapForm",wrapForm);
		return "admincreate";
	}

	//ゲスト登録情報保存
	@PostMapping("/guest/create")
	public String adminCreate(@Validated WrapForm wrapForm,BindingResult bindingResult,RedirectAttributes attributes, Model model,@ModelAttribute BookForm bookForm) {
		UserForm userForm = new UserForm();
		userForm.setDisplayName(wrapForm.getDisplayName());
		userForm.setTellNumber(wrapForm.getTellNumber());

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

		//パスワードを12345678で固定
		userForm.setPassword("12345678");
		String hashpass = PasswordGenerator.hashGenerate(userForm.getPassword());
		userForm.setPassword(hashpass);

		//userNameを重複しないように自動生成
		String guest = "guest";
		int num = 0;
		while(true) {
			num++;
			String guestNum = guest + num;
			User user = service.userFindByUserName(guestNum);

			if(user == null) {
				userForm.setUsername(guestNum);
				
				service.userInsert(userForm);
				
				ConnectUser.username = guestNum;
				break;
			}
		}
		
		attributes.addFlashAttribute("message", "ゲストアカウントを作成しました");
		return "redirect:/entry";
	}
}
