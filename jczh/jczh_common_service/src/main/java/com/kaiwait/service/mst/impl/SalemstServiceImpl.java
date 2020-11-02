package com.kaiwait.service.mst.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.kaiwait.bean.mst.entity.Salemst;
import com.kaiwait.bean.mst.io.SalemstInput;
import com.kaiwait.bean.mst.vo.SalemstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.CommonmstMapper;
import com.kaiwait.mappers.mst.SalemstMapper;
import com.kaiwait.service.mst.CompanyService;
import com.kaiwait.service.mst.SalemstService;

/**
 * @ClassName: SalesListServiceImpl
 * @Description:  Sales模块service层实现类(这里用一句话描述这个类的作用)
 * @author mayouyi
 * @date 2018年5月14日 上午8:22:34
 * 
 */
@Service
public class SalemstServiceImpl implements SalemstService {

	@Resource
	private SalemstMapper salesmstMapper;
	@Resource
	private CommonmstMapper commonmstMapper;
	@Resource
	private CompanyService Companyservice;

	/* (非 Javadoc) 
	* <p>Title: selectAll</p> 
	* <p>Description:查询 </p> 
	* @return List<CommonmstVo>
	* @see com.kaiwait.service.mst.CommonmstService#selectAll() 
	*/
	public List<SalemstVo> select(String sale_cd,String sale_account_cd,String sale_name,String del_flg,String companyID) {
		List<SalemstVo> monmst = salesmstMapper.select(sale_cd,sale_account_cd,Companyservice.changSqlInput(sale_name),del_flg,companyID);
		
		if(monmst.size()<1) {
			monmst.add(salesmstMapper.searchTaxNameByLang(companyID));
		}
		return monmst;
	}
	
	
	
	@Override
	public List<SalemstVo> select(String sale_cd, String sale_account_cd, String sale_name, String del_flg,
			String companyID, String pd) {
		
		String divnms =pd;
		pd = divnms.replace("/", "-");
		pd=pd+"-01";
			/*String pds = pd.replace("\","-");
*/		
		
			List<SalemstVo> monmst = salesmstMapper.select(sale_cd,sale_account_cd,Companyservice.changSqlInput(sale_name),del_flg,companyID,pd);
		
		if(monmst.size()<1) {
			monmst.add(salesmstMapper.searchTaxNameByLang(companyID));
		}
		return monmst;
	}


   

	/* (非 Javadoc) 
	* <p>Title: insertNewTx</p> 
	* <p>Description:新增 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#insertNewTx(com.kaiwait.bean.mst.entity.Commonmst) 
	*/
	public int insertNewTx(Salemst monmst) {
		int num;
		num=salesmstMapper.SaleAccountVa(monmst.getSale_account_cd(),null,monmst.getCompany_cd());	
		if(num==1) {
			//num等于1时代表，数据库困中存在一条一样的数据。即Sale_account_cd重复。向前台返回更新0条数据
			num=3;
		}else {
			 num=1;
			 String sale_cd =salesmstMapper.selectMaxCd();
			 monmst.setSale_cd(sale_cd);
			 Date date = new Date();
			 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			 monmst.setAdd_date(dt.format(date));
			 monmst.setVar_add_date(dt.format(date));
			 monmst.setUpdate(dt.format(date));
			 monmst.setUpusercd(monmst.getAdd_user());
			 monmst.setAdddate(dt.format(date));
			 monmst.setAddusercd(monmst.getAdd_user());
			 //插入数据到增值税表中
			 salesmstMapper.insertVatRate(monmst);
			 //插入数据到税率表中
			 salesmstMapper.insertSalemst(monmst);
			 //插入数据到关联表中
			 salesmstMapper.insertSaleRatemst(monmst);
			
		}
		 return num;	
	}



