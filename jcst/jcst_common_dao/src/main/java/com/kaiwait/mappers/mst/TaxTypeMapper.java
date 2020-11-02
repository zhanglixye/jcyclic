package com.kaiwait.mappers.mst;

import com.kaiwait.bean.mst.entity.Taxtype;
import com.kaiwait.common.dao.BaseMapper;

public interface TaxTypeMapper extends BaseMapper{
/*    int deleteByPrimaryKey(Integer id);
*/
		
	//原价税率登录
	void insertTaxtype(Taxtype taxtype);
	

/*    int insertSelective(Order record);

    Order selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Order record);

    int updateByPrimaryKey(Order record);*/
}