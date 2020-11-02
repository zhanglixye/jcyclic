package com.kaiwait.bean.mst.io;


import java.util.Date;

import com.kaiwait.bean.mst.entity.Clmst;
import com.kaiwait.common.vo.json.server.BaseInputBean;

public class Clmst00000001Input extends BaseInputBean{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5085368117650161941L;

	private Clmst Clmst;

	private int CLDIVCD;	
	
	private String ACCOUNT_CD;
	
	private String DIVNM;
	
	private String DIVNM_EN;
	
	private String DIVNAME_FULL;

	private String DIVADD;

	private String DIV_TEL;
	
	private String CONTACTS_NAME;
	
	private String CONTACTS_NAME_EN;
	
	private String CONTACTS_NAME_JP;
	
	private String CONTACTS_NAME_HK;
	
	private String SELF_COMPANY_NAME;
	
	private String SELF_COMPANY_NAME_EN;
	
	private String SELF_COMPANY_NAME_JP;
	
	private String SELF_COMPANY_NAME_HK;
	
	private String CONTACTS_ADDRESS;
	
	private String CONTACTS_ADDRESS_EN;
	
	private String CONTACTS_ADDRESS_JP;
	
	private String CONTACTS_ADDRESS_HK;
	
	private String TEL_NUMBER;
	
	private String TEL_NUMBER_EN;
	
	private String TEL_NUMBER_JP;
	
	private String TEL_NUMBER_HK;
	
	private int DATE_AUTO_SETTING;
	
	private int AUTO_MONTH;
	
	private int AUTO_DAY;

	private String CONTRA_CONTACTS_NAME;
	
	private String CONTRA_CONTACTS_NAME_EN;
	
	private String CONTRA_CONTACTS_NAME_JP;
	
	private String CONTRA_CONTACTS_NAME_HK;
	
	private String CONTRA_SELF_COMPANY_NAME;
	
	private String CONTRA_SELF_COMPANY_NAME_EN;
	
	private String CONTRA_SELF_COMPANY_NAME_JP;
	
	private String CONTRA_SELF_COMPANY_NAME_HK;
	
	private String CONTRA_ADDRESS;
	
	private String CONTRA_ADDRESS_EN;
	
	private String CONTRA_ADDRESS_JP;
	
	private String CONTRA_ADDRESS_HK;
	
	private String CONTRA_TEL;
	
	private String CONTRA_TEL_EN;
	
	private String CONTRA_TEL_JP;
	
	private String CONTRA_TEL_HK;
	
	private int PAY_AUTO_SETTING;

	private int PAY_DATE_FLG;

	private int PAY_AUTO_MONTH;

	private int PAY_AUTO_DAY;

	private String BANK_INTO_ONE;

	private String BANK_INFO_TWO;

	private String NOTE;

	private String PAY_INFO;

	private String OTHER_NOTE;

	private String OTHER_NOTE1;

	private String OTHER_NOTE2;
		
	private Date ADDDATE;
	
	private Date UPDATE;

	private String ADDUSERCD;

	private String UPDUSERCD;

	private int CLIENT_FLG;

	private int CONTRA_FLG;

	private int PAY_FLG;

	private int HDY_FLG;

	private int COMPANY_CD;

	private int DEL_FLG;
	
	public int getCLDIVCD() {
		return CLDIVCD;
	}

	public void setCLDIVCD(int cLDIVCD) {
		CLDIVCD = cLDIVCD;
	}

	public String getACCOUNT_CD() {
		return ACCOUNT_CD;
	}

	public void setACCOUNT_CD(String aCCOUNT_CD) {
		ACCOUNT_CD = aCCOUNT_CD;
	}

	public String getDIVNM() {
		return DIVNM;
	}

	public void setDIVNM(String dIVNM) {
		DIVNM = dIVNM;
	}

	public String getDIVNM_EN() {
		return DIVNM_EN;
	}

