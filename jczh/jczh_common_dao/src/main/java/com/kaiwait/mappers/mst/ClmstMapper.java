package com.kaiwait.mappers.mst;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.bean.mst.entity.Taxtype;
import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.bean.mst.io.ClmstAddInput;
import com.kaiwait.common.dao.BaseMapper;

public interface ClmstMapper extends BaseMapper{

	void delete(int cldivcd);
	
	void update(Clmst clmst);
	
	Map<String,Object> querymaxno();

	/**
	* 方法名 insertClmst
	* 方法的说明 往来单位登录
	* @param Clmst clmst
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertClmst(Clmst clmst);

	/**
	* 方法名 clmstQuery
	* 方法的说明 往来单位列表
	* @param Clmst clmst
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Clmst> clmstQuery(Clmst clmst);

	/**
	* 方法名 insertTaxTypeTx
	* 方法的说明 纳税人种类新增
	* @param List<Taxtype>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertTaxTypeTx(@Param("taxList")List<Taxtype> list,@Param("Cldivcd")int Cldivcd,@Param("UserID")String UserID,@Param("CompanyID")String CompanyID,@Param("AddDate")String AddDate);	

	/**
	* 方法名 deleteTaxTypeTx
	* 方法的说明 纳税人种类删除
	* @param int Cldivcd
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int deleteTaxTypeTx(@Param("Cldivcd")int Cldivcd);	


	/**
	* 方法名 updateClmst
	* 方法的说明 来往单位修改
	* @param 
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int updateClmst(Clmst clmst);
	

	/**
	* 方法名 getWedgemembersleft getWedgemembersright
	* 方法的说明 职员关联信息修改初始化
	* @param String companyID
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<ClmstAddInput> getWedgemembersleft(@Param("Cldivcd")int Cldivcd,@Param("companyID")String companyID);
	
	List<ClmstAddInput> getWedgemembersright(@Param("Cldivcd")int Cldivcd,@Param("companyID")String companyID);

	//
	/**
	* 方法名 insertWedgemembersTx
	* 方法的说明 职员关联信息修改
	* @param List<ClmstAddInput>
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int insertWedgemembersTx(@Param("clietList")List<ClmstAddInput> list,@Param("Cldivcd")int Cldivcd,@Param("addUserID")String addUserID,@Param("companyID")String companyID,@Param("AddDate")String AddDate);

	/**
	* 方法名 deleteWedgemembersTx
	* 方法的说明 职员关联信息修改
	* @param 
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	int deleteWedgemembersTx(@Param("Cldivcd")int Cldivcd,@Param("companyID")String companyID);
	/**
	* 方法名 QueryTaxtype
	* 方法的说明 取引先修改页面纳税人种类查询返回
	* @param String companyID
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Taxtype> QueryTaxtype(@Param("Cldivcd")int Cldivcd,@Param("companyID")int companyID);
	List<Taxtype> QueryTaxtype(@Param("Cldivcd")int Cldivcd,@Param("companyID")int companyID,@Param("divnm")String divnm);

	Map<String,Object> QueryTaxtypes(@Param("Cldivcd")int cldivcd,@Param("companyID")int companyID, @Param("divnm")String divnm);
	
	Map<String,Object> QueryTaxtypesd(@Param("Cldivcd")int cldivcd,@Param("companyID")int companyID, @Param("divnm")String divnm);
	
	/**
	* 方法名 getUserHistoryByInsert
	* 方法的说明 获取最新添加的用户和添加者的信息
	* @param String companyID
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<User> getUserHistoryByInsert(@Param("companyID")String companyID);
	
	/**
	* 方法名 getUserHistoryByInsert
	* 方法的说明 获取最新更新者的用户和添加者的信息
	* @param String companyID
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<User> getUserHistoryByUp(@Param("companyID")String companyID);
	/**
	* 方法名 getUserHistoryByInsert
	* 方法的说明 获取最新更新者的用户和添加者的信息
	* @param String companyID
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	List<Clmst> searchClmstByPop(@Param("companyID")String companyID,@Param("departCD")String departCD,@Param("divnm")String divName,@Param("pd")String pd);
	/**
	* 方法名 deleteTaxTypeTx
	* 方法的说明 财务编号校验
	* @param int Cldivcd
	* @return List<User>
	* @author FQQ
	* @date 2018.05.16
	*/
	String clmstCheck(@Param("account_cd")String account_cd,@Param("companyid")String companyid,@Param("clidivcd")int clidivcd);
	int queryLock(@Param("cldivcd")int cldivcd,@Param("companyid")String companyid);
	int updateLock(@Param("cldivcd")int cldivcd,@Param("companyid")String companyid);
	String querycomm(@Param("companyid")String companyid);
	int TaxTypeUpload(@Param("taxList")List<Taxtype> list,@Param("UserID")String UserID,@Param("CompanyID")String CompanyID,@Param("AddDate")String AddDate);
	int ClmstUpload(@Param("clietList")List<Clmst> list,@Param("addUserID")String addUserID,@Param("companyID")String companyID,@Param("AddDate")String AddDate);
	int queryClientflg(@Param("cldivcd")int cldivcd,@Param("companyid")String companyid);

	//List<Clmst> searchClmstByYY(@Param("companyID")String companyID, @Param("userCD")String userCD);

	List<Clmst> searchClmstByYY(@Param("companyID")String companyID,@Param("pd")String pd,@Param("userCD")String userCD,@Param("divnm")String divName);




}