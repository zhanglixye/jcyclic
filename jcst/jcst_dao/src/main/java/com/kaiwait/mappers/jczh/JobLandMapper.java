package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.BsTickets;
import com.kaiwait.bean.jczh.entity.Job;
import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.JobLand;
import com.kaiwait.bean.jczh.entity.JobLandOut;
import com.kaiwait.bean.jczh.entity.JobUserLable;
import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.PdfBolbInfo;
import com.kaiwait.bean.jczh.entity.ReqIns;
import com.kaiwait.bean.jczh.vo.CommonmstVo;
import com.kaiwait.bean.jczh.vo.SalemstVo;
import com.kaiwait.bean.jczh.vo.TranLendVo;
import com.kaiwait.common.dao.BaseMapper;

public interface JobLandMapper extends BaseMapper{
	//插入job基本信息
   int insertJob(JobLand jobland);
   //跟新job
   int updateJob(JobLand jobland);
    //插入jobuserlable
   int insertJobUser(@Param("julableList")List<JobUserLable> julableList);
   //插入 joblabletrn
   int insertJobLable(@Param("jltrnList")List<JobLableTrn> jltrnList);
   //获取页面上货币下拉
   List<CommonmstVo> selectSaleCode(@Param("company_cd")int company_cd,@Param("codecd")String codecd,@Param("mstcd")String mstcd);
   //売上种目信息
   List<SalemstVo> selectSaleList(@Param("saleType")String saleType,@Param("tranLend")String tranLend,@Param("companycd")int companycd);
   //获取采番规则用于生成job 和 lable 的id
   int selectNumberRules(@Param("company_cd")int company_cd);
   //获取标签
   List<Lable>  selectLableList(@Param("usercd")String usercd,@Param("labletype")int labletype,@Param("companycd")int companycd,@Param("powerflg")int powerflg);
   //job更新 根据jobcd 查job
   JobLand selectJobByCd(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   //查询 当前公司的 转账和代垫款
   TranLendVo countTranLendByCd(@Param("jobcd")String jobcd,@Param("company_cd")int company_cd);
   //查询姓名
   String  getNameByUserCd(@Param("usercd")String usercd,@Param("companycd")int companycd);
   //查询jobusertrn 表
   List<JobUserLable>  selectJobUser(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   //查询 标签 当前公司选择的标签 
   List<JobLableTrn> selectJobLable(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   //根据  ID 和 公司ID 删除数据
   int deleteByJobCd(@Param("jobcd")String jobcd,@Param("costno")String costno,@Param("company_cd")int company_cd,@Param("dbname")String dbname);
   //查询 job的买上登录是否完了
   int isFinishFlg(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   //删除job
   int delJob(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("dbname")String dbname,@Param("usercd")String usercd,@Param("delFlg")int delFlg,@Param("level")String level,@Param("lockFlg")int lockFlg);
   //查询 jobuser表中 是否有这个担当者
   int isHaveRequst(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("usercd")String usercd);
   //卖上承认用 查询 本国货币
   List<CommonmstVo> selectCurrencyCode(@Param("company_cd")int company_cd,@Param("codecd")String codecd,@Param("mstcd")String mstcd);
   int updateJutrn(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("usercd")String usercd,@Param("levelflg")String levelflg,@Param("upusercd")String upusercd,@Param("update")String update);
   JobLandOut jobLogOutPut(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   int insertJobUpUser(JobUserLable card);
   ReqIns selectReqInsOutPut(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   BsTickets selectBs(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   String getReqMoney(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   Job selectRateByJobCd(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
   int insertPdfByDB(@Param("pdfBytes")byte[] pdfBytes);
   PdfBolbInfo getPdfByDB(@Param("pdfID")int pdfID);
}
 