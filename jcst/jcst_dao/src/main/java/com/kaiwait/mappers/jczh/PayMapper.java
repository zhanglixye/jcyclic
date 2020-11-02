package com.kaiwait.mappers.jczh;


import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Pay;
import com.kaiwait.bean.jczh.entity.PayConfirmPDF;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.io.PayInput;
import com.kaiwait.common.dao.BaseMapper;

/**   
 * @ClassName:  PayMapper   
 * @Description:TODO(这里用一句话描述这个类的作用)   
 * @author: "马有翼" 
 * @date:   2018年9月19日 下午2:38:46     
 * @Copyright: 2018 KAIWAIT. All rights reserved. 
 * 注意：本内容仅限于海和信息技术（大连）有限公司内部传阅，禁止外泄以及用于其他的商业目 
 */
public interface PayMapper extends BaseMapper{
	
	/**   
	 * @Title: insertPayInfoTx
	 * @Description: 插入支付情报嘻嘻(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int insertPayInfoTx(PayInput payInput);
	/**   
	 * @Title: updatePayInfoTx
	 * @Description: 更新支付情报信息(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int updatePayInfoTx(PayInput payInput);
	/**   
	 * @Title: updateCostTx
	 * @Description: 插入外发信息时，更新costtrn表中的inputno字段(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int updateCostTx(PayInput payInput);
	/**   
	 * @Title: insertInvoiceintrnTx
	 * @Description: 插入发票信息(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int insertInvoiceintrnTx(PayInput payInput);
	/**   
	 * @Title: updateInvoiceintrnTx
	 * @Description: 更新发票信息(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int updateInvoiceintrnTx(PayInput payInput);
	/**   
	 * @Title: selectPayInfo
	 * @Description: 查询支付情报信息(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return List<Pay>  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	List<Pay> selectPayInfo(CostInput payInput);
	
	/**   
	 * @Title: deletePayInfoTx
	 * @Description: 删除(这里用一句话描述这个方法的作用)
	 * @param payInput
	 * @return
	 * @return int  返回值  
	 * @author: "马有翼"
	 * @throws
	 */  
	int deletePayInfoTx(PayInput payInput);
	
	PayConfirmPDF PayConfirmOutPDF(OutPutInput payInput);
	int getPayLockFlg(@Param("inputno")String inputno,@Param("companycd")int companycd);
	
	int updatePayREQTx(PayInput payInput);
}