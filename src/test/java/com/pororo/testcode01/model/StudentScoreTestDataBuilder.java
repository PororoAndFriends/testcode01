package com.pororo.testcode01.model;

public class StudentScoreTestDataBuilder {

    public static StudentScore.StudentScoreBuilder passed(){

        return StudentScore
                .builder()
                .korScore(80)
                .englishScore(100)
                .mathScore(90)
                .studentName("pororo")
                .exam("examtest");
    }

    public static StudentScore.StudentScoreBuilder failed(){

        return StudentScore
                .builder()
                .korScore(20)
                .englishScore(50)
                .mathScore(50)
                .studentName("pororo")
                .exam("examtest");
    }
}
