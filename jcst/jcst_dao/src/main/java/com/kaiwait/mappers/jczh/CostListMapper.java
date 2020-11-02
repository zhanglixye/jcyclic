package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.PayDeal;
import com.kaiwait.bean.jczh.vo.CostListVo;
import com.kaiwait.common.dao.BaseMapper;

public interface CostListMapper  extends BaseMapper {
	//基本详细查询原价
   List<CostListVo>  getCostList(CostListVo costListVo);
    //模糊查询原价
   List<CostListVo>  getCostListVogue(CostListVo costListVo);
   //其他查询原价
   List<CostListVo>  getCostListByOther(CostListVo costListVo);
   //支付处理
   int insertPaytrnByInum(PayDeal payList);
   //查询checkDate 用于top原价查询
   String selectCheckDate(@Param("stt")String stt,@Param("companycd")int companycd);
   //查询top过来的原价
   List<CostListVo>  getCostListFromTop(CostListVo costListVo);
  
}
