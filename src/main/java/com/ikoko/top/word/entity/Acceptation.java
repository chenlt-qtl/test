package com.ikoko.top.word.entity;

import com.ikoko.top.sys.entity.DataEntity;

public class Acceptation  extends DataEntity<Acceptation> {
    
    private String wordId;

    private String pos;

    private String acceptation;

    public String getPos() {
        return pos;
    }

    public void setPos(String pos) {
        this.pos = pos;
    }

    public String getAcceptation() {
        return acceptation;
    }
    public void setAcceptation(String acceptation) {
        this.acceptation = acceptation;
    }

    public String getWordId() {
        return wordId;
    }

    public void setWordId(String wordId) {
        this.wordId = wordId;
    }
    
}