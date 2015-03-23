package com.ultrapower.eoms.ftrmaintain.manager;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.ultrapower.eoms.ftrmaintain.model.SysParam;
import com.ultrapower.eoms.ftrmaintain.service.IndexManagerService;

public class IndexManagerImpl implements IndexManagerService {

	public List<SysParam> getSystemParam() {
//		InputStream in = this.getClass().getResourceAsStream("/full-text-retrieval.properties");
		InputStream in = null;
		try {
		File proFile = new File(this.getClass().getResource("/full-text-retrieval.properties").toURI());
			in = new FileInputStream(proFile);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		List<SysParam> param = new ArrayList<SysParam>();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String key;
		String value;
		String desc = null;
		String content = null;
		int local;
		while(true)
		{
			key = null;
			value = null;
			try {
				if(desc==null)
					desc = reader.readLine();
				if(desc==null)
					break;
				if(content==null)
					content = reader.readLine();
				if(content==null)
					break;
				if(content.startsWith("#"))
				{
					desc = content;
					content = null;
				}
				if(desc!=null && content!=null)
				{
					local = content.indexOf("=");
					key = content.substring(0,local);
					if(content.length()>local+1)
					{
						value = content.substring(local+1);
						value = value.replaceAll("<", "&lt;");
						value = value.replaceAll(">", "&gt;");
					}
					param.add(new SysParam(key,value,unicodeToString(desc.substring(1))));
					desc = null;
					content = null;
				}
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("-----全文检索参数配置文件有误-----");
			}
		}
		try {
			if(in!=null)
				in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return param;
	}
	
	/**
	 * 将unicode转换成中文
	 * @param str
	 * @return
	 */
	public static String unicodeToString(String str) {
        Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");    
        Matcher matcher = pattern.matcher(str);
        char ch;
        while (matcher.find()) {
            ch = (char) Integer.parseInt(matcher.group(2), 16);
            str = str.replace(matcher.group(1), ch + "");    
        }
        return str;
    }
	
	/**
	 * 把中文字符转换成unicode
	 * @param s
	 * @return
	 */
	public static String stringToUnicode(String s) {
		String str = "";
		for (int i = 0; i < s.length(); i++) {
			int ch = (int) s.charAt(i);
			if (ch > 255)
				str += "\\u" + Integer.toHexString(ch);
			else
 				str += "\\" + Integer.toHexString(ch);
		}
		return str;
	}
}
