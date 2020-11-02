package com.kaiwait.mappers.jczh;


import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.InvoiceInfo;
import com.kaiwait.bean.jczh.entity.RealSaleCost;
import com.kaiwait.bean.jczh.entity.ReqInfo;
import com.kaiwait.bean.jczh.entity.SaleAdmin;
import com.kaiwait.bean.jczh.entity.SaleType;
import com.kaiwait.common.dao.BaseMapper;

public interface SaleTypeMapper extends BaseMapper {
	//买上登录
	int insertSaleType(SaleType saleType);
	//根据公司和jobcd查询 卖上信息
    SaleType selectSaleTypeByCd(@Param("jobcd")String jobcd,@Param("company_cd")int company_cd);
    //查询此job下是否有实际的卖上信息
    int isHaveRealSaleByJobCd(@Param("jobcd")String jobcd,@Param("company_cd")int company_cd);
    //更新卖上信息
    int updateSaleType(SaleType saleType);
    //查询卖上承认信息
    SaleAdmin selectSaleAdmin(@Param("jobcd")String jobcd,@Param("company_cd")int company_cd);
    //更新状态
    int updateSaleStatus(@Param("jobcd")String jobcd,@Param("company_cd")int company_cd,
    		@Param("upuser_cd")String upuser_cd,@Param("admin_date")String admin_date,@Param("sale_admit_remark")String sale_admit_remark,@Param("saleLockFlg")int saleLockFlg);
    //查询请求书
    ReqInfo selectReqByCd(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("reqcd")String reqcd);
    //查询 发行书
    InvoiceInfo selectInvoiceInfoByCd(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("invoicdno")String invoicdno);
    //想reqtrn插入一条信息
    int insertToReq(ReqInfo req);
    //更新 req数据
    int updateToReq(ReqInfo req);
    //插入 发票
    int insertToInvoic(InvoiceInfo inv);
    //更新invoice数据
    int updateToInvoic(InvoiceInfo inv);
    //查询表reqtrn 与invoictrn中是否存在这条job的数据
    int isHaveMsg(@Param("jobcd")String jobcd,@Param("company_cd")int company_cd,@Param("colName")String colName,@Param("cd")String cd,@Param("String")String dbname );
    //查询实际原价卖上登录
    RealSaleCost realSale(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
    int deleteTimes(@Param("jobcd")String jobcd,@Param("companycd")int companycd,@Param("dbname")String dbname);
    ReqInfo selReq(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
    InvoiceInfo selInv(@Param("jobcd")String jobcd,@Param("companycd")int companycd);
}
