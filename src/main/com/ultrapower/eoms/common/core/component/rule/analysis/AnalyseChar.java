package com.ultrapower.eoms.common.core.component.rule.analysis;

public class AnalyseChar {

	public static boolean isSpace(final char character) 
	{
		if (character == ' ' || character == '\t' || character == '\n'
				|| character == '\r' || character == '\f') {
			return true;
		}
		return false;
	}
}
