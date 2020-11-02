package com.kaiwait.webservice.mst;



import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.io.Commonmst0000001Input;
import com.kaiwait.common.utils.StringUtil;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CommonmstService;



/** 
* @ClassName: Commonmst0000004 
* @Description: 删除commonmst信息的接口(更新del_flg状态为0)
* @author mayouyi 
* @date 2017年11月14日 下午2:32:32 
*  
*/
@Component("commonmst0000004")
public class Commonmst0000004 implements SingleFunctionIF<Commonmst0000001Input>{
	 
	@Resource
	private CommonmstService CommonmstService;
	
	@Override
	public Object process(Commonmst0000001Input inputParam) {
		Commonmst monmst = new Commonmst();
		monmst.setUpdusercd(inputParam.getUpdusercd());
		monmst.setMstcd(inputParam.getMstcd());
		monmst.setItemcd(inputParam.getItemcd());
		monmst.setCompany_cd(inputParam.getCompany_cd());
		CommonmstService.deleteComTx(monmst);
		return null;
		//return "login............";
	}

	@Override
	public ValidateResult validate(Commonmst0000001Input inputParam) {
		if (StringUtil.isEmpty(inputParam.getMstcd())) {
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("用户Id列表不能为空");
			return validateResult;
		}
		
		if (StringUtil.isEmpty(inputParam.getItemcd())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("主题Id不能为空");
			return validateResult;
		}
		if (null==inputParam.getCompany_cd()) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("公司Id不能为空");
			return validateResult;
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return Commonmst0000001Input.class;
	}

}
