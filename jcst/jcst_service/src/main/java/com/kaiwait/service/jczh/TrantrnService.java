package com.kaiwait.service.jczh;


import java.util.List;

import com.kaiwait.bean.jczh.entity.Trantrn;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.TrantrnInput;

import net.sf.jasperreports.engine.JRException;

public interface TrantrnService {
	//振替登录页面初始化
	List<Trantrn> searchTrantrnList(TrantrnInput inputParam);
	//振替确认页面初始化
	TrantrnInput TrantrnQueryList (TrantrnInput inputParam);
	//振替承认
	int TrantrnApproval (TrantrnInput inputParam);
	//新增
	String TrantrnAddTx(TrantrnInput inputParam);
	//删除
	int TrantrnDeleteTx(TrantrnInput inputParam);
	//振替修改
	String TrantrnUpdateTx(TrantrnInput inputParam);
	//页面初始化
	TrantrnInput initTrantrnList(TrantrnInput trantrn);
	//页面初始化
	TrantrnInput initLendtrnList(TrantrnInput trantrn);
	//振替取消
	int cancelTranTx(TrantrnInput trantrn);
	//インプット確認票（振替）
	String TrantrnConfirmPDF(OutPutInput jobLand) throws JRException;
	//経費振替承認票
	String TrantrnApprovalPDF(OutPutInput jobLand) throws JRException;
}
