package com.kaiwait.service.jczh.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.jczh.entity.Company;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.InvoiceInfo;
import com.kaiwait.bean.jczh.entity.Job;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.RecTrn;
import com.kaiwait.bean.jczh.entity.ReqInfo;
import com.kaiwait.bean.jczh.entity.SaleAdmin;
import com.kaiwait.bean.jczh.entity.SaleType;
import com.kaiwait.bean.jczh.entity.Skip;
import com.kaiwait.bean.jczh.io.JobZhInput;
import com.kaiwait.bean.jczh.io.SaleZhInput;
import com.kaiwait.bean.jczh.vo.JobLandVo;
import com.kaiwait.bean.jczh.vo.SalemstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.jczh.CommonMethedMapper;
import com.kaiwait.mappers.jczh.JobLandMapper;
import com.kaiwait.mappers.jczh.JobMapper;
import com.kaiwait.mappers.jczh.MonthBalanceMapper;
import com.kaiwait.mappers.jczh.RecTrnMapper;
import com.kaiwait.mappers.jczh.SaleTypeMapper;
import com.kaiwait.service.jczh.CommonMethedService;
import com.kaiwait.service.jczh.SaleTypeService;
@Service
public class SaleTypeServiceImp implements SaleTypeService{
	@Resource
	private SaleTypeMapper saleTypeMapper;
	@Resource
	private JobLandMapper jobLandMapper;
	@Resource
	private CommonMethedMapper commonMethedMapper;
	@Resource
	private CommonMethedService commonMethedService;
	@Resource
	private MonthBalanceMapper monthBalanceMapper;
	@Resource
	private JobMapper jobMapper;
	@Resource
	private RecTrnMapper recTrnMapper;
	SimpleDateFormat dfomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	SimpleDateFormat dfomat2 = new SimpleDateFormat("yyyy-MM-dd");
	
	//插入卖上信息
	public int insertSaleTypeTx(SaleType saleType,int company_cd,String usercd,List<JobLableTrn> jltrn) {
		// TODO Auto-generated method stub
		String jobcd = saleType.getJob_cd();
		JobLand jobp = jobLandMapper.selectJobByCd(jobcd, company_cd);
		Date date = new Date();
		//?????????job不存在了  不能卖上登录  如果卖上登录时  job被删除了 制御job刪除
		if(jobp==null) {
			return 540;
		}
		int jobDBLockFLg = jobp.getLockFlg();
		int jobPreLockFLg = saleType.getJobLockFlg()+1;
		//制御job更新
		if(!(jobPreLockFLg>jobDBLockFLg)) {
			return 540;
		}	
		//制御賣上登錄
		SaleType saletp = saleTypeMapper.selectSaleTypeByCd(jobcd, company_cd);
		if(saletp!=null) {
			return 540;
		}
		//权限验证
		boolean isHavePower = commonMethedService.validatePower(jobcd, usercd, company_cd, jobp.getCldiv_cd(), "saleUpdate");
		if(!isHavePower) {
			return 550;
		}
		//返回计上日错误信息 返回999 
		String accountingMonth = commonMethedService.getSystemDate(company_cd);
		String Dlvday =saleType.getDlvday().substring(0,7);;
		accountingMonth =accountingMonth.substring(0,7);
		if(Dlvday.compareTo(accountingMonth)<0) {
			return 999;
		}


		saleType.setSaleupddate(dfomat.format(date));
		saleType.setSaleadddate(dfomat.format(date));
		saleType.setSaleaddusercd(usercd);
		saleType.setSaleupdusercd(usercd);
		saleType.setCompany_cd(company_cd);
		jobLandMapper.deleteByJobCd(saleType.getJob_cd(),null,saleType.getCompany_cd(), "joblabeltrn");
		if(jltrn!=null) {
			jobLandMapper.insertJobLable(jltrn);
		}
		/*卖上承认跳过的情况下，卖上登陆后自动承认。 by wangyan 20190730
		Skip skip = commonMethedMapper.selectSkipByCd(company_cd);
		if(skip.getConfirm()==1) {
			saleType.setSale_admit_date(dfomat.format(date));
			saleType.setSale_admit_usercd(usercd);
			saleType.setSalecanceldate(null);
			saleType.setSalecancelusercd(null);
		}
		*/
		saleType.setDel_flg(0);
		commonMethedMapper.updateLockFlg(jobcd, company_cd, "jobtrn", jobPreLockFLg);
		return saleTypeMapper.insertSaleType(saleType);
	}
	
