package com.cos.blog.test;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

//사용자가 요청 -> 응답(HTML 파일) : @Controller

//사용자가 요청 -> 응답(Data)
@RestController
public class HttpControllerTest {

	private static final String TAG = "HttpControllerTest : ";
	
	// http://localhost:8000/blog/http/lombok (application.yml 에서 포트번호와 context-path를 바꾸었기때문에 연결주소도 변경됨)
	@GetMapping("/http/lombok")
	public String lombokTest() {
		Member m = new Member(1, "ssar", "12345", "leejh@naver.com");
		System.out.println(TAG + "getter : " + m.getId());
		m.setId(5000);
		System.out.println(TAG + "getter : " + m.getId());
		return "lombok test 완료";
	}

	//인터넷 브라우저 요청은 무조건 'get 요청' 밖에 할 수 없다.
	//아래의 (post, put, delete) 를 브라우저에 검색하면 405에러 발생
	
	// http://localhost:8080/http/get (select)
	// get요청은 ?뒤 쿼리스트링을 통해 데이터를 보낼 수 있음
	//?id=1&username=leejh&password=1234&email=ssar@nate.com >>이 값들을 Member에 넣어주는 역할도 스프링이 해줌(MessageConverter (스프링부트))
	@GetMapping("/http/get")
	public String getTest(Member m) { 
		return "get 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	/* 위 방법은 객체로 값을 묶어서 전달, 아래는 하나하나 전달
	 @GetMapping("/http/get")
	public String getTest(@RequestParam int id, @RequestParam String username) {
		return "get 요청 : " + id + ", " + username;
	}
	*/

	// http://localhost:8080/http/post (insert)
	/*@PostMapping("/http/post")
	public String postTest(Member m) {
		return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}*/
	//postman 에서 Body탭의 'x-www-form-urlencoded'로 값을 전달하는 방식이 form태그로 데이터를 전송했던 방식과 같음
	
	
	@PostMapping("/http/post") //방법1 text/plain = row 데이터 ,  방법2 application/json
	public String postTest(@RequestBody Member m) { //MessageConverter (스프링부트)
		return "post 요청 : " + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/put (update)
	@PutMapping("/http/put")
	public String putTest(@RequestBody Member m) {
		return "put 요청" + m.getId() + ", " + m.getUsername() + ", " + m.getPassword() + ", " + m.getEmail();
	}
	
	// http://localhost:8080/http/delete (delete)
	@DeleteMapping("/http/delete")
	public String deleteTest() {
		return "delete 요청";
	}
}
