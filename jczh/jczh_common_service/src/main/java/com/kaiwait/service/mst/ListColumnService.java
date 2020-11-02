package com.kaiwait.service.mst;


import com.kaiwait.bean.mst.io.ListColumnInput;

public interface ListColumnService {
	//标签查询
	ListColumnInput searchListColumnList(ListColumnInput inputParam);
//	//留言板新增
//	void addListColumnTx (ListColumnInput inputParam);
//	//留言板删除
//	void deleteListColumnTx (ListColumnInput inputParam);
	//标签修改
	int ListColumnUpdateTx(ListColumnInput inputParam);
	
}
