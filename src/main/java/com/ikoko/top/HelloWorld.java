/**
* @(#)HelloWorld.java 2016年11月8日
* 
* Copyright 2000-2016 by ChinanetCenter Corporation.
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
package com.ikoko.top;
/**
 * 描述：
 * 
 * @author chenlt
 */
public class HelloWorld {
    private String name;
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void printHello() {
        System.out.println("Spring 3 : Hello ! " + name);
    }
}
