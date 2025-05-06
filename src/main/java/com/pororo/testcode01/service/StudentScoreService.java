package com.pororo.testcode01.service;

import com.pororo.testcode01.MyCalculator;
import com.pororo.testcode01.controller.response.ExamFailStudentResponse;
import com.pororo.testcode01.controller.response.ExamPassStudentResponse;
import com.pororo.testcode01.model.StudentFail;
import com.pororo.testcode01.model.StudentPass;
import com.pororo.testcode01.model.StudentScore;
import com.pororo.testcode01.repository.StudentFailRepository;
import com.pororo.testcode01.repository.StudentPassRepository;
import com.pororo.testcode01.repository.StudentScoreRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentScoreService {
  private final StudentScoreRepository studentScoreRepository;
  private final StudentPassRepository studentPassRepository;
  private final StudentFailRepository studentFailRepository;

  public void saveScore(
      String studentName, String exam, Integer korScore, Integer englishScore, Integer mathScore) {
    StudentScore studentScore =
        StudentScore.builder()
            .exam(exam)
            .studentName(studentName)
            .korScore(korScore)
            .englishScore(englishScore)
            .mathScore(mathScore)
            .build();

    studentScoreRepository.save(studentScore);

    MyCalculator calculator = new MyCalculator(0.0);
    Double avgScore =
        calculator
            .add(korScore.doubleValue())
            .add(englishScore.doubleValue())
            .add(mathScore.doubleValue())
            .divide(3.0)
            .getResult();

    if (avgScore >= 60) {
      StudentPass studentPass =
          StudentPass.builder().exam(exam).studentName(studentName).avgScore(avgScore).build();

      studentPassRepository.save(studentPass);
    } else {
      StudentFail studentFail =
          StudentFail.builder().exam(exam).studentName(studentName).avgScore(avgScore).build();

      studentFailRepository.save(studentFail);
    }
  }

  public List<ExamPassStudentResponse> getPassStudentsList(String exam) {
    List<StudentPass> studentPasses = studentPassRepository.findAll();

    return studentPasses.stream()
        .filter((pass) -> pass.getExam().equals(exam))
        .map((pass) -> new ExamPassStudentResponse(pass.getStudentName(), pass.getAvgScore()))
        .toList();
  }

  public List<ExamFailStudentResponse> getFailStudentsList(String exam) {
    List<StudentFail> studentFails = studentFailRepository.findAll();

    return studentFails.stream()
        .filter((fail) -> fail.getExam().equals(exam))
        .map((fail) -> new ExamFailStudentResponse(fail.getStudentName(), fail.getAvgScore()))
        .toList();
  }
}
