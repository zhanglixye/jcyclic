package com.kaiwait.service.mst;


import com.kaiwait.bean.mst.entity.Payeetaxmst;
import com.kaiwait.bean.mst.io.PayeeTaxAddInput;

import java.util.List;

public interface PayeeTaxService {
	//原价税率登录
	int insertPayeetaxmstTx(Payeetaxmst payeetaxmst);
	//原价税率修改
	int updatePayeetaxmstTx(Payeetaxmst payeetaxmst);
	//查询主键号码
	String querymaxno();
	//页面初始化
	PayeeTaxAddInput initSalescategory(PayeeTaxAddInput payeetaxmst);
	//成本税率列表
	List<Payeetaxmst> queryPayeeTaxList(PayeeTaxAddInput payeetaxmst);
}
