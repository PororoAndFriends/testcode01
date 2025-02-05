package com.pororo.testcode01;

public class MyCalculatorApplication {
    public static void main(String[] args) {
        MyCalculator myCalculator = new MyCalculator();

        myCalculator.add(10.0);
        myCalculator.minus(20.0);
        myCalculator.multiply(5.0);
        System.out.println(myCalculator.getResult());

        myCalculator.divide(0.0);

    }
}
