package com.cos.blog.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.config.auth.PrincipalDetail;

@Controller
public class BoardController {
	
	@GetMapping({"","/"}) //{아무것도 붙이지 않았을때, /붙였을때}
	public String index(@AuthenticationPrincipal PrincipalDetail principal) { //컨트롤러에서 세션을 어떻게 찾을까??
		// /WEB-INF/views/index.jsp
		System.out.println("로그인 사용자 아이디 : " + principal.getUsername());
		return "index";
		
		//스프링시큐리티에서 /logout 하면 로그아웃될 수 있도록 설정되어있음
	}
}
