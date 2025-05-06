package com.pororo.testcode01;

import java.util.stream.Stream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

public class MyCalculatorRepeatableTest {

  @RepeatedTest(5)
  void repeatedAddTest() {
    // AAA 패턴

    // Arrange : 준비
    MyCalculator myCalculator = new MyCalculator(10.0);

    // Act : 행동
    myCalculator.add(10.0);

    // Assert : 단언/검증
    Assertions.assertEquals(20.0, myCalculator.getResult());
  }

  @ParameterizedTest
  @MethodSource("parameterizedTestParameters")
  void parameterizedTest(Double addValue, Double expected) {
    MyCalculator myCalculator = new MyCalculator(0.0);
    myCalculator.add(addValue);
    Assertions.assertEquals(expected, myCalculator.getResult());
  }

  public static Stream<Arguments> parameterizedTestParameters() {
    return Stream.of(
        Arguments.of(10.0, 10.0), Arguments.of(13.0, 13.0), Arguments.of(100.0, 100.0));
  }
}
