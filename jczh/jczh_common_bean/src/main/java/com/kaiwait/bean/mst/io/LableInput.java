package com.kaiwait.bean.mst.io;

import com.kaiwait.bean.mst.entity.BindLable;
import com.kaiwait.bean.mst.entity.Lable;
import com.kaiwait.bean.mst.entity.ListLable;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class LableInput extends BaseInputBean{
		
		private static final long serialVersionUID = 5085368117650161941L;
	
		  private Lable lable;
		  private BindLable bindlable;
		  private ListLable listlable;
		  private String job_cd;
		public Lable getLable() {
			return lable;
		}
		public void setLable(Lable lable) {
			this.lable = lable;
		}
		public BindLable getBindlable() {
			return bindlable;
		}
		public void setBindlable(BindLable bindlable) {
			this.bindlable = bindlable;
		}
		public ListLable getListlable() {
			return listlable;
		}
		public void setListlable(ListLable listlable) {
			this.listlable = listlable;
		}
		public String getJob_cd() {
			return job_cd;
		}
		public void setJob_cd(String job_cd) {
			this.job_cd = job_cd;
		}
	
}
