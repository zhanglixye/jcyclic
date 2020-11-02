package com.kaiwait.service.mst;


import java.util.List;

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.bean.mst.io.ClmstAddInput;
import com.kaiwait.bean.mst.io.UserInfoInput;

public interface ClmstService {
	void deleteTx(int cldivcd);
	void updateTx(Clmst clmst);
	//原价税率登录
	Object insertClmstTx(ClmstAddInput inputParam);
	//来往单位修改
	Object updateClmstTx(ClmstAddInput inputParam);
	//往来单位列表
	ClmstAddInput clmstQuery(ClmstAddInput inputParam);
	//查询主键号码
	String querymaxno();
	//职员关联信息修改初始化
	ClmstAddInput userclmstQuery(ClmstAddInput inputParam);
	//职员关联信息修改
	int userclmstChangeTx(ClmstAddInput inputParam);
	//纳税人种类init
	ClmstAddInput taxtypeInit(ClmstAddInput inputParam);
	//上传
	Object UploadClmst(ClmstAddInput inputParam);
	/**
	* 方法名 searchClmstByPop
	* 方法的说明 客户弹窗查询
	* @param String companyID,String departCD
	* @return List<Clmst> 
	* @author 王岩
	* @date 2018.06.27
	*/
	List<Clmst> searchClmstByPop(UserInfoInput inputParam);
	//财务编号校验
	String clmstCheck(String account_cd,String companyid,int clidivcd);
}
