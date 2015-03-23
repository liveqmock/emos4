/*
 * Copyright 2006-2007 original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.ultrapower.eoms.common.plugin.ecside.filter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.ultrapower.eoms.common.plugin.ecside.common.log.LogHandler;
import com.ultrapower.eoms.common.plugin.ecside.core.ECSideConstants;
import com.ultrapower.eoms.common.plugin.ecside.core.ECSideContext;
import com.ultrapower.eoms.common.plugin.ecside.core.Preferences;
import com.ultrapower.eoms.common.plugin.ecside.core.TableConstants;
import com.ultrapower.eoms.common.plugin.ecside.core.TableModelUtils;
import com.ultrapower.eoms.common.plugin.ecside.core.context.ContextUtils;
import com.ultrapower.eoms.common.plugin.ecside.core.context.HttpServletRequestContext;
import com.ultrapower.eoms.common.plugin.ecside.core.context.ServletInitContext;
import com.ultrapower.eoms.common.plugin.ecside.core.context.WebContext;
import com.ultrapower.eoms.common.plugin.ecside.easyda.DataAccessModel;
import com.ultrapower.eoms.common.plugin.ecside.easyda.DataExportModel;
import com.ultrapower.eoms.common.plugin.ecside.easylist.AbstractEasyListModel;
import com.ultrapower.eoms.common.plugin.ecside.easylist.EasyDataAccessHandler;
import com.ultrapower.eoms.common.plugin.ecside.preferences.PreferencesConstants;
import com.ultrapower.eoms.common.plugin.ecside.preferences.TableProperties;
import com.ultrapower.eoms.common.plugin.ecside.resource.MimeUtils;
import com.ultrapower.eoms.common.plugin.ecside.util.ECSideUtils;
import com.ultrapower.eoms.common.plugin.ecside.util.ExceptionUtils;
import com.ultrapower.eoms.common.plugin.ecside.util.RequestUtils;
import com.ultrapower.eoms.common.plugin.ecside.view.ViewResolver;



/**
 * @author Wei Zijun
 *
 */

public class ECSideFilter implements Filter {

	protected static Log logger = LogFactory.getLog(ECSideFilter.class);

	protected static String encoding = ECSideConstants.DEFAULT_ENCODING;
	protected static boolean useEncoding = true;
	protected static boolean useEasyDataAccess = true;
	protected static boolean responseHeadersSetBeforeDoFilter=true;

		
	public String servletRealPath;
	public ServletContext servletContext;
	
	protected FilterConfig filterConfig = null;
	protected EasyDataAccessHandler easyDataAccessHandler;

    public void init(FilterConfig filterConfig) throws ServletException {
		this.filterConfig = filterConfig;
    	servletContext=filterConfig.getServletContext();
    	servletRealPath=servletContext.getRealPath("/");

    	initEncoding();
    	initEasyList();
    	initProperties();

        
    	String responseHeadersSetBeforeDoFilter = filterConfig.getInitParameter("responseHeadersSetBeforeDoFilter");
        if (StringUtils.isNotBlank(responseHeadersSetBeforeDoFilter)) {
        	ECSideFilter.responseHeadersSetBeforeDoFilter = Boolean.valueOf(responseHeadersSetBeforeDoFilter).booleanValue();
        }

    }
    public void initProperties() throws ServletException {
    	WebContext context=new ServletInitContext(servletContext);
        Preferences preferences = new TableProperties();
        preferences.init(context, TableModelUtils.getPreferencesLocation(context));
        
        ECSideContext.DEFAULT_PAGE_SIZE = new Integer(preferences.getPreference(PreferencesConstants.TABLE_ROWS_DISPLAYED)).intValue();
        
    }
    public void initEasyList() throws ServletException {
    	useEasyDataAccess=true;
    	String useEasyDataAccessC = filterConfig.getInitParameter("useEasyDataAccess");
    	if ("off".equalsIgnoreCase(useEasyDataAccessC)||"false".equalsIgnoreCase(useEasyDataAccessC)
    			|| "no".equals(useEasyDataAccessC)|| "0".equals(useEasyDataAccessC) ){
    		useEasyDataAccess=false;
    	}
    	easyDataAccessHandler=new EasyDataAccessHandler();
    }
    
   
	public void initEncoding() throws ServletException {
		String encodingValue = filterConfig.getInitParameter("encoding");
		String useEncodingC = filterConfig.getInitParameter("useEncoding");
			
		if (encodingValue!=null && encodingValue.length()>0){
			encoding=encodingValue; 
		}
		if (useEncodingC == null|| useEncodingC.equalsIgnoreCase("true") 
				|| useEncodingC.equalsIgnoreCase("yes") || useEncodingC.equalsIgnoreCase("on")
				||useEncodingC.equalsIgnoreCase("1")){
			useEncoding = true;
		}else{ 
			useEncoding = false;
		}
		
		ECSideContext.ENCODING=encoding;

	}
    
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (RequestUtils.isAJAXRequest(request)) {
        	doAjaxFilter(request,response,chain);
	        return;
		}

