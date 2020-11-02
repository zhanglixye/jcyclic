package com.kaiwait.service.jczh.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.BsTickets;
import com.kaiwait.bean.jczh.entity.Calculate;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.JobLandOut;
import com.kaiwait.bean.jczh.entity.JobUserLable;
import com.kaiwait.bean.jczh.entity.ReqIns;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.entity.SaleType;
import com.kaiwait.bean.jczh.io.OutPutInput;
import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.bean.jczh.vo.CostListVo;
import com.kaiwait.bean.jczh.vo.JobLandVo;
import com.kaiwait.bean.jczh.vo.SalemstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.core.utils.JasperOutPdfUtil;
import com.kaiwait.core.utils.MathController;
import com.kaiwait.core.utils.PdfToZipUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.CostListMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.SaleTypeMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.JobLandService;
import com.kaiwait.service.jczh.SaleTypeService;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
@Service
public class JobLandServiceImpl implements JobLandService{
	@Resource
	private JobLandMapper joblandmapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private SaleTypeMapper saleTypeMapper;
	@Resource
	private CostListMapper costListMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private SaleTypeService saleTypeService;
	
	
	public Integer landJob(JobLand job_land) {
		// TODO Auto-generated method stub
		return null;
	}
   // DecimalFormat dmf = new DecimalFormat("0.00");
	public JobLandVo initShow(int company_cd,String usercd,String jobcd) {
		// TODO Auto-generated method stub
		JobLandVo jbVo = new JobLandVo();
		jbVo.setList_comst(joblandmapper.selectSaleCode(company_cd,null,"0050"));
		jbVo.setList_lable(joblandmapper.selectLableList(usercd,0,company_cd,1));
		jbVo.setList_tax(joblandmapper.selectSaleCode(company_cd,null,"0052"));
		jbVo.setList_foreign_tax(joblandmapper.selectSaleCode(company_cd,null,"0051"));
		jbVo.setAccountyear(joblandmapper.selectSaleCode(company_cd,null,"0001"));
		jbVo.setCostRate(commonMethedMapper.getCostTaxRate("901", company_cd));
		jbVo.setList_cost(commonMethedMapper.getSumcost(company_cd, jobcd));
		//jobcd 为空  返回job登陆的信息
		if("".equals(jobcd)||jobcd==null) {
			JobLand jobland = new JobLand();
			jobland.setSkip(commonMethedMapper.selectSkipByCd(company_cd));
			jobland.setOutFlg_list(joblandmapper.selectSaleCode(company_cd,"001","0070"));
			jbVo.setList_sale(joblandmapper.selectSaleList("","",company_cd));
			jbVo.setJobland(jobland);
		}else {
			//根据jobcd 查到当前job 売上cd 根据売上cd查到当前
			JobLand job = joblandmapper.selectJobByCd(jobcd,company_cd);
 			if(job!=null) {
				job.setAdddate(commonMethedService.getTimeByZone(job.getAdddate(), String.valueOf(company_cd)));
				job.setUpddate(commonMethedService.getTimeByZone(job.getUpddate(), String.valueOf(company_cd)));
			}else {
				return null;
			}
 			//查询用户ロールid
 			List<Role> role = commonMethedMapper.selectRoid(usercd);
			List<SalemstVo> saleVo = joblandmapper.selectSaleList(job.getSale_typ(),"",company_cd);
			String tranLend = saleVo.get(0).getTranlend();
			jbVo.setNodeList(role);
			if("0".equals(tranLend)) {
				//2018-10-09 查询所有
				//jbVo.setList_sale(joblandmapper.selectSaleList("","",company_cd));
				jbVo.setList_sale(joblandmapper.selectSaleList("","",company_cd));
			}else {
				//获取外发账票 是否有记录 都没记录  売上种目查询所有  否则查询可的
//				TranLendVo tranlend = joblandmapper.countTranLendByCd(jobcd, company_cd);
//				if(tranlend.getLendNum()<=0||tranlend.getTranNum()<=0) {
//					jbVo.setList_sale(joblandmapper.selectSaleList("","0",company_cd));
//				}else {
//					jbVo.setList_sale(joblandmapper.selectSaleList("","1",company_cd));
//				}
				jbVo.setList_sale(joblandmapper.selectSaleList("","1",company_cd));
			}
//			String addname = joblandmapper.getNameByUserCd(job.getAddusercd(),company_cd);
//			String updname = joblandmapper.getNameByUserCd(job.getUpdusercd(),company_cd);
//			job.setAdduser(addname);
//			job.setUpduser(updname);
			job.setJulable(joblandmapper.selectJobUser(jobcd,company_cd));
			job.setJlTrn(joblandmapper.selectJobLable(jobcd,company_cd));
			job.setIsSaleFinishFlg(joblandmapper.isFinishFlg(jobcd, company_cd));
			//张票出力flg
			job.setOutFlg_list(joblandmapper.selectSaleCode(company_cd,"002","0070"));
			//查询跳过
			job.setSkip(commonMethedMapper.selectSkipByCd(company_cd));
			jbVo.setJobland(job);
		}
		
		return jbVo;
	}
	public Object insertJobTx(JobLand jobland, List<JobUserLable> julable, List<JobLableTrn> jltrn,String upusercd) {
			// TODO Auto-generated method stub
			int companycd  = jobland.getCompany_cd();
			//验证权限
			boolean isHavePower = commonMethedService.validatePower("", upusercd, companycd,null, "jobLogin");
			if(!isHavePower) {
				return 550;
			}
			
			//验证 取引先是否有效
			int findDivNum = commonMethedMapper.validateClientFlg(jobland.getCldiv_cd(), companycd);
			int findPayNum = commonMethedMapper.validateClientFlg(jobland.getPayer_cd(), companycd);
			int findGNum = 1;
			String gcd = jobland.getG_company();
			if(!"".equals(gcd)) {
				 findGNum = commonMethedMapper.validateClientFlg(gcd, companycd);
				}
			//取引先无效  返回560码
			if(findDivNum<1||findPayNum<1||findGNum<1) {
				return 560;
			}
			//返回计上预定日错误信息 返回999 
			String accountingMonth = commonMethedService.getSystemDate(jobland.getCompany_cd());
			String Plan_dalday =jobland.getPlan_dalday().substring(0,7);;
			accountingMonth =accountingMonth.substring(0,7);
			if(Plan_dalday.compareTo(accountingMonth)<0) {
				return 999;
			}
			  joblandmapper.insertJob(jobland);
			  joblandmapper.insertJobUser(julable);
			           
			    if(jltrn!=null) {
			  joblandmapper.insertJobLable(jltrn);
			    }
			
			return jobland.getJob_cd();
	}
	public Object updateJobTx(JobLand jobland,List<JobUserLable> cardAll, List<JobUserLable> cardnew, List<JobUserLable> delcard,List<JobLableTrn> jltrn,String upusercd) {
		// TODO Auto-generated method stub
		String jobcd =jobland.getJob_cd();
		int companycd  = jobland.getCompany_cd();
		JobLand job = joblandmapper.selectJobByCd(jobcd, companycd);
		int dbLockFlg = job.getLockFlg();
	    int preLockFlg =jobland.getLockFlg()+1; 
	  //验证权限
        boolean isHavePower = true;
        if(jobland.getJobIsEditMDFlg() == null || !jobland.getJobIsEditMDFlg().equals("jobIsEditMDFlg"))
        {
        	boolean  customer = false;
        	//查询用户ロールid
 				List<Role> role = commonMethedMapper.selectRoid(upusercd);
 				for(int i =0;i<role.size();i++) {
 					if(role.get(i).getRoleID()==5 || role.get(i).getRoleID()==6||role.get(i).getRoleID()==7 ||role.get(i).getRoleID()==8 ) {
 						customer = true;
 					}
 					else {
 						customer = false; 
 						break;
 					}
 				}
 				if(!customer) {
	                isHavePower = commonMethedService.validatePower(jobcd, upusercd, companycd, job.getCldiv_cd(), "jobUpdate");
	                if(!isHavePower) {
	                            return 550;
	                    }
 				}
        }
            if(jobland.getJobIsEditMDFlg()!= null && jobland.getJobIsEditMDFlg().equals("jobIsEditMDFlg"))
            {
            	boolean  customer = false;
            	//查询用户ロールid
     				List<Role> role = commonMethedMapper.selectRoid(upusercd);
     				for(int i =0;i<role.size();i++) {
     					if(role.get(i).getRoleID()==5 ||role.get(i).getRoleID()==6 ||role.get(i).getRoleID()==7 || role.get(i).getRoleID()==8) {
     						customer = true;
     					}
     					else {
     						if( 10<role.get(i).getRoleID()&&role.get(i).getRoleID()<100) {
     							customer = false; 
         						break;
     						}
     					}
     				}
     				if(!customer) {
     					 isHavePower = commonMethedService.validatePower(jobcd, upusercd, companycd, job.getCldiv_cd(), "jobUpdateMD");
                         if(!isHavePower) {
                                 return 550;
                         }
     				} 
            }
		/*//状态验证 job更新  job终了与job结账的 无法进行job 更新
		int jobEnd = job.getJobEndFlg();
		int account = job.getAccountFlg();
		if(account==1||jobEnd==1) {
			return 540;
		}*/
		//2019/6/20宗哥确认修改，job计上济依然可以添加md，终了后不可以
		int jobEnd = job.getJobEndFlg();
			if(jobEnd==1) {
				return 540;
		}	
		//制御 前台传过来的preLockFLg + 1 !> dbLockFlg 返回 错误
		if(!(preLockFlg>dbLockFlg)) {
			return 540;
		}
		//如果买上登入了  则不验证计上预定日
		if(jobland.getIsSaleFinishFlg()!=1&&job.getAccountFlg()!=1) {
			//返回计上预定日错误信息 返回999 
			String accountingMonth = commonMethedService.getSystemDate(jobland.getCompany_cd());
			String Plan_dalday =jobland.getPlan_dalday().substring(0,7);
			accountingMonth =accountingMonth.substring(0,7);
			if(Plan_dalday.compareTo(accountingMonth)<0) {
				return 999;
			}
		}
		
		//如果是 jpp过来的则不验证 取引先
		if(job.getFromJpp()==0){
			//验证 取引先是否有效
			//int findDivNum = commonMethedMapper.validateClientFlg(jobland.getCldiv_cd(), companycd);
			int findPayNum = commonMethedMapper.validateClientFlg(jobland.getPayer_cd(), companycd);
			int findGNum = 1;
			String gcd = jobland.getG_company();
			if(!"".equals(gcd)) {
			 findGNum = commonMethedMapper.validateClientFlg(gcd, companycd);
			}
			//取引先无效  返回560码
			if(findPayNum<1||findGNum<1) {
				return 560;
			}
		}
		// upd_date  用于更新jobusertrn表中的 upddate字段  
		Date date = new Date();
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String upd_date = df.format(date);
	
		
		for(int k = 0;k<cardAll.size();k++) {
			boolean delFlg = true;
			boolean newFlg = true;
			for(int i=0;i<delcard.size();i++) {
				if(cardAll.get(k).getUsercd().equals(delcard.get(i).getUsercd())) {
					delFlg = false;
				}
				if(k == 0)
				{
					joblandmapper.delJob(jobcd,companycd, "jobuserstrn", delcard.get(i).getUsercd(),1,null,0,upd_date);
				}
				
			}
			//
			if(cardnew!=null&&cardnew.size()>0) {
				for(int i=0;i<cardnew.size();i++) {
					if(cardAll.get(k).getUsercd().equals(cardnew.get(i).getUsercd()))
					{
						newFlg = false;
					}
					if(k == 0)
					{
						if(joblandmapper.isHaveRequst(jobcd, companycd, cardnew.get(i).getUsercd())>0) {
							joblandmapper.delJob(jobcd, companycd, "jobuserstrn", cardnew.get(i).getUsercd(), 0,cardnew.get(i).getLevel_flg(),0,upd_date);
						}else {
							joblandmapper.insertJobUpUser(cardnew.get(i));
						}
					}
					
				}
				
			}
			if(delFlg && newFlg)
			{
				//update 
				joblandmapper.updateJutrn(jobcd,companycd,cardAll.get(k).getUsercd(),cardAll.get(k).getLevel_flg(),upusercd,DateUtil.getNowDate());
			}
		}
		joblandmapper.deleteByJobCd(jobcd,null,companycd, "joblabeltrn");
		//joblandmapper.deleteByJobCd( jobland.getJob_cd(),null,jobland.getCompany_cd(), "jobuserstrn");
		if(jltrn!=null) {
		    joblandmapper.insertJobLable(jltrn);
		}
		
		//20200228 xy  做过买上也可以进行更新 所以将下列代码注释了
//		SaleType saletp = saleTypeMapper.selectSaleTypeByCd(jobcd, companycd);
		/*edit BY WY 20190813
		if(saletp.getSaleadddate()!=null&&!"".equals(saletp.getSaleadddate())) {
			return 1;
		}
		*/
//		if(saletp != null) {
//			return 1;
//		}
		
		
		jobland.setLockFlg(preLockFlg);
		int num = joblandmapper.updateJob(jobland);
		return num;
	}

