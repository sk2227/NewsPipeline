package com.sahilkamat.model;

public class Article {
    private String title;
    private String content;
    private String translatedTitle;

    public Article(String title, String content,String translatedTitle) {
        this.title = title;
        this.content = content;
        this.translatedTitle=translatedTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTranslatedTitle() {
        return translatedTitle;
    }

    public void setTranslatedTitle(String translatedTitle) {
        this.translatedTitle = translatedTitle;
    }

    @Override
    public String toString() {
        return "Article[" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                ", translatedTitle='" + translatedTitle + '\'' +
                ']';
    }
}
