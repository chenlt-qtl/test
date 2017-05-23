package com.ikoko.top.word.entity;

public class Article {
    private Long id;
    
    private byte[] mp3;

    private String title;
    
    private int hasMp3;
    
    private int status;
    
    private Integer wordNum;
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getMp3() {
        return mp3;
    }

    public void setMp3(byte[] mp3) {
        this.mp3 = mp3;
    }

    public int getHasMp3() {
        return hasMp3;
    }

    public void setHasMp3(int hasMp3) {
        this.hasMp3 = hasMp3;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

}