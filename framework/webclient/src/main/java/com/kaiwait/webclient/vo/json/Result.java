/**
 * 
 */
package com.kaiwait.webclient.vo.json;

/**
 * @author wanght
 *
 */
public enum Result {
	/** 正常终止*/
    OK,
    /** 服务器端业务异常*/
    BUSINESS_ERROR,
    /** 要求客户端使用本地缓存，这种情况下将不会返回具体数据*/
    USE_CACHE,
    /**没有权限，限制访问*/
    FORBIDDEN,
    /** 没有登录，或者会话过期，需要重新发送登录请求*/
    LOGIN,
    /** 服务器端数据校验没通过，这种情况下将通过message属性返回详细错误信息*/
    VAIDATE_ERROR;
}
