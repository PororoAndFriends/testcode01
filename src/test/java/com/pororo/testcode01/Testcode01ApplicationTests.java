package com.pororo.testcode01;

import com.pororo.testcode01.model.StudentScoreFixture;
import com.pororo.testcode01.repository.StudentScoreRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class Testcode01ApplicationTests extends IntegrationTest {

  @Autowired private StudentScoreRepository studentScoreRepository;

  @Autowired private EntityManager entityManager;

  @Test
  void mysqlTest() {
    var studentScore = StudentScoreFixture.passed();
    var savedStudentScore = studentScoreRepository.save(studentScore);

    entityManager.flush(); // flush를 해도 트랜잭션은 여전히 활성화 상태 -> 트랜잭선 롤백 가능
    entityManager.clear();

    var queryStudentScore =
        studentScoreRepository.findById(savedStudentScore.getId()).orElseThrow();

    Assertions.assertEquals(savedStudentScore.getId(), queryStudentScore.getId());
    Assertions.assertEquals(savedStudentScore.getStudentName(), queryStudentScore.getStudentName());
    Assertions.assertEquals(savedStudentScore.getMathScore(), queryStudentScore.getMathScore());
    Assertions.assertEquals(
        savedStudentScore.getEnglishScore(), queryStudentScore.getEnglishScore());
    Assertions.assertEquals(savedStudentScore.getKorScore(), queryStudentScore.getKorScore());
  }
}
