package com.kaiwait.mappers.mst;

import org.apache.ibatis.annotations.Param;

import com.kaiwait.bean.mst.entity.Lable;


public interface LableDealMapper {
	  int bindLable(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("lablelevel")int lablelevel,@Param("lableid")String lableid,@Param("companycd")int companycd,
			  @Param("addDate")String addDate,@Param("addUsercd")String addUsercd);
	   //lable绑定表中是否存在数据
	   int isHaveBind(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("lableid")String lableid,@Param("companycd")int companycd);
	   //刪除綁定標籤
	   int delBindLable(@Param("jobcd")String jobcd,@Param("inputno")String inputno,@Param("lableid")String lableid,@Param("companycd")int companycd);
	   //是否有这个标签了
	   int isHaveLable(@Param("labletext")String labletext,@Param("lablelevel")int lablelevel,@Param("usercd")String usercd,@Param("companycd")int companycd);
	   //追加标签
	   int addLable(Lable lable);
}
