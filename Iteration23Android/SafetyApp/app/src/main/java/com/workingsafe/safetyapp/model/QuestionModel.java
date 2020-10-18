package com.workingsafe.safetyapp.model;

public class QuestionModel {
    private String question;
    private String questionA;
    private String questionB;
    private String questionC;
    private String questionD;
    private String questionAns;

    public QuestionModel(){

    }

    public QuestionModel(String question, String questionA, String questionB, String questionC, String questionD, String questionAns) {
        this.question = question;
        this.questionA = questionA;
        this.questionB = questionB;
        this.questionC = questionC;
        this.questionD = questionD;
        this.questionAns = questionAns;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getQuestionA() {
        return questionA;
    }

    public void setQuestionA(String questionA) {
        this.questionA = questionA;
    }

    public String getQuestionB() {
        return questionB;
    }

    public void setQuestionB(String questionB) {
        this.questionB = questionB;
    }

    public String getQuestionC() {
        return questionC;
    }

    public void setQuestionC(String questionC) {
        this.questionC = questionC;
    }

    public String getQuestionD() {
        return questionD;
    }

    public void setQuestionD(String questionD) {
        this.questionD = questionD;
    }

    public String getQuestionAns() {
        return questionAns;
    }

    public void setQuestionAns(String questionAns) {
        this.questionAns = questionAns;
    }
}
