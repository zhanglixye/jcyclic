package com.kaiwait.mappers.jczh;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.MsgInfo;
import com.kaiwait.bean.jczh.vo.CostListVo;
import com.kaiwait.bean.jczh.vo.TopVo;
import com.kaiwait.common.dao.BaseMapper;

public interface TopMapper extends BaseMapper{
	TopVo getCostAccount(CostListVo clvo);
    List<MsgInfo> getMsg(@Param("companycd")int companycd,@Param("usercd")String usercd,@Param("limitNum")int limitNum);
    List<Lable> getLable(Lable lable);
    int changeMsgStatus(@Param("id")int id,@Param("companycd")int companycd);
    int deleteFromUserMsg(@Param("id")int id,@Param("usercd")String usercd,@Param("companycd")int companycd);
    int insertReadMsg(@Param("id")int id,@Param("usercd")String usercd,@Param("companycd")int companycd,@Param("addDate")String addDate);
    String getMsgLimit();
}
