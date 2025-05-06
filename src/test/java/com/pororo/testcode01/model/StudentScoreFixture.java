package com.pororo.testcode01.model;

public class StudentScoreFixture {

    public static StudentScore passed(){

        return StudentScore
                .builder()
                .korScore(80)
                .englishScore(100)
                .mathScore(90)
                .studentName("pororo")
                .exam("examtest")
                .build();
    }
}
