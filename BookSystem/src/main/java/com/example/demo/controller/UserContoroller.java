package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.demo.entity.User;
import com.example.demo.form.UserForm;
import com.example.demo.security.PasswordGenerator;
import com.example.demo.service.BookService;

import jakarta.servlet.http.HttpSession;
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
		//新規登録ボタンを押したときの処理 アカウント作成
		@PostMapping("/user")
		public String save(@Validated UserForm userForm,HttpSession session, BindingResult bindingResult, RedirectAttributes attributes) {

			

			// ユーザー名の存在チェック
			if (service.userExistsByUserName(userForm.getUsername())) {
				bindingResult.rejectValue("userName", "error.userName", "このユーザー名は既に使用されています");
			}

			//バリデーションチェック
			if(bindingResult.hasErrors()) {
				return "login";
			}

			// カタカナかどうかをチェック
			String katakanaPattern = "^[\\u30A0-\\u30FF]+$";
			if (!userForm.getDisplayName().matches(katakanaPattern)) {
				bindingResult.rejectValue("displayName", "error.displayName", "名前はカタカナで入力してください");
			}

//			User user = new User();
//			user.setUsername(userForm.getUsername());
//			user.setPassword(userForm.getPassword());
//			user.setDisplayName(userForm.getDisplayName());
//			user.setTellNumber(userForm.getTellNumber());
			service.userInsert(userForm);
			attributes.addFlashAttribute("message", "新規アカウントを作成しました");
			return "redirect:/login";
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

		
		
		
}
