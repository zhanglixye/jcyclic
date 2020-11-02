package com.kaiwait.service.mst.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.RandomStringUtils;
import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.entity.Company;
import com.kaiwait.bean.mst.entity.Payeetaxmst;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.entity.SysMessage;
import com.kaiwait.bean.mst.io.CompanyMstInput;
import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.bean.mst.vo.CompanyVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.SpringUtil;
import com.kaiwait.mappers.mst.CommonmstMapper;
import com.kaiwait.mappers.mst.CompanyMapper;
import com.kaiwait.mappers.mst.RoleMapper;
import com.kaiwait.mappers.mst.UserMapper;
import com.kaiwait.service.mst.CompanyService;

/**   
*    
* 项目名称：common_service   
* 类名称：CompanyServiceImpl   
* 类描述：   
* 创建人：刘实  
* 创建时间：2018年5月14日 下午1:02:23   
* @version        
*/
@Service
public class CompanyServiceImpl implements CompanyService {
	@Resource
	private CompanyMapper companyMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private CommonmstMapper commonMapper;
	@Resource
	private RoleMapper roleMapper;

	
	
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	SimpleDateFormat df2 = new SimpleDateFormat("yyyy-MM-dd");
	//Date date = new Date();
	public Map<String,Object> insertCompanyTx(CompanyMstInput inputParam) throws Exception {
		// TODO Auto-generated method stub
		String nowDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
		Company company = inputParam.getCompany();
		company.setAddDate(nowDate);
//		company.setUpdDate(nowDate);
		String logUsercd = inputParam.getUserID();
		company.setAddUsercd(logUsercd);
//		company.setUpUsercd(logUsercd);
		companyMapper.insertCompany(company);
		int companycd =company.getCompany_cd();
		
		//随机生成4位
		boolean flg = true;
		String logId = companycd+"Admin";
		for(int i=0;i<10;i++) {
				String str = RandomStringUtils.randomAlphanumeric(4);
				logId = logId+str;
				int isHave = companyMapper.isExcistLogName(logId);
				if(isHave>0) {
					flg = false;
					continue;
				}else {
					flg = true;
					break;
				}
				
		}
		if(!flg) {
			return null;
		}
	    SysMessage sMessage =  companyMapper.getSysMessage();
		String pwd =sMessage.getPwd();
		String userName = "管理员";
		String departcd =sMessage.getDepartcd();
		String levelcd = sMessage.getLevelcd();
		//根据 传过来的语言 设置新用户名的语言
		switch(company.getCommon_language()) {
		case "001":
			userName = sMessage.getUnamejp();
			break;
		case "003":
			userName = sMessage.getUnamehk();
			break;
		case "002":
			userName = sMessage.getUname();
			break;
		case "004":
			userName = sMessage.getUnameen();
			break;
		}
		int roleId = 1 ;
		//根据公司类型赋予不同权限
		switch(company.getCompany_type()) {
		case 0:
			roleId = 3;
			break;
		case 1:
			roleId = 2;
			break;
		case 2:
			roleId = 1;
			break;
		}
		UserInfoInput userVo =  new UserInfoInput();
		userVo.setLoginName(logId);
		userVo.setPwd(SpringUtil.encrypt(pwd));
		userVo.setNickname(userName);
		userVo.setLangtyp(company.getCommon_language());
		userVo.setDepartCD(departcd);
		userVo.setLevel(levelcd);
		userVo.setCompanyID(Integer.toString(companycd));
		userVo.setDelFlg("0");
		userVo.setMemberID(companycd+"0001");
		userVo.setAddDatetime(nowDate);
		userVo.setUpDatetime(nowDate);
		userVo.setUserID(inputParam.getUserID());
		userMapper.insertUserTx(userVo);
		roleMapper.addUserRoleTrn(Integer.toString(userVo.getUserIDBysql()), roleId,nowDate,
				logUsercd);
		userMapper.addUserCampany(Integer.toString(userVo.getUserIDBysql()), companycd,nowDate,
				logUsercd);
		List<Role> roleList = roleMapper.getRoleMstID();
		for(Role role:roleList) {
			role.setIsOn(1);
			role.setCompanycd(companycd);
		}
		roleMapper.insertRoleComOrderTrn(roleList);
		List<CommonmstVo> commonVo = commonMapper.selectBaseCommon();
		String currencyCode = company.getCurrency_code();
		
		String timeNum = company.getTimenum();
		if(timeNum==null||"".equals(timeNum)) {
			timeNum = "0"; 
		}
		for(int i=0;i<commonVo.size();i++) {
				commonVo.get(i).setAdddate(nowDate);
				commonVo.get(i).setUpddate(nowDate);
				commonVo.get(i).setUpdusercd(inputParam.getUserID());
				commonVo.get(i).setAddusercd(inputParam.getUserID());
				commonVo.get(i).setCompany_cd(companycd);
				if("0050".equals(commonVo.get(i).getMstcd())&&currencyCode.equals(commonVo.get(i).getItemcd())) {
				commonVo.get(i).setDel_flg("1");
				continue;
				}
				if("0006".equals(commonVo.get(i).getMstcd())) {
				commonVo.get(i).setDel_flg("1");
				continue;
				}
				if("0015".equals(commonVo.get(i).getMstcd())&&"002".equals(commonVo.get(i).getItemcd())) {
					int pay= company.getPay_skip();
					if(pay == 1) {
						commonVo.get(i).setDel_flg("1");
					}else {
						commonVo.get(i).setDel_flg("0");
					}	
				}
				if("0010".equals(commonVo.get(i).getMstcd())&&"003".equals(commonVo.get(i).getItemcd())) {
					int confirm = company.getConfirm_skip();
					if(confirm == 1) {
						commonVo.get(i).setDel_flg("1");
					}else {
						commonVo.get(i).setDel_flg("0");
					}
				}
				
				//会计年月等于  当前日期 +  时差小时
				if("0001".equals(commonVo.get(i).getMstcd())) {
					String now = DateUtil.getNewTime(nowDate, Integer.valueOf(timeNum));
					Date d = DateUtil.stringtoDate(now,DateUtil.dateFormat);
					commonVo.get(i).setItmvalue(df2.format(d));
					commonVo.get(i).setDel_flg("1");
				}
	
		}
		List<Payeetaxmst> payList = new ArrayList<Payeetaxmst>();
		
		String [] arr = {"901","902"};
		for(int i=0;i<arr.length;i++) {
			Payeetaxmst payMst = new Payeetaxmst();
			payMst.setUser_tax_type(arr[i]);
			payMst.setInvoice_type(arr[i]);
			payMst.setInvoice_text(arr[i]);
			payMst.setVat_rate("0.06");
			payMst.setDeduction("1");
			payMst.setDel_flg("0");
			payMst.setCompanycd(companycd);
			payMst.setAddDate(nowDate);
			//payMst.setUpdDate(nowDate);
			payMst.setAddUsercd(logUsercd);
			//payMst.setUpUsercd(logUsercd);
			payList.add(payMst);
		}
		companyMapper.insertPayeeTaxMst(payList);
		int num = commonMapper.insertNewByCd(commonVo);
		if(num>0) {
			Map<String,Object> map =new HashMap<String,Object>();
			map.put("usercd",userVo.getUserIDBysql());
			map.put("companycd",companycd);
			map.put("logId", logId);
			map.put("pwd", pwd);
			return map;
		}else {
			return null;
		}
		
	}
	public List<Company> selectCompany(String usercd,int companycd,String company_full_name,Integer del_flg){
		// TODO Auto-generated method stub
		List<Role> roleList = userMapper.searchNodeListByUser(usercd,String.valueOf(companycd));
		Company company = companyMapper.getUpdateCompany(companycd);
		int ccd =0;
		int companyTyp = 99999;
		for(Role role:roleList) {
			if(role.getNodeID()==84) {
				
				break;
			}
			if(role.getNodeID()==85) {
				companyTyp=company.getCompany_type();
				break;
			}
			if(role.getNodeID()==86) {
				ccd=companycd;
				break;
			}
		}
		
			return companyMapper.selectCompany(companyTyp,this.changSqlInput(company_full_name), del_flg,ccd);
		
		
	}