        doEncoding(request, response);
        
        String easyListName=getEasyList(request);
        String easyDataAccessName=getEasyDataAccess(request);
        String easyDataExport=getEasyDataExport(request);
		
		WebContext context = new HttpServletRequestContext(request);
		boolean isExported = ExportFilterUtils.isExported(context);
		

		if (isExported) {
			doExportFilter(request,response,chain,context);
			context=null;
			return;
		}
		
		if (StringUtils.isNotBlank(easyDataExport)){
			String sqlName=getSqlName(request);
			String fileName=getExportFileName(request);
			String type=fileName.substring(fileName.indexOf('.')+1);
			try {
			 setResponseHeaders(request,response, fileName);

//				response.setBufferSize(8192);
				easyDataAccessHandler.exportList(getDataExportModelBean(request,easyDataExport), sqlName,type, request, response);
//				response.flushBuffer();
				response.getOutputStream().flush();
	            response.getOutputStream().close();
			}catch(Exception e){
				LogHandler.errorLog(logger,e);				
			}
			return;
		}
		
		if (StringUtils.isNotBlank(easyListName)){
			easyDataAccessHandler.easyList(request,response,getEasyListModelBean(request,easyListName));
		}else if (StringUtils.isNotBlank(easyDataAccessName)){
			String sqlName=getSqlName(request);
			easyDataAccessHandler.dataAccess(getDataAccessModelBean(request,easyDataAccessName), sqlName, request, response);
		}
		chain.doFilter(request, response);

    }
    
	
	public void doEncoding(ServletRequest request, ServletResponse response) throws IOException, ServletException {
		if (useEncoding || (request.getCharacterEncoding() == null)) {
			if (encoding != null)
				//Set the same character encoding for the request and the response  
				request.setCharacterEncoding(encoding);
//				response.setCharacterEncoding(encoding);
		}
	}
	
    
    public void doAjaxFilter(HttpServletRequest request,HttpServletResponse response,FilterChain chain)throws IOException, ServletException{
    	
		request.setCharacterEncoding("UTF-8");
//		response.setCharacterEncoding("UTF-8");
		
        String easyListName=getEasyList(request);
        String easyDataAccessName=getEasyDataAccess(request);

		String findAjaxZoneAtClient=request.getParameter(TableConstants.AJAX_FINDZONE_CLIENT);
		if (findAjaxZoneAtClient!=null && ("false".equalsIgnoreCase(findAjaxZoneAtClient)||"0".equals(findAjaxZoneAtClient))) {
			ECSideAjaxResponseWrapper bufferResponseWrapper = new ECSideAjaxResponseWrapper(response);
			try {

				String ectableId = request.getParameter(TableConstants.EXTREME_COMPONENTS_INSTANCE);
				if (easyListName!=null){
					easyDataAccessHandler.easyList(request,bufferResponseWrapper,getEasyListModelBean(request,easyListName));
				}else if (easyDataAccessName!=null){
					String sqlName=getSqlName(request);
					easyDataAccessHandler.dataAccess(getDataAccessModelBean(request,easyDataAccessName), sqlName, request, response);
				}
				chain.doFilter(request, bufferResponseWrapper);
				String zone = bufferResponseWrapper.findSubstring(
						ECSideUtils.getAjaxBegin(ectableId), ECSideUtils.getAjaxEnd(ectableId));
				HttpServletResponse originalResponse = bufferResponseWrapper.getOriginalResponse();
				if (zone!=null){
					originalResponse.getOutputStream().write(zone.getBytes("UTF-8"));
				}
//				originalResponse.flushBuffer();
				originalResponse.getOutputStream().flush();
				originalResponse.getOutputStream().close();
			} catch (Exception e) {
				LogHandler.errorLog(logger, e);
				response.setContentType("text/html;charset=UTF-8");
				response.getWriter().println("Exception:<br />\n"+ExceptionUtils.formatStackTrace(e).replaceAll("\n","<br/>\n"));
				response.getWriter().close();
				
			}
		} else {
			if (easyListName!=null){
				easyDataAccessHandler.easyList(request,response,getEasyListModelBean(request,easyListName));
			}else if (easyDataAccessName!=null){
				String sqlName=getSqlName(request);
				easyDataAccessHandler.dataAccess(getDataAccessModelBean(request,easyDataAccessName), sqlName, request, response);
			}
			chain.doFilter(request, response);
		}
    }
    
    public void doExportFilter(HttpServletRequest request,HttpServletResponse response,FilterChain chain,WebContext context)throws IOException, ServletException{
    	String easyListName=getEasyList(request);
    	String exportFileName = ExportFilterUtils.getExportFileName(context);
		boolean isPrint="_print_".equals(exportFileName);

		try{
				if (isPrint){
					if (easyListName!=null){
						easyDataAccessHandler.easyList(request,response,getEasyListModelBean(request,easyListName));
					}
					chain.doFilter(request, response);
				}else {
					request.setAttribute(ContextUtils.RESPONSE_OUTPUTSTREAM_KEY, response.getOutputStream());
					HttpServletResponseWrapper responseWrapper=new ExportResponseWrapper(response);
		//	    	if (responseHeadersSetBeforeDoFilter) {
			            setResponseHeaders(request,response, exportFileName);
		//	        }
					if (easyListName!=null){
						easyDataAccessHandler.easyList(request,responseWrapper,getEasyListModelBean(request,easyListName));
					}
					
					chain.doFilter(request, responseWrapper);
					responseWrapper=null;
		//			if (!responseHeadersSetBeforeDoFilter ) {
		//	            setResponseHeaders(request,response, exportFileName);
		//	        }
		
				}
				handleExport(request, response, context);
		}catch(IOException e){
			throw e;
		}finally{
			//request.setAttribute(ContextUtils.RESPONSE_OUTPUTSTREAM_KEY, "".intern());
		}
		
    }
    
    
    public void handleExport(HttpServletRequest request, HttpServletResponse response, WebContext context) {
        try {
            Object viewData = request.getAttribute(TableConstants.VIEW_DATA);
            if (viewData != null) {
                Preferences preferences = new TableProperties();
                preferences.init(null, TableModelUtils.getPreferencesLocation(context));
                String viewResolver = (String) request.getAttribute(TableConstants.VIEW_RESOLVER);
                Class classDefinition = Class.forName(viewResolver);
                ViewResolver handleExportViewResolver = (ViewResolver) classDefinition.newInstance();
                request.setAttribute(TableConstants.SERVLET_REAL_PATH, servletRealPath);
                handleExportViewResolver.resolveView(request, response, preferences, viewData);
//   			response.flushBuffer();
                if (!response.isCommitted()){
                	response.getOutputStream().flush();
                	response.getOutputStream().close();
                }
            }
        } catch (Exception e) {
        	LogHandler.errorLog(logger, e);
        }
    }

    
    public void setResponseHeaders(HttpServletRequest request,HttpServletResponse response, String exportFileName) {
        String mimeType = MimeUtils.getFileMimeType(exportFileName);
//        response.reset(); 
        if (StringUtils.isNotBlank(mimeType)) {
            response.setContentType(mimeType);
        }
//        response.setHeader("Content-Type",mimeType);
        try {
        	 exportFileName= RequestUtils.encodeFileName(request,exportFileName);

	    } catch (UnsupportedEncodingException e) {
				String errorMessage = "Unsupported response.getCharacterEncoding() [" + "UTF-8" + "].";
				LogHandler.errorLog(logger,"TDExportFilter.setResponseHeaders() - " + errorMessage);
		}
	    //ec:
        response.setHeader("Content-Disposition", "attachment;filename=\"" + exportFileName + "\"");
        response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        // end ec;
        
//        response.setHeader("Content-Transfer-Encoding","binary");
//        response.setHeader("Cache-Control","private");
//        response.setHeader("Pragma", "private");
        response.setDateHeader("Expires", (System.currentTimeMillis() + 1000));

    }

    
    
    // TODO : 
    protected void webProxy(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/xml");
		PrintWriter out = response.getWriter();
		URL url = new URL(request.getParameter("url"));
		BufferedReader bf = new BufferedReader(new InputStreamReader(url
				.openStream()));
		String line;
		while ((line = bf.readLine()) != null) {
			out.println(line);
		}
		bf.close();
	} 
    
    
    public String getEasyList(ServletRequest servletRequest){
    	String easyDataAccess=null;
    	if (useEasyDataAccess){
    		try{
    			easyDataAccess=servletRequest.getParameter(ECSideConstants.EASY_DATA_LIST_FLAG);
    		}catch(Exception e){
    			easyDataAccess=null;
    		}
    	}
     	return StringUtils.isNotBlank(easyDataAccess)?easyDataAccess:null;
    }

    public String getEasyDataExport(ServletRequest servletRequest){
    	String easyDataAccess=null;
    	if (useEasyDataAccess){
    		easyDataAccess=servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FLAG);
    	}
		if (StringUtils.isNotBlank(easyDataAccess) && easyDataAccess.indexOf('.')>0){
			easyDataAccess=easyDataAccess.substring(0,easyDataAccess.indexOf('.'));
		}
     	return StringUtils.isNotBlank(easyDataAccess)?easyDataAccess:null;
    }
    
    
    public String getEasyDataAccess(ServletRequest servletRequest){
    	String easyDataAccess=null;
    	if (useEasyDataAccess){
    		easyDataAccess=servletRequest.getParameter(ECSideConstants.EASY_DATA_ACCESS_FLAG);
    	}
		if (StringUtils.isNotBlank(easyDataAccess) && easyDataAccess.indexOf('.')>0){
			easyDataAccess=easyDataAccess.substring(0,easyDataAccess.indexOf('.'));
		}
     	return StringUtils.isNotBlank(easyDataAccess)?easyDataAccess:null;
    }
    
    
    public static String getExportFileName(ServletRequest servletRequest){
    	String fileName=servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FILENAME);
    	if (StringUtils.isBlank(fileName)){
    		String eda=servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FLAG);
    		if (eda.indexOf('.')>0){
    			fileName=eda.substring(eda.indexOf('.')+1);
    		}
    	}
     	return StringUtils.isNotBlank(fileName)?fileName:null;
    }
    
    public static String getSqlName(ServletRequest servletRequest){
    	String sqlName=servletRequest.getParameter(ECSideConstants.EASY_DATA_ACCESS_SQLNAME);
    	if (StringUtils.isBlank(sqlName)){
    		String eda=servletRequest.getParameter(ECSideConstants.EASY_DATA_ACCESS_FLAG);
    		if (StringUtils.isBlank(eda)){
    			eda=servletRequest.getParameter(ECSideConstants.EASY_DATA_EXPORT_FLAG);
    		}
    		if (StringUtils.isNotBlank(eda) && eda.indexOf('.')>0){
    			sqlName=eda.substring(eda.indexOf('.')+1);
    		}
    	}
     	return StringUtils.isNotBlank(sqlName)?sqlName:null;
    }
    
	public AbstractEasyListModel getEasyListModelBean(HttpServletRequest request,String beanName){
    	return (AbstractEasyListModel)getBean(request,beanName);
    }
    
    public DataAccessModel getDataAccessModelBean(HttpServletRequest request,String beanName){
    	return (DataAccessModel)getBean(request,beanName);
    }
	
    public DataExportModel getDataExportModelBean(HttpServletRequest request,String beanName){
    	return (DataExportModel)getBean(request,beanName);
    }
    
    public Object getBean(HttpServletRequest request,String beanName){
    	return ExportFilterUtils.getBean(request, beanName);
    }
    
	public void destroy() {

		this.filterConfig = null;

	}

    
}