	public void setDIVNM_EN(String dIVNM_EN) {
		DIVNM_EN = dIVNM_EN;
	}

	public String getDIVNAME_FULL() {
		return DIVNAME_FULL;
	}

	public void setDIVNAME_FULL(String dIVNAME_FULL) {
		DIVNAME_FULL = dIVNAME_FULL;
	}

	public String getDIVADD() {
		return DIVADD;
	}

	public void setDIVADD(String dIVADD) {
		DIVADD = dIVADD;
	}

	public String getDIV_TEL() {
		return DIV_TEL;
	}

	public void setDIV_TEL(String dIV_TEL) {
		DIV_TEL = dIV_TEL;
	}

	public String getCONTACTS_NAME() {
		return CONTACTS_NAME;
	}

	public void setCONTACTS_NAME(String cONTACTS_NAME) {
		CONTACTS_NAME = cONTACTS_NAME;
	}

	public String getCONTACTS_NAME_EN() {
		return CONTACTS_NAME_EN;
	}

	public void setCONTACTS_NAME_EN(String cONTACTS_NAME_EN) {
		CONTACTS_NAME_EN = cONTACTS_NAME_EN;
	}

	public String getCONTACTS_NAME_JP() {
		return CONTACTS_NAME_JP;
	}

	public void setCONTACTS_NAME_JP(String cONTACTS_NAME_JP) {
		CONTACTS_NAME_JP = cONTACTS_NAME_JP;
	}

	public String getCONTACTS_NAME_HK() {
		return CONTACTS_NAME_HK;
	}

	public void setCONTACTS_NAME_HK(String cONTACTS_NAME_HK) {
		CONTACTS_NAME_HK = cONTACTS_NAME_HK;
	}

	public String getSELF_COMPANY_NAME() {
		return SELF_COMPANY_NAME;
	}

	public void setSELF_COMPANY_NAME(String sELF_COMPANY_NAME) {
		SELF_COMPANY_NAME = sELF_COMPANY_NAME;
	}

	public String getSELF_COMPANY_NAME_EN() {
		return SELF_COMPANY_NAME_EN;
	}

	public void setSELF_COMPANY_NAME_EN(String sELF_COMPANY_NAME_EN) {
		SELF_COMPANY_NAME_EN = sELF_COMPANY_NAME_EN;
	}

	public String getSELF_COMPANY_NAME_JP() {
		return SELF_COMPANY_NAME_JP;
	}

	public void setSELF_COMPANY_NAME_JP(String sELF_COMPANY_NAME_JP) {
		SELF_COMPANY_NAME_JP = sELF_COMPANY_NAME_JP;
	}

	public String getSELF_COMPANY_NAME_HK() {
		return SELF_COMPANY_NAME_HK;
	}

	public void setSELF_COMPANY_NAME_HK(String sELF_COMPANY_NAME_HK) {
		SELF_COMPANY_NAME_HK = sELF_COMPANY_NAME_HK;
	}

	public String getCONTACTS_ADDRESS() {
		return CONTACTS_ADDRESS;
	}

	public void setCONTACTS_ADDRESS(String cONTACTS_ADDRESS) {
		CONTACTS_ADDRESS = cONTACTS_ADDRESS;
	}

	public String getCONTACTS_ADDRESS_EN() {
		return CONTACTS_ADDRESS_EN;
	}

	public void setCONTACTS_ADDRESS_EN(String cONTACTS_ADDRESS_EN) {
		CONTACTS_ADDRESS_EN = cONTACTS_ADDRESS_EN;
	}

	public String getCONTACTS_ADDRESS_JP() {
		return CONTACTS_ADDRESS_JP;
	}

	public void setCONTACTS_ADDRESS_JP(String cONTACTS_ADDRESS_JP) {
		CONTACTS_ADDRESS_JP = cONTACTS_ADDRESS_JP;
	}

