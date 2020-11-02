package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.common.dao.BaseMapper;
/** 
* @ClassName: CommonmstMapper 
* @Description: commonmst模块dao层与xml对应的接口文档
* @author mayouyi
* @date 2017年11月14日 下午2:47:29 
*  
*/
public interface CommonmstMapper extends BaseMapper{
	
  
	/** 
	* @Title: selectAll 
	* @Description: 查询所有的commonmst(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return List<Commonmst>    返回类型 
	* @throws 
	*/
	List<CommonmstVo> selectAll(@Param("company_cd")String company_cd);
	//List<Commonmst> selectAll(@Param("cldivcd") String cldivcd, @Param("divnm") String divnm, @Param("del_flg") String del_flg,@Param("classify_cd") String classify_cd);
    
	
	/** 
	* @Title: selectComByCd 
	* @Description: 更新commonmst时，前置commonmst信息查询 
	* @param     mstcd 
	* @return List<Commonmst>    返回类型 
	* @throws 
	*/
	List<CommonmstVo> selectComByCd(@Param("mstcd")String mstcd, @Param("itemcd")String itemcd,@Param("company_cd")Integer company_cd);
	
	/** 
	* @Title: insertNew 
	* @Description: 为commonmst新增一条信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	int insertNew(Commonmst monmst);
	
	/** 
	* @Title: updateOldComTx 
	* @Description: 为commonmst更新一条旧有信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateOldCom(Commonmst monmst);
	
	/** 
	* @Title: updateOldComTx 
	* @Description: 为commonmst删除一条旧有信息（即更新del_flag值为0）
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void deleteCom(Commonmst monmst);
	
	
	/**
	 * @param pd  
	* @Title: selectMstNameByCD
	* @Description: 根据主表编号(mstcd)和系统标号(itemcd)查询所有主表分类名
	* @param    mstcd 
	*  @param    itemcd 
	* @return List<Commonmst>    
	* @throws 
	*/
	List<CommonmstVo> selectMstNameByCD(@Param("mstcd")String mstcd,@Param("company_cd")Integer company_cd);
	List<CommonmstVo> selectMstFromSysCom(@Param("mstcd")String mstcd);
	//去除原价不明
	List<CommonmstVo> selectMstNameByCDNO(@Param("mstcd")String mstcd,@Param("company_cd")Integer company_cd);
	
	/** 
	* @Title: insertNewByCd
	* @Description: 根绝 company_cd插入数据
	* @param    monmstVo 
	*  @param    company_cd 
	* @return Integer 
	* @throws 
	*/
	Integer insertNewByCd(@Param("monmstVo")List<CommonmstVo> monmstVo);
	/** 
	* @Title: selectBaseCommon
	* @Description: 查询系统共同
	* @return List<CommonmstVo> 
	* @throws 
	*/
	List<CommonmstVo> selectBaseCommon();
	int editSystemLock(@Param("companyID")String companyID,@Param("sysLockFlg")int sysLockFlg,@Param("userID")String userID,@Param("addDate")String addDate);
	Integer getLockFlg(@Param("mstcd")String mstcd,@Param("itemcd")String itemcd,@Param("company_cd")Integer company_cd);
	int updateComdelflg (Commonmst commonmst);


	List<CommonmstVo> selectDepartList(@Param("mstcd")String mstcd,@Param("company_cd")Integer company_cd, @Param("pd")String pd);
}