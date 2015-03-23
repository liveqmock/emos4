package com.ultrapower.eoms.common.core.component.sms.model;

public class ReturnMsg {
	private String RTS;
	private String SrvNo;
	private String SrcSys;
	private String DestSys;
	private String Msg;
	private String RetMsg;
	private String BizType;
	private String StdCode;
	private String StdDesc;

	public String getRTS() {
		return RTS;
	}

	public void setRTS(String rts) {
		RTS = rts;
	}

	public String getSrvNo() {
		return SrvNo;
	}

	public void setSrvNo(String srvNo) {
		SrvNo = srvNo;
	}

	public String getSrcSys() {
		return SrcSys;
	}

	public void setSrcSys(String srcSys) {
		SrcSys = srcSys;
	}

	public String getDestSys() {
		return DestSys;
	}

	public void setDestSys(String destSys) {
		DestSys = destSys;
	}

	public String getMsg() {
		return Msg;
	}

	public void setMsg(String msg) {
		Msg = msg;
	}

	public String getRetMsg() {
		return RetMsg;
	}

	public void setRetMsg(String retMsg) {
		RetMsg = retMsg;
	}

	public String getBizType() {
		return BizType;
	}

	public void setBizType(String bizType) {
		BizType = bizType;
	}

	public String getStdCode() {
		return StdCode;
	}

	public void setStdCode(String stdCode) {
		StdCode = stdCode;
	}

	public String getStdDesc() {
		return StdDesc;
	}

	public void setStdDesc(String stdDesc) {
		StdDesc = stdDesc;
	}

}
