package com.pororo.testcode01.service;

import com.pororo.testcode01.controller.response.ExamFailStudentResponse;
import com.pororo.testcode01.controller.response.ExamPassStudentResponse;
import com.pororo.testcode01.model.StudentFail;
import com.pororo.testcode01.model.StudentFailFixture;
import com.pororo.testcode01.model.StudentPass;
import com.pororo.testcode01.model.StudentPassFixture;
import com.pororo.testcode01.model.StudentScore;
import com.pororo.testcode01.model.StudentScoreTestDataBuilder;
import com.pororo.testcode01.repository.StudentFailRepository;
import com.pororo.testcode01.repository.StudentPassRepository;
import com.pororo.testcode01.repository.StudentScoreRepository;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;

class StudentScoreServiceMockTest {

  private StudentScoreService studentScoreService;
  private StudentScoreRepository studentScoreRepository;
  private StudentPassRepository studentPassRepository;
  private StudentFailRepository studentFailRepository;

  @BeforeEach
  public void beforeEach() {
    studentScoreRepository = Mockito.mock(StudentScoreRepository.class);
    studentPassRepository = Mockito.mock(StudentPassRepository.class);
    studentFailRepository = Mockito.mock(StudentFailRepository.class);
    studentScoreService =
        new StudentScoreService(
            studentScoreRepository, studentPassRepository, studentFailRepository);
  }

  @Test
  @DisplayName("성적 저장 검증 로직 : 60점 이상인 경우")
  public void saveScoreMockTest() {

    // given
    String givenStudentName = "pororo";
    String givenExam = "test";
    Integer givenKorScore = 80;
    Integer givenEnglishScore = 100;
    Integer givenMathScore = 60;

    // when
    studentScoreService.saveScore(
        givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

    // then -> when의 상황이 실행되었을때 각각의 메서드가 몇 번씩 호출되어야 하는지 검증
    Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(studentPassRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(studentFailRepository, Mockito.times(0)).save(Mockito.any());
  }

  @Test
  @DisplayName("성적 저장 검증 로직 : 60점 미만인 경우")
  public void thirdScoreMockTest() {

    // given
    String givenStudentName = "pororo";
    String givenExam = "test";
    Integer givenKorScore = 30;
    Integer givenEnglishScore = 20;
    Integer givenMathScore = 60;

    // when
    studentScoreService.saveScore(
        givenStudentName, givenExam, givenKorScore, givenEnglishScore, givenMathScore);

    // then
    Mockito.verify(studentScoreRepository, Mockito.times(1)).save(Mockito.any());
    Mockito.verify(studentPassRepository, Mockito.times(0)).save(Mockito.any());
    Mockito.verify(studentFailRepository, Mockito.times(1)).save(Mockito.any());
  }

  @Test
  @DisplayName("합격자 명단 가져오기 검증")
  void getPassStudentsList() {
    // given
    String givenTestExam = "testexam";
    StudentPass studentPass = StudentPassFixture.create("pororo", "testexam");
    StudentPass studentPass2 = StudentPassFixture.create("crong", "testexam");
    StudentPass studentPass3 = StudentPassFixture.create("eddy", "exam123");

    Mockito.when(studentPassRepository.findAll())
        .thenReturn(List.of(studentPass, studentPass2, studentPass3));

    List<ExamPassStudentResponse> list =
        Stream.of(studentPass, studentPass2)
            .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
            .toList();
    List<ExamPassStudentResponse> passStudentsList =
        studentScoreService.getPassStudentsList(givenTestExam);

    Assertions.assertIterableEquals(list, passStudentsList);
  }

  @Test
  @DisplayName("불합격자 명단 가져오기 검증")
  void getFailStudentsList() {

    // given
    String givenTestExam = "testexam";
    StudentFail studentFail = StudentFailFixture.create("pororo", "testexam");
    StudentFail studentFail2 = StudentFailFixture.create("crong", "testexam");
    StudentFail studentFail3 = StudentFailFixture.create("eddy", "exam123");

    Mockito.when(studentFailRepository.findAll())
        .thenReturn(List.of(studentFail, studentFail2, studentFail3));

    List<ExamFailStudentResponse> list =
        Stream.of(studentFail, studentFail2)
            .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
            .toList();
    List<ExamFailStudentResponse> passStudentsList =
        studentScoreService.getFailStudentsList(givenTestExam);

    Assertions.assertIterableEquals(list, passStudentsList);
  }

  @Test
  @DisplayName("성적 저장 검증 로직 : 인자값 검증")
  public void saveScoreMockArgTest() {
    // given
    StudentScore expactedStudentScore = StudentScoreTestDataBuilder.passed().build();

    StudentPass expectStudentPass = StudentPassFixture.create(expactedStudentScore);

    // when
    studentScoreService.saveScore(
        expactedStudentScore.getStudentName(),
        expactedStudentScore.getExam(),
        expactedStudentScore.getKorScore(),
        expactedStudentScore.getEnglishScore(),
        expactedStudentScore.getMathScore());

    ArgumentCaptor<StudentScore> studentScoreArgumentCaptor =
        ArgumentCaptor.forClass(StudentScore.class);
    ArgumentCaptor<StudentPass> studentPassArgumentCaptor =
        ArgumentCaptor.forClass(StudentPass.class);

    // then
    Mockito.verify(studentScoreRepository, Mockito.times(1))
        .save(studentScoreArgumentCaptor.capture());
    StudentScore capturedStudentScore = studentScoreArgumentCaptor.getValue();

    Assertions.assertEquals(
        expactedStudentScore.getStudentName(), capturedStudentScore.getStudentName());
    Assertions.assertEquals(expactedStudentScore.getExam(), capturedStudentScore.getExam());
    Assertions.assertEquals(expactedStudentScore.getKorScore(), capturedStudentScore.getKorScore());
    Assertions.assertEquals(
        expactedStudentScore.getEnglishScore(), capturedStudentScore.getEnglishScore());
    Assertions.assertEquals(
        expactedStudentScore.getMathScore(), capturedStudentScore.getMathScore());

    Mockito.verify(studentPassRepository, Mockito.times(1))
        .save(studentPassArgumentCaptor.capture());
    StudentPass value = studentPassArgumentCaptor.getValue();
    Assertions.assertEquals(expectStudentPass.getStudentName(), value.getStudentName());
    Assertions.assertEquals(expectStudentPass.getExam(), value.getExam());
    Assertions.assertEquals(expectStudentPass.getAvgScore(), value.getAvgScore());
  }
}
