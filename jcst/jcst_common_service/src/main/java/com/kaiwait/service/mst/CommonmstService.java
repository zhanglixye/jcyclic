package com.kaiwait.service.mst;

import java.util.List;

import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.vo.CommonmstVo;

/** 
* @ClassName: CommonmstService 
* @Description: commonmst模块service成的接口类
* @author mayouyi 
* @date 2017年11月14日 上午8:43:29 
*  
*/
public interface CommonmstService {
	/** 
	* @Title: selectAll 
	* @Description: 查询所有的commonmst 
	* @param    设定文件 
	* @return List<Commonmst>    
	* @throws 
	*/
	List<CommonmstVo> selectAll(String company);
	
	
	/** 
	* @Title: selectComByCd 
	* @Description: 更新commonmst时，前置commonmst信息查询 
	* @param     mstcd 
	* @return List<Commonmst>    返回类型 
	* @throws 
	*/
	List<CommonmstVo> selectComByCd(String mstcd,String itemcd,Integer company_cd);
	
	/** 
	* @Title: insertNewTx 
	* @Description: 为commonmst增加一条新的纪录 
	* @param  monmst    
	* @return void    
	* @throws 
	*/
	int insertNewTx(Commonmst monmst);
	
	/** 
	* @Title: updateOldComTx 
	* @Description: commonmst更新一条旧有纪录 
	* @param  monmst    
	* @return void    
	* @throws 
	*/
	int updateOldComTx(Commonmst monmst);
	
	
	/** 
	* @Title: deleteComTx 
	* @Description: commonmst删除一条旧有纪录 
	* @param  monmst    
	* @return void    
	* @throws 
	*/
	void deleteComTx(Commonmst monmst);
	
	
	//——————下面的方法问共同方法，有人调用，误删
	/** 
	* @Title: selectMstNameByCD
	* @Description: 根据主表编号(mstcd)和系统标号(itemcd)查询所有主表分类名
	* @param    mstcd 
	*  @param    itemcd 
	* @return List<Commonmst>    
	* @throws 
	*/
	List<CommonmstVo> selectMstNameByCD(String mstcd,Integer company_cd);
	int editSystemLock(String companyID,int sysLockFlg,String userID);
}
