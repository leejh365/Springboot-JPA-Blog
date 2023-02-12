package com.cos.blog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

//빈 등록 : 스프링 컨테이너에서 객체를 관리할 수 있게 하는 것 
//아래 어노테이션 세가지는 스프링시큐리티 사용시 기본사항

@Configuration //빈등록 (IoC관리)
@EnableWebSecurity //security 필터 등록
@EnableGlobalMethodSecurity(prePostEnabled = true) //특정 주소를 접근을 하면 권한 및 인증을 미리 체크하겠다는 뜻
public class SecurityConfig {

	@Bean //=>IoC가 된다.(아래의 함수가 return 하는 값을 스프링이 관리) 
	public BCryptPasswordEncoder encodePWD() {
		return new BCryptPasswordEncoder();
	}
	
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
		  .csrf().disable() //csrf 토큰 비활성화(테스트시 걸어두는 게 좋음)
		  .authorizeRequests()
		  	.antMatchers("/", "/auth/**", "/js/**", "/css/**", "/image/**")
		  	.permitAll()
		  	.anyRequest()
		  	.authenticated()
		  .and()
		  	.formLogin()
		  	.loginPage("/auth/loginForm");
        return http.build();
    }
}
