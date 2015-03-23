package com.ultrapower.eoms.workflow.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;

public class WorkflowUtils {

	/**
	 * 是否AR流程
	 * @param baseSchema
	 * @return
	 */
	public static boolean isARflow(String baseSchema) {
		boolean isAR = false;
		if (StringUtils.isNotBlank(baseSchema) && (baseSchema.startsWith("WF4:") || baseSchema.startsWith("WF:"))) {
			isAR = true;
		}
		return isAR;
	}
	
	public static String unRepeat(String s1, String s2) {
		String[] s1Ary = null;
		String[] s2Ary = null;
		Set<String> set = new HashSet<String>();
		if (StringUtils.isNotBlank(s1)) {
			s1Ary = s1.split(";");
		}
		if (StringUtils.isNotBlank(s2)) {
			s2Ary = s2.split(";");
		}
		if (s1Ary != null) {
			for (int i = 0; i < s1Ary.length; i++) {
				if (StringUtils.isNotBlank(s1Ary[i])) {
					set.add(s1Ary[i]);
				}
			}
		}
		if (s2Ary != null) {
			for (int i = 0; i < s2Ary.length; i++) {
				if (StringUtils.isNotBlank(s2Ary[i])) {
					set.add(s2Ary[i]);
				}
			}
		}
		return StringUtils.join(set, ";");
	}
}
