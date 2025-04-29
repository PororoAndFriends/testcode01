package com.pororo.testcode01;

import com.pororo.testcode01.MyCalculator.ZeroDivisionException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyCalculatorTest {

    @Test
    void addTest() {
        // AAA 패턴

        // Arrange : 준비
        MyCalculator myCalculator = new MyCalculator(10.0);

        // Act : 행동
        myCalculator.add(10.0);

        // Assert : 단언/검증
        Assertions.assertEquals(20.0, myCalculator.getResult());
    }

    @Test
    void minusTest() {
        // GWT 패턴

        // Given : 준비
        MyCalculator myCalculator = new MyCalculator(10.0);

        // When : 행동
        myCalculator.minus(5.0);

        // Then : 단언/검증
        Assertions.assertEquals(5.0, myCalculator.getResult());
    }

    @Test
    void multiplyTest() {
        MyCalculator myCalculator = new MyCalculator(2.0);
        myCalculator.multiply(2.0);

        Assertions.assertEquals(4.0, myCalculator.getResult());
    }

    @Test
    void divideTest() {
        MyCalculator myCalculator = new MyCalculator(4.0);
        myCalculator.divide(2.0);

        Assertions.assertEquals(2.0, myCalculator.getResult());
    }

    @Test
    void complicatedCalculateTest() {
        MyCalculator myCalculator = new MyCalculator(4.0);
        Double result = myCalculator
                .add(10.0)
                .minus(5.0)
                .multiply(2.0)
                .divide(2.0)
                .getResult();

        Assertions.assertEquals(9.0, result);
    }

    @Test
    void zeroDivisionTest() {
        // given
        MyCalculator myCalculator = new MyCalculator(4.0);

        // when & then
        Assertions.assertThrows(ZeroDivisionException.class, () -> myCalculator.divide(0.0));
    }

}