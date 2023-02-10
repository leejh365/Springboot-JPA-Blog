package com.cos.blog.test;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //html파일이 아니라 data를 리턴해주는 컨트롤러
public class DummyControllerTest {

	@Autowired //의존성주입(DI)
	private UserRepository userRepository;
	
	//{id} 주소로 파라미터를 전달받을 수 있음
	//http://localhost:8000/blog/dummy/user/3
	@GetMapping("/dummy/user/{id}")
	public User detail(@PathVariable int id) {
		
		// user/4 를 주소창에 입력하면 db에 값이 없을때 user가 null이되어 return null 
		// Optional 로 User 객체를 감싸서 가져올테니 null인지아닌지 판단해서 return해야함.
		
		// 람다식 : 아래와 같이 (36번이하)리턴타입을 몰라도 사용가능
//		User user = userRepository.findById(id).orElseThrow(()->{
//				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
//		});
//		return user;
//	}
	
		User user = userRepository.findById(id).orElseThrow(new Supplier<IllegalArgumentException>() {

			@Override
			public IllegalArgumentException get() {
				// TODO Auto-generated method stub
				return new IllegalArgumentException("해당 유저는 없습니다. id : " + id);
			}
		});
		// 요청 : 웹브라우저에서 함
		// user 객체는 자바오브객체
		// 변환(웹브라우저가 이해할 수 있는 데이터) -> json(Gson 라이브러리)
		// 스프링부트 = MessageConverter가 응답시 자동작동
		// 만약 자바 오브젝트를 리턴하게 되면 MessageConverter가 Jackson 라이브러리를 호출해서
		// user 오브젝트를 json으로 변환해서 브라우저에게 던져줌.
		return user;
	}
	
	// http://localhost:8000/blog/dummy/join(요청)
	// http의 body에 username, password, email 데이터를 가지고 (요청)
//	@PostMapping("/dummy/join")
//	public String join(String username, String password, String email) { //key=value(약속된 규칙)
//		System.out.println("username : " + username);
//		System.out.println("password : " + password);
//		System.out.println("email : " + email);
//		
//		return "회원가입이 완료되었습니다";
//	}
	
	@PostMapping("/dummy/join")
	public String join(User user) { 
		System.out.println("id : " + user.getId());
		System.out.println("role : " + user.getRole());		
		System.out.println("createDate : " + user.getCreateDate());
		System.out.println("username : " + user.getUsername());
		System.out.println("password : " + user.getPassword());
		System.out.println("email : " + user.getEmail());
		
		user.setRole(RoleType.USER); //회원가입시 role컬럼에 default 값으로 USER를 넣어줌
		userRepository.save(user);
		return "회원가입이 완료되었습니다";
	}
}
