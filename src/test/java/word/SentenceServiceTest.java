
/**
* @(#)SentenceServiceTest.java 2017年6月13日
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

import org.junit.Test;

import com.ikoko.top.word.service.SentenceService;

/**
 * 描述:
 * @author chenlt
 */

public class SentenceServiceTest {
    
    @Test
    public void testAnalyArticle() throws IOException{
        SentenceService service = new SentenceService();
        service.analyArticle("Phoebe: Just, 'cause, I don't want her to go through what I went through with Carl- oh!Monica: Okay, everybody relax. This is not even a date. It's just two people going out to dinner and- not having sex.Chandler: Sounds like a date to me.");
    }

}