	//卖上加载
	public SaleType pageLoad(String jobcd, String sale_no,int company_cd,String usercd) {
		// TODO Auto-generated method stub
		SaleType saleType  = new SaleType();
		JobLandVo joblandVo = new JobLandVo();
		JobLand jobland = jobLandMapper.selectJobByCd(jobcd,company_cd);
		if(jobland!=null) {
			jobland.setAdddate(commonMethedService.getTimeByZone(jobland.getAdddate(), String.valueOf(company_cd)));
			jobland.setUpddate(commonMethedService.getTimeByZone(jobland.getUpddate(), String.valueOf(company_cd)));
		}else {
			return null;
		}
		
//		String addname = jobLandMapper.getNameByUserCd(jobland.getAddusercd(),company_cd);
//		String updname = jobLandMapper.getNameByUserCd(jobland.getUpdusercd(),company_cd);
//		jobland.setAdduser(addname);
//		jobland.setUpduser(updname);
//		jobland.setSkip(commonMethedMapper.selectSkipByCd(company_cd));
		 List<Cost> sellplansalelist=jobMapper.searchOrderNosBysellplan(jobcd, company_cd+"");
		 if(sellplansalelist.get(0)!=null) {
			 jobland.setSale_ishave(Integer.parseInt(sellplansalelist.get(0).getPlancostishave()));
		 }else {
			 jobland.setSale_ishave(0);
		 }
		jobland.setJulable(jobLandMapper.selectJobUser(jobcd,company_cd));
		jobland.setJlTrn(jobLandMapper.selectJobLable(jobcd,company_cd));
		joblandVo.setJobland(jobland);
		joblandVo.setList_comst(jobLandMapper.selectSaleCode(company_cd,null,"0050"));
		joblandVo.setList_tax(jobLandMapper.selectSaleCode(company_cd,null,"0052"));
		joblandVo.setList_foreign_tax(jobLandMapper.selectSaleCode(company_cd,null,"0051"));
		joblandVo.setList_lable(jobLandMapper.selectLableList(usercd,0,company_cd,1));
		joblandVo.setList_sale(jobLandMapper.selectSaleList(jobland.getSale_typ(), "",company_cd));
		joblandVo.setAccountyear(jobLandMapper.selectSaleCode(company_cd,null,"0001"));
		joblandVo.setList_cost(commonMethedMapper.getSumcost(company_cd, jobcd));
		//是否存在 实际卖上
		int num = saleTypeMapper.isHaveRealSaleByJobCd(jobcd,company_cd);
		if(num>0) {
			saleType= saleTypeMapper.selectSaleTypeByCd(jobcd, company_cd);
			if(saleType!=null) {
				saleType.setSaleadddate(commonMethedService.getTimeByZone(saleType.getSaleadddate(), String.valueOf(company_cd)));
				saleType.setSaleupddate(commonMethedService.getTimeByZone(saleType.getSaleupddate(), String.valueOf(company_cd)));
			}
			
//			String saleUpName = jobLandMapper.getNameByUserCd(saleType.getSaleupdusercd(),company_cd);
//			String saleAddName = jobLandMapper.getNameByUserCd(saleType.getSaleaddusercd(),company_cd);
//			saleType.setUpname(saleUpName);
//			saleType.setSaleadduserName(saleAddName);
		}
		
		saleType.setJobLandVo(joblandVo);
		ReqInfo req = saleTypeMapper.selectReqByCd(jobcd, company_cd,null);
		if(req!=null) {
			req.setAdddate(commonMethedService.getTimeByZone(req.getAdddate(), String.valueOf(company_cd)));
			req.setUpdate(commonMethedService.getTimeByZone(req.getUpdate(), String.valueOf(company_cd)));
		}
		saleType.setReq(req);
		InvoiceInfo inv = saleTypeMapper.selectInvoiceInfoByCd(jobcd, company_cd,null);
		if(inv!=null) {
			inv.setAdddate(commonMethedService.getTimeByZone(inv.getAdddate(), String.valueOf(company_cd)));
			inv.setUpdate(commonMethedService.getTimeByZone(inv.getUpdate(), String.valueOf(company_cd)));
		}
		saleType.setInv(inv);
		saleType.setRealcost(saleTypeMapper.realSale(jobcd, company_cd));
		saleType.setCompany(commonMethedMapper.getAutoDate(company_cd));
	    saleType.setJobLockFlg(jobland.getLockFlg());
	    RecTrn rec = recTrnMapper.getRec(jobcd, company_cd);
	    int recLockFlg = 0;
	    if(rec!=null) {
	    	recLockFlg = rec.getRecLockFlg();
	    }
	    saleType.setRecLockFlg(recLockFlg);
		
		return saleType;
	}
	//更新卖上信息
	public int updateSaleTypeTx(SaleType saleType,int company_cd,String usercd,List<JobLableTrn> jltrn) {
		// TODO Auto-generated method stub
		
		String jobcd = saleType.getJob_cd();
		JobLand jobp = jobLandMapper.selectJobByCd(jobcd, company_cd);
		//权限验证
		boolean isHavePower = commonMethedService.validatePower(jobcd, usercd, company_cd, jobp.getCldiv_cd(), "saleUpdate");
		if(!isHavePower) {
			return 550;
		}
		SaleType saletp = saleTypeMapper.selectSaleTypeByCd(jobcd, company_cd);
		if(saletp==null) {
			return 540;
		}
		SaleAdmin saleadmin =saleTypeMapper.selectSaleAdmin(jobcd, company_cd);
		String adminDate =saleadmin.getSaleadmitdate();
		//String adminDate = saletp.getSale_admit_date();
		if(adminDate!=null&&!("".equals(adminDate))) {
				return 540;
		}
		int saleDBLockFLg = saletp.getSaleLockFlg();
		int salePreLockFlg = saleType.getSaleLockFlg()+1;
		//制御賣上更新  取消
		if(!(salePreLockFlg>saleDBLockFLg)) {
			return 540;
		}
		RecTrn rec = recTrnMapper.getRec(jobcd, company_cd);
		if(rec!=null) {
			int recDBLockFlg = rec.getRecLockFlg();
			int recPreLockFlg = saleType.getRecLockFlg()+1;
			if(!(recPreLockFlg>recDBLockFlg)) {
				return 540;
			}
			
		}
		//返回计上日错误信息 返回999 
		String accountingMonth = commonMethedService.getSystemDate(company_cd);
		String Dlvday =saleType.getDlvday().substring(0,7);;
		accountingMonth =accountingMonth.substring(0,7);
		if(Dlvday.compareTo(accountingMonth)<0) {
			return 999;
		}


		SimpleDateFormat dfomat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		saleType.setCompany_cd(company_cd);
		saleType.setSaleupddate(dfomat.format(date));
		saleType.setSaleupdusercd(usercd);
		jobLandMapper.deleteByJobCd(saleType.getJob_cd(),null, saleType.getCompany_cd(), "joblabeltrn");
		if(jltrn!=null) {
			jobLandMapper.insertJobLable(jltrn);
		}
		Skip skip = commonMethedMapper.selectSkipByCd(company_cd);
		if(skip.getConfirm()==1) {
			//2019-11-03 卖上承认跳过时 不插入承认信息 
			//saleType.setSale_admit_date(dfomat.format(date));
			//saleType.setSale_admit_usercd(usercd);
			saleType.setSale_admit_date(null);
			saleType.setSale_admit_usercd(null);
			saleType.setSalecanceldate(null);
			saleType.setSalecancelusercd(null);
		}
		saleType.setSaleLockFlg(salePreLockFlg);
		return saleTypeMapper.updateSaleType(saleType);
	}
	//查询卖上承认页面信息
	public SaleAdmin selectSaleAdmin(SaleZhInput saleInput) {
		// TODO Auto-generated method stub
		String jobcd = saleInput.getJob_cd();
		int company_cd = Integer.valueOf(saleInput.getCompanyID());
		SaleType saleType = saleTypeMapper.selectSaleTypeByCd(jobcd, company_cd);
		if(saleType==null) {
			return null;
		}
		JobLand job = jobLandMapper.selectJobByCd(jobcd, company_cd);
		//是否终了
		
		//取jobratehistorytrn中的 税金2 、3
		List<JobList> joblist = jobLandMapper.selectjobhistrn(jobcd, company_cd);
		JobList jobs = new JobList();
		if(joblist.get(0)!=null) {
			
			jobs.setAccountflg(job.getAccountFlg()+"");
			jobs.setHistoryrate2(joblist.get(0).getHistoryrate2());
			jobs.setHistoryrate3(joblist.get(0).getHistoryrate3());

		}
		
		if(job==null) {
			return null;
		}
		@SuppressWarnings("unused")
		String d = job.getPlan_dalday();
		@SuppressWarnings("unused")
		String id = job.getSale_typ();
		Company company = commonMethedMapper.getAutoDate(company_cd);
		String salecd = job.getSale_typ();
		String usercd = saleInput.getUserID();
		SaleAdmin saleAdmin = saleTypeMapper.selectSaleAdmin(jobcd, company_cd);
		//如果卖上承认者与承认日是空的 说明没有承认   saleAdmin的承认flg设置为0 否则为1
		if(saleAdmin.getSaleadmitdate()==null&&saleAdmin.getSaleadmitusercd()==null) {
			saleAdmin.setIsAdmin(0);
		}else {
			saleAdmin.setIsAdmin(1);
		}
		saleAdmin.setJobList(jobs);
		//原价信息
		saleAdmin.setCost(commonMethedMapper.getSumcost(company_cd, jobcd));
		//卖上登入时存入的税率
		saleAdmin.getCost().get(0).setVatrate(Double.parseDouble(job.getVat_rate()));
		

		
		
		JobZhInput inputParam = new JobZhInput();
		inputParam.setDalday(job.getPlan_dalday());
		inputParam.setSalecd(job.getSale_typ());
		inputParam.setCompanyID(company_cd+"");
		//需要对比的税率
		SalemstVo list = commonMethedService.getRateByDateAndSaleID(inputParam);
		saleAdmin.getCost().get(0).setCost_rate(list.getVar_rate());
		//卖上更新者
		saleAdmin.setSaleuper(saleType.getUpname());
		saleAdmin.setSalecolor(saleType.getSaleupcolor());
		//job更新
		saleAdmin.setUper(job.getUpduser());
		saleAdmin.setJobcolor(job.getUpdcolor());
		//2019-11-04  xy   原价、卖上的税入税拔 
		saleAdmin.setPlanishave(job.getPlan_cost_ishave());
		//实际下拉
		if("".equals(saleAdmin.getSale_foreign_code())||saleAdmin.getSale_foreign_code()==null) {
			saleAdmin.setSale_foreign(jobLandMapper.selectCurrencyCode(company_cd, company.getCurrencyCode(),"0050"));
		}else {
			saleAdmin.setSale_foreign(jobLandMapper.selectSaleCode(company_cd, saleAdmin.getSale_foreign_code(),"0050"));
		}
		if("".equals(saleAdmin.getPlan_sale_foreign_type())||saleAdmin.getPlan_sale_foreign_type()==null) {
			saleAdmin.setPlan_sale_foreign(jobLandMapper.selectCurrencyCode(company_cd, company.getCurrencyCode(),"0050"));
		}else {
			//预计下拉
			saleAdmin.setPlan_sale_foreign(jobLandMapper.selectSaleCode(company_cd, job.getPlansale_foreign_code(),"0050"));
		}
		//登录更新时间转换
		if(saleAdmin!=null) {
			saleAdmin.setUpddate(commonMethedService.getTimeByZone(saleAdmin.getUpddate(), String.valueOf(company_cd)));
			saleAdmin.setSaleupddate(commonMethedService.getTimeByZone(saleAdmin.getSaleupddate(), String.valueOf(company_cd)));
		}
		//卖上税率
		saleAdmin.setSalemstvo(commonMethedMapper.getRateByDateAndSaleID(saleType.getDlvday(),salecd, company_cd));
		saleAdmin.setList_tax(jobLandMapper.selectSaleCode(company_cd,null,"0052"));
		saleAdmin.setList_foreign_tax(jobLandMapper.selectSaleCode(company_cd,null,"0051"));
		saleAdmin.setJlTrn(jobLandMapper.selectJobLable(jobcd,company_cd));
		saleAdmin.setList_lable(jobLandMapper.selectLableList(usercd,0,company_cd,1));
		saleAdmin.setOutFlg_list(jobLandMapper.selectSaleCode(company_cd,"003","0070"));
		
		saleAdmin.setSaleLockFlg(saleType.getSaleLockFlg());
		return saleAdmin;
	}
	//更新卖上状态
	public int updateSaleStatusTx(String jobcd, int company_cd, String upuser_cd,String sale_admit_remark,int ad_flg,List<JobLableTrn> jltrn,int lockFlg,String usercd) {
		// TODO Auto-generated method stub
		   JobLand jobp = jobLandMapper.selectJobByCd(jobcd,company_cd);
		   @SuppressWarnings("unused")
		Job job = jobLandMapper.selectRateByJobCd(jobcd, company_cd);
		   
		//权限验证
			boolean isHavePower = commonMethedService.validatePower(jobcd, upuser_cd, company_cd, jobp.getCldiv_cd(), "saleUpdate");
			if(!isHavePower) {
				return 550;
			}
		    SaleType saleType = saleTypeMapper.selectSaleTypeByCd(jobcd, company_cd);
		    if(saleType==null) {
		    	return 540;
		    }
		    int saleDBLockFlg = saleType.getSaleLockFlg();
		    int salePreLockFlg = lockFlg+1;
		    String adminDate =saleType.getSale_admit_date();
		    int num = 0;
		    if(!(salePreLockFlg>saleDBLockFlg)) {
				return 540;
			}
			if(ad_flg==1) {
				
				//承認
				if(adminDate==null||"".equals(adminDate)) {
					@SuppressWarnings("unused")
					String nowDate=DateUtil.getDateForNow(DateUtil.dateTimeFormat);
					//monthBalanceMapper.addJobSellRateByHistory(company_cd, jobcd, job.getRate2(), job.getRate3(),nowDate,usercd);
					num= saleTypeMapper.updateSaleStatus(jobcd, company_cd, upuser_cd,DateUtil.getNowDate(),sale_admit_remark,salePreLockFlg);
				}else {
					num = 540;
				}
				
				
				
			}
			//承認取消
			if(ad_flg==0) {
				if(adminDate!=null||!("".equals(adminDate))){
					jobMapper.deleteratehistory(jobcd, String.valueOf(company_cd));
					num = saleTypeMapper.updateSaleStatus(jobcd, company_cd, null,null,sale_admit_remark,salePreLockFlg);
				}else {
					num = 540;
				}
				
			}
			jobLandMapper.deleteByJobCd(jobcd,null,company_cd, "joblabeltrn");
			if(jltrn!=null) {
				jobLandMapper.insertJobLable(jltrn);
			}
		return num;
	}
	//删除卖上
	public int delSaleTx(String jobcd, int companycd,String usercd,int lockFlg,int recLockFlg) {
		// TODO Auto-generated method stub
		SaleType saletp = saleTypeMapper.selectSaleTypeByCd(jobcd, companycd);
		if(saletp==null) {
			return 540;
		}
		RecTrn rec = recTrnMapper.getRec(jobcd, companycd);
		if(rec!=null) {
			int recDBLockFlg = rec.getRecLockFlg();
			int recPreLockFlg = recLockFlg+1;
			if(!(recPreLockFlg>recDBLockFlg)) {
				return 540;
			}
			
		}
		String adminDate = saletp.getSale_admit_date();
		if(adminDate!=null&&!("".equals(adminDate))) {
				return 540;
		}
		 JobLand jobp = jobLandMapper.selectJobByCd(jobcd,companycd);
		int saleDBLockFlg = saletp.getSaleLockFlg();
		int salePreLockFlg = lockFlg+1;
		if(!(salePreLockFlg>saleDBLockFlg)) {
			return 540;
		}
			//权限验证
		boolean isHavePower = commonMethedService.validatePower(jobcd, usercd, companycd, jobp.getCldiv_cd(), "saleUpdate");
		if(!isHavePower) {
			return 550;
		}
		saleTypeMapper.deleteTimes(jobcd, companycd, "reqtrn");
		saleTypeMapper.deleteTimes(jobcd, companycd, "invoicetrn");
		int num =saleTypeMapper.deleteTimes(jobcd, companycd, "sellordertrn");
		return num;
	}

