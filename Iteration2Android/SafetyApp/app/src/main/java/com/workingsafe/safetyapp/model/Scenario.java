package com.workingsafe.safetyapp.model;

public class Scenario {
    int imageId;
    boolean isQuestion;
    String scenarioNumber;

    public Scenario(){}
    public Scenario(int imageId, boolean isQuestion,String scenarioNumber) {
        this.imageId = imageId;
        this.isQuestion = isQuestion;
        this.scenarioNumber = scenarioNumber;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public boolean isQuestion() {
        return isQuestion;
    }

    public void setQuestion(boolean question) {
        isQuestion = question;
    }

    public String getScenarioNumber() {
        return scenarioNumber;
    }

    public void setScenarioNumber(String scenarioNumber) {
        this.scenarioNumber = scenarioNumber;
    }
}
