package com.kaiwait.mappers.mst;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Payeetaxmst;
import com.kaiwait.common.dao.BaseMapper;

public interface PayeeTaxMapper extends BaseMapper{
/*    int deleteByPrimaryKey(Integer id);
*/

	
	Map<String,Object> querymaxno();
	
	//原价税率登录	
	int insertPayeetaxmstTx(Payeetaxmst payeetaxmst);
	//原价税率修改
	int updatePayeetaxmstTx(Payeetaxmst payeetaxmst);
	//
	int updateLock(Payeetaxmst payeetaxmst);
	//页面初始化1
	List<Map<String,Object>> Selectinitone(String id);
	
	//成本税率列表
	List<Payeetaxmst> queryPayeeTaxList(@Param("companycd")String companycd,@Param("delflg")String delflg);
	
	//跳转到原价税率登录重新查询数据
	Payeetaxmst changeQuery(Payeetaxmst payeetaxmst);
	int payeeCheck(@Param("user_tax_type")String user_tax_type,@Param("invoice_type")String invoice_type,@Param("invoice_text")String invoice_text,@Param("company_cd")String company_cd);
	int payeeLock(@Param("id")String id);
	int queryHave(@Param("user_tax_type")String user_tax_type,@Param("invoice_type")String invoice_type,@Param("invoice_text")String invoice_text,@Param("companyID")String companyID,@Param("id")String id);
/*    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);*/
}