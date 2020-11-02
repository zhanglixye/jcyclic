/**
 * 
 */
package com.kaiwait.webclient.vo.json;

import java.util.Map;

/**
 * @author wanght
 *
 */
public class MetaInfo {
	/**
	 * 处理结果
	 */
	private Result result;

	/**
	 * 数据版本信息 <br>
	 * 客户端需保持每一个请求的版本信息，通过该参数发送至服务器端，服务器端得到处理结果后，通过该参数将新的版本信息返回至客户端。<br>
	 * 客户端初次提交请求时，不需要设置该参数<br>
	 * 服务器端通过该参数判断是否可以使用客户端的缓存
	 */
	private Long dataVersion;

	/**
	 * 客户端令牌<br>
	 */
	private String token;

	/**
	 * 服务器返回给客户端的消息
	 */
	private String message;

	/**
	 * 数据对象的全类型名
	 */
	private String dataType;

	/**
	 * 附加信息,备用
	 */
	private Map<String, String> attachInfo;
	
	/**
	 * @return the result
	 */
	public Result getResult() {
		return result;
	}

	/**
	 * @param result
	 *            the result to set
	 */
	public void setResult(Result result) {
		this.result = result;
	}

	/**
	 * @return the dataVersion
	 */
	public Long getDataVersion() {
		return dataVersion;
	}

	/**
	 * @param dataVersion
	 *            the dataVersion to set
	 */
	public void setDataVersion(Long dataVersion) {
		this.dataVersion = dataVersion;
	}

	/**
	 * @return the token
	 */
	public String getToken() {
		return token;
	}

	/**
	 * @param token
	 *            the token to set
	 */
	public void setToken(String token) {
		this.token = token;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public Map<String, String> getAttachInfo() {
		return attachInfo;
	}

	public void setAttachInfo(Map<String, String> attachInfo) {
		this.attachInfo = attachInfo;
	}

}
