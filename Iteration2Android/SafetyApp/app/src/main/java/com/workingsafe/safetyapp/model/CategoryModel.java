package com.workingsafe.safetyapp.model;

public class CategoryModel {
    private String titleImage;
    private String title;

    public CategoryModel(){}

    public CategoryModel(String titleImage, String title) {
        this.titleImage = titleImage;
        this.title = title;
    }

    public String getTitleImage() {
        return titleImage;
    }

    public void setTitleImage(String titleImage) {
        this.titleImage = titleImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
