package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//ORM -> JAVA(다른언어포함) Object -> 테이블로 매핑해주는 기술
@Builder //빌더패턴
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity //User클래스가 MySQL 에 테이블이 생성된다.
// @DynamicInsert //insert할때 null인 필드를 제외시켜줌
public class User {

	@Id //Primary key
	@GeneratedValue(strategy = GenerationType.IDENTITY) //프로젝트에서 연결된 db의 넘버링 전략(시퀀스 또는 auto_increment)을 따라감.
	//use-new-id-generator-mappings: false 이면 jsp의 넘버링 전략을 따라가지 않음을 의미함
	private int id; // 시퀀스(오라클), auto_increment(MySQL)
	
	@Column(nullable = false, length = 30, unique = true)
	private String username; // 아이디
	
	@Column(nullable = false, length = 100) //비밀번호 암호화(해쉬)때문에 넉넉히 100
	private String password;
	
	@Column(nullable = false, length = 50)
	private String email; //myEmail, my_email
	
	// @ColumnDefault("'user'")
	// DB는 RoleType 이라는 게 없다
	@Enumerated(EnumType.STRING)
	private RoleType role; //Enum을 쓰는게 좋다.(도메인을 만들수있기때문_도메인은 범위를 의미(성별의 도메인은 남,여)) //ADMIN, USER
	
	@CreationTimestamp //시간 자동입력
	private Timestamp createDate;
}
