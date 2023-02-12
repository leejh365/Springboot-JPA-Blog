package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession session; //세션객체는 스프링컨테이너가 빈으로 등록해서 갖고 있음.필요하면 DI 로 받아서 쓸수있음
	
	@PostMapping("/api/user")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println("UserApiController : save 호출됨");	//실제로 DB에 insert를 하고 아래에서 return 되면 된다.
		user.setRole(RoleType.USER);
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 	1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	};
	
	//아래는 전통적인 로그인방법 => 스프링시큐리티를 이용한 로그인
	@PostMapping("/api/user/login")
	public ResponseDto<Integer> login(@RequestBody User user){ //매개변수로 HttpSession session 를 쓰지 않고 Autowired 로 위에 선언가능
		System.out.println("UserApiController : login 호출됨");
		User principal = userService.로그인(user); //principal : 접근주체
		
		if(principal != null) {
			session.setAttribute("principal", principal);
		}
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 	1); //1이면 로그인이 제대로 요청됐다는 뜻
	}
}