	public String getCONTACTS_ADDRESS_HK() {
		return CONTACTS_ADDRESS_HK;
	}

	public void setCONTACTS_ADDRESS_HK(String cONTACTS_ADDRESS_HK) {
		CONTACTS_ADDRESS_HK = cONTACTS_ADDRESS_HK;
	}

	public String getTEL_NUMBER() {
		return TEL_NUMBER;
	}

	public void setTEL_NUMBER(String tEL_NUMBER) {
		TEL_NUMBER = tEL_NUMBER;
	}

	public String getTEL_NUMBER_EN() {
		return TEL_NUMBER_EN;
	}

	public void setTEL_NUMBER_EN(String tEL_NUMBER_EN) {
		TEL_NUMBER_EN = tEL_NUMBER_EN;
	}

	public String getTEL_NUMBER_JP() {
		return TEL_NUMBER_JP;
	}

	public void setTEL_NUMBER_JP(String tEL_NUMBER_JP) {
		TEL_NUMBER_JP = tEL_NUMBER_JP;
	}

	public String getTEL_NUMBER_HK() {
		return TEL_NUMBER_HK;
	}

	public void setTEL_NUMBER_HK(String tEL_NUMBER_HK) {
		TEL_NUMBER_HK = tEL_NUMBER_HK;
	}

	public int getDATE_AUTO_SETTING() {
		return DATE_AUTO_SETTING;
	}

	public void setDATE_AUTO_SETTING(int dATE_AUTO_SETTING) {
		DATE_AUTO_SETTING = dATE_AUTO_SETTING;
	}

	public int getAUTO_MONTH() {
		return AUTO_MONTH;
	}

	public void setAUTO_MONTH(int aUTO_MONTH) {
		AUTO_MONTH = aUTO_MONTH;
	}

	public int getAUTO_DAY() {
		return AUTO_DAY;
	}

	public void setAUTO_DAY(int aUTO_DAY) {
		AUTO_DAY = aUTO_DAY;
	}

	public String getCONTRA_CONTACTS_NAME() {
		return CONTRA_CONTACTS_NAME;
	}

	public void setCONTRA_CONTACTS_NAME(String cONTRA_CONTACTS_NAME) {
		CONTRA_CONTACTS_NAME = cONTRA_CONTACTS_NAME;
	}

	public String getCONTRA_CONTACTS_NAME_EN() {
		return CONTRA_CONTACTS_NAME_EN;
	}

	public void setCONTRA_CONTACTS_NAME_EN(String cONTRA_CONTACTS_NAME_EN) {
		CONTRA_CONTACTS_NAME_EN = cONTRA_CONTACTS_NAME_EN;
	}

	public String getCONTRA_CONTACTS_NAME_JP() {
		return CONTRA_CONTACTS_NAME_JP;
	}

	public void setCONTRA_CONTACTS_NAME_JP(String cONTRA_CONTACTS_NAME_JP) {
		CONTRA_CONTACTS_NAME_JP = cONTRA_CONTACTS_NAME_JP;
	}

	public String getCONTRA_CONTACTS_NAME_HK() {
		return CONTRA_CONTACTS_NAME_HK;
	}

	public void setCONTRA_CONTACTS_NAME_HK(String cONTRA_CONTACTS_NAME_HK) {
		CONTRA_CONTACTS_NAME_HK = cONTRA_CONTACTS_NAME_HK;
	}

	public String getCONTRA_SELF_COMPANY_NAME() {
		return CONTRA_SELF_COMPANY_NAME;
	}

	public void setCONTRA_SELF_COMPANY_NAME(String cONTRA_SELF_COMPANY_NAME) {
		CONTRA_SELF_COMPANY_NAME = cONTRA_SELF_COMPANY_NAME;
	}

	public String getCONTRA_SELF_COMPANY_NAME_EN() {
		return CONTRA_SELF_COMPANY_NAME_EN;
	}

