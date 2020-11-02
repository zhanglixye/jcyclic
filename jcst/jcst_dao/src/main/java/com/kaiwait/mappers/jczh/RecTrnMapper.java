package com.kaiwait.mappers.jczh;


import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.RecTrn;
import com.kaiwait.bean.jczh.io.RecTrnInput;
import com.kaiwait.common.dao.BaseMapper;

public interface RecTrnMapper extends BaseMapper{
	
	/**
	* 方法名 RecTrnUpdateTx
	* 方法的说明  更新入金表数据
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnUpdate1Tx(RecTrnInput inputParam);
	/**
	* 方法名 RecTrnUpdateTx
	* 方法的说明  更新入金表数据多条
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnUpdateMoreTx(RecTrnInput inputParam);
	/**
	* 方法名 RecTrnInsertTx
	* 方法的说明  插入入金表数据
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnInsertTx(RecTrnInput inputParam);
	/**
	* 方法名 RecTrnDeleteDateTx
	* 方法的说明  更新入金表时间为空
	* @param List<RecTrnInput>
	* @return int 
	* @author 马有翼
	* @date 2018.08.08
	*/
	int RecTrnDeleteDateTx(RecTrnInput inputParam);
	
	RecTrn getRec(@Param("jobcd") String jobcd,@Param("companycd")int companycd);
	RecTrn getRecAll(@Param("jobcd") String jobcd,@Param("companycd")int companycd);
	
}