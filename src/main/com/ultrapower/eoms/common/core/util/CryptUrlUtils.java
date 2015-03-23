package com.ultrapower.eoms.common.core.util;
/**
 * 由于在url上有些特殊字符会被自动转换，所以特写此类
 * @author SunHailong
 */
public class CryptUrlUtils {
	/**
	 * 加密明文
	 * @param data 明文
	 * @return
	 */
	public static String encode(String data) {
		if(data == null) {
			return data;
		}
		CryptUtils crypt = CryptUtils.getInstance();
		data = crypt.encode(data);
		data = data.replaceAll("+", "-");
		return data;
	}
	/**
	 * 解密密文
	 * @param data 密文
	 * @return
	 */
	public static String decode(String data) {
		if(data == null) {
			return data;
		}
		CryptUtils crypt = CryptUtils.getInstance();
		data = data.replaceAll("-", "+");
		data = crypt.decode(data);
		return data;
	}
}
