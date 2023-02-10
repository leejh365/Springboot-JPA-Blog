package com.cos.blog.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class Board {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량데이터
	private String content; //써머노트 라이브러리 <html>태그가 섞여서 디자인됨.
	
	@ColumnDefault("0") //int 이기때문에 ''안써줘도됨
	private int count; //조회수
	
	@ManyToOne //Board = Many, User = One
	@JoinColumn(name="userId") //필드명은 userId로 , 연관관계는 ManyToOne으로 만들어짐.
	private User user; //DB는 오브젝트를 저장할 수 없다. FK를 사용, 자바는 오브젝트를 저장할 수 있다.
	
	@CreationTimestamp
	private Timestamp createDate;
}
