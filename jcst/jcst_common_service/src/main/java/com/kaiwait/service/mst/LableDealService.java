package com.kaiwait.service.mst;


import com.kaiwait.bean.mst.io.LableInput;

public interface LableDealService {
	   Object addLableTx(LableInput inputParam);
	   Object listLableAddTx(LableInput inputParam);
	   Object listLableSetTx(LableInput inputParam);
	 
}
