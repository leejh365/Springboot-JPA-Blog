package com.cos.blog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cos.blog.model.User;

// DAO
// 자동으로 bean 등록이 된다.
//@Repository 생략가능하다.
public interface UserRepository extends JpaRepository<User, Integer>{
	
	//SELECT * FROM user WHERE username = ?;
	Optional<User> findByUsername(String username); //Naming 쿼리
	
}


	//JPA Naming 쿼리 전략
	//SELECT * FROM user WHERE username = ? AND password = ?
	//User findByUsernameAndPassword(String username, String password);
	
	//	아래와 같은 방법으로 써도 되기는 함
	//	@Query(value = "SELECT * FROM user WHERE username = ? AND password = ?", nativeQuery = true)
	//	User login(String username, String password);