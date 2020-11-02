package com.kaiwait.service.mst.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.ListColumn;
import com.kaiwait.bean.mst.entity.Role;
import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.bean.mst.io.ListColumnInput;
import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.RequestUtil;
import com.kaiwait.core.utils.SpringUtil;
import com.kaiwait.mappers.mst.UserMapper;
import com.kaiwait.mappers.mst.CommonmstMapper;
import com.kaiwait.mappers.mst.ListColumnMapper;
import com.kaiwait.mappers.mst.RoleMapper;
import com.kaiwait.service.mst.UserService;

@Service
public class UserServiceImpl implements UserService{

	@Resource
	private UserMapper userMapper;
	@Resource
	private CommonmstMapper commonMapper;
	@Resource
	private RoleMapper roleMapper;
	@Resource
	private ListColumnMapper listColumnMapper;
	/**
	* 方法名 userInfoInit
	* 方法的说明 用户页面初始化
	* @param String userID,String companyID
	* @return User 
	* @author 王岩
	* @date 2018.05.16
	*/
	public User userInfoInit(String userID,String companyID)
	{
		
		User userInfo = new User();
		String companyTyp = userMapper.getCompanyType(companyID);
		//用userID判断是否为更新页面
		if(!userID.equals(""))
		{
			//更新时查询用户相关信息
			userInfo = userMapper.getUserInitInfo(userID,companyID);
			if(userInfo == null)
			{
				return null;
			}
			userInfo.setCheckedComapnyList(userMapper.getCheckedCompanyList(userID));
			userInfo.setUserRoleList(roleMapper.getRoleListByUserID(userID));
		}
		//公司列表，参照公司用
		userInfo.setComapnyList(userMapper.getCompanyListForUser(companyID));
		//部门列表，下拉选框用
		userInfo.setDepartList(commonMapper.selectMstNameByCD("0005",Integer.valueOf(companyID)));
		//语言列表，下拉选框用
		userInfo.setLangTypList(commonMapper.selectMstNameByCD("0002",Integer.valueOf(companyID)));
		//级别列表，radio
		userInfo.setLevelList(commonMapper.selectMstNameByCD("0004",Integer.valueOf(companyID)));
		//更新者历史记录
		String timeZone = userMapper.getTimeZoneByCompany(companyID);
		String upTime = "";
		List<User> userHisList = userMapper.getUserHistoryByUp(companyID);
		for(int i = 0;i < userHisList.size();i++)
		{
			upTime = DateUtil.getNewTime(userHisList.get(i).getUpTime(), Integer.valueOf(timeZone));
			userHisList.get(i).setUpTime(upTime);
		}
		userInfo.setUserHistoryByUp(userHisList);
		
		userInfo.setRoleList(roleMapper.getRoleList(companyID,"",companyTyp,"isOn"));
		
		return userInfo;
	}
	/**
	* 方法名 addUser
	* 方法的说明 添加用户
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	* @date 2018.05.16
	*/
	public int addUserTx(UserInfoInput userVO) {
			
			String dateTimeNow = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
			int isHaveLoginName = userMapper.validataLoginName(userVO.getLoginName(),"");
			int isHaveMemberID = userMapper.validataMemberID(userVO.getMemberID(),userVO.getCompanyID(),"");
			if(isHaveLoginName > 0)
			{
				return 998;
			}
			if(isHaveMemberID > 0)
			{
				return 997;
			}
			
			//密码MD5加密
			//MessageDigest md = MessageDigest.getInstance("MD5");
			//BASE64Encoder base64en = new BASE64Encoder();
			String pwd = userVO.getPwd();
			//userVO.setPwd(base64en.encode(md.digest(pwd.getBytes("UTF-8"))));
			userVO.setPwd(SpringUtil.encrypt(pwd));
			userVO.setAddDatetime(dateTimeNow);
			userVO.setUpDatetime(dateTimeNow);
			//插入用户数据
			userMapper.insertUserTx(userVO);
			int userID = userVO.getUserIDBysql();
			String companyListStr = userVO.getCompanyID()+",";
			String[] agrs;
			if(userVO.getCompanyList() != null && userVO.getCompanyList().length() > 0)
			{
				agrs = (userVO.getCompanyList()+companyListStr).split(",");
			}else {
				agrs = companyListStr.split(",");
			}
			//选择参照公司的情况下
			if(agrs.length > 0)
			{
				//批量插入用户参照公司ID
				userMapper.companyByUserTx(agrs, userID,dateTimeNow,userVO.getUserID());
			}
			if(userVO.getRoles() != null)
			{
				String[] agrss = userVO.getRoles().split(",");
				if(agrss.length > 0)
				{
					//批量插入用户参照公司ID
					userMapper.roleByUserTx(agrss, userID,dateTimeNow,userVO.getUserID());
				}
			}
			
			//营业所属长，插入与所有客户绑定关系
			if(userVO.getDepartCD().equals("004") && userVO.getLevel().equals("001") && !userVO.getFlag().equals("csvUpEmp"))
			{
				insertAllClientByUser(String.valueOf(userID),userVO.getCompanyID(),userVO.getUserID());
			}
			for(int i = 0;i < agrs.length;i++)
			{
				this.setUserTableHeaders(userID,agrs[i],dateTimeNow,userVO.getUserID(),"1");
			}
			return userID;
		
		
	}
	public void setUserTableHeaders(int userID,String companyID, String dateTimeNow, String addUserId ,String type)
	{
		
		//type:0 会社登录  1社员登录 会社登录从syslist取值，社员从list取
        List<ListColumn> columList = new ArrayList<ListColumn>();
        if(type.equals("0")) {
                columList = userMapper.serchSysColumnListAll(companyID);
        }else {
                columList = userMapper.serchColumnListAll(companyID);
        }
        
        
        
//		List<ListColumn> columList = new ArrayList<ListColumn>();
//		columList = userMapper.serchColumnListAll();
		List<ListColumnInput> columListJob = new ArrayList<ListColumnInput>();
		for(int i = 0;i < columList.size();i++)
		{
			ListColumnInput columnObj = new ListColumnInput();
			columnObj.setColumn_id(columList.get(i).getList_column_id());
			columnObj.setColumn_width(columList.get(i).getDefault_wide());
			columnObj.setColumn_order(columList.get(i).getList_column_id());
			columnObj.setLevel(columList.get(i).getLevel());
			columnObj.setShow_numbers(String.valueOf(20));
			columnObj.setOnflag(columList.get(i).getIson());
			columListJob.add(columnObj);
		}
		listColumnMapper.insertusersettingtrn(columListJob,String.valueOf(userID),companyID,dateTimeNow,addUserId);
	}
	/**
	* 方法名 employeeInitInfo
	* 方法的说明 用户一览，用户与客户绑定关系页面初始化
	* @param UserInfoInput userVO
	* @return User 
	* @author 王岩
	* @date 2018.05.16
	*/
	public User employeeInitInfo(UserInfoInput userVO)
	{
		User userInfo = new User();
		//绑定关系页面
		if(userVO.getSearchFLg().equals("relevance"))
		{
			userInfo = userMapper.getUserInitInfo(userVO.getUserCD(),userVO.getCompanyID());
			if(userInfo == null)
			{
				return userInfo;
			}
			userInfo.setRelevancedList(userMapper.getRelevancedList(userVO.getUserCD(),userVO.getCompanyID()));	
			userInfo.setIrrelevantList(userMapper.getIrrelevantList(userVO.getUserCD(),userVO.getCompanyID()));	
		}else {
			
			String changekeyword=userVO.getNickname().replace("_", "\\_");
			String finalkeyword = changekeyword.replace("%", "\\%");
			userVO.setNickname(finalkeyword);
			//用户一览页面
			String companyTyp = userMapper.getCompanyType(userVO.getCompanyID());
			userInfo.setUserList(userMapper.searchUserList(userVO));
			if(userVO.getSearchFLg().equals("initFlg"))
			{
				userInfo.setDepartList(commonMapper.selectMstNameByCD("0005",Integer.valueOf(userVO.getCompanyID())));
				userInfo.setLevelList(commonMapper.selectMstNameByCD("0004",Integer.valueOf(userVO.getCompanyID())));
				userInfo.setRoleList(roleMapper.getRoleList(userVO.getCompanyID(),"",companyTyp,"isOn"));
			}
		}
		return userInfo;
	}
	
	
	/**
	* 方法名 editUser
	* 方法的说明 用户修改
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	* @date 2018.05.16
	*/
	public int editUserTx(UserInfoInput userVO) {
		try {
			int lockFlg = userMapper.getLockFlg(userVO.getUserCD(),userVO.getCompanyID());
			if(lockFlg > userVO.getLockFlg())
			{
				return 996;
			}else {
				userVO.setLockFlg(lockFlg+1);
			}
			
			int isHaveLoginName = userMapper.validataLoginName(userVO.getLoginName(),userVO.getUserCD());
			int isHaveMemberID = userMapper.validataMemberID(userVO.getMemberID(),userVO.getCompanyID(),userVO.getUserCD());
			if(isHaveLoginName > 0)
			{
				return 998;
			}
			if(isHaveMemberID > 0)
			{
				return 997;
			}
			String agrs[] = null;
			if(userVO.getCompanyList() != null)
			{
				agrs = (userVO.getCompanyList()+userVO.getCompanyID()).split(",");
			}else {
				agrs = (userVO.getCompanyID()).split(",");
			}
			//选择参照公司的情况下
			if(agrs.length > 0)
			{
				//删除该用户所有参照公司数据
				userMapper.delCompanyByUserTx(userVO.getUserCD());
				//批量插入用户参照公司数据
				userMapper.companyByUserTx(agrs, Integer.valueOf(userVO.getUserCD()),DateUtil.getDateForNow(DateUtil.dateTimeFormat),userVO.getUserID());
			}
			if(userVO.getCompanyList() != null)
			{
				userMapper.delTableHeaders(userVO.getUserCD(),userVO.getCompanyID());
				String chooseCompany[] = userVO.getCompanyList().split(",");
				for(int i=0;i<chooseCompany.length;i++)
				{
					this.setUserTableHeaders(Integer.valueOf(userVO.getUserCD()),chooseCompany[i],DateUtil.getDateForNow(DateUtil.dateTimeFormat),userVO.getUserID(),"1");
				}
			}
			if(userVO.getRoles() != null)
			{
				String[] agrsRoles = userVO.getRoles().split(",");
				if(agrsRoles.length > 0)
				{
					//删除该用户所有参照公司数据
					userMapper.delRoleByUserTx(userVO.getUserCD());
					//批量插入用户权限组ID
					userMapper.roleByUserTx(agrsRoles, Integer.valueOf(userVO.getUserCD()),DateUtil.getDateForNow(DateUtil.dateTimeFormat),userVO.getUserID());
				}
			}
			//营业所属长，插入与所有客户绑定关系
			if(userVO.getDepartCD().equals("004") && userVO.getLevel().equals("001"))
			{
				//删除原先所有绑定
				userMapper.delAllClientByUserTx(userVO.getUserCD(),userVO.getCompanyID());
				//绑定所有
				insertAllClientByUser(userVO.getUserCD(),userVO.getCompanyID(),userVO.getUserID());
			}
			if(!userVO.getDepartCD().equals("004"))
			{
				//删除原先所有绑定
				userMapper.delAllClientByUserTx(userVO.getUserCD(),userVO.getCompanyID());
			}
			//密码MD5
			//MessageDigest md = MessageDigest.getInstance("MD5");
			//BASE64Encoder base64en = new BASE64Encoder();
			String pwd = userVO.getPwd();
			//填写了新密码的情况下
			if(!pwd.equals("") && !pwd.equals("********"))
			{
				Date date = new Date();
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
				String pswupdate = formatter.format(date);
				userVO.setPswUpdateTime(pswupdate);
				userVO.setPwd(SpringUtil.encrypt(pwd));
			}
			userVO.setUpDatetime(DateUtil.getDateForNow(DateUtil.dateTimeFormat));
			int flg = userMapper.upUserInfoTx(userVO);
			
			if(userVO.getUserCD().equals(userVO.getUserID()))
			{
				flg = 999;
			}
			return  flg;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		 
		
	}
	/**
	* 方法名 insertAllClientByUser
	* 方法的说明 插入与所有客户的绑定关系
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	* @date 2018.05.16
	*/
	public int insertAllClientByUser(String userID,String companyID,String addUserID)
	{
		try {
			//查询所有客户
			String[] clients = userMapper.getAllClients(companyID);
			String addDateTime = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
			if(clients.length > 0)
			{
				return userMapper.insertAllClientByUserTx(clients,userID,companyID,addUserID,addDateTime);
			}
			return 0;
		} catch (Exception e) {
			// TODO: handle exception
			return 0;
		}
		
	}
	/**
	* 方法名 relevanceClientByUser
	* 方法的说明 用户和客户绑定关系变更
	* @param UserInfoInput userVO
	* @return int 
	* @author 王岩
	* @date 2018.05.16
	*/
	public int relevanceClientByUserTx(UserInfoInput userVO)
	{
		int lockFlg = userMapper.getLockFlg(userVO.getUserCD(),userVO.getCompanyID());
		if(lockFlg > userVO.getLockFlg())
		{
			return 996;
		}else {
			userMapper.upUserMstLockFlg(userVO.getUserCD(),userVO.getCompanyID(),(lockFlg+1));
		}
		User userInfo = new User();
		userInfo = userMapper.getUserInitInfo(userVO.getUserCD(),userVO.getCompanyID());
		if(!userInfo.getDepartCD().equals("004"))
		{
			return 999;
		}else {
			//删除所有原有的绑定关系
			userMapper.delAllClientByUserTx(userVO.getUserCD(),userVO.getCompanyID());
			if(userVO.getIrrelevantList().size() > 0)
			{
				//插入新的绑定关系
				return userMapper.insertClientByUserTx(userVO.getIrrelevantList(),userVO.getUserCD(),userVO.getCompanyID(),userVO.getUserID());
			}else {
				//插入新的绑定关系
				return 1;
			}
		}
	}
	public User employeePopSearch(UserInfoInput userInput)
	{
		User userInfo = new User();
		String pd = userInput.getPd();
		//if(pd.equals("searchCus")) {timesheet
			List<Role> listrole = userMapper.searchNodeListByUser(userInput.getUserID(),userInput.getCompanyID());
			String level = "0";
			for(int i=0;i<listrole.size();i++) {
				//获取权限列表
				String nodeid = String.valueOf(listrole.get(i).getNodeID());
				if(nodeid.equals("65")) {//[会社員]
					level = "1";
					break;
				}
				if(nodeid.equals("66")) {//[所属社員] 本部门下
					level = "2";
					if(pd.equals("searchCus")) {
						String userid = userInput.getUserID();
						userInput.setDepartCD(userMapper.queryDepart(userid));
					}
					break;
				}
				if(nodeid.equals("67")) {//[本人]
					level = "3";	
					break;
				}

			}
			//User my = userMapper.serchLevel(userInput.getUserID());
			userInput.setLevel(level);
			//userInput.setDepartCD(my.getDepartCD());
			//返回前台用
			userInfo.setLevel(level);
			
//		}
		if(pd.equals("searchLend")) {
			userInput.setPd("searchLend");
			userInput.setDelFlg("0");
		}
		userInfo.setUserList(userMapper.empPopSearch(userInput));
		//userInfo.setDepartList(commonMapper.selectMstNameByCD("0005",Integer.valueOf(userInput.getCompanyID())));
		userInfo.setDepartList(commonMapper.selectDepartList("0005",Integer.valueOf(userInput.getCompanyID()),pd));
		return userInfo;
	}
	public User changeCompany(UserInfoInput userInput)
	{
		User userInfo = new User();
		String timeZone = "";
		userInfo = userMapper.getUserInfoByChangeCompany(userInput.getUserID(),userInput.getChangeCompanyID());
		
		userInfo.setRoleStr(userMapper.searchRoleStr(userInput.getUserID()));
		if(userInfo.getSysLockFlg() == 1)
		{
			Boolean flg = RequestUtil.getSysLockFlg(userInfo.getRoleStr());
			if(flg)
			{
				userInfo.setSysLockMsg("locked");
				return userInfo;
			}
		}
		userInfo.setRoleList(userMapper.searchNodeListByUser(userInput.getUserID(), userInput.getChangeCompanyID()));
		userInfo.setPageNodeList(userMapper.searchPageNodeList());
		userInfo.setComapnyList(userMapper.getCheckedCompanyList(userInput.getUserID()));
		timeZone = userMapper.getTimeZoneByCompany(userInput.getChangeCompanyID());
		userInfo.setTimeZoneType(timeZone);
		userInfo.setDateCompanyZone(DateUtil.getNewTime(DateUtil.getDateForNow(DateUtil.dateTimeFormat), Integer.valueOf(timeZone)));
		return userInfo;
	}
	public int changeColorByUser(UserInfoInput userInput)
	{
		return userMapper.upColorByUser(userInput.getUserID(),userInput.getColorV());
	}
	public int changeLanguageByUser(UserInfoInput userInput)
	{
		return userMapper.changeLanguageByUser(userInput.getUserID(),userInput.getLangtyp());
	}
	public Map<String,String> uploadEmpFile(UserInfoInput userInput)
	{
		String[] fileData = userInput.getFileData().split("\\^");
		List<UserInfoInput> userList = new ArrayList<UserInfoInput>();
		UserInfoInput userVO = null;
		boolean flg = false;
		List<String> errArr = new ArrayList<String>();
		//List<Map<String,String>> msg = new ArrayList<Map<String,String>>();
		Map<String,String> map = new HashMap<String,String>();
		int isHaveMemberID = 0;
		int isHaveLoginName = 0;
		boolean departFlg = false;
		boolean levelFlg = false;
		boolean langFlg = false;
		List<CommonmstVo> langTypList = commonMapper.selectMstNameByCD("0002",Integer.valueOf(userInput.getCompanyID()));
		List<CommonmstVo> departList = commonMapper.selectMstNameByCD("0005",Integer.valueOf(userInput.getCompanyID()));
		List<CommonmstVo> levelList = commonMapper.selectMstNameByCD("0004",Integer.valueOf(userInput.getCompanyID()));
		
		for(int i = 0;i < fileData.length;i++)
		{
			String[] data = fileData[i].split(",",-1);
			if(data.length != 20)
			{
				errArr.add("Line "+(i+5)+" length error!");
				break;
			}
			String roleStr = "";
			flg = false;
			userVO = new UserInfoInput();
			
			if(errArr.size() > 9)
			{
				break;
			}
			
			if(StringUtil.isNotEmpty(data[1]) && validataRegular(data[1],"[0-9A-Za-z]+"))
			{
				isHaveMemberID = userMapper.validataMemberID(data[1],userInput.getCompanyID(),"");
				if(isHaveMemberID > 0)
				{
					errArr.add("B"+(i+5));
				}else {
					userVO.setMemberID(data[1]);
				}
			}else {
				errArr.add("B"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[2]) && data[2].length() < 101)
			{
				userVO.setNickname(data[2]);
			}else {
				errArr.add("C"+(i+5));
			}
			if(data[3].length() < 101)
			{
				userVO.setNickNameEn(data[3]);
			}else {
				errArr.add("D"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[4]) && validataRegular(data[4],"[0-9A-Za-z_-[\\.@&]]+"))
			{
				isHaveLoginName = userMapper.validataLoginName(data[4],"");
				if(isHaveLoginName > 0)
				{
					errArr.add("E"+(i+5));
				}else {
					userVO.setLoginName(data[4]);
				}
			}else {
				errArr.add("E"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[5]) && validataRegular(data[5],"[0-9A-Za-z]+") && data[5].length() > 7 && data[5].length() < 201)
			{
				userVO.setPwd(data[5]);
			}else {
				errArr.add("F"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[6]))
			{
				departFlg = false;
				for(int n = 0;n < departList.size();n++)
				{
					if(departList.get(n).getItemcd().equals(data[6]))
					{
						departFlg = true;
						break;
					}
				}
				if(departFlg)
				{
					userVO.setDepartCD(data[6]);
				}else {
					errArr.add("G"+(i+5));
				}
			}else {
				errArr.add("G"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[7]))
			{
				levelFlg = false;
				for(int n = 0;n < levelList.size();n++)
				{
					if(levelList.get(n).getItemcd().equals(data[7]))
					{
						levelFlg = true;
						break;
					}
				}
				if(levelFlg)
				{
					userVO.setLevel(data[7]);
				}else {
					errArr.add("H"+(i+5));
				}
			}else {
				errArr.add("H"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[18]))
			{
				langFlg = false;
				for(int n = 0;n < langTypList.size();n++)
				{
					if(langTypList.get(n).getItemcd().equals(data[18]))
					{
						langFlg = true;
						break;
					}
				}
				if(langFlg)
				{
					userVO.setLangtyp(data[18]);
				}else {
					errArr.add("S"+(i+5));
				}
			}else {
				errArr.add("S"+(i+5));
			}
			if(StringUtil.isNotEmpty(data[19]) && (data[19].equals("1")||data[19].equals("0")))
			{
				userVO.setDelFlg(data[19]);
			}else {
				errArr.add("T"+(i+5));
			}
			userVO.setCompanyID(userInput.getCompanyID());
			userVO.setUserID(userInput.getUserID());
			for(int j = 8;j < 18;j++)
			{
				if(StringUtil.isNotEmpty(data[j]))
				{
					if(j == 8 || j == 9)
                    {
                            int companyTyp = Integer.valueOf(userMapper.getCompanyType(userInput.getCompanyID()));
                            if(companyTyp == 0 || (companyTyp == 1 && j == 8))
                            {
                                    flg = false;
                                    break;
                            }
                    }
					if(Integer.valueOf(data[j]) != (j-7))
					{
						flg = false;
						break;
					}else {
						flg = true;
						if(j == 17)
						{
							roleStr += data[j];
						}else {
							roleStr += data[j]+",";
						}
					}
				}
			}
			if(!flg)
			{
				errArr.add("I"+(i+5)+"-"+ "R"+(i+5));
			}
			userVO.setRoles(roleStr);
			userList.add(userVO);
		}
		if(errArr.size() < 1)
		{
			for(int i = 0;i < userList.size();i++)
			{
				userList.get(i).setFlag("csvUpEmp");
				addUserTx(userList.get(i));
			}
			map.put("status", "success");
			map.put("msg", userList.size()+"");
		}else {
			map.put("status", "error");
			map.put("msg", errArr.toString());
		}
		return map;
	}
	public boolean validataRegular(String value,String regEx)
	{
		Pattern pattern = Pattern.compile(regEx);
		Matcher matcher = pattern.matcher(value);
		// 字符串是否与正则表达式相匹配
		if (!matcher.matches()) {
			return false;
		}
		return true;
	}
}
