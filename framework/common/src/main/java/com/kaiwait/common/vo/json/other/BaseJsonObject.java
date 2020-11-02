package com.kaiwait.common.vo.json.other;

import com.kaiwait.common.constants.NullObject;

public class BaseJsonObject {
	public static final String META_FIELD = "metaInfo";
	public static final String DATA_FIELD = "data";

	private MetaInfo metaInfo;

	private Object data;

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

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
		if (data != null) {
			this.metaInfo.setDataType(data.getClass().getCanonicalName());
		} else {
			this.metaInfo.setDataType(NullObject.class.getCanonicalName());
		}
	}

}