	@Override
	public void upReqTx(String jobcd, int companycd,String usercd) {
		ReqInfo req = saleTypeMapper.selReq(jobcd, companycd);
		String amt = jobLandMapper.getReqMoney(jobcd, companycd);
		Date date = new Date();
		if(req==null) {
			req = new ReqInfo();
			req.setJobcd(jobcd);
			req.setReqcd(commonMethedService.getMaxKeyCode("R", companycd));
			req.setReqamt(amt);
			req.setReqdate(dfomat2.format(date));
			req.setAdddate(dfomat.format(date));
			req.setUpdate(dfomat.format(date));
			req.setAddusercd(usercd);
			req.setUpusercd(usercd);
			req.setDelflg(0);
			req.setReqtimes(1);
			req.setCompanycd(companycd);
			saleTypeMapper.insertToReq(req);
		}else {
			req.setJobcd(jobcd);
			req.setDelflg(0);
			req.setReqtimes(req.getReqtimes()+1);
			req.setCompanycd(companycd);
			req.setUpdate(dfomat.format(date));
			req.setUpusercd(usercd);
			saleTypeMapper.updateToReq(req);
		}	
		
	}

	@Override
	public void upInvTx(String jobcd, int companycd,String usercd) {
		InvoiceInfo inv = saleTypeMapper.selInv(jobcd, companycd);
		String amt = jobLandMapper.getReqMoney(jobcd, companycd);
		Date date = new Date();
		if(inv==null) {
			inv = new InvoiceInfo();
			inv.setJobcd(jobcd);
			inv.setInvoicdno(commonMethedService.getMaxKeyCode("I", companycd));
			inv.setInvoiceamt(amt);
			inv.setInvoicedate(dfomat2.format(date));
			inv.setInvoiceusercd(usercd);
			inv.setInvoicetimes(1);
			inv.setAdddate(dfomat.format(date));
			inv.setUpdate(dfomat.format(date));
			inv.setUpusercd(usercd);
			inv.setAddusercd(usercd);
			inv.setDelflg(0);
			inv.setCompanycd(companycd);
			saleTypeMapper.insertToInvoic(inv);
		}else {
			inv.setJobcd(jobcd);
			inv.setDelflg(0);
			inv.setCompanycd(companycd);
			inv.setInvoicetimes(inv.getInvoicetimes()+1);
			inv.setUpdate(dfomat.format(date));
			inv.setUpusercd(usercd);
			saleTypeMapper.updateToInvoic(inv);
		}
		
	}
	
	
	

}
