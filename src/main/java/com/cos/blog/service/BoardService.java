package com.cos.blog.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cos.blog.dto.ReplySaveRequestDto;
import com.cos.blog.model.Board;
import com.cos.blog.model.User;
import com.cos.blog.repository.BoardRepository;
import com.cos.blog.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

//스프링이 컴포넌트 스캔을 통해 Bean에 등록해줌. (= IoC)
@Service
@RequiredArgsConstructor
public class BoardService {

	private final BoardRepository boardRepository; //final은 값을 초기화 해주어야하지만, @RequiredArgsConstructor를 사용하면 안해도됨
	private final ReplyRepository replyRepository;
	
	/*
	아래와 같이 @Autowired를 사용하지 않고도 위와 같은 방법으로 DI 가능하다.
	 @Autowired
	 private BoardRepository boardRepository;
	 @Autowired
	 private ReplyRepository replyRepository;
	 */

	@Transactional
	public void 글쓰기(Board board, User user) { //title, content
		board.setCount(0);
		board.setUser(user);
		boardRepository.save(board);
	}
	
	@Transactional(readOnly=true)
	public Page<Board> 글목록(Pageable pageable){
		return boardRepository.findAll(pageable);
	}

	@Transactional(readOnly=true)
	public Board 글상세보기(int id) {
		return boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글상세보기 실패 : 아이디를 찾을 수 없습니다.");
				});
	}

	@Transactional
	public void 글삭제하기(int id) {
		System.out.println("글삭제하기 : " + id);
		boardRepository.deleteById(id);
	}

	@Transactional
	public void 글수정하기(int id, Board requestBoard) {
		Board board = boardRepository.findById(id)
				.orElseThrow(()->{
					return new IllegalArgumentException("글찾기 실패 : 아이디를 찾을 수 없습니다.");
				}); //영속화 완료
		board.setTitle(requestBoard.getTitle());
		board.setContent(requestBoard.getContent());
		//해당 함수 종료시(Service가 종료될때) 트랜잭션이 종료된다. 
		//이때 더티체킹이 일어난다. - 자동업데이트가 됨. DB로 fulsh
	}

	@Transactional
	public void 댓글쓰기(ReplySaveRequestDto replySaveRequestDto) {
		int result = replyRepository.mSave(replySaveRequestDto.getUserId(), replySaveRequestDto.getBoardId(), replySaveRequestDto.getContent());
		System.out.println("BoardService : " + result);
	}

	public void 댓글삭제(int replyId) {
		replyRepository.deleteById(replyId);
	}
}
