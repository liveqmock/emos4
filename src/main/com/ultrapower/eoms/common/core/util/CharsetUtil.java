package com.ultrapower.eoms.common.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Map;

public class CharsetUtil {
	/**
	 * 将map的string值，以utf-8编码进行URLDecode.decode
	 */
	public static void urlDecode(Map<String,String> map){
		try {
			for(String s : map.keySet()){
				map.put(s, URLDecoder.decode(map.get(s), "utf-8"));
			}
		} catch (UnsupportedEncodingException e) {
			//will not happen
			e.printStackTrace();
		}
	}
}
