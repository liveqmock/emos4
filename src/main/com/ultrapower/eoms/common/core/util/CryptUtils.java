package com.ultrapower.eoms.common.core.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.IvParameterSpec;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class CryptUtils {

	private BASE64Encoder base64Encoder = new BASE64Encoder();
	private BASE64Decoder base64Decoder = new BASE64Decoder();
	// 设置密钥,最少为24位
	private static String DEFAULT_KEY = "com.ultrapower.eoms4.deckey";
	private static String DEFAULT_IV = "cryptpwd";
	private Cipher ENCRYPT_MODE_CIPHER;// 加密
	private Cipher DECRYPT_MODE_CIPHER;// 解密

	private CryptUtils() {
	}

	private CryptUtils(String key) {
		SecureRandom sr = new SecureRandom();
		// 从原始密匙数据创建一个DESKeySpec对象
		DESedeKeySpec dks;
		try {
			dks = new DESedeKeySpec(key.getBytes());
			// 创建一个密匙工厂，然后用它把DESKeySpec转换成一个SecretKey对象
			SecretKeyFactory keyFactory = SecretKeyFactory
					.getInstance("DESede");
			SecretKey skey = keyFactory.generateSecret(dks);
			// Cipher对象实际完成加密操作
			ENCRYPT_MODE_CIPHER = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			DECRYPT_MODE_CIPHER = Cipher.getInstance("DESede/CBC/PKCS5Padding");
			// 用密匙初始化Cipher对象
			IvParameterSpec iv = new IvParameterSpec(DEFAULT_IV.getBytes());
			ENCRYPT_MODE_CIPHER.init(Cipher.ENCRYPT_MODE, skey, iv, sr);
			DECRYPT_MODE_CIPHER.init(Cipher.DECRYPT_MODE, skey, iv, sr);
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
	}

	public static CryptUtils getInstance(String... key) {
		String s = null;
		if (key == null ||key.length==0 || key[0] == null || key[0].length() < 24) {
			s = DEFAULT_KEY;
		} else {
			s = key[0];
		}
		CryptUtils instance = new CryptUtils(s);
		return instance;
	}

	/**
	 * 加密明文
	 * 
	 * @param data
	 *            明文
	 * @return 密文
	 * @throws Exception
	 */
	public synchronized String encode(String data) {
		if (data == null) {
			data = "";
		}
		byte[] pasByte = null;
		try {
			pasByte = ENCRYPT_MODE_CIPHER.doFinal(data.getBytes("utf-8"));
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return base64Encoder.encode(pasByte);
	}

	/**
	 * 解密密文
	 * 
	 * @param data
	 *            密文
	 * @return 明文
	 * @throws Exception
	 */
	public synchronized String decode(String data) {
		if (data == null || "".equals(data.trim())) {
			return "";
		}
		byte[] pasByte = null;
		String txt = null;
		try {
			pasByte = DECRYPT_MODE_CIPHER.doFinal(base64Decoder
					.decodeBuffer(data));
			txt = new String(pasByte, "UTF-8");
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return txt;
	}

	/**
	 * 将明文按base64进行编码
	 * 
	 * @param data
	 *            明文
	 * @return 将明文按base64进行编码后的密文
	 */
	public String base64Encode(String data) {
		if (data == null) {
			return "";
		}
		return base64Encoder.encode(data.getBytes());
	}

	/**
	 * 将明文按base64进行编码的密文转换为明文
	 * 
	 * @param data
	 *            base64密文
	 * @return 明文
	 */
	public String base64Decode(String data) {
		if (data == null) {
			return "";
		}
		byte[] dByte = null;
		try {
			dByte = base64Decoder.decodeBuffer(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(dByte);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		CryptUtils cp = null;
		String key = "";
		if (args.length < 2) {
			System.out.println("Usage:");
			System.out
					.println("java com.ultrapower.eoms.common.util.CryptUtils txt mode key");
			System.out.println("txt is required");
			System.out.println("mode is required,1 is encode,2 is decode");
			System.out
					.println("key is optional,the min length of key is 24 char");
			System.out
					.println("Example:java com.ultrapower.eoms.common.util.CryptUtils CryptUtils password 1 105269288E76C311410B6595");
			cp = CryptUtils.getInstance();
			return;
		}
		if (args.length > 2) {
			key = args[2];
			cp = CryptUtils.getInstance(null);
		} else {
			cp = CryptUtils.getInstance(null);
		}
		String txt = args[0];
		if ("\"\"".equals(txt)) {
			txt = "";
		}
		String mode = args[1];
		String result = null;
		if ("1".equals(mode)) {
			result = cp.encode(txt);
		} else if ("2".equals(mode)) {
			result = cp.decode(null);
		} else {
			System.out.println("mode  must be multiple 1 or 2");
			return;
		}
		System.out.println("Result:" + result);
	}
}