	public void setCONTRA_SELF_COMPANY_NAME_EN(String cONTRA_SELF_COMPANY_NAME_EN) {
		CONTRA_SELF_COMPANY_NAME_EN = cONTRA_SELF_COMPANY_NAME_EN;
	}

	public String getCONTRA_SELF_COMPANY_NAME_JP() {
		return CONTRA_SELF_COMPANY_NAME_JP;
	}

	public void setCONTRA_SELF_COMPANY_NAME_JP(String cONTRA_SELF_COMPANY_NAME_JP) {
		CONTRA_SELF_COMPANY_NAME_JP = cONTRA_SELF_COMPANY_NAME_JP;
	}

	public String getCONTRA_SELF_COMPANY_NAME_HK() {
		return CONTRA_SELF_COMPANY_NAME_HK;
	}

	public void setCONTRA_SELF_COMPANY_NAME_HK(String cONTRA_SELF_COMPANY_NAME_HK) {
		CONTRA_SELF_COMPANY_NAME_HK = cONTRA_SELF_COMPANY_NAME_HK;
	}

	public String getCONTRA_ADDRESS() {
		return CONTRA_ADDRESS;
	}

	public void setCONTRA_ADDRESS(String cONTRA_ADDRESS) {
		CONTRA_ADDRESS = cONTRA_ADDRESS;
	}

	public String getCONTRA_ADDRESS_EN() {
		return CONTRA_ADDRESS_EN;
	}

	public void setCONTRA_ADDRESS_EN(String cONTRA_ADDRESS_EN) {
		CONTRA_ADDRESS_EN = cONTRA_ADDRESS_EN;
	}

	public String getCONTRA_ADDRESS_JP() {
		return CONTRA_ADDRESS_JP;
	}

	public void setCONTRA_ADDRESS_JP(String cONTRA_ADDRESS_JP) {
		CONTRA_ADDRESS_JP = cONTRA_ADDRESS_JP;
	}

	public String getCONTRA_ADDRESS_HK() {
		return CONTRA_ADDRESS_HK;
	}

	public void setCONTRA_ADDRESS_HK(String cONTRA_ADDRESS_HK) {
		CONTRA_ADDRESS_HK = cONTRA_ADDRESS_HK;
	}

	public String getCONTRA_TEL() {
		return CONTRA_TEL;
	}

	public void setCONTRA_TEL(String cONTRA_TEL) {
		CONTRA_TEL = cONTRA_TEL;
	}

	public String getCONTRA_TEL_EN() {
		return CONTRA_TEL_EN;
	}

	public void setCONTRA_TEL_EN(String cONTRA_TEL_EN) {
		CONTRA_TEL_EN = cONTRA_TEL_EN;
	}

	public String getCONTRA_TEL_JP() {
		return CONTRA_TEL_JP;
	}

	public void setCONTRA_TEL_JP(String cONTRA_TEL_JP) {
		CONTRA_TEL_JP = cONTRA_TEL_JP;
	}

	public String getCONTRA_TEL_HK() {
		return CONTRA_TEL_HK;
	}

	public void setCONTRA_TEL_HK(String cONTRA_TEL_HK) {
		CONTRA_TEL_HK = cONTRA_TEL_HK;
	}

	public int getPAY_AUTO_SETTING() {
		return PAY_AUTO_SETTING;
	}

	public void setPAY_AUTO_SETTING(int pAY_AUTO_SETTING) {
		PAY_AUTO_SETTING = pAY_AUTO_SETTING;
	}

	public int getPAY_DATE_FLG() {
		return PAY_DATE_FLG;
	}

	public void setPAY_DATE_FLG(int pAY_DATE_FLG) {
		PAY_DATE_FLG = pAY_DATE_FLG;
	}

	public int getPAY_AUTO_MONTH() {
		return PAY_AUTO_MONTH;
	}

