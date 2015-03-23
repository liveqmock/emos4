package com.ultrapower.eoms.common.plugin.ecside.easydataaccess;

import java.util.Map;


public class SqlSnippet {
	
	
	public String snippet;
	public String condition;
	public String content;
	public int start;
	public int end;
	
	public String type;
	
	public String condition_before;
	public String condition_c;
	public String condition_after;
	
	public Object value;
	
	public boolean need=false;
	public SqlSnippet() {

	}
	
	@Override
	public String toString(){
		StringBuffer bufs=new StringBuffer();
		bufs.append("===== ").append(this.getClass().getName()).append(" =====\n");
		bufs.append("snippet : ").append(snippet).append("\n");
		bufs.append("condition : ").append(condition).append("\n");
		bufs.append("condition_ex : ").append(condition_before).append(condition_c).append(condition_after).append("\n");
		bufs.append("content : ").append(content).append("\n");
		bufs.append("start : ").append(start).append("\n");
		bufs.append("end : ").append(end).append("\n");
		return bufs.toString();
	}
	
	public void init(){
		if (condition.indexOf(EasyDataAccessConstants.NOTEQUAL_S)>0){
			condition_c=EasyDataAccessConstants.NOTEQUAL_S;
		}else if (condition.indexOf(EasyDataAccessConstants.LESSEQUAL_S)>0){
			condition_c=EasyDataAccessConstants.LESSEQUAL_S;
		}else if (condition.indexOf(EasyDataAccessConstants.GREATEQUAL_S)>0){
			condition_c=EasyDataAccessConstants.GREATEQUAL_S;
		}else if (condition.indexOf(EasyDataAccessConstants.EQUAL_S)>0){
			condition_c=EasyDataAccessConstants.EQUAL_S;
		}else if (condition.indexOf(EasyDataAccessConstants.LESS_S)>0){
			condition_c=EasyDataAccessConstants.LESS_S;
		}else if (condition.indexOf(EasyDataAccessConstants.GREAT_S)>0){
			condition_c=EasyDataAccessConstants.GREAT_S;
		}
		
		String[] ba=condition.split(condition_c);
		condition_before=ba[0];
		condition_after=ba[1];

	}
	
	public boolean check(Map map){
		String type="string";
		value=map.get(condition_before);
		
		String beforeS=(String)value;
		String afterS=condition_after;
		

		long beforeL=-1;
		long afterL=-1;

		if (condition_after.indexOf("'")<0 && condition_after.indexOf("\"")<0){
			try{
				afterL=new Long(condition_after).longValue();
				type="number";
			}catch(Exception e){
				type="string";
			}
		}
		if ("number".equalsIgnoreCase(type)){
			try{
				beforeL=new Long(beforeS).longValue();
				type="number";
			}catch(Exception e){
				type="string";
			}
		}
		
		boolean need=false;
		this.type=type;
		
		
		if ("number".equalsIgnoreCase(type)){
			if (condition.indexOf(EasyDataAccessConstants.NOTEQUAL_S)>0){
				need=EasyDataAccessUtil.isNotEquals(beforeL,afterL);
			}else if (condition.indexOf(EasyDataAccessConstants.LESSEQUAL_S)>0){
				need=EasyDataAccessUtil.isLessThenE(beforeL,afterL);
			}else if (condition.indexOf(EasyDataAccessConstants.GREATEQUAL_S)>0){
				need=EasyDataAccessUtil.isGreatThenE(beforeL,afterL);
			}else if (condition.indexOf(EasyDataAccessConstants.EQUAL_S)>0){
				need=EasyDataAccessUtil.isEquals(beforeL,afterL);
			}else if (condition.indexOf(EasyDataAccessConstants.LESS_S)>0){
				need=EasyDataAccessUtil.isLessThen(beforeL,afterL);
			}else if (condition.indexOf(EasyDataAccessConstants.GREAT_S)>0){
				need=EasyDataAccessUtil.isGreatThen(beforeL,afterL);
			}
		}else{
			if (condition.indexOf(EasyDataAccessConstants.NOTEQUAL_S)>0){
				if (afterS.equalsIgnoreCase(EasyDataAccessConstants.C_EMPTY)){
					need=EasyDataAccessUtil.isNotEmpty(beforeS);
				}else{
					if (afterS.equalsIgnoreCase(EasyDataAccessConstants.C_NULL)){
						afterS=null;
					}
					need=EasyDataAccessUtil.isNotEquals(beforeS,afterS);
				}
			}else if (condition.indexOf(EasyDataAccessConstants.LESSEQUAL_S)>0){
				need=EasyDataAccessUtil.isLessThenE(beforeS,afterS);
			}else if (condition.indexOf(EasyDataAccessConstants.GREATEQUAL_S)>0){
				need=EasyDataAccessUtil.isGreatThenE(beforeS,afterS);
			}else if (condition.indexOf(EasyDataAccessConstants.EQUAL_S)>0){
				if (afterS.equalsIgnoreCase(EasyDataAccessConstants.C_EMPTY)){
					need=EasyDataAccessUtil.isEmpty(beforeS);
				}else{
					if (afterS.equalsIgnoreCase(EasyDataAccessConstants.C_NULL)){
						afterS=null;
					}
					need=EasyDataAccessUtil.isEquals(beforeS,afterS);
				}
			}else if (condition.indexOf(EasyDataAccessConstants.LESS_S)>0){
				need=EasyDataAccessUtil.isLessThen(beforeS,afterS);
			}else if (condition.indexOf(EasyDataAccessConstants.GREAT_S)>0){
				need=EasyDataAccessUtil.isGreatThen(beforeS,afterS);
			}
		}
		this.need=need;
		return need;
	}
}
