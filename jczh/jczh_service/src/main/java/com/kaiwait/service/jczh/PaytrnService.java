package com.kaiwait.service.jczh;


import java.util.List;
import java.util.Map;

import com.kaiwait.bean.jczh.entity.Paytrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.io.PaytrnInput;

public interface PaytrnService {
	//立替登录页面初始化
	List<Paytrn> searchPaytrnList(PaytrnInput inputParam);
	//支付承认查询
	PaytrnInput PaytrnQueryList (PaytrnInput inputParam);
	// 支付申请
	int PaytrnApplyTx (PaytrnInput inputParam);
	// 付款信息批准
	int PaytrnApprovalTx (PaytrnInput inputParam);
	// 付款信息驳回
	int PaytrnApprovalBackTx (PaytrnInput inputParam);
	// 付款信息取消
	int PaytrnApprovalCancelTx (PaytrnInput inputParam);
	//新增
	int PaytrnAddTx(PaytrnInput inputParam);
	//立替修改
	int PaytrnUpdateTx(PaytrnInput inputParam);
	//支付承认页面初始化
	PaytrnInput initPaytrnList(PaytrnInput Paytrn);
	//支付承认初始化
	List<Prooftrn> getProoftrnList(String companycd,String inputno);
	//备注查询
	Map<String,Object> getRemarkList(String companycd,String inputno,String jobcd);
	//取引先查询
	Map<String,Object> getClmstList(String companycd,String inputno,String jobcd);
	Paytrn paytrnvatrate(String input_no, String companycd);
}
