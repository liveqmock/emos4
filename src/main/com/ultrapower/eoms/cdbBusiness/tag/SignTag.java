package com.ultrapower.eoms.cdbBusiness.tag;

import java.io.IOException;
import java.io.StringWriter;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.ultrapower.eoms.common.core.util.TimeUtils;

public class SignTag extends SimpleTagSupport{
	private String basestatus;
	private Long basedealouttime;
	private Long basefinishdate;
	private Long baseacceptouttime;
	private Long baseacceptdate;
	private String imgSrc;
	
	StringWriter sw = new StringWriter();
	@Override
	public void doTag() throws JspException, IOException {
		JspWriter out = getJspContext().getOut();
		String title = "";
		String imgName = "";
		if(basestatus.equals("已作废")){
			//donothing
		}else{
			if(basestatus.equals("已关闭")&&basedealouttime.equals(0l)){
				imgName = "green.png";
			}else{
				long currentTime = TimeUtils.getCurrentTime();
				if(!basedealouttime.equals(0l)&&!baseacceptdate.equals(0l)){//处理环节
					if(!basefinishdate.equals(0l)){//已处理完成
						if(basefinishdate > basedealouttime){
							title = "超时"; imgName = "red.png";
						}else{
							title = "未超时"; imgName = "green.png";
						}
					}else{//未处理完成
						if(currentTime > basedealouttime){
							title = "超时"; imgName = "yellow.png";
						}else{
							title = "未超时"; imgName = "gray.png";
						}
					}
				}
				else if(baseacceptdate.equals(0l)&&!basedealouttime.equals(0l)){//进入处理环节之后退回,再提交
					if(currentTime > baseacceptouttime){
						title = "超时"; imgName = "yellow.png";
					}else{
						title = "未超时"; imgName = "gray.png";
					}
				}
				else{//受理环节
					if(!baseacceptouttime.equals(0l)){//已受理
						if(currentTime > baseacceptouttime){
							title = "超时"; imgName = "yellow.png";
						}else{
							title = "未超时"; imgName = "gray.png";
						}
					}else{//未提交到受理
						title = "未超时"; imgName = "gray.png";
					}
				}
			}
			out.print("<img src=\""+imgSrc+imgName+"\" title=\""+title+"\" />");
		}
	}
	public void setBasestatus(String basestatus) {
		this.basestatus = basestatus;
	}
	public void setBasedealouttime(Long basedealouttime) {
		this.basedealouttime = basedealouttime;
	}
	public void setBasefinishdate(Long basefinishdate) {
		this.basefinishdate = basefinishdate;
	}
	public void setBaseacceptouttime(Long baseacceptouttime) {
		this.baseacceptouttime = baseacceptouttime;
	}
	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}
	public void setBaseacceptdate(Long baseacceptdate) {
		this.baseacceptdate = baseacceptdate;
	}
	
}
