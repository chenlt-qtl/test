
/**
* @(#)FormMap.java 2017年6月22日
*
* Copyright 2000-2017 by ChinanetCenter Corporation.
*
* All rights reserved.
*
* This software is the confidential and proprietary information of
* ChinanetCenter Corporation ("Confidential Information"). You
* shall not disclose such Confidential Information and shall use
* it only in accordance with the terms of the license agreement
* you entered into with ChinanetCenter.
*
*/

package com.ikoko.top.word.dto;

import java.util.List;

import com.ikoko.top.word.entity.Word;

/**
 * 描述:
 * @author chenlt
 */

public class WordListForm {
    private List<Word> words;

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

}
