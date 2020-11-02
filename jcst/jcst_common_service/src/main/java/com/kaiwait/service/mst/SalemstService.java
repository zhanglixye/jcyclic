package com.kaiwait.service.mst;

import java.util.List;

import com.kaiwait.bean.mst.entity.Salemst;
import com.kaiwait.bean.mst.io.SalemstInput;
import com.kaiwait.bean.mst.vo.SalemstVo;

/** 
* @ClassName: CommonmstService 
* @Description: commonmst模块service成的接口类
* @author mayouyi 
* @date 2017年11月14日 上午8:43:29 
*  
*/
public interface SalemstService {
	/** 
	* @Title: selectAll 
	* @Description: 查询所有的commonmst 
	* @param    设定文件 
	* @return List<Commonmst>    
	* @throws 
	*/
	List<SalemstVo> select(String sale_cd,String sale_account_cd,String sale_name,String del_flg,String companyID);
	
	/**
	 * @return  
	* @Title: insertNewTx 
	* @Description: 为commonmst增加一条新的纪录 
	* @param  monmst    
	* @return void    
	* @throws 
	*/
	int insertNewTx(Salemst monmst);
	
	/**
	 * @return  
	* @Title: updateOldComTx 
	* @Description: commonmst更新一条旧有纪录 
	* @param  monmst    
	* @return void    
	* @throws 
	*/
	int updateOldSaleTx(Salemst monmst);
	
	
	/** 
	* @Title: deleteComTx 
	* @Description: commonmst删除一条旧有纪录 
	* @param  monmst    
	* @return void    
	* @throws 
	*/
	void deleteSaleTx(Salemst monmst);
	
	/** 
	* @Title: selectVatRateByCD 
	* @Description: 查询旧有增值税  
	* @param  sale_cd    
	* @return List<SalemstVo>    
	* @throws 
	*/	
	
	List<SalemstVo> selectVatRateByCD(String sale_cd,String companyID);
	
	/** 
	* @Title: selectVatRateByCD 
	* @Description: 查询旧有税率       
	* @param  sale_cd    
	* @return List<SalemstVo>    
	* @throws 
	*/	
	
	List<SalemstVo> selectRateByCD(String sale_cd,String companyID);
	
	/** 
	* @Title: selectVatRateByCD 
	* @Description: 初始化 查询下拉框并生成新sale_cd 
	* @param  sale_cd    
	* @return List<SalemstVo>    
	* @throws 
	*/	
	
	SalemstVo initSalescategory(SalemstInput Salemst);
	
	
	/** 
	* @Title: selectNewRate 
	* @Description: 根据cd查询壳上更新条目的数据，并显示 
	* @param  sale_cd    
	* @return List<SalemstVo>    
	* @throws 
	*/	
	Object salemstUpdInit(String sale_cd,String companyID);
	
	
	
}
