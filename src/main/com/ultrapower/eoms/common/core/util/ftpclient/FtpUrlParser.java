package com.ultrapower.eoms.common.core.util.ftpclient;

public class FtpUrlParser {
	
	//ftp://[<user>[:<password>]@]<host>[:<port>]/<url-path>
	//For example:
	//ftp://public.ftp-servers.example.com/mydirectory/myfile.txt

	//ftp://csp:tjcsp@10.142.78.70:21/WORKFLOW/11070700/1107070000002935/15822211188.xls
	
	private String ftpUrl;//FTP协议url串

	private String username="anonymous";//用户名
	private String password;//用户登录口令
	private String host;//FTP服务器主机
	private int port=21;//FTP服务器端口号
	private String urlPath;//FTP的URL路径 eg:  /flowwork/test/测试.doc
    private String direct;//FTP的目录 eg: flowwork/test
    private String filename;//文件名  测试.doc

	
	public String getDirect() {
		return direct;
	}

	public void setDirect(String direct) {
		this.direct = direct;
	}

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public FtpUrlParser(String ftpUrl){
		this.ftpUrl = ftpUrl;
	}
	
	public void parse() throws Exception{
		if (ftpUrl==null || ftpUrl.trim().equals("")){
			throw new Exception("请先设置初始化参数!");
		}
		if ( !ftpUrl.toLowerCase().startsWith("ftp://")){
			throw new Exception("FTP协议串错误!");
		}
		
		String urlTemp  = ftpUrl.substring(6);//去掉ftp://
		urlPath  = urlTemp.substring(urlTemp.indexOf("/"));//获取UrlPath
		direct=urlPath.substring(1,urlPath.lastIndexOf("/"));
		filename=urlPath.substring(urlPath.lastIndexOf("/")+1);
		String urlTemp0  = urlTemp.substring(0,urlTemp.indexOf("/"));
		String hostIp = "";
		String userpass  = "";
		if (urlTemp0.indexOf("@")>0){//有@表示有用户名和口令，需要解析
			hostIp  = urlTemp0.substring(urlTemp0.indexOf("@")+1);
			userpass  = urlTemp0.substring(0, urlTemp0.indexOf("@"));
			if (userpass.indexOf(":")<0){
				username = userpass;
				password = "";
			}else{
				username = userpass.split(":")[0];
				password = userpass.split(":")[1];
			}
			if (hostIp.indexOf(":")<0){
				host = hostIp;
				port = 21;
			}else{
				host = hostIp.split(":")[0];
				try {
					port = Integer.parseInt(hostIp.split(":")[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					throw new Exception("端口串格式错误!");
				}
			}
		} else {//不指定用户名口令
			username = "anonymous";//匿名
			password = "";
			hostIp  = urlTemp0;
			if (hostIp.indexOf(":")<0){
				host = hostIp;
				port = 21;
			}else{
				host = hostIp.split(":")[0];
				try {
					port = Integer.parseInt(hostIp.split(":")[1]);
				} catch (NumberFormatException e) {
					e.printStackTrace();
					throw new Exception("端口串格式错误!");
				}
			}
		}
	}

	public String getFtpUrl() {
		return ftpUrl;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	public String getUrlPath() {
		return urlPath;
	}

	public static void main(String[] args){
		String ftpUrl ="ftp://csp:tjcsp@10.142.78.70:21/WORKFLOW/11070700/1107070000002935/15822211188.xls";
//		String urlTemp  = ftpUrl.substring(6);
//		System.out.println(urlTemp);
//		
//		String urlPath  = urlTemp.substring(urlTemp.indexOf("/"));
//		System.out.println(urlPath);
//		String urlTemp0  = urlTemp.substring(0,urlTemp.indexOf("/"));
//		System.out.println(urlTemp0);
//		String hostIp  = urlTemp0.substring(urlTemp0.indexOf("@")+1);
//		String userpass  = urlTemp0.substring(0, urlTemp0.indexOf("@"));
//		System.out.println(userpass);
//		System.out.println(hostIp);
		
		FtpUrlParser parser = new FtpUrlParser(ftpUrl);
		try {
			parser.parse();
			System.out.println("Username="+parser.getUsername());
			System.out.println("Password="+parser.getPassword());
			System.out.println("Host="+parser.getHost());
			System.out.println("Port="+parser.getPort());
			System.out.println("UrlPath="+parser.getUrlPath());
			System.out.println("direct="+parser.getDirect());
			System.out.println("filename="+parser.getFilename());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
