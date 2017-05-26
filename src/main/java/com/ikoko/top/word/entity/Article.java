package com.ikoko.top.word.entity;

import com.ikoko.top.sys.entity.DataEntity;

public class Article  extends DataEntity<Article> {
    private static final long serialVersionUID = 1L;
    
    private byte[] mp3;
    private String title;
    private int hasMp3;
    private Integer wordNum;
    
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

    public Integer getWordNum() {
        return wordNum;
    }

    public void setWordNum(Integer wordNum) {
        this.wordNum = wordNum;
    }

}