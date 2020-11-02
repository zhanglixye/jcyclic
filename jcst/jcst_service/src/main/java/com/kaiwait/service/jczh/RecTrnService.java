package com.kaiwait.service.jczh;


import java.util.List;


import com.kaiwait.bean.jczh.entity.RecTrn;
import com.kaiwait.bean.jczh.io.RecTrnInput;
/** 
* @ClassName: RecTrnService 
* @Description: 入金操作
* @author mayouyi 
* @date 2018年8月8日 上午10:50:29 
*  
*/
public interface RecTrnService {

	/**
	* 方法名 RecTrnUpdateTx
	* 方法的说明  更新入金表数据
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnUpdateTx(List<RecTrnInput> inputParam);
	/**
	* 方法名 RecTrnInsertTx
	* 方法的说明  插入入金表数据
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnInsertTx(List<RecTrnInput> inputParam);
	/**
	* 方法名 RecTrnDeleteDateTx
	* 方法的说明  设置时间为空
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnDeleteDateTx(List<RecTrnInput> inputParam);
	
	public RecTrn getRecAll(String jobcd,int companycd);
	

}
