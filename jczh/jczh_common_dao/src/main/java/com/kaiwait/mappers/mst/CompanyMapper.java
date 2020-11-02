package com.kaiwait.mappers.mst;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.bean.mst.entity.Payeetaxmst;
import com.kaiwait.bean.mst.entity.SysMessage;
import com.kaiwait.common.dao.BaseMapper;

/**   
*    
* 项目名称：common_dao   
* 类名称：CompanyMapper   
* 类描述：  
* 创建人：刘实   
* 创建时间：2018年5月14日 下午12:35:52   
* @version        
*/
public interface CompanyMapper extends BaseMapper{
	/** 
	* @Title: insertCompany
	* @Description: 登陆一条公司信息
	* @param company 
	* @return Integer  插入条数
	*/
	Integer insertCompany(Company company);

	/** 
	 * @Title:updateCompany
	 * @Description:修改公司信息
	 * @param company
	 * @return 修改条数
	 */
	Integer updateCompany(Company company);
    /** 
     * @Title:deleteFlg
     * @Description:修改公司的状态 0：有效 1无效
     * @param company
     * @return 修改条数
     */
    Integer deleteFlg(Company company);
	   /** 
	 * @Title:selectCompany
	 * @Description:根据条件查询 公司信息
	 * @param String company_full_name Integer del_flg 
	 * @return 符合条件的公司集合
	 */
	List<Company>  selectCompany(@Param("companyType")int companyType,@Param("company_full_name")String company_full_name,@Param("del_flg")Integer del_flg,@Param("companycd")int companycd);
//	List<Company>  selectCompanyTz(@Param("company_full_name")String company_full_name,@Param("del_flg")Integer del_flg);
//	List<Company>  selectCompanyCc(@Param("company_full_name")String company_full_name,@Param("del_flg")Integer del_flg);
	   /** 
	 * @Title:getUpdateCompany
	 * @Description:根据 company_cd 查询公司信息
	 * @param company_cd
	 * @return  一条公司信息
	 */
	Company  getUpdateCompany(@Param("company_cd")Integer company_cd);
	 /** 
	 * @Title:madeGet
	 * @Description:获取页面下拉
	 * @return List<String>
	 */
//	List<String>  madeGet();
	int insertPayeeTaxMst(@Param("payeetax")List<Payeetaxmst> payeetax);
	SysMessage getSysMessage();
	int isExcistLogName(@Param("testLoginId")String testLoginId);

	void upCompany(@Param("itemcd")String itemcd,@Param("company_cd")Integer company_cd);

	int getIsCompanyInvoiceFlg(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
	String getSystemDate(@Param("companyID")int companyID);

	void insertListColumnMst(@Param("companycd")int companycd);

	int getcompanyType(@Param("companyID") String companyID);

}
