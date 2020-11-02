package com.kaiwait.webservice.mst;


import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.io.Commonmst0000001Input;
import com.kaiwait.core.process.SingleFunctionIF;
import com.kaiwait.core.process.ValidateResult;
import com.kaiwait.service.mst.CommonmstService;

/**
 * @ClassName: commonmstUpd
 * @Description: 更新commonmst信息的接口(这里用一句话描述这个类的作用)
 * @author mayouyi
 * @date 2017年11月14日 上午8:46:22
 * 
 */
@Component("commonmstUpd")
public class commonmstUpd implements SingleFunctionIF<Commonmst0000001Input> {

	@Resource
	private CommonmstService CommonmstService;

	@Override
	public Object process(Commonmst0000001Input inputParam) {
		Commonmst monmst = new Commonmst();// 实例化一个新的Commonmst对象
		monmst = inputParam.getCommonmst();// 取出Commonmst0000001Input中的Commonmst对象的值
		monmst.setUpdusercd(inputParam.getUserID());//取出Commonmst对象中的新增人id。（新增时，更新人id就是新增人id）
		monmst.setCompany_cd(Integer.parseInt(inputParam.getCompanyID()));
		return CommonmstService.updateOldComTx(monmst);
		// return "login............";
	}

	@Override
	public ValidateResult validate(Commonmst0000001Input inputParam) {
		/*if (StringUtil.isEmpty(inputParam.getMstcd())) {
			ValidateResult validateResult = new ValidateResult("2");
			validateResult.setErrorMessage("区分Id列表不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getMstname())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("区分名称不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getItemcd())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("项目Id不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getItmname())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("项目名称不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getItemname_en())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("项目名称（英语）不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getItmvalue())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("设定值不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getOrderno())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("表示順不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getAidcd())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("補助コード不能为空");
			return validateResult;
		}
		if (StringUtil.isEmpty(inputParam.getItemname_jp())) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("项目名称（日语）不能为空");
			return validateResult;
		}
		if (null==inputParam.getCompany_cd()) {
			ValidateResult validateResult = new ValidateResult("3");
			validateResult.setErrorMessage("公司Id不能为空");
			return validateResult;
		}*/
		return null;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Class getParamType() {
		// TODO Auto-generated method stub
		return Commonmst0000001Input.class;
	}

}
