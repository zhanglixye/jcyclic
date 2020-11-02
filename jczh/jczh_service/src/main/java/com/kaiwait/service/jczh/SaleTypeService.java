package com.kaiwait.service.jczh;

import java.util.List;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.SaleAdmin;
import com.kaiwait.bean.jczh.entity.SaleType;
import com.kaiwait.bean.jczh.io.SaleZhInput;

public interface SaleTypeService {
  int insertSaleTypeTx(SaleType saleType,int company_cd,String usercd,List<JobLableTrn> jltrn);
  SaleType pageLoad(String jobcd,String sale_no,int company_cd,String usercd);
  int updateSaleTypeTx(SaleType saleType,int company_cd,String usercd,List<JobLableTrn> jltrn);
  SaleAdmin selectSaleAdmin(SaleZhInput saleInput);
  int updateSaleStatusTx(String jobcd,int company_cd,String upuser_cd,String sale_admit_remark,int ad_flg,List<JobLableTrn> jltrn,int lockFlg,String usercd);
  int delSaleTx(String jobcd,int companycd,String usercd,int lockFlg,int recLockFlg);
  void upReqTx(String jobcd,int companycd,String usercd);
  void upInvTx(String jobcd,int companycd,String usercd);
}
