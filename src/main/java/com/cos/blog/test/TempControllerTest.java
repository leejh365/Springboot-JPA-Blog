package com.cos.blog.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller //해당경로 이하에 있는 파일을 리턴해준다.(RestController는 문자 그자체를 리턴)
public class TempControllerTest {

	// http://localhost:8000/blog/temp/home
	@GetMapping("/temp/home")
	public String tempHome() {
		System.out.println("tempHome()");
		// 파일리턴 기본경로 : src/main/resources/static
		// 리턴명 : /home.html 로 해야 풀경로가 src/main/resources/static/home.html 로 된다.
		// 스프링부트는 jsp를 지원하지 않기 때문에 정상동작을 위해 pom.xml에 JSP 템플릿엔진 의존성 설정을 해주어야 한다.
		// src/main/resources/static의 static은 브라우저가 인식할 수 있는 정적파일을 놓는 경로
		// jsp는 동적인 자바파일이기 때문에 (컴파일이 일어나야하는 파일) 못읽음. > 경로를 바꾸어야한다.
		return "/home.html";
	}

	@GetMapping("/temp/jsp")
	public String tempJsp() {
		// prefix: /WEB-INF/views/
	    // suffix: .jsp
		// return "/test.jsp"; 로 할 경우 풀경로 : /WEB-INF/views/test.jsp.jsp
		// 따라서 return 경로를 "test"(또는 "/test")로해야함.
		return "/test";
	}

}
