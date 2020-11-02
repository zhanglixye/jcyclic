package com.kaiwait.webclient.vo.json;

/**
 * Json值对象基类
 * 
 * @author wanght
 *
 */
public class BaseJsonObject {
	public static final String META_FIELD = "metaInfo";
	public static final String DATA_FIELD = "data";
	
	/**
	 * 元信息
	 */
	private MetaInfo metaInfo;
    
    /**
     * 数据对象
     */
    private Object data;

    
    /**
     * 创建新实例
     * @return 对象实例
     */
    public static BaseJsonObject newInstance() {
    	MetaInfo metaInfo = new MetaInfo();
    	BaseJsonObject jsonObject = new BaseJsonObject();
    	jsonObject.setMetaInfo(metaInfo);
    	return jsonObject;
    }
    
    public MetaInfo getMetaInfo() {
		return metaInfo;
	}

	public void setMetaInfo(MetaInfo metaInfo) {
		this.metaInfo = metaInfo;
	}

	/**
     * @return the data
     */
    public Object getData() {
        return data;
    }

    /**
     * @param data the data to set
     */
    public void setData(Object data) {
        this.data = data;
    }

}
