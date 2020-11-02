/**
 * 
 */
package com.kaiwait.common.vo.json.base;

public enum Result {
	/** 正常终止*/
    OK,
    /** 系统异常*/
    SYSTEM_ERROR,
    /** 业务异常*/
    BUSINESS_ERROR,
    /** 校验没通过*/
    VALIDATE_ERROR;
}