	public int delJobTx(String jobcd, int companycd,String usercd,int lockFlg) {
		// TODO Auto-generated method stub
		JobLand job = joblandmapper.selectJobByCd(jobcd, companycd);
		if(job==null) {
			return 540;
		}
		int dbLockFlg = job.getLockFlg();
		int preLockFlg =lockFlg+1; 
		//验证权限
		boolean isHavePower = commonMethedService.validatePower(jobcd, usercd, companycd, job.getCldiv_cd(), "jobUpdate");
		if(!isHavePower) {
			return 550;
		}
//		int jobEnd = job.getJobEndFlg();
//		int account = job.getAccountFlg();
//		if(account==1||jobEnd==1) {
//			return 540;
//		}
		//原价条数 大于1 不许删除
		List<Cost> cost = commonMethedMapper.getSumcost(companycd, jobcd);
		int costNum = Integer.valueOf(cost.get(0).getCostnum());
		if(costNum>1) {
			return 540;
		}
		
		SaleType saletp = saleTypeMapper.selectSaleTypeByCd(jobcd, companycd);
		if(saletp!=null) {
			return 540;
		}
		if(!(preLockFlg>dbLockFlg)) {
			return 540;
		}
		 Date date = new Date();
		 SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String upd_date = df.format(date);
		
		int num = joblandmapper.delJob(jobcd, companycd,"jobtrn",null,1,null,preLockFlg,upd_date);
		@SuppressWarnings("unused")
		int numjobuser = joblandmapper.delJob(jobcd, companycd,"jobuserstrn",null,1,null,0,upd_date);
		return num;
	}

	@Override
	public String jobLogOutPut(OutPutInput jobLand) throws JRException, NumberFormatException{
		JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobLand.getJobNo(),Integer.valueOf(jobLand.getCompanyID()));
		int localPoint = jobLandOut.getLocalPoint();
		int companycd = Integer.valueOf(jobLand.getCompanyID());
		List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
		List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
		//外货端数flg，0051 001
		@SuppressWarnings("unused")
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		@SuppressWarnings("unused")
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		int planSalePoint = jobLandOut.getPlanSalePoint();
		int planCostPoint = jobLandOut.getPlanCostPoint();
		//重新设置责任 担当 md
		
		//将数据整合成字符串
		jobLandOut.setPlanSaleString(commonMethedService.getNewNum(jobLandOut.getPlanSale(),localPoint)+"   "+jobLandOut.getLocalMoney()); 
		jobLandOut.setPlanSaleTaxString(commonMethedService.getNewNum(jobLandOut.getPlanSaleTax(),localPoint)+"   "+jobLandOut.getLocalMoney()); 
		jobLandOut.setPlanCostString(commonMethedService.getNewNum(jobLandOut.getPlanCost(),localPoint)+"   "+jobLandOut.getLocalMoney());
		jobLandOut.setPlanCostTaxString(commonMethedService.getNewNum(jobLandOut.getPlanCostTax(),localPoint)+"   "+jobLandOut.getLocalMoney());
		jobLandOut.setTaxString(commonMethedService.getNewNum(jobLandOut.getPlanTax(),localPoint)+"   "+jobLandOut.getLocalMoney());
//		jobLandOut.setProfitRate(String.valueOf(commonMethedService.pointFormatHandler(Double.parseDouble(jobLandOut.getProfitRate()), foreignFormatFlg, localPoint)));
		jobLandOut.setProfitRate(String.valueOf(MathController.scaleNumber(Double.valueOf(jobLandOut.getProfitRate()))));

		jobLandOut.setProfitRateString("[ "+commonMethedService.profitRateFormat(jobLandOut.getProfitRate())+" % ]");
		jobLandOut.setPlanSaleTax(commonMethedService.getNewNum(jobLandOut.getPlanSaleTax(),localPoint));
		jobLandOut.setPlanCostTax(commonMethedService.getNewNum(jobLandOut.getPlanCostTax(),localPoint));
		jobLandOut.setProfit(commonMethedService.getNewNum(jobLandOut.getProfit(),localPoint));
		
		
		//jobLandOut.setPlanCostCureCode(String.valueOf(Double.parseDouble(jobLandOut.getPlanCostCureCode())));
		//判断是否有外货
		if(jobLandOut.getPlanSaleCureCode()==null||"".equals(jobLandOut.getPlanSaleCureCode())) {
			jobLandOut.setPlanSaleForeignString(commonMethedService.getNewNum(jobLandOut.getPlanSaleForeignAmt(),localPoint)+"   "+jobLandOut.getLocalMoney());
			jobLandOut.setPlanSaleCureCodeString(null);
		}else {
			jobLandOut.setPlanSaleCureCode(String.valueOf(Double.parseDouble(jobLandOut.getPlanSaleCureCode())));
			jobLandOut.setPlanSaleForeignString(commonMethedService.getNewNum(jobLandOut.getPlanSaleForeignAmt(),planSalePoint)+"   "+jobLandOut.getPlanSaleMoney());
			jobLandOut.setPlanSaleCureCodeString(jobLandOut.getPlanSaleCureCode()+" ( 1 "+jobLandOut.getLocalMoney()+" : "+jobLandOut.getPlanSaleChangeUnit()+jobLandOut.getPlanSaleMoney()+" )");
		}
		if(jobLandOut.getPlanCostCureCode()==null||"".equals(jobLandOut.getPlanCostCureCode())) {
			jobLandOut.setPlanCostForeignString(commonMethedService.getNewNum(jobLandOut.getPlanCostForeignAmt(),localPoint)+"   "+jobLandOut.getLocalMoney());
			jobLandOut.setPlanCostCureCodeString(null);
		}else {
			jobLandOut.setPlanCostCureCode(String.valueOf(Double.parseDouble(jobLandOut.getPlanCostCureCode())));
			jobLandOut.setPlanCostForeignString(commonMethedService.getNewNum(jobLandOut.getPlanCostForeignAmt(),planCostPoint)+"   "+jobLandOut.getPlanCostMoney());
			jobLandOut.setPlanCostCureCodeString(jobLandOut.getPlanCostCureCode()+" ( 1 "+jobLandOut.getLocalMoney()+" : "+jobLandOut.getPlanCostChangeUnit()+jobLandOut.getPlanCostMoney()+" )");
		}
		String langTyp= jobLand.getLangTyp();
		Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
	    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
	
			//预计原价
			if("1".equals(jobLandOut.getPlanCostIsHave())) {
				jobLandOut.setPlanCostIsHave(bundle.getString("haveTax"));
			}else {
				jobLandOut.setPlanCostIsHave(bundle.getString("notHaveTax"));
			}
			//预计卖上
			if("1".equals(jobLandOut.getPlanSaleIsHave())) {
				jobLandOut.setPlanSaleIsHave(bundle.getString("haveTax"));
			}else {
				jobLandOut.setPlanSaleIsHave(bundle.getString("notHaveTax"));
			}
	
