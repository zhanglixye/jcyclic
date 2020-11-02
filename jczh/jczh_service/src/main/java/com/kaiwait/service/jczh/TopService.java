package com.kaiwait.service.jczh;

import java.util.List;
import java.util.Map;

import com.kaiwait.bean.jczh.entity.MsgInfo;

public interface TopService {
   Map<String,Object> topLoad(int companycd,String usercd);
   List<MsgInfo> changeMsgStatusTx(int msgId,int companycd,String usercd);
}
