package com.cos.blog.model;

import java.sql.Timestamp;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Board {

	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(nullable = false, length = 100)
	private String title;
	
	@Lob //대용량데이터
	private String content; //써머노트 라이브러리 <html>태그가 섞여서 디자인됨.
	
	private int count; //조회수
	
	@ManyToOne(fetch = FetchType.EAGER) //Board = Many, User = One
	@JoinColumn(name="userId") //필드명은 userId로 , 연관관계는 ManyToOne으로 만들어짐.
	private User user; //DB는 오브젝트를 저장할 수 없다. FK를 사용, 자바는 오브젝트를 저장할 수 있다.
	
	//하나의게시글과 여러개의답변. select 하기 위한 코드
	@OneToMany(mappedBy = "board", fetch = FetchType.EAGER) //기본이 FetchType.LAZY 전략임. 
	//필드이름(board), mappedBy 연관관계의 주인이 아님(FK가 아니라는 뜻. DB에 컬럼을 만들지말아라..Reply의 board가 FK)
	private List<Reply> reply; // reply : Board 를 select 할때 join문을 통해 값을 얻기위해 필요
	
	@CreationTimestamp
	private Timestamp createDate;
}
