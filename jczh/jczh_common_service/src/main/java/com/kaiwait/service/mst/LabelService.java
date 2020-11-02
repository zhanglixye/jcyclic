package com.kaiwait.service.mst;


import com.kaiwait.bean.mst.io.LabelInput;

public interface LabelService {
	//标签查询
	LabelInput searchLabelList(LabelInput inputParam);
	//标签新增
	int addLabelTx (LabelInput inputParam);
	//标签删除
	int deleteLabelTx (LabelInput inputParam);
	//标签修改
	int UpdateLabelTx (LabelInput inputParam);
	String getMaxKeyCode(String keyFlg,int company_cd);
}
