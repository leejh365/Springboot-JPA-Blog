package com.cos.blog.config.auth;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cos.blog.model.User;

import lombok.Data;
import lombok.Getter;

// 스프링시큐리티가 로그인요청을 가로채서 로그인을 진행하고 완료되면 UserDetails 타입의 오브젝트를
// 스프링시큐리티의 고유한 세션 저장소에 저장해준다.(UserDetails 타입의 Principal 이 저장됨)
@Data
public class PrincipalDetail implements UserDetails {
	private User user; //컴포지션(객체를 품고있음)

	public PrincipalDetail(User user) {
		this.user = user;
	}
	
	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	//계정이 만료되지 않았는지 리턴(true : 만료안됨)
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	//계정이 잠겨있지 않았는지 리턴(true : 잠기지 않음)
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//비밀번호가 만료되지 않았는지 리턴(true : 만료안됨)
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//계정이 활성화(사용가능)인지 리턴(true : 활성화)
	@Override
	public boolean isEnabled() {
		return true;
	}

	//계정이 갖고있는 권한 목록을 리턴한다.(권한이 여러개 있을수 있어서 루프를 돌아야하는데 우리는 한개만)
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		Collection<GrantedAuthority> collectors = new ArrayList<>(); //ArrayList 가 Collection 타입임
		collectors.add(()->{return "ROLE_"+user.getRole();}); //ROLE_USER 와 같은 형태로 리턴된다.(규칙임)
		
		return collectors;
	}
}
