package com.ultrapower.eoms.ultrabpp.model;

public interface BaseFieldDraw<F extends BaseField>
{
	public String doStartTag(F field);
	
	public String doEndTag(F field);
}
