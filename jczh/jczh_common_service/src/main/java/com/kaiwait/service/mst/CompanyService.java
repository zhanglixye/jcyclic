package com.kaiwait.service.mst;

import java.util.List;
import java.util.Map;

import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.bean.mst.io.CompanyMstInput;
import com.kaiwait.bean.mst.vo.CompanyVo;

/**   
*    
* 项目名称：common_service   
* 类名称：CompanyService   
* 类描述：   
* 创建人：刘实   
* 创建时间：2018年5月14日 下午12:57:55   
* @version        
*/
public interface CompanyService {
   /** 
 * @Title:insertCompanyTx
 * @Description: 登陆一条公司信息
 * @param company
 * @return Integer
 * @throws Exception 
 */
	Map<String,Object> insertCompanyTx(CompanyMstInput inputParam) throws Exception;
   /** 
 * @Title:updateCompanyTx
 * @Description:更新公司信息 
 * @param company
 * @return Integer
 */
Integer updateCompanyTx(CompanyMstInput inputParam);
   /** 
 * @Title:deleteFlgTx
 * @Description:修改公司状态 
 * @param company
 * @return Integer
 */
Integer deleteFlgTx(Company company) ;
   /** 
 * @Title:selectCompany
 * @Description:查询公司信息
 * @param company_full_name
 * @param del_flg
 * @return List<Company>
 * @throws Exception 
 */
List<Company> selectCompany(String usercd,int companycd,String company_full_name,Integer del_flg);
   /** 
 * @Title:getUpdateCompany
 * @Description:获取需要更新的公司信息
 * @param company_cd
 * @return Company
 */
Company  getUpdateCompany(CompanyMstInput inputParam);
   /** 
 * @Title:madeGet
 * @Description:获取会社登陆页面下拉
 * @return List<String>
 */
CompanyVo   madeGet();
public String changSqlInput(String oldword);
	
}
