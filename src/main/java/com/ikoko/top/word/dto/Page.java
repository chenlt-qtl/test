
/**
* @(#)PageBean.java 2016年12月12日
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

package com.ikoko.top.word.dto;

/**
 * 描述:
 * @author chenlt
 */

public class Page {
	private int pagesize;
    private int pagebegin;
    private int count;
    
    public Page(int start,int limit){
    	this.setPagebegin(start);
    	this.setPagesize(limit);
    }
    
	public int getPagesize() {
		return pagesize;
	}
	public void setPagesize(int pagesize) {
		this.pagesize = pagesize;
	}
	public int getPagebegin() {
		return pagebegin;
	}
	public void setPagebegin(int pagebegin) {
		this.pagebegin = pagebegin;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
    
}
