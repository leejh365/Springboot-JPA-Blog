package com.cos.blog.test;

import java.util.List;
import java.util.function.Supplier;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

@RestController //html파일이 아니라 data를 리턴해주는 컨트롤러
public class DummyControllerTest {

	@Autowired //의존성주입(DI)
	private UserRepository userRepository;
	
	@DeleteMapping("/dummy/user/{id}")
	public String delete(@PathVariable int id) {
		try {
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			return "삭제실패! 해당 id는 DB에 없습니다. ";
		}
		return "삭제되었습니다. id : " + id;
	}
	
	
	//save함수는 id를 전달하지 않으면 insert 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 update를 해주고
	//save함수는 id를 전달하면 해당 id에 대한 데이터가 없으면 insert를 한다.
	//email, password
	@Transactional //함수종료시 자동 commit됨, 48번라인과 같이 save하지 않아도 update 된다 ->더티체킹
	@PutMapping("/dummy/user/{id}")					
	public User updateUser(@PathVariable int id, @RequestBody User requestUser) { //json 데이터를 받으려면 @RequestBody 가 필요
		//json데이터 요청 => Java Object(MessageConverter의 Jackson 라이브러리가 변환해서 받아줌
		System.out.println("id : " + id);
		System.out.println("password : " + requestUser.getPassword());
		System.out.println("email : " + requestUser.getEmail());
		
		User user = userRepository.findById(id).orElseThrow(()->{
			return new IllegalArgumentException("수정에 실패했습니다.");
		});
		user.setPassword(requestUser.getPassword());
		user.setEmail(requestUser.getEmail());
		
		//userRepository.save(user);
		
		//더티체킹
		return user;
	}
	
	
	//http://localhost:8000/blog/dummy/user
	@GetMapping("/dummy/users")
	public List<User> list(){
		return userRepository.findAll();
	}

	//http://localhost:8000/blog/dummy/user?page=0 은 3, 2번 user정보를 가져옴. ?page=1 은 1번 user정보
	//한페이지당 2건에 데이터를 리턴받아 볼 예정
	@GetMapping("/dummy/user")
	public Page<User> pageList(@PageableDefault(size=2, sort="id", direction=Sort.Direction.DESC) Pageable pageable){
		Page<User> pagingUser = userRepository.findAll(pageable);
		List<User> users = pagingUser.getContent();
		return pagingUser;
	}
	
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
				return new IllegalArgumentException("해당 사용자가 없습니다~ : " + id);
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
