package com.cos.blog.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class BoardController {
	
	@GetMapping({"","/"}) //{아무것도 붙이지 않았을때, /붙였을때}
	public String index() { //컨트롤러에서 세션을 어떻게 찾을까??
		// /WEB-INF/views/index.jsp
		return "index";
		//참고. 스프링시큐리티에서 /logout 하면 로그아웃될 수 있도록 설정되어있음
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
