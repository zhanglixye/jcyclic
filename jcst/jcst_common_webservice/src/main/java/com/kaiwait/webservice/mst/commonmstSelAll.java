package com.kaiwait.webservice.mst;



import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.mst.io.Commonmst0000001Input;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CommonmstService;
import com.kaiwait.thrift.common.server.annotation.MatchEnum;
import com.kaiwait.thrift.common.server.annotation.Privilege;

/** 
* @ClassName: Commonmst0000001 
* @Description: 查询所有commonmst信息的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2017年11月14日 上午8:46:22 
*  
*/
@Component("commonmstSelAll")
@Privilege(keys= {"83"}, match=MatchEnum.ANY)
public class commonmstSelAll implements SingleFunctionIF<Commonmst0000001Input>{

	@Resource
	private CommonmstService CommonmstService;
	
	@Override
	public List<CommonmstVo> process(Commonmst0000001Input inputParam) {
		return CommonmstService.selectAll(inputParam.getCompanyID());
		
		//return "login............";
	}

	@Override
	public ValidateResult validate(Commonmst0000001Input inputParam) {
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return Commonmst0000001Input.class;
	}

}
