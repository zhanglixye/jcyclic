package com.kaiwait.service.jczh;


import java.util.List;

import com.kaiwait.bean.jczh.entity.Lendtrn;
import com.kaiwait.bean.jczh.entity.Prooftrn;
import com.kaiwait.bean.jczh.io.LendtrnInput;
import com.kaiwait.bean.jczh.io.OutPutInput;

import net.sf.jasperreports.engine.JRException;

public interface LendtrnService {
	//立替登录页面初始化
	List<Lendtrn> searchLendtrnList(LendtrnInput inputParam);
	//立替确认页面初始化
	LendtrnInput LendtrnQueryList (LendtrnInput inputParam);
	//立替承认
	int LendtrnApproval (LendtrnInput inputParam);
	//新增
	String LendtrnAddTx(LendtrnInput inputParam);
	//删除
	int LendtrnDeleteTx(LendtrnInput inputParam);
	//立替修改
	String LendtrnUpdateTx(LendtrnInput inputParam);
	//
	String QueryJobstatus(String job_cd,String companyid);
	//页面初始化
	LendtrnInput initLendtrnList(LendtrnInput Lendtrn);
	//初始化
	List<Prooftrn> getProoftrnList(String companycd,String inputno);
	//立替承认取消
	int cancelLendTrnTx(LendtrnInput Lendtrn);
	//インプット確認票（立替）
	String LendtrnConfirmPDF(OutPutInput jobLand) throws JRException;
	//立替原価承認票
	String LendtrnApprovalPDF(OutPutInput jobLand) throws JRException;
	
}
