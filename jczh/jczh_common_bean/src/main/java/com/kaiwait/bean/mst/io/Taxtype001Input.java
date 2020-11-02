package com.kaiwait.bean.mst.io;



import com.kaiwait.bean.mst.entity.Taxtype;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class Taxtype001Input extends BaseInputBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5085368117650161941L;

	private Taxtype taxtype;

	
    
    public Taxtype getTaxtype() {
		return taxtype;
	}

	public void setTaxtype(Taxtype taxtype) {
		this.taxtype = taxtype;
	}

	
}
