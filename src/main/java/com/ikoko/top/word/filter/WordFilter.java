
/**
* @(#)WordFilter.java 2017年6月9日
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

package com.ikoko.top.word.filter;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 描述:
 * @author chenlt
 */

public class WordFilter extends TokenFilter {

    private CharTermAttribute termAttribute;
    
    public WordFilter(TokenStream input) {
        super(input);
        termAttribute = addAttribute(CharTermAttribute.class);
    }

    
    @Override
    public final boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }
        String small = termAttribute.toString();
        
        Pattern pattern = Pattern.compile("^(-?\\d+)(\\.\\d+)?$"); 
        Matcher matcher = pattern.matcher(small); 
        //过滤掉带'符号的单词
        if (small.contains("'")||matcher.matches()) {
            termAttribute.setEmpty();
        }
        return true;
    }

}
