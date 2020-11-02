package com.kaiwait.service.jczh;

import java.util.List;
import java.util.Map;

import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.TitleMsg;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.bean.jczh.vo.CostListVo;

import net.sf.jasperreports.engine.JRException;

public interface CostService {

	Map<String,Object> insertCostOutSoureTx(CostInput inputParam);
	 //发注初始化
	 Map<String,Object> initOutSoure(CostInput inputParam);
	 //发票初始化
	 Map<String,Object> initInvoice(CostInput inputParam);
	 //查询税率
	 Cost selectTax(CostInput inputParam);
	 
	 //发注初始化
	 Map<String,Object> initCostupdate(CostInput inputParam);	 
	 Map<String, Object>  updateCostOutSoureTx(CostInput inputParam);
	 //原价一览
	 Object getCostListVo(CostListVo clvo,int companycd,String usercd);
	 //原价一览页面加载 支付状态下拉
	 Map<String,Object> pageLoad(CostListVo clvo,int company_cd,String usercd,String ad);
     //支付处理
	 int payDealTx(PayInput pay);	 
	/**   
	 * @Title: deleteOutSoure
	 * @Description: TODO(这里用一句话描述这个方法的作用)
	 * @param @param inputParam
	 * @return int    返回类型 
	 * @author: "马有翼"
	 * @throws
	 */  
	int deleteOutSoure(CostInput inputParam);
	List<Cost> selectCLDIV(String jobcd,String company_cd);
    int updateTitleUpTx(List<TitleMsg> titleMsg);
    
	String OrderPDFTx(OutPutInput inputParam)throws JRException;
	int updPdftimeTx(CostInput inputParam);
	Object outOrderPDFTx(OutPutInput inputParam) throws JRException; 
	
}
