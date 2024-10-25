package com.example.demo.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class WrapForm {
	
	@NotBlank(message = "氏名は必須です")
	private String displayName; // 氏名

	@NotBlank(message = "電話番号は必須です")
	@Pattern(regexp = "^\\d{3}-\\d{4}-\\d{4}$",message="電話番号は「123-4567-8912」の形式で入力してください")
	private String tellNumber; // 電話番号

}
