package com.kaiwait.service.mst.impl;

import java.util.List;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.kaiwait.bean.mst.entity.Commonmst;
import com.kaiwait.bean.mst.vo.CommonmstVo;
import com.kaiwait.core.utils.DateUtil;
import com.kaiwait.mappers.mst.CommonmstMapper;
import com.kaiwait.service.mst.CommonmstService;

/**
 * @ClassName: CommonmstServiceImpl
 * @Description: commonmst模块service层实现类(这里用一句话描述这个类的作用)
 * @author mayouyi
 * @date 2017年11月14日 上午8:22:34
 * 
 */
@Service
public class CommonmstServiceImpl implements CommonmstService {

	@Resource
	private CommonmstMapper commonmstMapper;
	

	/* (非 Javadoc) 
	* <p>Title: selectAll</p> 
	* <p>Description:查询所有 </p> 
	* @return List<CommonmstVo>
	* @see com.kaiwait.service.mst.CommonmstService#selectAll() 
	*/
	public List<CommonmstVo> selectAll(String company) {
		return commonmstMapper.selectAll(company);
	}
	
	/* (非 Javadoc) 
	* <p>Title: selectComByCd</p> 
	* <p>Description:查询common前置信息 </p> 
	* @return List<CommonmstVo>
	* @see com.kaiwait.service.mst.CommonmstService#selectAll() 
	*/
	public List<CommonmstVo> selectComByCd(String mstcd,String itemcd,Integer company_cd) {
		return commonmstMapper.selectComByCd(mstcd,itemcd,company_cd);
	}

   

	/* (非 Javadoc) 
	* <p>Title: insertNewTx</p> 
	* <p>Description:新增 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#insertNewTx(com.kaiwait.bean.mst.entity.Commonmst) 
	*/
	public int insertNewTx(Commonmst monmst) {
		   //剔除前台传入你的del——flg多余的逗号
			String del = monmst.getDel_flg();
			if(del!=null&&del!="") {
				del = monmst.getDel_flg().replace(",", "");
			}else {
				del="0";
			}
	  monmst.setDel_flg(del);
      return  commonmstMapper.insertNew(monmst);
	}



	/* (非 Javadoc) 
	* <p>Title: updateOldComTx</p> 
	* <p>Description:更新 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#updateOldComTx(com.kaiwait.bean.mst.io.Commonmst0000003Input) 
	*/
	public int updateOldComTx(Commonmst monmst) {
		int num =1;
		Integer lock =commonmstMapper.getLockFlg(monmst.getMstcd(), monmst.getItemcd(), monmst.getCompany_cd());
		if(monmst.getLock_flg()!=lock){
			 return 2;
		}
		//判断前台是否有del——flg传入，若没有，就赋值0
		String del = monmst.getDel_flg();
		if(del!=null&&del!="") {
			 //剔除前台传入你的del——flg多余的逗号
			del = monmst.getDel_flg().replace(",", "");
		}else {
			del="0";
		}		
		monmst.setDel_flg(del);
		commonmstMapper.updateOldCom(monmst);
		 return num;
		
	}



	/* (非 Javadoc) 
	* <p>Title: deleteComTx</p> 
	* <p>Description:删除 </p> 
	* @param monmst 
	* @see com.kaiwait.service.mst.CommonmstService#deleteComTx(com.kaiwait.bean.mst.io.Commonmst0000001Input) 
	*/
	public void deleteComTx(Commonmst monmst) {
		 commonmstMapper.deleteCom(monmst);
		
	}
	
	public List<CommonmstVo> selectMstNameByCD(String mstcd,Integer company_cd){
		return commonmstMapper.selectMstNameByCD(mstcd,company_cd);
	}

	public int editSystemLock(String companyID,int sysLockFlg,String userID)
	{
		String addDate = DateUtil.getDateForNow(DateUtil.dateTimeFormat);
		commonmstMapper.editSystemLock(companyID,sysLockFlg,userID,addDate);
		return sysLockFlg;
	}
}
