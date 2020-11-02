package com.kaiwait.bean.jczh.vo;

import java.util.List;

import com.kaiwait.bean.jczh.entity.Lable;
import com.kaiwait.bean.jczh.entity.Msgtrn;

public class TopVo {
	//job总件数
    private int joballcount;
    //业务登录件数
    private int cldlogcount;
    //已登录业务未处理件数
    private int cldnodealcount;
    //已终止成本未处理件数
    private int costovernodealcount;
    //
    private String cldmoneysum;
    //
    private String nodealsum;
    //原价件数
    private int costallcount;
    //本月批准件数
    private int adcount;
    //本月未批准件数
    private int noadcount;
    //lable my
    private List<Lable> mylable; 
    //lable manager
    private List<Lable> managerlable; 
    //留言板
    private List<Msgtrn> msg;
	public int getJoballcount() {
		return joballcount;
	}
	public void setJoballcount(int joballcount) {
		this.joballcount = joballcount;
	}
	public int getCldlogcount() {
		return cldlogcount;
	}
	public void setCldlogcount(int cldlogcount) {
		this.cldlogcount = cldlogcount;
	}
	public int getCldnodealcount() {
		return cldnodealcount;
	}
	public void setCldnodealcount(int cldnodealcount) {
		this.cldnodealcount = cldnodealcount;
	}
	public int getCostovernodealcount() {
		return costovernodealcount;
	}
	public void setCostovernodealcount(int costovernodealcount) {
		this.costovernodealcount = costovernodealcount;
	}
	public String getCldmoneysum() {
		return cldmoneysum;
	}
	public void setCldmoneysum(String cldmoneysum) {
		this.cldmoneysum = cldmoneysum;
	}
	public String getNodealsum() {
		return nodealsum;
	}
	public void setNodealsum(String nodealsum) {
		this.nodealsum = nodealsum;
	}
	public int getCostallcount() {
		return costallcount;
	}
	public void setCostallcount(int costallcount) {
		this.costallcount = costallcount;
	}
	public int getAdcount() {
		return adcount;
	}
	public void setAdcount(int adcount) {
		this.adcount = adcount;
	}
	public int getNoadcount() {
		return noadcount;
	}
	public void setNoadcount(int noadcount) {
		this.noadcount = noadcount;
	}
	public List<Lable> getMylable() {
		return mylable;
	}
	public void setMylable(List<Lable> mylable) {
		this.mylable = mylable;
	}
	public List<Lable> getManagerlable() {
		return managerlable;
	}
	public void setManagerlable(List<Lable> managerlable) {
		this.managerlable = managerlable;
	}
	public List<Msgtrn> getMsg() {
		return msg;
	}
	public void setMsg(List<Msgtrn> msg) {
		this.msg = msg;
	}
    
	
}
