package com.kaiwait.service.jczh;

import java.util.Map;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.PayInput;

import net.sf.jasperreports.engine.JRException;

/**   
 * @ClassName:  PayService   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: "马有翼" 
 * @date:   2018年9月19日 下午2:45:01   
 *     
 * @Copyright: 2018 www.tydic.com Inc. All rights reserved. 
 * 注意：本内容仅限于海和信息技术（大连）有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface PayService {

	 /**   
	 * @Title: insertPayInfoTx
	 * @Description: 插入支付情报信息(这里用一句话描述这个方法的作用)
	 * @param inputParam
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	Map<String, Object> insertPayInfoTx(PayInput inputParam);
	 /**   
	 * @Title: initPayInfo
	 * @Description: 支付情报页面初始化(这里用一句话描述这个方法的作用)
	 * @param inputParam
	 * @return
	 * @return Map<String,Object>  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	Map<String,Object> initPayInfo(CostInput inputParam);
	 //发票初始化
	 /**   
	 * @Title: initInvoice
	 * @Description: 发票初始化(这里用一句话描述这个方法的作用)
	 * @param inputParam
	 * @return
	 * @return Map<String,Object>  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	Map<String,Object> initInvoice(CostInput inputParam);
	
	 /**   
	 * @Title: updatePayInfoTx
	 * @Description: 更新支付情报信息(这里用一句话描述这个方法的作用)
	 * @param inputParam
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	Map<String, Object> updatePayInfoTx(PayInput inputParam);
	 
	 /**   
	 * @Title: deletePayInfoTx
	 * @Description: 删除支付情报信息(这里用一句话描述这个方法的作用)
	 * @param inputParam
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int deletePayInfoTx(PayInput inputParam);
	
	String PayConfirmOutPDF(OutPutInput inputParam)throws JRException;

}
