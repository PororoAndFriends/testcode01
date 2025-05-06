package com.pororo.testcode01.controller.request;

public record SaveExamScoreRequest(
    String studentName, Integer korScore, Integer engScore, Integer mathScore) {}
