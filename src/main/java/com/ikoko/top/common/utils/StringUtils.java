
/**
* @(#)StringUtils.java 2017年6月13日
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

package com.ikoko.top.common.utils;

/**
 * 描述:
 * @author chenlt
 */

public class StringUtils {
    
    public static String unCleanXSS(String value) {
        value = value.replaceAll("& lt;","<" ).replaceAll("& gt;", ">" );
        value = value.replaceAll("& #40;", "\\(").replaceAll("& #41;" ,"\\)" );
        value = value.replaceAll("& #39;","'" );
        return value;
    }
}