	public Integer deleteFlgTx(Company company) {
		// TODO Auto-generated method stub
		return companyMapper.deleteFlg(company);
	}

	public Integer updateCompanyTx(CompanyMstInput inputParam){
		// TODO Auto-generated method stub
		Company company = inputParam.getCompany();
		Date date = new Date();
		//要更新的 公司信息
		inputParam.setCompany_cd(company.getCompany_cd());
		if(this.getUpdateCompany(inputParam)==null) {
			return 540;
		}
		Company cp = this.getUpdateCompany(inputParam);
		int cpDBLockFlg = cp.getCompanyLockFlg();
		int cpPreLockFlg = company.getCompanyLockFlg()+1;
		if(!(cpPreLockFlg>cpDBLockFlg)) {
			return 540;
		}
			
			List<Role>	rootList = userMapper.searchNodeListByUser(inputParam.getUserID(), String.valueOf(company.getCompany_cd()));
	        boolean flg =true;
	        //接受companytype防止下边变空
	        int conpanytype = company.getCompany_type();
			if(!isHavePower(rootList,88)) {
    			 ////没有基本情报权限 加遮罩
				company.setCompany_name("");
				company.setCompany_full_name("");
				company.setCompany_name_en("");
				company.setCommon_language("");
				company.setTime_zone("");
				company.setCurrency_code("");
				company.setCompany_type(null);
				company.setNumber_rules(null);
				company.setStart_month(null);
//				company.setMd_skip(null);
//				company.setCost_skip(null);
//				company.setPay_skip(null);
//				company.setConfirm_skip(null);
				company.setDel_flg(null);
				flg = false;
		    }
		    if(!isHavePower(rootList,89)&&!isHavePower(rootList,90)&&!isHavePower(rootList,91)) {
		    ////没有基本情报权限 加遮罩		
		    	company.setOrder_company_name("");
		    	company.setOrder_company_name_en("");
		    	company.setOrder_company_name_hk("");
		    	company.setOrder_company_name_jp("");
		    	
		    	company.setOrder_location("");
		    	company.setOrder_location_en("");
		    	company.setOrder_location_hk("");
		    	company.setOrder_location_jp("");
		    	
		    	company.setOrder_location1("");
		    	company.setOrder_location1_en("");
		    	company.setOrder_location1_hk("");
		    	company.setOrder_location1_jp("");
		    	
		    	company.setOrder_location2("");
		    	company.setOrder_location2_en("");
		    	company.setOrder_location2_hk("");
		    	company.setOrder_location2_jp("");
		    	
		    	company.setTelnumber("");
		    	company.setTelnumberen("");
		    	company.setTelnumberhk("");
		    	company.setTelnumberjp("");
		    	
		    	company.setRegistration_automatic(null);
		    	company.setAuto_month("");
		    	company.setAuto_day("");
		    	flg = false;
		    }
		    	 if(!isHavePower(rootList,89)&&isHavePower(rootList,90)) {
		    		 if(conpanytype == 1) {
		    			
		    		 }else {
		    			////没有基本情报权限 加遮罩	
		    			 company.setOrder_company_name("");
		 		    	company.setOrder_company_name_en("");
		 		    	company.setOrder_company_name_hk("");
		 		    	company.setOrder_company_name_jp("");
		 		    	
		 		    	company.setOrder_location("");
		 		    	company.setOrder_location_en("");
		 		    	company.setOrder_location_hk("");
		 		    	company.setOrder_location_jp("");
		 		    	
		 		    	company.setOrder_location1("");
		 		    	company.setOrder_location1_en("");
		 		    	company.setOrder_location1_hk("");
		 		    	company.setOrder_location1_jp("");
		 		    	
		 		    	company.setOrder_location2("");
		 		    	company.setOrder_location2_en("");
		 		    	company.setOrder_location2_hk("");
		 		    	company.setOrder_location2_jp("");
		 		    	
		 		    	company.setTelnumber("");
		 		    	company.setTelnumberen("");
		 		    	company.setTelnumberhk("");
		 		    	company.setTelnumberjp("");
		 		    	
		 		    	company.setRegistration_automatic(null);
				    	company.setAuto_month("");
				    	company.setAuto_day("");
		 		    	flg = false; 
		    		 }
		    			 
		    	 }
		    	 if(!isHavePower(rootList,89)&&!isHavePower(rootList,90)&&isHavePower(rootList,91)) {
		    		 if(company.getCompany_cd() == inputParam.getCompany_cd()) {
		    			
		    		 }else {
		    			////没有基本情报权限 加遮罩	
		    			 company.setOrder_company_name("");
		 		    	company.setOrder_company_name_en("");
		 		    	company.setOrder_company_name_hk("");
		 		    	company.setOrder_company_name_jp("");
		 		    	
		 		    	company.setOrder_location("");
		 		    	company.setOrder_location_en("");
		 		    	company.setOrder_location_hk("");
		 		    	company.setOrder_location_jp("");
		 		    	
		 		    	company.setOrder_location1("");
		 		    	company.setOrder_location1_en("");
		 		    	company.setOrder_location1_hk("");
		 		    	company.setOrder_location1_jp("");
		 		    	
		 		    	company.setOrder_location2("");
		 		    	company.setOrder_location2_en("");
		 		    	company.setOrder_location2_hk("");
		 		    	company.setOrder_location2_jp("");
		 		    	
		 		    	company.setTelnumber("");
		 		    	company.setTelnumberen("");
		 		    	company.setTelnumberhk("");
		 		    	company.setTelnumberjp("");
		 		    	
		 		    	company.setRegistration_automatic(null);
				    	company.setAuto_month("");
				    	company.setAuto_day("");
		 		    	flg = false; 
		    		 }
		    			 
		    	 }
		    	 	if(flg) {
		    	 	company.setUpdDate(df.format(date));
				    company.setUpUsercd(inputParam.getUserID());
					company.setCompanyLockFlg(cpPreLockFlg);
					int pay= company.getPay_skip();
					Commonmst  commonmst = new Commonmst();
					commonmst.setCompany_cd(company.getCompany_cd());
					commonmst.setUpddate(date);
					commonmst.setUpdusercd(inputParam.getUserID());
					if(pay == 1) {
						commonmst.setDel_flg("1");
						commonmst.setMstcd("0015");
						commonmst.setItemcd("002");
						commonMapper.updateComdelflg(commonmst);	
					}else {
						commonmst.setDel_flg("0");
						commonmst.setMstcd("0015");
						commonmst.setItemcd("002");
						commonMapper.updateComdelflg(commonmst);	
					}
					int confirm = company.getConfirm_skip();
					if(confirm == 1) {
						commonmst.setDel_flg("1");
						commonmst.setMstcd("0010");
						commonmst.setItemcd("003");
						commonMapper.updateComdelflg(commonmst);	
					}else {
						commonmst.setDel_flg("0");
						commonmst.setMstcd("0010");
						commonmst.setItemcd("003");
						commonMapper.updateComdelflg(commonmst);	
					}
		    	 	}
					return companyMapper.updateCompany(company);
	}
	//循环集合
	public boolean isHavePower(List<Role> roleList,int roleID) {
		boolean flg = false;
		for(Role role : roleList) {
			if(roleID==role.getNodeID()) {
				flg = true;
				break;
			}
		}
		return flg;
	}
	public CompanyVo madeGet(){
		// TODO Auto-generated method stub
		CompanyVo cpVo = new CompanyVo();
		cpVo.setCl_list(commonMapper.selectMstFromSysCom("0002"));
		cpVo.setCc_list(commonMapper.selectMstFromSysCom("0050"));
		cpVo.setTz_list(commonMapper.selectMstFromSysCom("0006"));
		return cpVo;
	}
	public Company getUpdateCompany(CompanyMstInput inputParam) {
		// TODO Auto-generated method stub
	     if(inputParam.getCompany_cd()==null) {
	    	 return null;
	     }
		int upCompanycd = inputParam.getCompany_cd();
		Company upCompany = companyMapper.getUpdateCompany(upCompanycd);
		if(upCompany==null) {
			return upCompany;
		}
		String usercd = inputParam.getUserID();
		String myCompanycd = inputParam.getCompanyID();
		List<Role> roleList = userMapper.searchNodeListByUser(usercd,myCompanycd);
		int upCompanyType = upCompany.getCompany_type();
		boolean flg = true;
		for(Role role:roleList) {
			if(role.getNodeID()==84) {
				
				break;
			}
			if(role.getNodeID()==85) {
				if(upCompanyType==2) {
					flg=false;
				}
				break;
			}
			if(role.getNodeID()==86) {
				if(Integer.parseInt(myCompanycd)!=upCompanycd) {
					flg=false;
				}
				break;
			}
		}
		if(!flg) {
			return null;
		}
		return upCompany;
	}
	public String changSqlInput(String oldword) {
		if(oldword!=null&&!"".equals(oldword)) {
			String changekeyword=oldword.replace("_", "\\_");
			String finalkeyword = changekeyword.replace("%", "\\%");
			return finalkeyword;
		}else {
			return null;
		}
	}
	
}
