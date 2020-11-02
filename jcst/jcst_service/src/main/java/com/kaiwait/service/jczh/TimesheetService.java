package com.kaiwait.service.jczh;


import java.util.List;
import java.util.Map;

import com.kaiwait.bean.jczh.io.JobListInput;
import com.kaiwait.bean.jczh.io.TimesheetInput;

public interface TimesheetService {
	//timesheet初始化
	List<Map<String,Object>> searchInitList(TimesheetInput inputParam);
	//timesheet新增
	int timesheetInsert(TimesheetInput inputParam);
	//
	TimesheetInput timesheetQuery(JobListInput jobInput);
}
