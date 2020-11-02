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

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.bean.mst.entity.Taxtype;
import com.kaiwait.bean.mst.entity.User;
import com.kaiwait.bean.mst.io.ClmstAddInput;
import com.kaiwait.bean.mst.io.UserInfoInput;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.ClmstMapper;
import com.kaiwait.mappers.mst.CommonmstMapper;
import com.kaiwait.mappers.mst.UserMapper;
import com.kaiwait.service.mst.ClmstService;
import com.kaiwait.service.mst.CompanyService;

/**
 * @author Administrator
 *
 */
@Service
public class ClmstServiceImpl implements ClmstService {
	
	@Resource
	private ClmstMapper clmstMapper;
	@Resource
	private CommonmstMapper commonMapper;
	@Resource
	private UserMapper userMapper;
	@Resource
	private CompanyService Companyservice;
	
	Date day=new Date();
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	String datenow = df.format(day);
	
	public void deleteTx(int cldivcd) {
		
		clmstMapper.delete(cldivcd);
	}
	
	public void updateTx(Clmst clmst) {
		clmstMapper.update(clmst);		
	}
	//往来单位登录	
	public Object insertClmstTx(ClmstAddInput inputParam) {
		String cldivcd;		
		Clmst clmst = inputParam.getClmst();
		clmst.setAddDate(DateUtil.getNowDate());
		clmst.setUpDate(DateUtil.getNowDate());
		Map<String,Object> ID = clmstMapper.querymaxno();
		int cldivcdd;
		//首次插入
		 if(ID == null) {
			  cldivcd ="100001";
			  clmst.setAddusercd(clmst.getUpdusercd());
			}
		 else {
			 cldivcdd = Integer.parseInt(ID.get("num").toString())+1;
			 cldivcd = cldivcdd+"";
		 }
		 clmst.setCldivcd(Integer.parseInt(cldivcd));
		 if(inputParam.getTaxtypeListthree()!=null) {
			 List<Taxtype> ss = inputParam.getTaxtypeListthree();
				if(ss.size()>1) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String st1 = ss.get(0).getStart_date();
					String st2 = ss.get(1).getStart_date();
			         try {
			        	 Date dt1 = sdf.parse(st1);
			    	     Date dt2 = sdf.parse(st2);
			    	     //
			             if (dt1.getTime() < dt2.getTime()) {
			                 return -2;
			             }
			         } catch (Exception exception) {
			             exception.printStackTrace();
			         }
				}
			 clmstMapper.deleteTaxTypeTx(clmst.getCldivcd());
			 clmstMapper.insertTaxTypeTx(inputParam.getTaxtypeListthree(),clmst.getCldivcd(),inputParam.getUserID(),inputParam.getCompanyID(),clmst.getAddDate()); 
		 }
		 return clmstMapper.insertClmst(clmst);
		

	}
	//往来单位修改	
		public Object updateClmstTx(ClmstAddInput inputParam) {
			Clmst clmst = inputParam.getClmst();
			int locknum = inputParam.getLock_flg();
			int locknumnow=clmstMapper.queryLock(clmst.getCldivcd(),inputParam.getCompanyID());
			if(locknumnow>locknum){
				 return -1; 
			 }
			
			clmst.setAddDate(DateUtil.getNowDate());
			clmst.setUpDate(DateUtil.getNowDate());
			if(inputParam.getTaxtypeListthree()!=null) {
				List<Taxtype> ss = inputParam.getTaxtypeListthree();
				if(ss.size()>1) {
					SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
					String st1 = ss.get(0).getStart_date();
					String st2 = ss.get(1).getStart_date();
			         try {
			        	 Date dt1 = sdf.parse(st1);
			    	     Date dt2 = sdf.parse(st2);
			    	     //
			             if (dt1.getTime() < dt2.getTime()) {
			                 return -2;
			             }
			         } catch (Exception exception) {
			             exception.printStackTrace();
			         }
				}
				

				clmstMapper.deleteTaxTypeTx(clmst.getCldivcd());
				clmstMapper.insertTaxTypeTx(inputParam.getTaxtypeListthree(),clmst.getCldivcd(),inputParam.getUserID(),inputParam.getCompanyID(),clmst.getAddDate());
			}
			clmstMapper.updateLock(clmst.getCldivcd(),inputParam.getCompanyID());
			return clmstMapper.updateClmst(clmst);			
		}
		//csv上传	
			public Object UploadClmst(ClmstAddInput inputParam) {
				String uplist = inputParam.getFileData();
				String list[] = uplist.split("\\^");
				String clmstl[] = null;
				List<Clmst> clmstlist = new ArrayList<Clmst>();
				List<Taxtype> taxtypelist = new ArrayList<Taxtype>();
				Map<String,String> map = new HashMap<String,String>();
				List<String> errArr = new ArrayList<String>();
				//主键编号
				Map<String,Object> ID = clmstMapper.querymaxno();
				int cldivcdd;
				String cldivcd;	
				//首次插入
				 if(ID == null) {
					  cldivcd ="100001";
					}
				 else {
					 cldivcdd = Integer.parseInt(ID.get("num").toString())+1;
					 cldivcd = cldivcdd+"";
				 }
				for(int i=0;i<list.length;i++) {
					if(errArr.size() > 9)
					{
						break;
					}
					clmstl = list[i].split(",",-1);
					if(clmstl.length<44) {
						errArr.add("erro");
					}else {
						
					
					Clmst clmst = new Clmst();
					Taxtype taxtype = new Taxtype();
					String s =Integer.parseInt(cldivcd)+i+"";
					clmst.setCldivcd(Integer.parseInt(s));
					if(StringUtil.isNotEmpty(clmstl[1]) && validataRegular(clmstl[1],"[0-9A-Za-z]+") && clmstl[1].length() < 11)
					{
						clmst.setAccount_cd(clmstl[1]);//取引先会計コード
					}else {
						errArr.add("B"+(i+5));
					}
					
					if(StringUtil.isNotEmpty(clmstl[2]) &&  clmstl[2].length() < 101)
					{
						clmst.setDivname_full(clmstl[2]);//名称
					}else {
						errArr.add("C"+(i+5));
					}
					
					if(StringUtil.isNotEmpty(clmstl[3]) &&  clmstl[3].length() < 101)
					{
						clmst.setDivnm(clmstl[3]);//略称
					}else {
						errArr.add("D"+(i+5));
					}
					
					if(clmstl[4].length() < 101)
					{
						clmst.setDivnm_en(clmstl[4]);//英語名称
					}else {
						errArr.add("E"+(i+5));
					}
					
					if(clmstl[5].equals("") && clmstl[6].equals("") && clmstl[7].equals("") && clmstl[8].equals(""))
					{
						errArr.add("F"+(i+5)+"-"+"I"+(i+5));
					}
					if(!"".equals(clmstl[5]) && !"1".equals(clmstl[5])) {
						errArr.add("F"+(i+5));
					}
					if(!"".equals(clmstl[6]) && !"1".equals(clmstl[6])) {
						errArr.add("G"+(i+5));
					}
					if(!"".equals(clmstl[7]) && !"1".equals(clmstl[7])) {
						errArr.add("H"+(i+5));
					}
					if(!"".equals(clmstl[8]) && !"1".equals(clmstl[8])) {
						errArr.add("I"+(i+5));
					}
					
					
						if("1".equals(clmstl[5])) {
							clmst.setClient_flg(clmstl[5]);//得意先						
						}else {
							clmst.setClient_flg("0");//得意先
						}
						
						if("1".equals(clmstl[6])) {
							clmst.setContra_flg(clmstl[6]);//発注先	
						}else {
							clmst.setContra_flg("0");//発注先							
						}
						
						if("1".equals(clmstl[7])) {
							clmst.setPay_flg(clmstl[7]);//請求先	
						}else {						
							clmst.setPay_flg("0");//請求先	
						}
						
						if("1".equals(clmstl[8])) {
							clmst.setHdy_flg(clmstl[8]);//相手先Ｇ会社
						}else {
							clmst.setHdy_flg("0");//相手先Ｇ会社						
						}
					
					
					
					if(clmstl[9].length() < 201)
					{
						clmst.setDivadd1(clmstl[9]);//１行目(取引先住所)	
					}else {
						errArr.add("J"+(i+5));
					}
					
					if(clmstl[10].length() < 201)
					{
						clmst.setDivadd2(clmstl[10]);//２行目(取引先住所)
					}else {
						errArr.add("K"+(i+5));
					}
					
					if(clmstl[11].length() < 201)
					{
						clmst.setDivadd(clmstl[11]);//３行目(取引先住所)
					}else {
						errArr.add("L"+(i+5));
					}
					
					if(clmstl[12].length() < 201)
					{
						clmst.setDiv_tel(clmstl[12]);//４行目(取引先住所)
					}else {
						errArr.add("M"+(i+5));
					}
					
					
					if("1".equals(clmstl[6])) {
						if("".equals(clmstl[13])||
								!validataRegular(clmstl[13],"(\\d{4}\\-\\d{1,2}\\-\\d{1,2})+")) {
							errArr.add("N"+(i+5));	
						}
						if("".equals(clmstl[14])) {
							errArr.add("O"+(i+5));	
						}
						String sa = clmstMapper.querycomm(inputParam.getCompanyID());
						String listsa[] = sa.split(",");
						String flg="0";
						for(int k=0;k<listsa.length;k++) {
							if(listsa[k].equals(clmstl[14])) {
								flg="1";
							}
						}
						if("0".equals(flg)) {
							errArr.add("O"+(i+5));	
						}
						else {
							taxtype.setStart_date(clmstl[13]);//適用開始日
							taxtype.setPayeecd(s);
							taxtype.setEnd_date("9999-12-31");
							taxtype.setTax_type(clmstl[14]);//区分
						}
						
					}
					
					
					if("0".equals(clmstl[15])||"1".equals(clmstl[15]))
					{
						if("1".equals(clmstl[15])) {//只有自动设定填写为1才做q r s的验证，modify20190530 
							clmst.setPay_auto_setting(Integer.parseInt(clmstl[15]));//自動設定フラグ
							if("0".equals(clmstl[16])||"1".equals(clmstl[16]))
							{
								clmst.setPay_Date_flg(Integer.parseInt(clmstl[16]));//基準日 納品日：0
							}else {
								errArr.add("Q"+(i+5));
							}
							if(StringUtil.isNotEmpty(clmstl[17]) 
									&&validataRegular(clmstl[17],"[0-9]+")
									&& Integer.parseInt(clmstl[17])>0 && Integer.parseInt(clmstl[17])<13) {
								clmst.setPay_auto_month(Integer.parseInt(clmstl[17]));//XXヵ月後	(支払予定日)	
							}else {
								errArr.add("R"+(i+5));
							}
							if(StringUtil.isNotEmpty(clmstl[18]) && validataRegular(clmstl[18],"[0-9]+")&& (Integer.parseInt(clmstl[18])>0 && Integer.parseInt(clmstl[18])<32)) {
								clmst.setPay_auto_day(Integer.parseInt(clmstl[18]));//XX日(支払予定日)
							}else {
								errArr.add("S"+(i+5));
							}
						}
						
						
					}
					else {
						errArr.add("P"+(i+5));
					}
					
					//
					//発注書拡張項目(英語）
					if(clmstl[19].length() < 101)
					{
						clmst.setContra_contacts_name_en(clmstl[19]);//取引先名称	
					}else {
						errArr.add("T"+(i+5));
					}
					
					if(clmstl[20].length() < 101)
					{
						clmst.setContra_self_company_name_en(clmstl[20]);//自社名称
					}else {
						errArr.add("U"+(i+5));
					}
					
					if(clmstl[21].length() < 201)
					{
						clmst.setContra_address_en(clmstl[21]);//自社住所１行目	
					}else {
						errArr.add("V"+(i+5));
					}
					
					if(clmstl[22].length() < 201)
					{
						clmst.setContra_address_en1(clmstl[22]);//自社住所２行目	
					}else {
						errArr.add("W"+(i+5));
					}
					
					if(clmstl[23].length() < 201)
					{
						clmst.setContra_address_en2(clmstl[23]);//自社住所３行目	
					}else {
						errArr.add("X"+(i+5));
					}
					
					if(clmstl[24].length() < 201)
					{
						clmst.setContra_tel_en(clmstl[24]);//自社住所４行目
					}else {
						errArr.add("Y"+(i+5));
					}	
					
					//
					//発注書拡張項目(中国語簡体字）	
					if(clmstl[25].length() < 101)
					{
						clmst.setContra_contacts_name(clmstl[25]);//取引先名称
					}else {
						errArr.add("Z"+(i+5));
					}
					
					if(clmstl[26].length() < 101)
					{
						clmst.setContra_self_company_name(clmstl[26]);//自社名称	
					}else {
						errArr.add("AA"+(i+5));
					}
					
					if(clmstl[27].length() < 201)
					{
						clmst.setContra_address(clmstl[27]);//自社住所１行目
					}else {
						errArr.add("AB"+(i+5));
					}
					
					if(clmstl[28].length() < 201)
					{
						clmst.setContra_address1(clmstl[28]);//自社住所２行目	
					}else {
						errArr.add("AC"+(i+5));
					}
						
					if(clmstl[29].length() < 201)
					{
						clmst.setContra_address2(clmstl[29]);//自社住所３行目	
					}else {
						errArr.add("AD"+(i+5));
					}
					
					if(clmstl[30].length() < 201)
					{
						clmst.setContra_tel(clmstl[30]);//自社住所４行目
					}else {
						errArr.add("AE"+(i+5));
					}
					
					//
					//発注書拡張項目(中国語繁体字）
					if(clmstl[31].length() < 101)
					{
						clmst.setContra_contacts_name_hk(clmstl[31]);//取引先名称
					}else {
						errArr.add("AF"+(i+5));
					}
					
					if(clmstl[32].length() < 101)
					{
						clmst.setContra_self_company_name_hk(clmstl[32]);//自社名称	
					}else {
						errArr.add("AG"+(i+5));
					}
					
					if(clmstl[33].length() < 201)
					{
						clmst.setContra_address_hk(clmstl[33]);//自社住所１行目
					}else {
						errArr.add("AH"+(i+5));
					}
					
					if(clmstl[34].length() < 201)
					{
						clmst.setContra_address_hk1(clmstl[34]);//自社住所２行目
					}else {
						errArr.add("AI"+(i+5));
					}
					
					if(clmstl[35].length() < 201)
					{
						clmst.setContra_address_hk2(clmstl[35]);//自社住所３行目	
					}else {
						errArr.add("AJ"+(i+5));
					}
					
					if(clmstl[36].length() < 201)
					{
						clmst.setContra_tel_hk(clmstl[36]);//自社住所４行目
					}else {
						errArr.add("AK"+(i+5));
					}
					
					
					
					//
					//発注書用拡張項目(日本語）
					if(clmstl[37].length() < 101)
					{
						clmst.setContra_contacts_name_jp(clmstl[37]);//取引先名称	
					}else {
						errArr.add("AL"+(i+5));
					}
					
					if(clmstl[38].length() < 101)
					{
						clmst.setContra_self_company_name_jp(clmstl[38]);//自社名称	
					}else {
						errArr.add("AM"+(i+5));
					}
					
					if(clmstl[39].length() < 201)
					{
						clmst.setContra_address_jp(clmstl[39]);//自社住所１行目	
					}else {
						errArr.add("AN"+(i+5));
					}
					
					if(clmstl[40].length() < 201)
					{
						clmst.setContra_address_jp1(clmstl[40]);//自社住所２行目
					}else {
						errArr.add("AO"+(i+5));
					}
					
					if(clmstl[41].length() < 201)
					{
						clmst.setContra_address_jp2(clmstl[41]);//自社住所３行目	
					}else {
						errArr.add("AP"+(i+5));
					}
					
					if(clmstl[42].length() < 201)
					{
						clmst.setContra_tel_jp(clmstl[42]);//自社住所４行目
					}else {
						errArr.add("AQ"+(i+5));
					}
					
					if(!"0".equals(clmstl[43]) && !"1".equals(clmstl[43]))
					{
						errArr.add("AR"+(i+5));
					}else {
						clmst.setDel_flg(Integer.parseInt(clmstl[43]));//無効
					}
					//clmst.setCompany_cd(Integer.parseInt(inputParam.getCompanyID()));
					clmstlist.add(clmst);
					if(taxtype.getPayeecd() != null)
					{
						taxtypelist.add(taxtype);
					}
				}
				}
				if(errArr.size() < 1)
				{
					if(taxtypelist.size() > 0)
					{
						clmstMapper.TaxTypeUpload(taxtypelist,inputParam.getUserID(),inputParam.getCompanyID(),DateUtil.getNowDate());
					}
					clmstMapper.ClmstUpload(clmstlist,inputParam.getUserID(),inputParam.getCompanyID(),DateUtil.getNowDate());
					map.put("status", "success");
					map.put("msg", clmstlist.size()+"");
				}else {
					map.put("status", "error");
					map.put("msg", errArr.toString());
				}
				return map;

//				int locknum = inputParam.getLock_flg();
//				int locknumnow=clmstMapper.queryLock(clmst.getCldivcd(),inputParam.getCompanyID())
//				if(locknumnow>locknum){
//					 return -1; 
//				 }
//				clmst.setAddDate(DateUtil.getNowDate());
//				clmst.setUpDate(DateUtil.getNowDate());
//				if(inputParam.getTaxtypeListthree()!=null) {
//					clmstMapper.deleteTaxTypeTx(clmst.getCldivcd());
//					clmstMapper.insertTaxTypeTx(inputParam.getTaxtypeListthree(),clmst.getCldivcd(),inputParam.getUserID(),inputParam.getCompanyID(),clmst.getAddDate());
//				}
//				clmstMapper.updateLock(clmst.getCldivcd(),inputParam.getCompanyID());
//				return clmstMapper.updateClmst(clmst);	
			}
		
	//获取主键编号
	public String querymaxno() {

		Map<String,Object> ss = clmstMapper.querymaxno();
		int cldivcdd = Integer.parseInt(ss.get("num").toString())+1;
		//int cldivcd = cldivcdd;		 
		return cldivcdd+"";
		
	}
	//往来单位列表
	public ClmstAddInput clmstQuery(ClmstAddInput inputParam){
		ClmstAddInput clmstinput = new ClmstAddInput();
		String companyid =  inputParam.getCompanyID();
		inputParam.getClmst().setCompany_cd(Integer.parseInt(companyid));
		String divnm = inputParam.getClmst().getDivnm();
		String newdivnm = Companyservice.changSqlInput(divnm);
		inputParam.getClmst().setDivnm(newdivnm);
		clmstinput.setClmstList(clmstMapper.clmstQuery(inputParam.getClmst()));
		clmstinput.setTaxtypeListthree(clmstMapper.QueryTaxtype(inputParam.getClmst().getCldivcd(),Integer.parseInt(inputParam.getCompanyID())));
		return  clmstinput;
		
	}
	
	//往来单位列表
	public ClmstAddInput userclmstQuery(ClmstAddInput inputParam){
		ClmstAddInput clmstInfo = new ClmstAddInput();
		int a = clmstMapper.queryClientflg(inputParam.getCldivcd(),inputParam.getCompanyID());
		if(a<1) {
			return null;
		}
		//绑定关系页面
		clmstInfo.setWedgemembersleft(clmstMapper.getWedgemembersleft(inputParam.getCldivcd(),inputParam.getCompanyID()));
		clmstInfo.setWedgemembersright(clmstMapper.getWedgemembersright(inputParam.getCldivcd(),inputParam.getCompanyID()));
		clmstInfo.setLock_flg(clmstMapper.queryLock(inputParam.getCldivcd(),inputParam.getCompanyID()));
		return clmstInfo;
	}
	//

	//职员关联信息修改
	public int userclmstChangeTx(ClmstAddInput inputParam){
			int locknum = inputParam.getLock_flg();
			int locknumnow=clmstMapper.queryLock(inputParam.getCldivcd(),inputParam.getCompanyID());
			if(locknumnow>locknum){
				 return -1; 
			 }
			//删除所有原有的绑定关系
			clmstMapper.deleteWedgemembersTx(inputParam.getCldivcd(),inputParam.getCompanyID());
			//插入新的绑定关系
			if(inputParam.getWedgemembersright().size()>0) {
				clmstMapper.updateLock(inputParam.getCldivcd(),inputParam.getCompanyID());
				return clmstMapper.insertWedgemembersTx(inputParam.getWedgemembersright(),inputParam.getCldivcd(),inputParam.getUserID(),inputParam.getCompanyID(),datenow);
			}
			
			return 1;

	}
	
	//纳税人种类init
	public ClmstAddInput taxtypeInit(ClmstAddInput inputParam){
		ClmstAddInput clmst = new ClmstAddInput();
		String companyID = inputParam.getCompanyID();
		//纳税人种类，下拉选框用
		clmst.setTaxtypeList((commonMapper.selectMstNameByCDNO("0060",Integer.valueOf(companyID))));
		//添加者历史记录
		List<User> insertlist = clmstMapper.getUserHistoryByInsert(companyID);
		String timeZone = userMapper.getTimeZoneByCompany(companyID);
		String upTime = "";
		for(int i = 0;i < insertlist.size();i++)
		{
			upTime = DateUtil.getNewTime(insertlist.get(i).getAddTime(), Integer.valueOf(timeZone));
			insertlist.get(i).setAddTime(upTime);
		}
		clmst.setUserHistoryByInsert(insertlist);
//		//更新者历史记录
//		List<User> uplist = clmstMapper.getUserHistoryByUp(companyID);
//		String upTimet = "";
//		for(int i = 0;i < uplist.size();i++)
//		{
//			upTimet = DateUtil.getNewTime(uplist.get(i).getUpTime(), Integer.valueOf(timeZone));
//			uplist.get(i).setUpTime(upTimet);
//		}
//		clmst.setUserHistoryByUp(uplist);
		//修改时候用
		String clidivcd = String.valueOf(inputParam.getClmst().getCldivcd());
		if(!clidivcd.equals("0")&&!clidivcd.equals(null)&&!clidivcd.equals("")) {
			inputParam.getClmst().setCompany_cd(Integer.parseInt(companyID));
			clmst.setClmstList(clmstMapper.clmstQuery(inputParam.getClmst()));
			clmst.setTaxtypeListthree(clmstMapper.QueryTaxtype(inputParam.getClmst().getCldivcd(),Integer.parseInt(inputParam.getCompanyID())));
				
		}
		

		return clmst;
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
	
	public static void main(String[] args) {
		//String gsID = "5";
		ClmstServiceImpl clmstServiceImpl =new ClmstServiceImpl();
		clmstServiceImpl.querymaxno();
		
	}

	
	public List<Clmst> searchClmstByPop(UserInfoInput inputParam) {
		
	return clmstMapper.searchClmstByPop(inputParam.getCompanyID(),inputParam.getDepartCD(),inputParam.getDivnm(),inputParam.getPd());
	}
	public String clmstCheck(String account_cd,String companyid,int clidivcd) {
		
	return clmstMapper.clmstCheck(account_cd,companyid,clidivcd);
	}
}
