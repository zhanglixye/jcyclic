package com.kaiwait.service.jczh;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kaiwait.bean.jczh.entity.Columns;
import com.kaiwait.bean.jczh.entity.Job;
import com.kaiwait.bean.jczh.io.CostInput;
import com.kaiwait.bean.jczh.io.JobListInput;

public interface JobService {

	HashMap<String,Object> searchJobList(JobListInput jobInput);
	HashMap<String,Object> searchJobListInit(JobListInput jobInput);
	HashMap<String, Object>  searchJobByKeyWord(JobListInput jobInput);
	Job searchJobInfoByJobNo(String jobNo,String companyID);
	Map<String,Object> searchCostInfo(CostInput inputParam);
	//壳上取消
/*	int saleCancelTx(JobListInput inputParam); */
	//壳上承认取消
	int saleAdmitCancelTx(JobListInput inputParam);

    //原价完了
	int costFinish(JobListInput inputParam);
	 //原价完了
	int costFinishCancel(JobListInput inputParam);
	 Map<String,Object> getJobDetailNoList(JobListInput jobInput);
	public  List<List<Columns>> selColumns(int company_cd,int level,int usercd,int ison);
	
	
}
