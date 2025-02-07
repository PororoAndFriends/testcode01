package com.pororo.testcode01;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class MyCalculatorTest {

    @Test
    void addTest() {
        MyCalculator myCalculator = new MyCalculator();
        myCalculator.add(10.0);

        Assertions.assertEquals(10.0, myCalculator.getResult());
    }

    @Test
    void minus() {
    }

    @Test
    void multiply() {
    }

    @Test
    void divide() {
    }
}