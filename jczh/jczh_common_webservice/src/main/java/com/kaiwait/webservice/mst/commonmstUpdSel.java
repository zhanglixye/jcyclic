package com.kaiwait.webservice.mst;



import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.mst.io.Commonmst0000001Input;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CommonmstService;

/** 
* @ClassName: Commonmst0000001 
* @Description: 更新commonmst时，前置commonmst信息查询的接口(这里用一句话描述这个类的作用) 
* @author mayouyi 
* @date 2018年5月9日 上午8:46:22 
*  
*/
@Component("commonmstUpdSel")
public class commonmstUpdSel implements SingleFunctionIF<Commonmst0000001Input>{

	@Resource
	private CommonmstService CommonmstService;
	
	@Override
	public List<CommonmstVo> process(Commonmst0000001Input inputParam) {
		
		return CommonmstService.selectComByCd(inputParam.getMstcd(),inputParam.getItemcd(),inputParam.getCompany_cd());
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
