
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

package word;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;

/**
 * 描述:
 * @author chenlt
 */

public class WordFilter extends TokenFilter {

    Map<String, String> courtesyTitleMap = new HashMap<>();
    private CharTermAttribute termAttribute;
    
    protected WordFilter(TokenStream input) {
        super(input);
        termAttribute = addAttribute(CharTermAttribute.class);
        courtesyTitleMap.put("Dr", "doctor");
        courtesyTitleMap.put("Mr", "mister");
        courtesyTitleMap.put("Mrs", "miss");
    }

    
    @Override
    public final boolean incrementToken() throws IOException {
        if (!input.incrementToken()) {
            return false;
        }
        String small = termAttribute.toString();
        if (courtesyTitleMap.containsKey(small)) {
            termAttribute.setEmpty().append(courtesyTitleMap.get(small));
        }
        return true;
    }

}
