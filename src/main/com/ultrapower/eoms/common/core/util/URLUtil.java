package com.ultrapower.eoms.common.core.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
/**
 * 网页链接内容获取
 * @author zhuzhaohui
 */
public class URLUtil {   
  
  /**
   * 通过网页链接获取链接网页的内容
   * @param urlString 要进行访问获取的链接地址 eg:http://www.enters.com.cn
   * @return 返回该网页的内容 or null
   */
  public static String getHtml(String urlString) {   
    try {   
      StringBuffer html = new StringBuffer();   
      URL url = new URL(urlString);   
      HttpURLConnection conn = (HttpURLConnection) url.openConnection();   
      InputStreamReader isr = new InputStreamReader(conn.getInputStream());   
      BufferedReader br = new BufferedReader(isr);   
      String temp;   
      while ((temp = br.readLine()) != null) {   
        html.append(temp).append("\n");   
      }   
      br.close();   
      isr.close();   
      return html.toString();   
    } catch (Exception e) {   
      e.printStackTrace();   
      return null;   
    }   
  }     
}  
