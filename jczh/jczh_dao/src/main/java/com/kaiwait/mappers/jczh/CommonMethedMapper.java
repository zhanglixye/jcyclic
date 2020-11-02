package com.kaiwait.mappers.jczh;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Calculate;
import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.entity.Company;
import com.kaiwait.bean.jczh.entity.Cost;
import com.kaiwait.bean.jczh.entity.JobList;
import com.kaiwait.bean.jczh.entity.Role;
import com.kaiwait.bean.jczh.entity.Skip;
import com.kaiwait.bean.jczh.entity.TitleMsg;
import com.kaiwait.bean.jczh.vo.SalemstVo;
import com.kaiwait.common.dao.BaseMapper;

public interface CommonMethedMapper extends BaseMapper{
   String getMaxJobCode(@Param("companyID")int companyID,@Param("isYearFlg")int isYearFlg,@Param("sysDate")String sysDate);
   String getMaxLableCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   int  selectNumberRules(@Param("company_cd")int company_cd);
   
   String getCostMaxCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   String getDeboursMaxCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   String getRecMaxCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   String getReqMaxCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   String getOncostMaxCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   String getPayMaxInPutNo(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   List<Cost>  getSumcost(@Param("companyID")int companyID,@Param("job_cd")String job_cd);
   List<Cost>  selectCLDIV(@Param("companyID")int companyID,@Param("job_cd")String job_cd);
   List<Columns>  selColumns(@Param("companyID") int companyID,@Param("level") int level,@Param("usercd") int usercd,@Param("ison") int ison,@Param("colname")String colname);
   String getSystemDate(@Param("companyID")int companyID);
   Skip selectSkipByCd(@Param("companyID")int companyID);
   List<Role> searchNodeListByUser(@Param("userID")String userID,@Param("companyID")String companyID);
   String getSellMaxCode(@Param("companyID")int companyID,@Param("sysDate")String sysDate);
   String getCostTaxRate(@Param("num")String num,@Param("companycd")int companycd);
   String getPayeetaxRate(@Param("num")String num,@Param("companyID")String companyID);
   Company getAutoDate(@Param("companycd")int companycd);
   JobList seltaxName(@Param("itmname") String itmname,@Param("mstcd") String mstcd,@Param("itemcd") String itemcd,@Param("company_cd") int company_cd);
   //更新title
  // int updateTitleMsg(@Param("listTitleMsg")List<TitleMsg> listTitleMsg);
   int updateTitleMsg(TitleMsg titleMsg);
   String getTimeZoneByCompany(String companyID);
   int getStatusByUserSettingTab(@Param("tabName")String tabName,@Param("cluonmName")String cluonmName,@Param("companyID")String companyID,@Param("jobNo")String jobNo,@Param("costNo")String costNo,@Param("orderTyp")String orderTyp);
   SalemstVo getRateByDateAndSaleID(@Param("dalday")String dalday,@Param("salecd")String salecd,@Param("companycd")int companycd);
   List<String> selJobuser(@Param("level_flg")String level_flg,@Param("job_cd")String job_cd,@Param("company_cd")String company_cd); 
   List<String> selUsercldiv(@Param("user_cd")String user_cd,@Param("company_cd")String company_cd);
   String selJobcldiv(@Param("job_cd")String job_cd,@Param("company_cd")String company_cd);
   Calculate getCalculateTaxInfo(@Param("jobNo")String jobNo,@Param("companyID")int companyID);
   int getPointNumberByCompany(@Param("companyID")int companyID);
   String searchStartTimeBySummaryMonth(@Param("companyID")int companyID,@Param("summaryMonth")String summaryMonth);
   String searchEndTimeBySummaryMonth(@Param("companyID")int companyID,@Param("summaryMonth")String summaryMonth);
   String selPdfflg(@Param("mstcd")String mstcd,@Param("itemcd")String itemcd, @Param("company_cd")int company_cd);
   Calculate getBeforMonthPayAmtAndCostVat(@Param("companyID")int companyID,@Param("jobNo")String jobNo,@Param("beforDate")String beforDate,@Param("outMonth")String outMonth);
   Calculate getBeforMonthAllPayAmtAndAllCostVat(@Param("companyID")int companyID,@Param("jobNo")String jobNo,@Param("beforDate")String beforDate,@Param("outMonth")String outMonth);
   Integer getLockFlg(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("dbname")String dbname);
   int updateLockFlg(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("dbname")String dbname,@Param("lockFlg")int lockFlg);
   List<Cost> getCostList(@Param("jobcd")String jobcd,@Param("checkMonth")String checkMonth,@Param("companyID")int companycd,@Param("monthFlg")String monthFlg);
   Calculate getOutMonthPayAmtAndCostVat(@Param("jobNo")String jobNo,@Param("companyID")int companyID,@Param("outMonth")String outMonth);
   int validateClientFlg(@Param("cldivcd")String cldivcd,@Param("companycd")int companycd);
   List<Role> selectRoid(@Param("usercd")String usercd);
   Map<Object, Object> selectcodetype(@Param("foreigntype")String foreigntype, @Param("companyID")String companyID);
   String getCompanyName(@Param("companyID")String companyID);
   String getCompanyMoneyUnit(@Param("companyID")String companyID);
}
