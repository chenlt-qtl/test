package com.ikoko.top.word.entity;

import com.ikoko.top.sys.entity.DataEntity;

public class Article  extends DataEntity<Article> {
    private static final long serialVersionUID = 1L;
    
    private String mp3;
    private String title;
    String content;
    private Integer wordNum;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMp3() {
        return mp3;
    }

    public void setMp3(String mp3) {
        this.mp3 = mp3;
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}