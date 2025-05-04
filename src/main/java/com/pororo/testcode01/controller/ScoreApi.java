package com.pororo.testcode01.controller;

import com.pororo.testcode01.controller.request.SaveExamScoreRequest;
import com.pororo.testcode01.controller.response.ExamFailStudentResponse;
import com.pororo.testcode01.controller.response.ExamPassStudentResponse;
import com.pororo.testcode01.service.StudentScoreService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScoreApi {
  private final StudentScoreService studentScoreService;

  @PutMapping("/exam/{exam}/score")
  public void save(@PathVariable("exam") String exam, @RequestBody SaveExamScoreRequest request) {
    studentScoreService.saveScore(
        request.studentName(),
        exam,
        request.korScore(),
        request.engScore(),
        request.mathScore());
  }

  @GetMapping("/exam/{exam}/pass")
  public List<ExamPassStudentResponse> pass(@PathVariable("exam") String exam) {
    return studentScoreService.getPassStudentsList(exam);
  }

  @GetMapping("/exam/{exam}/fail")
  public List<ExamFailStudentResponse> fail(@PathVariable("exam") String exam) {
    return studentScoreService.getFailStudentsList(exam);
  }
}
