package com.kaiwait.common.vo.json.base;

public class MessageWarp {
	public static final String META_FIELD = "metaInfo";
	public static final String DATA_FIELD = "data";
	public static final String REQUEST_MST_NAME = "mst";
	public static final String REQUEST_JCZH_NAME = "jczh";
	public static final String REQUEST_JCJP_NAME = "jcjp";
	
	private MessageMeta metaInfo;
	private Object data;

	public MessageMeta getMetaInfo() {
		return metaInfo;
	}
	public void setMetaInfo(MessageMeta metaInfo) {
		this.metaInfo = metaInfo;
	}
	public Object getData() {
		return data;
	}
	public void setData(Object data) {
		this.data = data;
	}
	
}
