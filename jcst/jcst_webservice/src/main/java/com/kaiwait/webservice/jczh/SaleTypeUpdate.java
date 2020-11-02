package com.kaiwait.webservice.jczh;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.kaiwait.bean.jczh.entity.JobLableTrn;
import com.kaiwait.bean.jczh.entity.SaleType;
import com.kaiwait.bean.jczh.io.SaleZhInput;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.service.jczh.SaleTypeService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

@Component("saleTypeUpdate")
@Privilege(keys= {"18","19","20","21"}, match=MatchEnum.ANY)
public class SaleTypeUpdate implements SingleFunctionIF<SaleZhInput>  {
	@Resource
	private SaleTypeService saleTypeService;
	@Override
	public Object process(SaleZhInput inputParam) {
		// TODO Auto-generated method stub
		SaleType saleType = inputParam.getSaleType();
		List<JobLableTrn> jltrn = saleType.getJlTrn();
	    if(jltrn==null||jltrn.size()==0) {
	    	jltrn = null;
	    }else {
		for(int i=0;i<jltrn.size();i++) {
			jltrn.get(i).setJobcd(saleType.getJob_cd());
			jltrn.get(i).setCompanycd(Integer.valueOf(inputParam.getCompanyID()));
			jltrn.get(i).setAddDate(DateUtil.getDateForNow(DateUtil.dateTimeFormat));
//			jltrn.get(i).setUpdDate(DateUtil.getDateForNow(DateUtil.dateTimeFormat));
			jltrn.get(i).setAddUsercd(inputParam.getUserID());
//			jltrn.get(i).setUpUsercd(inputParam.getUserID());
		}
	    }
		return saleTypeService.updateSaleTypeTx(saleType, Integer.valueOf(inputParam.getCompanyID()), inputParam.getUserID(),jltrn);
	}

	@Override
	public ValidateResult validate(SaleZhInput inputParam) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return SaleZhInput.class;
	}
}
