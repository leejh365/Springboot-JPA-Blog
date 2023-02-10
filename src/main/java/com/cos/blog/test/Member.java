package com.cos.blog.test;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data //@Getter와 @Setter 한꺼번에 만들어주는 어노테이션
@AllArgsConstructor //생성자 (final에 대한 생성자는 @RequiredArgsConstructor)
@NoArgsConstructor //빈생성자
//@Builder : 빌더패턴을 만들어줌(장점은 id, username, password, email 의 순서를 지키지 않아도 된다)
public class Member {

	private int id;
	private String username;
	private String password;
	private String email;

}
