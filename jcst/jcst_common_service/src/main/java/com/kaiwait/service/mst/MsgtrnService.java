package com.kaiwait.service.mst;



import com.kaiwait.bean.mst.io.MsgtrnInput;

public interface MsgtrnService {
	//留言板查询
	MsgtrnInput searchMsgtrnList(MsgtrnInput inputParam);
	//留言板新增
	void addMsgtrnTx (MsgtrnInput inputParam);
	//留言板删除
	int DeleteMsgtrnTx (MsgtrnInput inputParam);
	//留言板修改
	int UpdateMsgtrnTx (MsgtrnInput inputParam);
	
}
