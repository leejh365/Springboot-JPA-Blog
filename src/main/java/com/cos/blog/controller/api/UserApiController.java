package com.cos.blog.controller.api;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.config.auth.PrincipalDetailService;
import com.cos.blog.dto.ResponseDto;
import com.cos.blog.model.User;
import com.cos.blog.service.UserService;

@RestController
public class UserApiController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private PrincipalDetailService principalDetailService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	//@Autowired
	//private HttpSession session; //세션객체는 스프링컨테이너가 빈으로 등록해서 갖고 있음.필요하면 DI 로 받아서 쓸수있음
	
	@PostMapping("/auth/joinProc")
	public ResponseDto<Integer> save(@RequestBody User user) { //username, password, email
		System.out.println("UserApiController : save 호출됨");	//실제로 DB에 insert를 하고 아래에서 return 되면 된다.
		userService.회원가입(user);
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 	1); //자바오브젝트를 JSON으로 변환해서 리턴(Jackson)
	};
	
	@PutMapping("/user")
	public ResponseDto<Integer> update(@RequestBody User user){ //json데이터 받아야하므로 / 이게 없으면 key=value, x-www-form-urlencoded
		userService.회원수정(user);
		//여기서는 트랜잭션이 종료되기 때문에 DB값은 변경되나, 세션값은 변경되지 않은 상태.
		// =>로그아웃하고 다시로그인해야 변경내용확인가능; =>>> 직접 세션값을 변경해주어야 함
		//세션등록
		Authentication autentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(autentication);
				
		return new ResponseDto<Integer>(HttpStatus.OK.value(), 1);
	}
}