	/* (非 Javadoc) 
	* <p>Title: updateOldSaleTx</p> 
	* <p>Description:更新 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#updateOldComTx(com.kaiwait.bean.mst.io.Commonmst0000003Input) 
	*/
	public int updateOldSaleTx(Salemst monmst) {
		int num;
		num=salesmstMapper.SaleAccountVa(monmst.getSale_account_cd(),monmst.getSale_cd(),monmst.getCompany_cd());	
		if(num==1) {
			//num等于1时代表，数据库困中存在一条除要更新的条目，还有一条一样的数据。即Sale_account_cd重复。向前台返回更新0条数据
			num=3;
			return num;
		}else {
			Salemst sale = 	salesmstMapper.selectSalerate(monmst.getSale_cd(),monmst.getCompanyID());
			if(monmst.getLock_flg()!=sale.getLock_flg()){
				 return 2;
			}
		}
			
		 num=1;
		 Salemst rateDate = salesmstMapper.selectRateDate(monmst.getSale_cd(),monmst.getCompanyID());//税率最新天数
		 Salemst vatrateDate = salesmstMapper.selectNewVatRate(monmst.getSale_cd(),monmst.getCompanyID());//增值税最新天数
		 Date date = new Date();
		 SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		 @SuppressWarnings("unused")
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		 monmst.setAdd_date(dt.format(date));
		 monmst.setVar_add_date(dt.format(date));
		 monmst.setUpdate(dt.format(date));
		 monmst.setUpusercd(monmst.getAdd_user());
		 //前台传入的时间，和数据库查询 的时间不一致。插入
		if(!rateDate.getStart_date().equals(monmst.getStart_date())||!rateDate.getEnd_date().equals(monmst.getEnd_date())) {
			salesmstMapper.insertSaleRatemst(monmst);
			/*String Start_date= monmst.getStart_date();
		        Date d = dateformat.parse(Start_date);
		        Calendar calendar = Calendar.getInstance();
		        calendar.setTime(d);
		        calendar.add(calendar.DATE, -1);
		     String  end_date = dateformat.format(calendar.getTime());*/
			String end_date =DateUtil.nextDayDate(monmst.getStart_date());
			salesmstMapper.updateOldSaleRatemst(end_date,monmst.getSale_cd(),monmst.getCompanyID(),rateDate.getStart_date());
		}
		 //前台传入的时间，和数据库查询 的时间不一致。插入
		if(!vatrateDate.getVat_start_date().equals(monmst.getVat_start_date())||!vatrateDate.getVat_end_date().equals(monmst.getVat_end_date())) {
			 salesmstMapper.insertVatRate(monmst); 
		     String  vat_end_date = DateUtil.nextDayDate(monmst.getVat_start_date());
		     salesmstMapper.updateOldVatRatemst( vat_end_date,monmst.getSale_cd(),monmst.getCompanyID(),vatrateDate.getVat_start_date());
		}
		 salesmstMapper.updateOldSalemst(monmst);
		 return num;
	}



	/* (非 Javadoc)   s  
	* <p>Title: deleteComTx</p> 
	* <p>Description:删除 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#deleteComTx(com.kaiwait.bean.mst.io.Commonmst0000001Input) 
	*/
	public void deleteSaleTx(Salemst monmst) {
		salesmstMapper.deleteSale(monmst);
		
	}



	/* (非 Javadoc) 
	* <p>Title: deleteComTx</p> 
	* <p>Description:查询旧有增值税 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#deleteComTx(com.kaiwait.bean.mst.io.Commonmst0000001Input) 
	*/
	public List<SalemstVo> selectVatRateByCD(String sale_cd,String companyID ) {
		// TODO Auto-generated method stub
		return salesmstMapper.selectVatRateByCD(sale_cd,companyID);
	}
	
	
	
	public List<SalemstVo> selectVatRateByCD(String sale_cd,String companyID,String pd) {
		

		@SuppressWarnings("unused")
		List<SalemstVo> salemsatList = new ArrayList<SalemstVo>();
		List<SalemstVo> list = new ArrayList<SalemstVo>();
		salemsatList = salesmstMapper.selectVatRateByCD(sale_cd,companyID);
		String divnms =pd;
		pd = divnms.replace("/", "-");
		pd=pd+"-01";
		
		list = salesmstMapper.selectVatRateByCD(sale_cd,companyID,pd);
		if(list.size()!=0) {
			@SuppressWarnings("unused")
			String rate2 = list.get(0).getRate2();
			@SuppressWarnings("unused")
			String rate3 = list.get(0).getRate3();	
			
		}
		
		
		/*else {
			List<SalemstVo> listLt = new ArrayList<SalemstVo>();
			listLt = salesmstMapper.selectVatRateByCDlt(sale_cd,companyID,pd);
			list.addAll(listLt);
			listLt =salesmstMapper.selectVatRateByCDgt(sale_cd,companyID,pd);
			list.addAll(listLt);
			list = salesmstMapper.selectVatRateByCDgt(sale_cd,companyID,pd);
		}*/
		
		//return salesmstMapper.selectVatRateByCD(sale_cd,companyID);
		return list;
	}

	/* (非 Javadoc) 
	* <p>Title: deleteComTx</p> 
	* <p>Description:查询旧有税率 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#deleteComTx(com.kaiwait.bean.mst.io.Commonmst0000001Input) 
	*/
	public List<SalemstVo> selectRateByCD(String sale_cd,String companyID ) {
		// TODO Auto-generated method stub
		return salesmstMapper.selectRateByCD(sale_cd,companyID);
	}
	
	public List<SalemstVo> selectRateByCD(String sale_cd,String companyID,String pd) {
		List<SalemstVo> list = new ArrayList<SalemstVo>();
		String divnms =pd;
		pd = divnms.replace("/", "-");
		pd=pd+"-01";
		list =	 salesmstMapper.selectRateByCD(sale_cd,companyID,pd);
		
		if(list.size()!=0) {
			List<SalemstVo> listLt = new ArrayList<SalemstVo>();
			listLt = salesmstMapper.selectRateByCDlt(sale_cd,companyID,pd);
			list.addAll(listLt);
			listLt =salesmstMapper.selectRateByCDgt(sale_cd,companyID,pd);
			list.addAll(listLt);
		}
		else {
			list = salesmstMapper.selectRateByCDgt(sale_cd,companyID,pd);
		}
		
		//return salesmstMapper.selectVatRateByCD(sale_cd,companyID);
		return list;
		
	}

