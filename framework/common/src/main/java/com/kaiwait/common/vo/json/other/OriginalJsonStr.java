package com.kaiwait.common.vo.json.other;

public class OriginalJsonStr {
	private String jsonStr;

	public OriginalJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	/**
	 * @return the jsonStr
	 */
	public String getJsonStr() {
		return jsonStr;
	}

	/**
	 * @param jsonStr the jsonStr to set
	 */
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return jsonStr;
	}
}
