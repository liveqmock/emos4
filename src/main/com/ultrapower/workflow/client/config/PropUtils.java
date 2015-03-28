package com.ultrapower.workflow.client.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Properties;

public class PropUtils
{
  private static Properties prop = new Properties();

  static {
    init();
  }

  public static String getProp(String key)
  {
    return prop.getProperty(key);
  }

  public static void init() {
    try {
      System.out.println("开始加载 wfconfig.properties");
      InputStream is = PropUtils.class.getResourceAsStream("/wfconfig.properties");
      if (is != null) {
        System.out.println("加载路径 /wfconfig.properties");
      } else {
        is = new FileInputStream("wfconfig.properties");
        if (is != null) {
          System.out.println("加载路径 wfconfig.properties");
        }
      }
      System.out.println("wfconfig.properties 是否加载成功:" + (is != null));
      prop.load(is);
      System.out.println("Url :: " + prop.getProperty("Url"));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    init();
  }
}