	/* (非 Javadoc) 
	* <p>Title: initSalescategory</p> 
	* <p>Description:初始化下拉选框与生成cd </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#deleteComTx(com.kaiwait.bean.mst.io.Commonmst0000001Input) 
	*/
	public SalemstVo initSalescategory(SalemstInput Salemst){
		SalemstVo salemstVo = new SalemstVo();
		salemstVo = salesmstMapper.searchTaxNameByLang(Salemst.getCompanyID());
		//String sale_cd =salesmstMapper.selectMaxCd();
		//salemstVo.setSale_cd(sale_cd);
		salemstVo.setCommontypeList(commonmstMapper.selectMstNameByCD("7001",Integer.valueOf(Salemst.getCompanyID())));
		return salemstVo;
	}

/**
 * 
 * <p>Title: salemstUpdInit</p>   
 * <p>Description: </p>   
 * @param sale_cd
 * @param companyID
 * @return   
 * @see com.kaiwait.service.mst.SalemstService#salemstUpdInit(java.lang.String, java.lang.String)
 * @author "马有翼"
 * @date:   2018年9月19日 下午12:41:55
 */
 
	public Object salemstUpdInit(String sale_cd,String companyID){
		//查询税率相关消息
		int beUseInJobNumber=salesmstMapper.selJobtrnSaleNum(sale_cd);
		Salemst sale = 	salesmstMapper.selectSalerate(sale_cd,companyID);
		if(sale==null) {
			return 3;
		}
		sale = 	salesmstMapper.selectSalerate(sale_cd,companyID);
		sale.setBeUseInJobNumber(beUseInJobNumber);
		//查询增值税
		Salemst vatRate = salesmstMapper.selectNewVatRate(sale_cd,companyID);
		sale.setVat_rate(vatRate.getVat_rate());
		sale.setVat_start_date(vatRate.getVat_start_date());
		sale.setVat_end_date(vatRate.getVat_end_date());
		sale.setCommontypeList(commonmstMapper.selectMstNameByCD("7001",Integer.valueOf(companyID)));
		return sale;
	}


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public Object salemstUpdInit(String sale_cd, String companyID, String pd) {
		//查询税率相关消息
				int beUseInJobNumber=salesmstMapper.selJobtrnSaleNum(sale_cd);
				//Salemst sale = 	salesmstMapper.selectSalerate(sale_cd,companyID);
				String divnms =pd;
				pd = divnms.replace("/", "-");
				pd=pd+"-01";
				
				Salemst sale = 	salesmstMapper.selectSalerate(sale_cd,companyID);
				if(sale==null) {
					return 3;
				}
				Salemst salemst= 	salesmstMapper.selectSaleratedy(sale_cd,companyID,pd);
				if(salemst==null || salemst.equals("")) {
					salemst = salesmstMapper.selectSalerates(sale_cd, companyID, pd);
				}
				sale.setRate2(salemst.getRate2());
				sale.setRate3(salemst.getRate3());
				sale.setBeUseInJobNumber(beUseInJobNumber);
				//查询增值税
				
				Salemst vatRate = salesmstMapper.selectNewVatRate(sale_cd,companyID);
				Salemst vat = salesmstMapper.selectNewVatRatedy(sale_cd,companyID,pd);
			
				if(vat==null || sale.equals("")) {
					vat = salesmstMapper.selectNewVatRates(sale_cd, companyID, pd);
				}
				sale.setVat_rate(vat.getVat_rate());
				sale.setVat_start_date(vatRate.getVat_start_date());
				sale.setVat_end_date(vatRate.getVat_end_date());
				sale.setCommontypeList(commonmstMapper.selectMstNameByCD("7001",Integer.valueOf(companyID)));
				
				
				
				
			/*	
				String divnms =pd;
				pd = divnms.replace("/", "-");
				pd=pd+"-01";
				Salemst sale = 	salesmstMapper.selectSalerate(sale_cd,companyID,pd);
				if(sale==null || sale.equals("")) {
					sale = salesmstMapper.selectSalerates(sale_cd, companyID, pd);
				}
				sale.setBeUseInJobNumber(beUseInJobNumber);
				//查询增值税
			Salemst vatRate = salesmstMapper.selectNewVatRate(sale_cd,companyID,pd);
				if(vatRate==null || sale.equals("")) {
					vatRate = salesmstMapper.selectNewVatRates(sale_cd, companyID, pd);
				}
				sale.setVat_rate(vatRate.getVat_rate());
				sale.setVat_start_date(vatRate.getVat_start_date());
				sale.setVat_end_date(vatRate.getVat_end_date());
				sale.setCommontypeList(commonmstMapper.selectMstNameByCD("7001",Integer.valueOf(companyID)));
				
				*/return sale;
	}








}
