package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Salemst;
import com.kaiwait.bean.mst.vo.SalemstVo;
import com.kaiwait.common.dao.BaseMapper;
/** 
* @ClassName: CommonmstMapper 
* @Description: commonmst模块dao层与xml对应的接口文档
* @author mayouyi
* @date 2017年11月14日 下午2:47:29 
*  
*/
public interface SalemstMapper extends BaseMapper{
	
  
	/**
	 * @param companyID  
	* @Title: selectAll 
	* @Description: 查询所有的commonmst(这里用一句话描述这个方法的作用) 
	* @param @return    设定文件 
	* @return List<Commonmst>    返回类型 
	* @throws 
	*/
	List<SalemstVo> select(@Param("sale_cd") String sale_cd,@Param("sale_account_cd") String sale_account_cd,@Param("sale_name") String sale_name,@Param("del_flg") String del_flg,@Param("companyID") String companyID);
	
    
	/** 
	* @Title: insertSalemst 
	* @Description: 为Salemst新增一条信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertSalemst(Salemst monmst);
	
	/** 
	* @Title: insertSaleRatemst 
	* @Description: 为SaleRatemst 新增一条信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertSaleRatemst(Salemst monmst);
	
	/** 
	* @Title: insertVatRate 
	* @Description: 为vatratemst 新增一条信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void insertVatRate(Salemst monmst);
	/** 
	* @Title: updateOldComTx 
	* @Description: 为Salemst更新一条旧有信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void updateOldSalemst(Salemst monmst);
	
	/** 
	* @Title: updateOldSaleRatemst 
	* @Description: 为Salemst更新一条旧有信息
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	
	void updateOldSaleRatemst(@Param("end_date") String end_date,@Param("sale_cd") String sale_cd,@Param("company_cd") String company_cd,@Param("start_date") String start_date);
	void updateOldVatRatemst(@Param("end_date") String end_date,@Param("sale_cd") String sale_cd,@Param("company_cd") String company_cd,@Param("start_date") String start_date);
	
	/** 
	* @Title: updateOldComTx 
	* @Description: 为commonmst删除一条旧有信息（即更新del_flag值为0）
	* @param @param monmst    设定文件 
	* @return void    返回类型 
	* @throws 
	*/
	void deleteSale(Salemst monmst);
	
	/** 
	* @Title: selectVatRateByCD 
	* @Description: 查询旧有增值税
	* @param @param sale_cd 
	* @return List<SalemstVo>    返回类型 
	* @throws 
	*/
	List<SalemstVo> selectVatRateByCD(@Param("sale_cd") String sale_cd,@Param("companyID") String companyID);
	
	
	/** 
	* @Title: selectVatRateByCD 
	* @Description: 查询旧有税率
	* @param @param sale_cd 
	* @return List<SalemstVo>    返回类型 
	* @throws 
	*/
	List<SalemstVo> selectRateByCD(@Param("sale_cd") String sale_cd,@Param("companyID") String companyID);
	
	
	/** 
	* @Title: selectMaxCd 
	* @Description: 查询sale_cd的最大值
	* @param @param  
	* @return String    返回类型 
	* @throws 
	*/
	 String  selectMaxCd();
	 
	/** 
	* @Title: selectNewVatRate 
	* @Description: 根据cd 查询数据库中增值税率最新一条
	* @param @param  
	* @return String    返回类型 
	* @throws 
	*/	 
	 Salemst selectNewVatRate(@Param("sale_cd") String sale_cd,@Param("companyID") String companyID);
	 
	 /** 
	* @Title: selectSalerate 
	* @Description: 根据sale_cd查询旧有壳上信息
	* @param @param  
	* @return String    返回类型 
	* @throws 
	*/	 
	 Salemst selectSalerate(@Param("sale_cd") String sale_cd,@Param("companyID") String companyID);
	 
	 /** 
	* @Title: selectRateDate 
	* @Description: 根据sale_cd查询税率最新一条
	* @param @param  
	* @return String    返回类型 
	* @throws 
	*/
	 Salemst selectRateDate(@Param("sale_cd") String sale_cd,@Param("companyID") String companyID);
	 
	 int SaleAccountVa(@Param("sale_account_cd") String sale_account_cd,@Param("sale_cd") String sale_cd,@Param("company_cd") String company_cd);
	 
	 int selJobtrnSaleNum(@Param("sale_cd") String sale_cd);
	 SalemstVo searchTaxNameByLang(@Param("companyID") String companyID);
	
}