	public void setPAY_AUTO_MONTH(int pAY_AUTO_MONTH) {
		PAY_AUTO_MONTH = pAY_AUTO_MONTH;
	}

	public int getPAY_AUTO_DAY() {
		return PAY_AUTO_DAY;
	}

	public void setPAY_AUTO_DAY(int pAY_AUTO_DAY) {
		PAY_AUTO_DAY = pAY_AUTO_DAY;
	}

	public String getBANK_INTO_ONE() {
		return BANK_INTO_ONE;
	}

	public void setBANK_INTO_ONE(String bANK_INTO_ONE) {
		BANK_INTO_ONE = bANK_INTO_ONE;
	}

	public String getBANK_INFO_TWO() {
		return BANK_INFO_TWO;
	}

	public void setBANK_INFO_TWO(String bANK_INFO_TWO) {
		BANK_INFO_TWO = bANK_INFO_TWO;
	}

	public String getNOTE() {
		return NOTE;
	}

	public void setNOTE(String nOTE) {
		NOTE = nOTE;
	}

	public String getPAY_INFO() {
		return PAY_INFO;
	}

	public void setPAY_INFO(String pAY_INFO) {
		PAY_INFO = pAY_INFO;
	}

	public String getOTHER_NOTE() {
		return OTHER_NOTE;
	}

	public void setOTHER_NOTE(String oTHER_NOTE) {
		OTHER_NOTE = oTHER_NOTE;
	}

	public String getOTHER_NOTE1() {
		return OTHER_NOTE1;
	}

	public void setOTHER_NOTE1(String oTHER_NOTE1) {
		OTHER_NOTE1 = oTHER_NOTE1;
	}

	public String getOTHER_NOTE2() {
		return OTHER_NOTE2;
	}

	public void setOTHER_NOTE2(String oTHER_NOTE2) {
		OTHER_NOTE2 = oTHER_NOTE2;
	}

	public Date getADDDATE() {
		return ADDDATE;
	}

	public void setADDDATE(Date aDDDATE) {
		ADDDATE = aDDDATE;
	}

	public Date getUPDATE() {
		return UPDATE;
	}

	public void setUPDATE(Date uPDATE) {
		UPDATE = uPDATE;
	}

	public String getADDUSERCD() {
		return ADDUSERCD;
	}

	public void setADDUSERCD(String aDDUSERCD) {
		ADDUSERCD = aDDUSERCD;
	}

	public String getUPDUSERCD() {
		return UPDUSERCD;
	}

	public void setUPDUSERCD(String uPDUSERCD) {
		UPDUSERCD = uPDUSERCD;
	}

	public int getCLIENT_FLG() {
		return CLIENT_FLG;
	}

	public void setCLIENT_FLG(int cLIENT_FLG) {
		CLIENT_FLG = cLIENT_FLG;
	}

	public int getCONTRA_FLG() {
		return CONTRA_FLG;
	}

	public void setCONTRA_FLG(int cONTRA_FLG) {
		CONTRA_FLG = cONTRA_FLG;
	}

	public int getPAY_FLG() {
		return PAY_FLG;
	}

	public void setPAY_FLG(int pAY_FLG) {
		PAY_FLG = pAY_FLG;
	}

	public int getHDY_FLG() {
		return HDY_FLG;
	}

	public void setHDY_FLG(int hDY_FLG) {
		HDY_FLG = hDY_FLG;
	}

	public int getCOMPANY_CD() {
		return COMPANY_CD;
	}

	public void setCOMPANY_CD(int cOMPANY_CD) {
		COMPANY_CD = cOMPANY_CD;
	}

	public int getDEL_FLG() {
		return DEL_FLG;
	}

	public void setDEL_FLG(int dEL_FLG) {
		DEL_FLG = dEL_FLG;
	}
	
	public Clmst getClmst() {
		return Clmst;
	}

	public void setClmst(Clmst clmst) {
		Clmst = clmst;
	}
	
}
