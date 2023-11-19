package com.stb.sbb;

import com.stb.sbb.answer.entity.Answer;
import com.stb.sbb.answer.repository.AnswerRepository;
import com.stb.sbb.question.entity.Question;
import com.stb.sbb.question.repository.QuestionRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;
	@Test
	@DisplayName("데이터 삽입")
	void contextLoads() {
		Question q1 = new Question();
		q1.setSubject("첫 번째 제목입니다.");
		q1.setContent("첫 번째 제목의 내용입니다.");
		q1.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q1);

		Question q2 = new Question();
		q2.setSubject("두 번째 제목입니다.");
		q2.setContent("두 번째 제목의 내용입니다.");
		q2.setCreateDate(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	@DisplayName("전체 데이터 조회")
	void findAll(){
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q = all.get(0);
		assertEquals("첫 번째 제목입니다.", q.getSubject());
	}
	@Test
	@DisplayName("아이디로 데이터 조회")
	void findById(){
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()){
			Question q = oq.get();
			assertEquals("첫 번째 제목입니다.", q.getSubject());
		}
	}
	@Test
	@DisplayName("subject값으로 데이터 조회")
	void findBySubject(){
		Question q = this.questionRepository.findBySubject("첫 번째 제목입니다.");
		assertEquals(1, q.getId());
	}
	@Test
	@DisplayName("제목과 내용을 함께 조회")
	void findBySubjectAndContent(){
		Question q = this.questionRepository.findBySubjectAndContent("첫 번째 제목입니다.", "첫 번째 제목의 내용입니다.");
		assertEquals(1, q.getId());
	}
	@Test
	@DisplayName("특정 문자열이 포함되어있는 데이터 조회")
	void findBySubjectLike(){
		List<Question> qList = this.questionRepository.findBySubjectLike("%제목%");
		Question q = qList.get(0);
		assertEquals("첫 번째 제목입니다.", q.getSubject());
	}

	@Test
	@DisplayName("데이터 수정")
	 void modify(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setSubject("두 번째 제목이 수정되었습니다.");
		q.setContent("두 번째 제목의 내용도 수정되었습니다.");
		this.questionRepository.save(q);
	}
	@Test
	@DisplayName("데이터 삭제")
	void remove(){
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}
	@Test
	@DisplayName("답변 데이터 생성 후 저장")
	void answer(){
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		Answer a = new Answer();
		a.setContent("이것도 잘 수정되었네요.");
		a.setQuestion(q);
		a.setCreateDate(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	@DisplayName("답변 조회")
	void answerList(){
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId());
	}
}
