package com.cos.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.cos.blog.service.BoardService;

@Controller
public class BoardController {
	
	@Autowired
	private BoardService boardService;
	
	@GetMapping({"","/"}) 
	public String index(Model model, @PageableDefault(size=3, sort="id", direction=Sort.Direction.DESC) Pageable pageable) {
		model.addAttribute("boards", boardService.글목록(pageable));
		return "index"; //viewResolver 작동. 리턴하는 페이지로 모델의 정보를 들고 감
	}
	
	//USER 권한이 필요
	@GetMapping("/board/saveForm")
	public String saveForm() {
		return "board/saveForm";
	}
}
