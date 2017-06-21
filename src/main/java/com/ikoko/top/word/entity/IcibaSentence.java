package com.ikoko.top.word.entity;

import com.ikoko.top.sys.entity.DataEntity;

public class IcibaSentence  extends DataEntity<IcibaSentence> {
    private String wordId;
    private String orig;
    private String trans;
    public String getWordId() {
        return wordId;
    }
    public void setWordId(String wordId) {
        this.wordId = wordId;
    }
    public String getOrig() {
        return orig;
    }
    public void setOrig(String orig) {
        this.orig = orig;
    }

    public String getTrans() {
        return trans;
    }
    public void setTrans(String trans) {
        this.trans = trans;
    }
}