		jobLandOut.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
		List<JobLandOut> jbs = new ArrayList<JobLandOut>();
		jbs.add(jobLandOut);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
    //請求指示書
	@Override
	public String reqInsOutPutTx(OutPutInput jobLand) throws JRException {
		//ReqInfo req = saleType.getReq();
		String jobcd = jobLand.getJobNo();
		int companycd = Integer.valueOf(jobLand.getCompanyID());
		JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
		Calculate cal = commonMethedService.calcuateJobTax(jobcd, companycd);
		int localPoint = jobLandOut.getLocalPoint();
		List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
		List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
		//外货端数flg，0051 001
		@SuppressWarnings("unused")
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		@SuppressWarnings("unused")
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		String jobDtFlg = jobLand.getJobDtFlg();
		if(!"jobDetails".equals(jobDtFlg)) {
			saleTypeService.upReqTx(jobcd,companycd,jobLand.getUserID());
		}
		ReqIns reqIns = joblandmapper.selectReqInsOutPut(jobcd, companycd);
		int salePoint = reqIns.getSalePoint();
		reqIns.setCompanyName(jobLandOut.getCompanyName());
		reqIns.setCompanyNameEn(jobLandOut.getCompanyNameEn());
		reqIns.setJobName(jobLandOut.getJobName());
		reqIns.setJobcd(jobLandOut.getJobcd());
		reqIns.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
		reqIns.setAddUserName(jobLandOut.getAddUserName());
		reqIns.setSaleName(jobLandOut.getSaleName());
		reqIns.setdName(jobLandOut.getdName());
		reqIns.setqName(jobLandOut.getqName());
		reqIns.setHdyName(jobLandOut.getHdyName());
		reqIns.setzUser(jobLandOut.getzUser());
		reqIns.setdUser(jobLandOut.getdUser());
		reqIns.setLocalMoney(jobLandOut.getLocalMoney());
		reqIns.setLocalMoneyEn(jobLandOut.getLocalMoneyEn());
		reqIns.setLocalMoneyHk(jobLandOut.getLocalMoneyHk());
		reqIns.setLocalMoneyJp(jobLandOut.getLocalMoneyJp());
		reqIns.setSaleForeignMoney(jobLandOut.getSaleForeignMoney());
		reqIns.setSaleForeignMoneyEn(jobLandOut.getSaleForeignMoneyEn());
		reqIns.setSaleForeignMoneyHk(jobLandOut.getSaleForeignMoneyHk());
		reqIns.setSaleForeignMoneyJp(jobLandOut.getSaleForeignMoneyJp());
		reqIns.setDalDay(jobLandOut.getDalDay());
		reqIns.setPlanDalday(jobLandOut.getPlanDalday());
		//原价合计  原价条数  仕入增值税
		List<Cost> cost  = commonMethedMapper.getSumcost(companycd, jobcd);
		String costNum = cost.get(0).getCostnum();
	    String costAmt  = cost.get(0).getSumamt();
	    String vatAmt = cost.get(0).getSumvat();
		//如果原价完了
		if(reqIns.getCostFinishFlg()==1) {
			//没有实际成本
			if("0".equals(costNum)) {
				costAmt = "0";
				vatAmt = "0";
			}
		}else {
			if("0".equals(costNum)) {
				costAmt = reqIns.getPlanCostMoney();
				vatAmt = reqIns.getPlanCostTax();
			}
		}	
//		String saleAmt = reqIns.getSaleMoney();
//		double profit = Double.parseDouble(saleAmt)-(Double.parseDouble(costAmt)+Double.parseDouble(vatAmt));
//		double profitRate = profit/Double.parseDouble(saleAmt)*100;
		reqIns.setProfit(String.valueOf(cal.getProfit()));
		if(cal.getProfitRate() != Double.POSITIVE_INFINITY)
		{
			reqIns.setProfitRate(commonMethedService.profitRateFormat(String.valueOf(cal.getProfitRate())));
		}else {
			reqIns.setProfitRate(String.valueOf(cal.getProfitRate()));
		}
		reqIns.setRate2(String.valueOf(cal.getTaxWH()));
		reqIns.setRate3(String.valueOf(cal.getTaxZZF()));
		reqIns.setCostMoney(costAmt);
		reqIns.setCostRate(vatAmt);
		
		//整合字符串
		Map<Object,Object>  hbcode =null;
		if(reqIns.getSaleForeignCode()!=null && !reqIns.getSaleForeignCode().equals("")) {
			hbcode= commonMethedMapper.selectcodetype(reqIns.getSaleForeignCode(),jobLand.getCompanyID());
		}
		String moneyType = null;
		String localMoney = jobLandOut.getLocalMoney();
		if(jobLand.getLangTyp().equals("jp")) {
			localMoney = jobLandOut.getLocalMoneyJp();
			if(hbcode!=null) {
				moneyType = hbcode.get("ITEMNAME_JP").toString();
			}
			
		}else if(jobLand.getLangTyp().equals("en")) {
			localMoney =  jobLandOut.getLocalMoneyEn();
			if(hbcode!=null) {
				moneyType = hbcode.get("ITEMNAME_EN").toString();
			}
		}else if(jobLand.getLangTyp().equals("hk")) {
			localMoney = jobLandOut.getLocalMoneyHk();
			if(hbcode!=null) {
				moneyType = hbcode.get("ITEMNAME_HK").toString();
			}
		}else {
			if(hbcode!=null) {
				 moneyType = hbcode.get("ITMNAME").toString();
			}
		}
		
	
		reqIns.setLocalMoney(localMoney);
		reqIns.setSaleMoneyString(commonMethedService.getNewNum(reqIns.getSaleMoney(),localPoint)+"  "+localMoney);
		reqIns.setSaleTaxString(commonMethedService.getNewNum(reqIns.getSaleRate(),localPoint)+"  "+localMoney);
		reqIns.setCostMoneyString(commonMethedService.getNewNum(reqIns.getCostMoney(),localPoint)+"  "+localMoney);
		reqIns.setCostTaxString(commonMethedService.getNewNum(reqIns.getCostRate(),localPoint)+"  "+localMoney);
		reqIns.setTax2String(commonMethedService.getNewNum(reqIns.getRate2(),localPoint)+"  "+localMoney);
		reqIns.setTax3String(commonMethedService.getNewNum(reqIns.getRate3(),localPoint)+"  "+localMoney);
		reqIns.setProfitString(commonMethedService.getNewNum(reqIns.getProfit(),localPoint)+"  "+localMoney);
		//reqIns.setProfitRate(String.valueOf(MathController.scaleNumber(Double.parseDouble(reqIns.getProfitRate()))));
		reqIns.setProfitRateString("[ "+reqIns.getProfitRate()+" % ]");
		
		
		if(reqIns.getCureCode()==null||"".equals(reqIns.getCureCode())) {
			reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),localPoint)+"   "+localMoney);
			reqIns.setCureCodeString(null);
		}else {
			reqIns.setCureCode(String.valueOf(Double.parseDouble(reqIns.getCureCode())));
			reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),salePoint)+"   "+moneyType);
			reqIns.setCureCodeString(reqIns.getCureCode()+" ( 1 "+localMoney+" : "+reqIns.getSaleChangeUnit()+reqIns.getSaleMoneyUnit()+" )");
		}
		String langTyp= jobLand.getLangTyp();
		Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
	    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		
			//预计原价
			if("1".equals(reqIns.getSaleIsHave())) {
				reqIns.setSaleIsHave(bundle.getString("haveTax"));
			}else {
				reqIns.setSaleIsHave(bundle.getString("notHaveTax"));
			}
		reqIns.setReqMoney(commonMethedService.getNewNum(reqIns.getReqMoney(),localPoint));
		List<ReqIns> req = new ArrayList<ReqIns>();
		req.add(reqIns);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(req);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	
  //买卖登录票
	@Override
	public String bsTicketsOutPut(OutPutInput jobLand) throws JRException {
		// TODO Auto-generated method stub
		String jobcd = jobLand.getJobNo();
		int companycd = Integer.valueOf(jobLand.getCompanyID());
		CostListVo coVo = new CostListVo();
		coVo.setJobcd(jobcd);
		coVo.setCompanycd(companycd);
		//不验证 权限（all 得意先，担当割当）
		coVo.setAll("9999999");
		List<CostListVo> coList = costListMapper.getCostList(coVo);
		JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
		int localPoint = jobLandOut.getLocalPoint();
		List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
		List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
		//外货端数flg，0051 001
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		
		BsTickets bs = joblandmapper.selectBs(jobcd, companycd);
		//设置共通部分值
		bs.setCompanyName(jobLandOut.getCompanyName());
		bs.setCompanyNameEn(jobLandOut.getCompanyNameEn());
		bs.setJobName(jobLandOut.getJobName());
		bs.setJobcd(jobLandOut.getJobcd());
		bs.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0,10));
		bs.setSaleAddDate((commonMethedService.getTimeByZone(bs.getSaleAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
		bs.setAddUserName(jobLandOut.getAddUserName());
		bs.setSaleName(jobLandOut.getSaleName());
		bs.setdName(jobLandOut.getdName());
		bs.setqName(jobLandOut.getqName());
		bs.setHdyName(jobLandOut.getHdyName());
		bs.setzUser(jobLandOut.getzUser());
		bs.setdUser(jobLandOut.getdUser());
		bs.setmUser(jobLandOut.getmUser());
	    bs.setdCode(jobLandOut.getAccountCd());
	    Date date = new Date();
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    bs.setNowDate(commonMethedService.getTimeByZone(formatter.format(date), String.valueOf(Integer.valueOf(jobLand.getCompanyID()))));
	    bs.setPlanDalday(jobLandOut.getPlanDalday());
	    bs.setDalDay(jobLandOut.getDalDay());
	   //原价  实际原价合计  实际仕入增值税
	    List<Cost> cost = commonMethedMapper.getSumcost(companycd, jobcd);
	    String costNum = cost.get(0).getCostnum();
	    //如果没有实际成本  原价与仕入增税设置为0
	    if("0".equals(costNum)) {
	    	bs.setCostMoney("0");
	    	 bs.setCostTax("0");
	    	 bs.setPayMoney("0");
	    }else {
	    	 bs.setCostMoney(cost.get(0).getSumamt());
	   	    bs.setCostTax(cost.get(0).getSumvat());
	   	    bs.setPayMoney(cost.get(0).getPaysum());
	    }
	    
	    
	    Calculate calculate = commonMethedMapper.getCalculateTaxInfo(jobcd,companycd);
	   /* JobLand joblist = joblandmapper.selectJobByCd(jobcd, companycd);
		if(joblist.getAccountFlg()!=0) {
			List<JobList> jobs = joblandmapper.selectjobhistrn(jobcd, companycd);
			calculate.setRate(Double.parseDouble(jobs.get(0).getHistoryrate2()));
			calculate.setRate1(Double.parseDouble(jobs.get(0).getHistoryrate3()));
		}*/
	    Double rateWH = calculate.getRate();
		Double rateZZF = calculate.getRate1();
		Double taxTotal = 0.0;
		Double taxWH = 0.0;
		Double taxZZF = 0.0;
		
		//文化建设税 = （请求金额-支付金额）*税率1
		taxWH = MathController.mul(MathController.sub(Double.parseDouble(bs.getReqMoney()),Double.parseDouble(bs.getPayMoney())),rateWH);
		taxWH = commonMethedService.pointFormatHandler(taxWH,taxFormatFlg,localPoint);
	
		
		//增值附加税 = （卖上增值税-支付增值税）*税率2
		taxZZF = MathController.mul(MathController.sub(Double.parseDouble(bs.getSaleTax()),Double.parseDouble(bs.getCostTax())),rateZZF);
		taxZZF = commonMethedService.pointFormatHandler(taxZZF,taxFormatFlg,localPoint);
		
		//税金合计 = 文化+增值税附加
		taxTotal = MathController.add(taxWH, taxZZF);
		taxTotal = commonMethedService.pointFormatHandler(taxTotal,taxFormatFlg,localPoint);
		
		bs.setVatamt(String.valueOf(taxTotal));
		
	    //预计 营收
	    Double profit = 0.0;
		Double profitRate = 0.0;
		Double rProfit = 0.0;
		Double rProfitRate = 0.0;
		//营收 = 卖上金额-（原价金额+税金合计）
	    profit = MathController.sub(Double.parseDouble(bs.getPlanSaleMoney()), MathController.add(Double.parseDouble(bs.getPlanCostMoney()), Double.parseDouble(bs.getPlanTax())));
	    profit = commonMethedService.pointFormatHandler(profit,foreignFormatFlg,localPoint);
	    
		//营收率=营收/卖上金额 * 100
		profitRate = MathController.mul(MathController.div(profit,Double.parseDouble(bs.getPlanSaleMoney())),100);
//		profitRate = commonMethedService.pointFormatHandler(profitRate,foreignFormatFlg,localPoint);
		profitRate = Double.valueOf(commonMethedService.profitRateFormat(String.valueOf(profitRate)));
	  

	    //实际营收
	    rProfit = MathController.sub(Double.parseDouble(bs.getSaleMoney()), MathController.add(Double.parseDouble(bs.getCostMoney()), Double.parseDouble(bs.getVatamt())));
	    rProfit = commonMethedService.pointFormatHandler(rProfit,foreignFormatFlg,localPoint);
	  
		//营收率=营收/卖上金额 * 100
	    rProfitRate = MathController.mul(MathController.div(rProfit,Double.parseDouble(bs.getSaleMoney())),100);
//	    rProfitRate = commonMethedService.pointFormatHandler(rProfitRate,foreignFormatFlg,localPoint);
	    rProfitRate = Double.valueOf(commonMethedService.profitRateFormat(String.valueOf(rProfitRate)));
	  
	    //营收差
	    Double diffProfit = MathController.sub(rProfit,profit);
//	    Double diffProfitRate =  commonMethedService.pointFormatHandler(MathController.sub(rProfitRate, profitRate),foreignFormatFlg,localPoint);    
	    Double diffProfitRate =  MathController.scaleNumber(MathController.sub(rProfitRate, profitRate));    

	  //卖上 原价 差
	    Double diffSaleMoney =MathController.sub(Double.parseDouble(bs.getSaleMoney()),Double.parseDouble(bs.getPlanSaleMoney()));
	    Double diffSaleTax =MathController.sub(Double.parseDouble(bs.getSaleTax()),Double.parseDouble(bs.getPlanSaleTax()));
	    Double diffCostMoney =MathController.sub(Double.parseDouble(bs.getCostMoney()),Double.parseDouble(bs.getPlanCostMoney()));
	    Double diffCostTax = MathController.sub( Double.parseDouble(bs.getCostTax()),Double.parseDouble(bs.getPlanCostTax()));
	    Double diffTax = MathController.sub(Double.parseDouble(bs.getVatamt()),Double.parseDouble(bs.getPlanTax()));
	    // 千分符 格式化
	    bs.setDiffSaleMoney(commonMethedService.getNewNum(String.valueOf(diffSaleMoney), localPoint));
	    bs.setDiffSaleTax(commonMethedService.getNewNum(String.valueOf(diffSaleTax), localPoint));
	    bs.setDiffCostMoney(commonMethedService.getNewNum(String.valueOf(diffCostMoney), localPoint));
	    bs.setDiffCostTax(commonMethedService.getNewNum(String.valueOf(diffCostTax), localPoint));
	    bs.setDiffTax(commonMethedService.getNewNum(String.valueOf(diffTax), localPoint));
	    
	    //设置 营收块
	    bs.setPlanProfit(commonMethedService.getNewNum(String.valueOf(profit), localPoint));
	    bs.setProfit(commonMethedService.getNewNum(String.valueOf(rProfit), localPoint));
	    bs.setDiffProfit(commonMethedService.getNewNum(String.valueOf(diffProfit), localPoint));
	    bs.setProfitRate(String.valueOf(rProfitRate)+"%");
	    bs.setPlanProfitRate(String.valueOf(profitRate)+"%");
	    bs.setDiffProfitRate(String.valueOf(diffProfitRate)+"%");
	    
	
	    bs.setCostMoney(commonMethedService.getNewNum(String.valueOf(cost.get(0).getSumamt()), localPoint));
	    bs.setCostTax(commonMethedService.getNewNum(String.valueOf(cost.get(0).getSumvat()), localPoint));
	    //格式化 数字
	    bs.setPlanSaleMoney(commonMethedService.getNewNum(String.valueOf(bs.getPlanSaleMoney()), localPoint));
	    bs.setPlanSaleTax(commonMethedService.getNewNum(String.valueOf(bs.getPlanSaleTax()), localPoint));
	    bs.setPlanCostMoney(commonMethedService.getNewNum(String.valueOf(bs.getPlanCostMoney()), localPoint));
	    bs.setPlanCostTax(commonMethedService.getNewNum(String.valueOf(bs.getPlanCostTax()), localPoint));
	    bs.setSaleMoney(commonMethedService.getNewNum(String.valueOf(bs.getSaleMoney()), localPoint));
	    bs.setSaleTax(commonMethedService.getNewNum(String.valueOf(bs.getSaleTax()), localPoint));
	    bs.setPlanTax(commonMethedService.getNewNum(String.valueOf(bs.getPlanTax()), localPoint));
	    bs.setTax(commonMethedService.getNewNum(String.valueOf(bs.getVatamt()), localPoint));
	    
	    List<BsTickets> bsTicket = new ArrayList<BsTickets>();
	    bsTicket.add(bs);
	    //创建 ireport paramter 参数 
	    HashMap<String, Object> parameters = new HashMap<String, Object>();
	    //格式化 原价数据
	    for(CostListVo cl:coList) {
	    	cl.setAdddate((commonMethedService.getTimeByZone(cl.getAdddate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0,10));
	    	cl.setAmt(commonMethedService.getNewNum(cl.getAmt(), localPoint));
	    	cl.setVatamt(commonMethedService.getNewNum(cl.getVatamt(), localPoint));
	    	if("001".equals(cl.getDiscd())) {
	    		
	    		cl.setLenduser(cl.getAddusername());
	    	}
	    	
	    }
	    // 将格式化后的结果集 传给 TSET 参数
	    parameters.put("TSET", coList);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(bsTicket);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp(),parameters);
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	//job更新
	@Override
	public String jobUpdateOutPut(OutPutInput jobLand) throws JRException {
		// TODO Auto-generated method stub
		String jobcd = jobLand.getJobNo();
		int companycd = Integer.valueOf(jobLand.getCompanyID());
		JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
		String addName = joblandmapper.getNameByUserName(jobcd, companycd);
		jobLandOut.setAddUserName(addName);
		int localPoint = jobLandOut.getLocalPoint();
		List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
		List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
		//外货端数flg，0051 001
		@SuppressWarnings("unused")
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		@SuppressWarnings("unused")
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		
		int planSalePoint = jobLandOut.getPlanSalePoint();
		int planCostPoint = jobLandOut.getPlanCostPoint();
		/*List<Cost> cost = commonMethedMapper.getSumcost(companycd, jobcd);
		Calculate calculate = commonMethedService.calcuateJobTax(jobcd,companycd);
		String costNum = cost.get(0).getCostnum();
	    String costAmt  = cost.get(0).getSumamt();
	    String vatAmt = cost.get(0).getSumvat();
		//如果原价完了
		if(jobLandOut.getCostFinishFlg()==1) {
			//没有实际成本
			if("0".equals(costNum)) {
				costAmt = "0";
				vatAmt = "0";
			}
		}else {
			if("0".equals(costNum)) {
				costAmt = jobLandOut.getPlanCost();
				vatAmt = jobLandOut.getPlanCostTax();
			}
		}
		
		jobLandOut.setPlanCost(costAmt);
		jobLandOut.setPlanCostTax(vatAmt);
		//设置 税金合计 营收 营收率 
		jobLandOut.setProfit(commonMethedService.getNewNum(String.valueOf(calculate.getProfit()),localPoint));
		jobLandOut.setProfitRate(String.valueOf(calculate.getProfitRate()));
		jobLandOut.setPlanTax(String.valueOf(calculate.getTaxTotal()));*/
		
		//将数据整合成字符串
//		jobLandOut.setPlanSaleString(commonMethedService.getNewNum(jobLandOut.getPlanSale(),localPoint)+"   "+jobLandOut.getLocalMoney()); 
//		jobLandOut.setPlanSaleTaxString(commonMethedService.getNewNum(jobLandOut.getPlanSaleTax(),localPoint)+"   "+jobLandOut.getLocalMoney()); 
//		jobLandOut.setPlanCostString(commonMethedService.getNewNum(jobLandOut.getPlanCost(),localPoint)+"   "+jobLandOut.getLocalMoney());
//		jobLandOut.setPlanCostTaxString(commonMethedService.getNewNum(jobLandOut.getPlanCostTax(),localPoint)+"   "+jobLandOut.getLocalMoney());
//		jobLandOut.setTaxString(commonMethedService.getNewNum(jobLandOut.getPlanTax(),localPoint)+"   "+jobLandOut.getLocalMoney());
//		jobLandOut.setProfitRate(String.valueOf(MathController.scaleNumber(Double.parseDouble(jobLandOut.getProfitRate()))));
//		
//		jobLandOut.setProfitRateString("[ "+jobLandOut.getProfitRate()+" %]");
//		jobLandOut.setPlanSaleTax(commonMethedService.getNewNum(jobLandOut.getPlanSaleTax(),localPoint));
//		jobLandOut.setProfit(commonMethedService.getNewNum(jobLandOut.getProfit(),localPoint));
		
		String LocalMoney = null;
	if(jobLand.getLangTyp().equals("jp")) {
		LocalMoney= jobLandOut.getLocalMoneyJp();
	}else if(jobLand.getLangTyp().equals("zc")) {
		LocalMoney = jobLandOut.getLocalMoney();
	}else if(jobLand.getLangTyp().equals("zt")) {
		LocalMoney = jobLandOut.getLocalMoneyHk();
	}else {
		LocalMoney = jobLandOut.getLocalMoneyEn();
	}
		jobLandOut.setLocalMoney(LocalMoney);
		jobLandOut.setPlanSaleString(commonMethedService.getNewNum(jobLandOut.getPlanSale(),localPoint)+"   "+LocalMoney); 
		jobLandOut.setPlanSaleTaxString(commonMethedService.getNewNum(jobLandOut.getPlanSaleTax(),localPoint)+"   "+LocalMoney); 
		jobLandOut.setPlanCostString(commonMethedService.getNewNum(jobLandOut.getPlanCost(),localPoint)+"   "+LocalMoney);
		jobLandOut.setPlanCostTaxString(commonMethedService.getNewNum(jobLandOut.getPlanCostTax(),localPoint)+"   "+LocalMoney);
		jobLandOut.setTaxString(commonMethedService.getNewNum(jobLandOut.getPlanTax(),localPoint)+"   "+LocalMoney);
//		jobLandOut.setProfitRate(String.valueOf(commonMethedService.pointFormatHandler(Double.parseDouble(jobLandOut.getProfitRate()), foreignFormatFlg, localPoint)));
		jobLandOut.setProfitRate(String.valueOf(MathController.scaleNumber(Double.parseDouble(jobLandOut.getProfitRate()))));
		
		jobLandOut.setProfitRateString("[ "+commonMethedService.profitRateFormat(jobLandOut.getProfitRate())+" % ]");
		jobLandOut.setPlanSaleTax(commonMethedService.getNewNum(jobLandOut.getPlanSaleTax(),localPoint));
		jobLandOut.setPlanCostTax(commonMethedService.getNewNum(jobLandOut.getPlanCostTax(),localPoint));
		jobLandOut.setProfit(commonMethedService.getNewNum(jobLandOut.getProfit(),localPoint));
		//去除数据库中取出的换算code的多余小数点位数 0
	//	jobLandOut.setPlanSaleCureCode(String.valueOf(Double.parseDouble(jobLandOut.getPlanSaleCureCode())));
		
		
		if(jobLandOut.getPlanSaleCureCode()==null||"".equals(jobLandOut.getPlanSaleCureCode())) {
			jobLandOut.setPlanSaleForeignString(commonMethedService.getNewNum(jobLandOut.getPlanSaleForeignAmt(),localPoint)+"   "+LocalMoney);
			jobLandOut.setPlanSaleCureCodeString(null);
		}else {
			jobLandOut.setPlanSaleCureCode(String.valueOf(Double.parseDouble(jobLandOut.getPlanSaleCureCode())));
			jobLandOut.setPlanSaleForeignString(commonMethedService.getNewNum(jobLandOut.getPlanSaleForeignAmt(),planSalePoint)+"   "+jobLandOut.getPlanSaleMoney());
			jobLandOut.setPlanSaleCureCodeString(jobLandOut.getPlanSaleCureCode()+" ( 1 "+jobLandOut.getLocalMoney()+" : "+jobLandOut.getPlanSaleChangeUnit()+jobLandOut.getPlanSaleMoney()+" )");
		}
		if(jobLandOut.getPlanCostCureCode()==null||"".equals(jobLandOut.getPlanCostCureCode())) {
			jobLandOut.setPlanCostForeignString(commonMethedService.getNewNum(jobLandOut.getPlanCostForeignAmt(),localPoint)+"   "+LocalMoney);
			jobLandOut.setPlanCostCureCodeString(null);
		}else {
			jobLandOut.setPlanCostCureCode(String.valueOf(Double.parseDouble(jobLandOut.getPlanCostCureCode())));
			jobLandOut.setPlanCostForeignString(commonMethedService.getNewNum(jobLandOut.getPlanCostForeignAmt(),planCostPoint)+"   "+jobLandOut.getPlanCostMoney());
			jobLandOut.setPlanCostCureCodeString(jobLandOut.getPlanCostCureCode()+" ( 1 "+LocalMoney+" : "+jobLandOut.getPlanCostChangeUnit()+jobLandOut.getPlanCostMoney()+" )");
		}
		String langTyp= jobLand.getLangTyp();
		Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
	    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
	
			//预计原价
			if("1".equals(jobLandOut.getPlanCostIsHave())) {
				jobLandOut.setPlanCostIsHave(bundle.getString("haveTax"));
			}else {
				jobLandOut.setPlanCostIsHave(bundle.getString("notHaveTax"));
			}
			//预计卖上
			if("1".equals(jobLandOut.getPlanSaleIsHave())) {
				jobLandOut.setPlanSaleIsHave(bundle.getString("haveTax"));
			}else {
				jobLandOut.setPlanSaleIsHave(bundle.getString("notHaveTax"));
			}
		jobLandOut.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0,10));
		jobLandOut.setUpddate((commonMethedService.getTimeByZone(jobLandOut.getUpddate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0,10));
		List<JobLandOut> jbs = new ArrayList<JobLandOut>();
		jbs.add(jobLandOut);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(jbs);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	
	
    //卖上承认
	@Override
	public String saleAdminPdf(OutPutInput jobLand) throws JRException {
		String jobcd = jobLand.getJobNo();
		int companycd = Integer.valueOf(jobLand.getCompanyID());
		JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
		Calculate cal = commonMethedService.calcuateJobTax(jobcd, companycd);
		

		ReqIns reqIns = joblandmapper.selectReqInsOutPut(jobcd, companycd);
		int localPoint = jobLandOut.getLocalPoint();
		
		List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
		List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
		//外货端数flg，0051 001
		@SuppressWarnings("unused")
		int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
		//taxFormatFlg	0052 003
		@SuppressWarnings("unused")
		int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
		
		int salePoint = reqIns.getSalePoint();
		
		reqIns.setCompanyName(jobLandOut.getCompanyName());
		reqIns.setCompanyNameEn(jobLandOut.getCompanyNameEn());
		reqIns.setJobName(jobLandOut.getJobName());
		reqIns.setJobcd(jobLandOut.getJobcd());
		reqIns.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
	
		reqIns.setAddUserName(jobLandOut.getAddUserName());
		reqIns.setSaleName(jobLandOut.getSaleName());
		reqIns.setdName(jobLandOut.getdName());
		reqIns.setqName(jobLandOut.getqName());
		reqIns.setHdyName(jobLandOut.getHdyName());
		reqIns.setzUser(jobLandOut.getzUser());
		reqIns.setdUser(jobLandOut.getdUser());
		reqIns.setLocalMoney(jobLandOut.getLocalMoney());
		reqIns.setLocalMoneyEn(jobLandOut.getLocalMoneyEn());
		reqIns.setLocalMoneyHk(jobLandOut.getLocalMoneyHk());
		reqIns.setLocalMoneyJp(jobLandOut.getLocalMoneyJp());
		reqIns.setSaleForeignMoney(jobLandOut.getSaleForeignMoney());
		reqIns.setSaleForeignMoneyEn(jobLandOut.getSaleForeignMoneyEn());
		reqIns.setSaleForeignMoneyHk(jobLandOut.getSaleForeignMoneyHk());
		reqIns.setSaleForeignMoneyJp(jobLandOut.getSaleForeignMoneyJp());
		reqIns.setDalDay(jobLandOut.getDalDay());
		reqIns.setPlanDalday(jobLandOut.getPlanDalday());
		//原价合计  原价条数  仕入增值税
		List<Cost> cost  = commonMethedMapper.getSumcost(companycd, jobcd);
		String costNum = cost.get(0).getCostnum();
	    String costAmt  = cost.get(0).getSumamt();
	    String vatAmt = cost.get(0).getSumvat();
		//如果原价完了
		if(reqIns.getCostFinishFlg()==1) {
			//没有实际成本
			if("0".equals(costNum)) {
				costAmt = "0";
				vatAmt = "0";
			}
		}else {
			if("0".equals(costNum)) {
				costAmt = reqIns.getPlanCostMoney();
				vatAmt = reqIns.getPlanCostTax();
			}
		}	
//		String saleAmt = reqIns.getSaleMoney();
//		double profit = Double.parseDouble(saleAmt)-(Double.parseDouble(costAmt)+Double.parseDouble(vatAmt));
//		double profitRate = profit/Double.parseDouble(saleAmt)*100;
		reqIns.setProfit(String.valueOf(cal.getProfit()));
	
		reqIns.setProfitRate(String.valueOf(cal.getProfitRate()));
		reqIns.setRate2(String.valueOf(cal.getTaxWH()));
		reqIns.setRate3(String.valueOf(cal.getTaxZZF()));
		reqIns.setCostMoney(costAmt);
		reqIns.setCostRate(vatAmt);
		//整合字符串
		String localMoney = reqIns.getLocalMoney();
		reqIns.setSaleMoneyString(commonMethedService.getNewNum(reqIns.getSaleMoney(),localPoint)+"  "+localMoney);
		reqIns.setSaleTaxString(commonMethedService.getNewNum(reqIns.getSaleRate(),localPoint)+"  "+localMoney);
		reqIns.setCostMoneyString(commonMethedService.getNewNum(reqIns.getCostMoney(),localPoint)+"  "+localMoney);
		reqIns.setCostTaxString(commonMethedService.getNewNum(reqIns.getCostRate(),localPoint)+"  "+localMoney);
		reqIns.setTax2String(commonMethedService.getNewNum(reqIns.getRate2(),localPoint)+"  "+localMoney);
		reqIns.setTax3String(commonMethedService.getNewNum(reqIns.getRate3(),localPoint)+"  "+localMoney);
		reqIns.setProfitString(commonMethedService.getNewNum(reqIns.getProfit(),localPoint)+"  "+localMoney);
//		reqIns.setProfitRate(String.valueOf(commonMethedService.pointFormatHandler(Double.parseDouble(reqIns.getProfitRate()), foreignFormatFlg, localPoint)));
		reqIns.setProfitRate(String.valueOf(MathController.scaleNumber(Double.parseDouble(reqIns.getProfitRate()))));
		String rate []		= reqIns.getProfitRate().split("\\.");
		if(rate[1].length()<=1) {
			rate[1] =rate[1]+"0";
			reqIns.setProfitRate(rate[0]+"."+rate[1]); 
		}
		reqIns.setProfitRateString("[ "+reqIns.getProfitRate()+" % ]");
		
		
		if(reqIns.getCureCode()==null||"".equals(reqIns.getCureCode())) {
			reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),localPoint)+"   "+localMoney);
			reqIns.setCureCodeString(null);
		}else {
			reqIns.setCureCode(String.valueOf(Double.parseDouble(reqIns.getCureCode())));
			reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),salePoint)+"   "+reqIns.getSaleMoneyUnit());
			reqIns.setCureCodeString(reqIns.getCureCode()+" ( 1 "+localMoney+" : "+reqIns.getSaleChangeUnit()+reqIns.getSaleMoneyUnit()+" )");
		}
		if(reqIns.getSaleAdminDate()!=null&&!"".equals(reqIns.getSaleAdminDate())) {
		reqIns.setSaleAdminDate((commonMethedService.getTimeByZone(reqIns.getSaleAdminDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID()))).substring(0,10)));
		}
		String langTyp= jobLand.getLangTyp();
		Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
	    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
		
			//预计原价
			if("1".equals(reqIns.getSaleIsHave())) {
				reqIns.setSaleIsHave(bundle.getString("haveTax"));
			}else {
				reqIns.setSaleIsHave(bundle.getString("notHaveTax"));
			}
			
		
		reqIns.setReqMoney(commonMethedService.getNewNum(reqIns.getReqMoney(),localPoint));
		List<ReqIns> req = new ArrayList<ReqIns>();
		req.add(reqIns);
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(req);
		String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
		if(fileBaseStr.equals("error"))
		{
			return null;
		}
		return fileBaseStr;
	}
	
	 //发票发行申请
		@Override
		public String invoiceOrderPdfTx(OutPutInput jobLand) throws JRException {
			String jobcd = jobLand.getJobNo();
			int companycd = Integer.valueOf(jobLand.getCompanyID());
			JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
			Calculate cal = commonMethedService.calcuateJobTax(jobcd, companycd);
			String jobDtFlg = jobLand.getJobDtFlg();
			if(!"jobDetails".equals(jobDtFlg)) {
				saleTypeService.upInvTx(jobcd,companycd,jobLand.getUserID());
			}
            ReqIns reqIns = joblandmapper.selectReqInsOutPut(jobcd, companycd);
			int localPoint = jobLandOut.getLocalPoint();
			
			List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
			List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
			//外货端数flg，0051 001
			@SuppressWarnings("unused")
			int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
			//taxFormatFlg	0052 003
			@SuppressWarnings("unused")
			int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
			
			int salePoint = reqIns.getSalePoint();
			reqIns.setCompanyName(jobLandOut.getCompanyName());
			reqIns.setCompanyNameEn(jobLandOut.getCompanyNameEn());
			reqIns.setJobName(jobLandOut.getJobName());
			reqIns.setJobcd(jobLandOut.getJobcd());
			reqIns.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
			reqIns.setAddUserName(jobLandOut.getAddUserName());
			reqIns.setSaleName(jobLandOut.getSaleName());
			reqIns.setdName(jobLandOut.getdName());
			reqIns.setqName(jobLandOut.getqName());
			reqIns.setHdyName(jobLandOut.getHdyName());
			reqIns.setzUser(jobLandOut.getzUser());
			reqIns.setdUser(jobLandOut.getdUser());
			reqIns.setLocalMoney(jobLandOut.getLocalMoney());
			reqIns.setLocalMoneyEn(jobLandOut.getLocalMoneyEn());
			reqIns.setLocalMoneyHk(jobLandOut.getLocalMoneyHk());
			reqIns.setLocalMoneyJp(jobLandOut.getLocalMoneyJp());
			reqIns.setSaleForeignMoney(jobLandOut.getSaleForeignMoney());
			reqIns.setSaleForeignMoneyEn(jobLandOut.getSaleForeignMoneyEn());
			reqIns.setSaleForeignMoneyHk(jobLandOut.getSaleForeignMoneyHk());
			reqIns.setSaleForeignMoneyJp(jobLandOut.getSaleForeignMoneyJp());
			reqIns.setPlanDalday(jobLandOut.getPlanDalday());
			reqIns.setDalDay(jobLandOut.getDalDay());
			//原价合计  原价条数  仕入增值税
			List<Cost> cost  = commonMethedMapper.getSumcost(companycd, jobcd);
			String costNum = cost.get(0).getCostnum();
		    String costAmt  = cost.get(0).getSumamt();
		    String vatAmt = cost.get(0).getSumvat();
			//如果原价完了
			if(reqIns.getCostFinishFlg()==1) {
				//没有实际成本
				if("0".equals(costNum)) {
					costAmt = "0";
					vatAmt = "0";
				}
			}else {
				if("0".equals(costNum)) {
					costAmt = reqIns.getPlanCostMoney();
					vatAmt = reqIns.getPlanCostTax();
				}
			}	
//			String saleAmt = reqIns.getSaleMoney();
//			double profit = Double.parseDouble(saleAmt)-(Double.parseDouble(costAmt)+Double.parseDouble(vatAmt));
//			double profitRate = profit/Double.parseDouble(saleAmt)*100;
			reqIns.setProfit(String.valueOf(cal.getProfit()));
			if(Double.POSITIVE_INFINITY==cal.getProfitRate())
			{
				reqIns.setProfitRate(String.valueOf(cal.getProfitRate()));
			}else {
				reqIns.setProfitRate(commonMethedService.profitRateFormat(String.valueOf(cal.getProfitRate())));
			}
			reqIns.setRate2(String.valueOf(cal.getTaxWH()));
			reqIns.setRate3(String.valueOf(cal.getTaxZZF()));
			reqIns.setCostMoney(costAmt);
			reqIns.setCostRate(vatAmt);
			//整合字符串
			Map<Object,Object>  hbcode =null;
			if(reqIns.getSaleForeignCode()!=null&&!reqIns.getSaleForeignCode().equals("")) {
				hbcode= commonMethedMapper.selectcodetype(reqIns.getSaleForeignCode(),jobLand.getCompanyID());
			}
			String moneyType =null;
			String localMoney = jobLandOut.getLocalMoney();
			if(jobLand.getLangTyp().equals("jp")) {
				localMoney = jobLandOut.getLocalMoneyJp();
				if(hbcode!=null) {
					moneyType = hbcode.get("ITEMNAME_JP").toString();
				}
				
			}else if(jobLand.getLangTyp().equals("en")) {
				localMoney =  jobLandOut.getLocalMoneyEn();
				if(hbcode!=null) {
					moneyType = hbcode.get("ITEMNAME_EN").toString();
				}
			}else if(jobLand.getLangTyp().equals("hk")) {
				localMoney = jobLandOut.getLocalMoneyHk();
				if(hbcode!=null) {
					moneyType = hbcode.get("ITEMNAME_HK").toString();
				}
			}else {
				if(hbcode!=null) {
					moneyType = hbcode.get("ITMNAME").toString();
				}
			}
			reqIns.setLocalMoney(localMoney);
			reqIns.setSaleMoneyString(commonMethedService.getNewNum(reqIns.getSaleMoney(),localPoint)+"  "+localMoney);
			reqIns.setSaleTaxString(commonMethedService.getNewNum(reqIns.getSaleRate(),localPoint)+"  "+localMoney);
			reqIns.setCostMoneyString(commonMethedService.getNewNum(reqIns.getCostMoney(),localPoint)+"  "+localMoney);
			reqIns.setCostTaxString(commonMethedService.getNewNum(reqIns.getCostRate(),localPoint)+"  "+localMoney);
			reqIns.setTax2String(commonMethedService.getNewNum(reqIns.getRate2(),localPoint)+"  "+localMoney);
			reqIns.setTax3String(commonMethedService.getNewNum(reqIns.getRate3(),localPoint)+"  "+localMoney);
			reqIns.setProfitString(commonMethedService.getNewNum(reqIns.getProfit(),localPoint)+"  "+localMoney);
			reqIns.setProfitRateString("[ "+reqIns.getProfitRate()+" % ]");
			
			if(reqIns.getCureCode()==null||"".equals(reqIns.getCureCode())) {
				reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),localPoint)+"   "+localMoney);
				reqIns.setCureCodeString(null);
			}else {
				reqIns.setCureCode(String.valueOf(Double.parseDouble(reqIns.getCureCode())));
				reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),salePoint)+"   "+moneyType);
				reqIns.setCureCodeString(reqIns.getCureCode()+" ( 1 "+localMoney+" : "+reqIns.getSaleChangeUnit()+reqIns.getSaleMoneyUnit()+" )");
			}
			String langTyp= jobLand.getLangTyp();
			Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
		    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
			
				//预计原价
				if("1".equals(reqIns.getSaleIsHave())) {
					reqIns.setSaleIsHave(bundle.getString("haveTax"));
				}else {
					reqIns.setSaleIsHave(bundle.getString("notHaveTax"));
				}
				reqIns.setReqMoney(reqIns.getReqMoney().replace(",", ""));
			reqIns.setReqMoney(commonMethedService.getNewNum(reqIns.getReqMoney(),localPoint));
			List<ReqIns> req = new ArrayList<ReqIns>();
			req.add(reqIns);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(req);
			String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
			if(fileBaseStr.equals("error"))
			{
				return null;
			}
			return fileBaseStr;
		}
		@Override
		public Object outMorePdf(OutPutInput jobLand) throws JRException {
			String jobcd = jobLand.getJobNo();
			Object  PdfToZip = null;
			if(jobLand.getFileName().equals("billOrder")) {
				if(jobcd==null||"".equals(jobcd)) {
					
						List<byte[]> pdfList = new ArrayList<byte[]>();
						for(String jobNo:jobLand.getJobList()) {
							pdfList.add(Base64.getDecoder().decode(this.reqInsOutPutTx(jobLand,jobNo)));
						}
						try {
							PdfToZip = PdfToZipUtil.pdfToZip(pdfList,jobLand.getPdfName(),jobLand.getJobList(),"","");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					
					return PdfToZip;
				}else{
					List<byte[]> pdfList = new ArrayList<byte[]>();
					if(jobLand.getSales()!=null&&!jobLand.getSales().equals("")) {
						if(jobLand.getSales().equals("invoiceApplication")) {
							pdfList.add(Base64.getDecoder().decode( this.reqInsOutPutTx(jobLand)));
							jobLand.setFileName("invoiceApplication");
							pdfList.add(Base64.getDecoder().decode( this.invoiceOrderPdfTx(jobLand)));
							try {
								PdfToZip = PdfToZipUtil.pdfToZip(pdfList,jobLand.getPdfName(),jobLand.getJobList(),jobcd,jobLand.getPdfName());
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					}
					else {
						PdfToZip = this.reqInsOutPutTx(jobLand);
					}
					return PdfToZip;		
				}
			}else {
				if(jobcd==null||"".equals(jobcd)) {
					if(jobLand.getJobList().size()>1) {
						List<byte[]> pdfList = new ArrayList<byte[]>();
						for(String jobNo:jobLand.getJobList()) {
							pdfList.add(Base64.getDecoder().decode(this.invoiceOrderPdfTx(jobLand,jobNo)));
						}
						try {
							PdfToZip = PdfToZipUtil.pdfToZip(pdfList,jobLand.getPdfName(),jobLand.getJobList(),"","");
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
					}else {
						List<String> pdfList = new ArrayList<String>();
						for(String jobNo:jobLand.getJobList()) {
							pdfList.add(this.invoiceOrderPdfTx(jobLand,jobNo));
						}
						PdfToZip = pdfList;
					}
				
				return PdfToZip;
				}else{
					return this.invoiceOrderPdfTx(jobLand);
				}
			}
			
		}
		@Override
		public String reqInsOutPutTx(OutPutInput jobLand, String jobcd) throws JRException {
			//ReqInfo req = saleType.getReq();
			int companycd = Integer.valueOf(jobLand.getCompanyID());
			JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
			Calculate cal = commonMethedService.calcuateJobTax(jobcd, companycd);
			int localPoint = jobLandOut.getLocalPoint();
			List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
			List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
			//外货端数flg，0051 001
			@SuppressWarnings("unused")
			int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
			//taxFormatFlg	0052 003
			@SuppressWarnings("unused")
			int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
			String jobDtFlg = jobLand.getJobDtFlg();
			if(!"jobDetails".equals(jobDtFlg)) {
				saleTypeService.upReqTx(jobcd,companycd,jobLand.getUserID());
			}
			
			ReqIns reqIns = joblandmapper.selectReqInsOutPut(jobcd, companycd);
			int salePoint = reqIns.getSalePoint();
			reqIns.setCompanyName(jobLandOut.getCompanyName());
			reqIns.setCompanyNameEn(jobLandOut.getCompanyNameEn());
			reqIns.setJobName(jobLandOut.getJobName());
			reqIns.setJobcd(jobLandOut.getJobcd());
			reqIns.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
			reqIns.setAddUserName(jobLandOut.getAddUserName());
			reqIns.setSaleName(jobLandOut.getSaleName());
			reqIns.setdName(jobLandOut.getdName());
			reqIns.setqName(jobLandOut.getqName());
			reqIns.setHdyName(jobLandOut.getHdyName());
			reqIns.setzUser(jobLandOut.getzUser());
			reqIns.setdUser(jobLandOut.getdUser());
			reqIns.setLocalMoney(jobLandOut.getLocalMoney());
			reqIns.setLocalMoneyEn(jobLandOut.getLocalMoneyEn());
			reqIns.setLocalMoneyHk(jobLandOut.getLocalMoneyHk());
			reqIns.setLocalMoneyJp(jobLandOut.getLocalMoneyJp());
			reqIns.setSaleForeignMoney(jobLandOut.getSaleForeignMoney());
			reqIns.setSaleForeignMoneyEn(jobLandOut.getSaleForeignMoneyEn());
			reqIns.setSaleForeignMoneyHk(jobLandOut.getSaleForeignMoneyHk());
			reqIns.setSaleForeignMoneyJp(jobLandOut.getSaleForeignMoneyJp());
			reqIns.setDalDay(jobLandOut.getDalDay());
			reqIns.setPlanDalday(jobLandOut.getPlanDalday());
			//原价合计  原价条数  仕入增值税
			List<Cost> cost  = commonMethedMapper.getSumcost(companycd, jobcd);
			String costNum = cost.get(0).getCostnum();
		    String costAmt  = cost.get(0).getSumamt();
		    String vatAmt = cost.get(0).getSumvat();
			//如果原价完了
			if(reqIns.getCostFinishFlg()==1) {
				//没有实际成本
				if("0".equals(costNum)) {
					costAmt = "0";
					vatAmt = "0";
				}
			}else {
				if("0".equals(costNum)) {
					costAmt = reqIns.getPlanCostMoney();
					vatAmt = reqIns.getPlanCostTax();
				}
			}	
//			String saleAmt = reqIns.getSaleMoney();
//			double profit = Double.parseDouble(saleAmt)-(Double.parseDouble(costAmt)+Double.parseDouble(vatAmt));
//			double profitRate = profit/Double.parseDouble(saleAmt)*100;
			reqIns.setProfit(String.valueOf(cal.getProfit()));
			if(cal.getProfitRate() != Double.POSITIVE_INFINITY)
			{
				reqIns.setProfitRate(commonMethedService.profitRateFormat(String.valueOf(cal.getProfitRate())));
			}else {
				reqIns.setProfitRate(String.valueOf(cal.getProfitRate()));
			}
			//reqIns.setProfitRate(commonMethedService.profitRateFormat(String.valueOf(cal.getProfitRate())));
			reqIns.setRate2(String.valueOf(cal.getTaxWH()));
			reqIns.setRate3(String.valueOf(cal.getTaxZZF()));
			reqIns.setCostMoney(costAmt);
			reqIns.setCostRate(vatAmt);
			//整合字符串
			Map<Object,Object>  hbcode =null;
			String moneyType = "";
			if(reqIns.getSaleForeignCode()!=null) {
				if(!reqIns.getSaleForeignCode().equals("")) {
					hbcode= commonMethedMapper.selectcodetype(reqIns.getSaleForeignCode(),jobLand.getCompanyID());
				}
			}
			String localMoney = jobLandOut.getLocalMoney();
			if(hbcode==null) {
				moneyType =localMoney;
			}else {
				moneyType = hbcode.get("ITMNAME").toString();
			}
			if(jobLand.getLangTyp().equals("jp")) {
				localMoney = jobLandOut.getLocalMoneyJp();
				if(hbcode==null) {
					moneyType =localMoney;
				}else {
					moneyType = hbcode.get("ITEMNAME_JP").toString();
				}
				
			}else if(jobLand.getLangTyp().equals("en")) {
				localMoney =  jobLandOut.getLocalMoneyEn();
				if(hbcode==null) {
					moneyType =localMoney;
				}else {
					moneyType = hbcode.get("ITEMNAME_EN").toString();
				}
				
			}else if(jobLand.getLangTyp().equals("hk")) {
				localMoney = jobLandOut.getLocalMoneyHk();
				if(hbcode==null) {
					moneyType =localMoney;
				}else {
					moneyType = hbcode.get("ITEMNAME_HK").toString();
				}
			}
			reqIns.setLocalMoney(localMoney);
			reqIns.setSaleMoneyString(commonMethedService.getNewNum(reqIns.getSaleMoney(),localPoint)+"  "+localMoney);
			reqIns.setSaleTaxString(commonMethedService.getNewNum(reqIns.getSaleRate(),localPoint)+"  "+localMoney);
			reqIns.setCostMoneyString(commonMethedService.getNewNum(reqIns.getCostMoney(),localPoint)+"  "+localMoney);
			reqIns.setCostTaxString(commonMethedService.getNewNum(reqIns.getCostRate(),localPoint)+"  "+localMoney);
			reqIns.setTax2String(commonMethedService.getNewNum(reqIns.getRate2(),localPoint)+"  "+localMoney);
			reqIns.setTax3String(commonMethedService.getNewNum(reqIns.getRate3(),localPoint)+"  "+localMoney);
			reqIns.setProfitString(commonMethedService.getNewNum(reqIns.getProfit(),localPoint)+"  "+localMoney);
			//reqIns.setProfitRate(String.valueOf(MathController.scaleNumber(Double.parseDouble(reqIns.getProfitRate()))));
			reqIns.setProfitRateString("[ "+reqIns.getProfitRate()+" % ]");
			
			
			if(reqIns.getCureCode()==null||"".equals(reqIns.getCureCode())) {
				reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),localPoint)+"   "+moneyType);
				reqIns.setCureCodeString(null);
			}else {
				reqIns.setCureCode(String.valueOf(Double.parseDouble(reqIns.getCureCode())));
				reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),salePoint)+"   "+reqIns.getSaleMoneyUnit());
				reqIns.setCureCodeString(reqIns.getCureCode()+" ( 1 "+localMoney+" : "+reqIns.getSaleChangeUnit()+reqIns.getSaleMoneyUnit()+" )");
			}
			String langTyp= jobLand.getLangTyp();
			Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
		    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
			
				//预计原价
				if("1".equals(reqIns.getSaleIsHave())) {
					reqIns.setSaleIsHave(bundle.getString("haveTax"));
				}else {
					reqIns.setSaleIsHave(bundle.getString("notHaveTax"));
				}
			reqIns.setReqMoney(commonMethedService.getNewNum(reqIns.getReqMoney(),localPoint));
			List<ReqIns> req = new ArrayList<ReqIns>();
			req.add(reqIns);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(req);
			String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
			if(fileBaseStr.equals("error"))
			{
				return null;
			}
			return fileBaseStr;
		}
		public String invoiceOrderPdfTx(OutPutInput jobLand, String jobcd) throws JRException {
			int companycd = Integer.valueOf(jobLand.getCompanyID());
			JobLandOut jobLandOut = joblandmapper.jobLogOutPut(jobcd,companycd);
			Calculate cal = commonMethedService.calcuateJobTax(jobcd, companycd);
			String jobDtFlg = jobLand.getJobDtFlg();
			if(!"jobDetails".equals(jobDtFlg)) {
				saleTypeService.upInvTx(jobcd,companycd,jobLand.getUserID());
			}
		
            ReqIns reqIns = joblandmapper.selectReqInsOutPut(jobcd, companycd);
			int localPoint = jobLandOut.getLocalPoint();
			
			List<CommonmstVo> foreignCodeVO = joblandmapper.selectSaleCode(companycd,"001","0051");
			List<CommonmstVo> taxCodeVO = joblandmapper.selectSaleCode(companycd,"003","0052");
			//外货端数flg，0051 001
			@SuppressWarnings("unused")
			int foreignFormatFlg = Integer.valueOf(foreignCodeVO.get(0).getAidcd());
			//taxFormatFlg	0052 003
			@SuppressWarnings("unused")
			int taxFormatFlg = Integer.valueOf(taxCodeVO.get(0).getAidcd());
			
			int salePoint = reqIns.getSalePoint();
			reqIns.setCompanyName(jobLandOut.getCompanyName());
			reqIns.setCompanyNameEn(jobLandOut.getCompanyNameEn());
			reqIns.setJobName(jobLandOut.getJobName());
			reqIns.setJobcd(jobLandOut.getJobcd());
			reqIns.setAddDate((commonMethedService.getTimeByZone(jobLandOut.getAddDate(), String.valueOf(Integer.valueOf(jobLand.getCompanyID())))).substring(0, 10));
			reqIns.setAddUserName(jobLandOut.getAddUserName());
			reqIns.setSaleName(jobLandOut.getSaleName());
			reqIns.setdName(jobLandOut.getdName());
			reqIns.setqName(jobLandOut.getqName());
			reqIns.setHdyName(jobLandOut.getHdyName());
			reqIns.setzUser(jobLandOut.getzUser());
			reqIns.setdUser(jobLandOut.getdUser());
			reqIns.setLocalMoney(jobLandOut.getLocalMoney());
			reqIns.setLocalMoneyEn(jobLandOut.getLocalMoneyEn());
			reqIns.setLocalMoneyHk(jobLandOut.getLocalMoneyHk());
			reqIns.setLocalMoneyJp(jobLandOut.getLocalMoneyJp());
			reqIns.setSaleForeignMoney(jobLandOut.getSaleForeignMoney());
			reqIns.setSaleForeignMoneyEn(jobLandOut.getSaleForeignMoneyEn());
			reqIns.setSaleForeignMoneyHk(jobLandOut.getSaleForeignMoneyHk());
			reqIns.setSaleForeignMoneyJp(jobLandOut.getSaleForeignMoneyJp());
			reqIns.setDalDay(jobLandOut.getDalDay());
			reqIns.setPlanDalday(jobLandOut.getPlanDalday());
			//原价合计  原价条数  仕入增值税
			List<Cost> cost  = commonMethedMapper.getSumcost(companycd, jobcd);
			String costNum = cost.get(0).getCostnum();
		    String costAmt  = cost.get(0).getSumamt();
		    String vatAmt = cost.get(0).getSumvat();
			//如果原价完了
			if(reqIns.getCostFinishFlg()==1) {
				//没有实际成本
				if("0".equals(costNum)) {
					costAmt = "0";
					vatAmt = "0";
				}
			}else {
				if("0".equals(costNum)) {
					costAmt = reqIns.getPlanCostMoney();
					vatAmt = reqIns.getPlanCostTax();
				}
			}	
//			String saleAmt = reqIns.getSaleMoney();
//			double profit = Double.parseDouble(saleAmt)-(Double.parseDouble(costAmt)+Double.parseDouble(vatAmt));
//			double profitRate = profit/Double.parseDouble(saleAmt)*100;
			reqIns.setProfit(String.valueOf(cal.getProfit()));
			if(cal.getProfitRate() != Double.POSITIVE_INFINITY)
			{
				reqIns.setProfitRate(commonMethedService.profitRateFormat(String.valueOf(cal.getProfitRate())));
			}else {
				reqIns.setProfitRate(String.valueOf(cal.getProfitRate()));
			}
			reqIns.setRate2(String.valueOf(cal.getTaxWH()));
			reqIns.setRate3(String.valueOf(cal.getTaxZZF()));
			reqIns.setCostMoney(costAmt);
			reqIns.setCostRate(vatAmt);
			//整合字符串
			String localMoney = jobLandOut.getLocalMoney();
			if(jobLand.getLangTyp().equals("jp")) {
				localMoney = jobLandOut.getLocalMoneyJp();
			}else if(jobLand.getLangTyp().equals("en")) {
				localMoney =  jobLandOut.getLocalMoneyEn();
			}else if(jobLand.getLangTyp().equals("hk")) {
				localMoney = jobLandOut.getLocalMoneyHk();
			}
			reqIns.setSaleMoneyString(commonMethedService.getNewNum(reqIns.getSaleMoney(),localPoint)+"  "+localMoney);
			reqIns.setSaleTaxString(commonMethedService.getNewNum(reqIns.getSaleRate(),localPoint)+"  "+localMoney);
			reqIns.setCostMoneyString(commonMethedService.getNewNum(reqIns.getCostMoney(),localPoint)+"  "+localMoney);
			reqIns.setCostTaxString(commonMethedService.getNewNum(reqIns.getCostRate(),localPoint)+"  "+localMoney);
			reqIns.setTax2String(commonMethedService.getNewNum(reqIns.getRate2(),localPoint)+"  "+localMoney);
			reqIns.setTax3String(commonMethedService.getNewNum(reqIns.getRate3(),localPoint)+"  "+localMoney);
			reqIns.setProfitString(commonMethedService.getNewNum(reqIns.getProfit(),localPoint)+"  "+localMoney);
			reqIns.setProfitRateString("["+reqIns.getProfitRate()+"%]");
			
			if(reqIns.getCureCode()==null||"".equals(reqIns.getCureCode())) {
				reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),localPoint)+"   "+localMoney);
				reqIns.setCureCodeString(null);
			}else {
				reqIns.setCureCode(String.valueOf(Double.parseDouble(reqIns.getCureCode())));
				reqIns.setSaleForeignAmtString(commonMethedService.getNewNum(reqIns.getSaleForeignAmt(),salePoint)+"   "+reqIns.getSaleMoneyUnit());
				reqIns.setCureCodeString(reqIns.getCureCode()+" ( 1"+localMoney+" : "+reqIns.getSaleChangeUnit()+reqIns.getSaleMoneyUnit()+" )");
			}
			String langTyp= jobLand.getLangTyp();
			Locale local = JasperOutPdfUtil.getLocalByLangT(langTyp);
		    ResourceBundle bundle = ResourceBundle.getBundle(JasperOutPdfUtil.BUNDLE_NAME , local);
			
				//预计原价
				if("1".equals(reqIns.getSaleIsHave())) {
					reqIns.setSaleIsHave(bundle.getString("haveTax"));
				}else {
					reqIns.setSaleIsHave(bundle.getString("notHaveTax"));
				}
			reqIns.setReqMoney(commonMethedService.getNewNum(reqIns.getReqMoney(),localPoint));
			List<ReqIns> req = new ArrayList<ReqIns>();
			req.add(reqIns);
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(req);
			String fileBaseStr = JasperOutPdfUtil.outPutPdfWithJavaBean(dataSource, jobLand.getFileName(), jobLand.getLangTyp());
			if(fileBaseStr.equals("error"))
			{
				return null;
			}
			return fileBaseStr;
		}
}

