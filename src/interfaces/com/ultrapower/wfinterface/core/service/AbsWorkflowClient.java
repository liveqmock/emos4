package com.ultrapower.wfinterface.core.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.ultrapower.eoms.common.core.component.data.DataAdapter;
import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.core.component.data.DataTable;
import com.ultrapower.eoms.common.core.component.data.QueryAdapter;
import com.ultrapower.eoms.common.core.component.rquery.util.SqlReplace;
import com.ultrapower.eoms.common.core.util.WebApplicationManager;
import com.ultrapower.eoms.common.core.util.fileutil.FileOperUtil;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.ultrasm.model.Attachment;
import com.ultrapower.eoms.ultrasm.service.AttachmentManagerService;
import com.ultrapower.eoms.workflow.sheet.attachment.model.WfAttachment;
import com.ultrapower.eoms.workflow.sheet.attachment.service.WfAttachmentService;
import com.ultrapower.wfinterface.RecordLog;
import com.ultrapower.wfinterface.core.model.ConfigClient;
import com.ultrapower.wfinterface.core.model.WfiDataout;
import com.ultrapower.wfinterface.core.model.WfiTempAttachment;
import com.ultrapower.wfinterface.core.util.Constants;

public abstract class AbsWorkflowClient implements IWorkflowClient
{
	protected IWFInterfaceConfig wfInterfaceConfig;
	protected WfiDataoutService wfiDataoutService;
	protected IServiceCaller wfInterfaceServiceCaller;
	private WfAttachmentService wfAttachmentImpl;
	private AttachmentManagerService attachmentManagerService;
	
	protected WfiDataout clientContext;
	protected DataTable dataTable;
	protected ConfigClient configClient;
	protected List<WfiTempAttachment> attachmentsList;
	protected String result = null;
	
	private String serviceClassName = getClass().getName();
	
	public String call() throws Exception
	{
		try
		{
			wfInterfaceConfig = (IWFInterfaceConfig)WebApplicationManager.getBean("wfInterfaceConfig");
			wfiDataoutService = (WfiDataoutService)WebApplicationManager.getBean("wfiDataoutService");
		}
		catch (Exception e)
		{
			return "ERROR:系统错误！";
		}
		
		configClient = wfInterfaceConfig.getClientMap().get(serviceClassName);
		
		try
		{
			result = mainHandle();
		}
		catch (Exception e)
		{
			result = e.getMessage();
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	private String mainHandle() throws Exception
	{
		//根据querysql获取数据
		try
		{
			String querySql = configClient.getQuerySql();
			//System.out.println("接口ClientSQL: "+querySql);
		
			if(querySql != null && !querySql.equals(""))
			{
				QueryAdapter adapter = new QueryAdapter();
				String sql=SqlReplace.stringReplaceAllVar(querySql, null);
				dataTable = adapter.executeQuery(sql);
			}
			else
			{
				System.out.println("error: 接口客户端configClient.getQuerySql()无法获取sql");
				updateDataOutStatus(-1,"error: 接口客户端configClient.getQuerySql()无法获取sql");
				dataTable =  new DataTable("");
			}
		}
		catch (Exception e)
		{
			String errorString = "读取接口信息失败！";
			return reportException(errorString, e);
		}
		
		if(dataTable.length() < 1)
		{
			return result;
		}
		
		List<DataRow> rows = dataTable.getRowList();
		for(DataRow row : rows)
		{
			clientContext = new WfiDataout();
			//客户化扩展init()
			//这里的逻辑要将context数据补充完整，opDeatil部分不用管，后续的逻辑会进行处理
			try
			{
				//执行init()
				boolean initResult = init(clientContext, row);
				
				//将字段转换成opDetail的XML，封装clientContext
				DataTable table = new DataTable("");
				table.putDataRow(row);
				String opDetail = wfInterfaceConfig.formatTable2Xml(table, serviceClassName);
				row.put("opDetail", opDetail);
				clientContext.setOpdetail(opDetail);
				clientContext.setSheettype(row.getString("SHEETTYPE"));
				clientContext.setOpaction(row.getString("OPACTION"));
				clientContext.setServicetype(row.getString("SERVICETYPE"));
				clientContext.setSerialno(row.getString("SERIALNO"));
				clientContext.setSersupplier(row.getString("SERSUPPLIER"));
				clientContext.setSercaller(row.getString("SERCALLER"));
				clientContext.setSendnumber(row.getInt("SENDNUMBER"));
				clientContext.setCalltime(row.getString("CALLTIME"));
				clientContext.setOpperson(row.getString("OPPERSON"));
				clientContext.setOpcorp(row.getString("OPCORP"));
				clientContext.setOpdepart(row.getString("OPDEPART"));
				clientContext.setOpcontact(row.getString("OPCONTACT"));
				clientContext.setOptime(row.getString("OPTIME"));
				clientContext.setCalltime(row.getString("CALLTIME"));
				clientContext.setMethodname(serviceClassName);
				clientContext.setScantime(System.currentTimeMillis()/1000);
				
				attachmentsList = new ArrayList<WfiTempAttachment>();
				
				String baseAttachID = row.getString("BASEATTACHID");
				wfAttachmentImpl = (WfAttachmentService)WebApplicationManager.getBean("wfAttachmentImpl");
				List<WfAttachment> wfAttachList = wfAttachmentImpl.queryBySheetId(baseAttachID);
				String attachSql = " relationcode='"+baseAttachID+"' ";
				if (CollectionUtils.isNotEmpty(wfAttachList))
				{
					for (int i = 0; i < wfAttachList.size(); i++)
					{
						WfAttachment wfAttachment = wfAttachList.get(i);
						String pId = wfAttachment.getProcessId();
						attachSql += " or relationcode='" + pId + "' ";
					}
					String hql = "from Attachment where "+attachSql;
					
					attachmentManagerService = (AttachmentManagerService)WebApplicationManager.getBean("attachmentManagerService");
					List<Attachment> attachments = attachmentManagerService.queryByHql(hql);
					attachmentsList = new ArrayList<WfiTempAttachment>();
					if (attachments != null && !attachments.isEmpty())
					{
						for (Attachment attach : attachments)
						{
							String filePath = SwfuploadUtil.SWFUPLOAD_UPLOAD_PATH + File.separator + attach.getPath() + File.separator + attach.getRealname();
							String realName = attach.getName();
							WfiTempAttachment attachObj = new WfiTempAttachment();
							attachObj.setAttachname(realName);
							attachObj.setAttachurl(Constants.ATTACHMENT_FTP_URL + "/" + attach.getRealname());
							attachmentsList.add(attachObj);
							System.out.println("开始复制附件：" + filePath + "到" + Constants.ATTACHMENT_FTP_PATH + File.separator + attach.getRealname());
							FileOperUtil.copyFile(filePath, Constants.ATTACHMENT_FTP_PATH + File.separator + attach.getRealname());
							System.out.println("复制附件完成");
							
						}
					}
				}
				
				clientContext.setAttachref(wfInterfaceConfig.formatAttachObj2AttachXml(attachmentsList));

				if(!initResult)
				{
					RecordLog.printLog("init逻辑设置不进行进一步操作，操作完成", RecordLog.LOG_LEVEL_DEBUG);
					updateDataOutStatus(5,"init逻辑设置不进行进一步操作，操作完成");
					continue;
				}
			}
			catch (Exception e)
			{
				String errorString = "init()逻辑异常！";
				updateDataOutStatus(4,reportException(errorString, e));
				updateInterfaceData(row, configClient.getExceptionSql());
				continue;
			}
			
			boolean updateResult = false;
			updateResult = updateInterfaceData(row, configClient.getUpdateSql());
			if(!updateResult)
			{
				continue;
			}
			
			//在Out表中新建一条数据，状态为处理中
			try
			{
				updateDataOutStatus(2,"处理中");
			}
			catch (Exception e)
			{
				String errorString = "保存接口信息失败！";
				updateDataOutStatus(-1,reportException(errorString, e));
				updateInterfaceData(row, configClient.getExceptionSql());
				continue;
			}
			
			//客户化扩展dataHandle()
			try
			{
				if(!dataHandle(clientContext, row))
				{
					RecordLog.printLog("dataHandle逻辑设置不进行进一步操作，操作完成", RecordLog.LOG_LEVEL_DEBUG);
					updateDataOutStatus(5,"dataHandle逻辑设置不进行进一步操作，操作完成");
					continue;
				}
			}
			catch (Exception e)
			{
				String errorString = "dataHandle逻辑失败！";
				updateDataOutStatus(4,reportException(errorString, e));
				updateInterfaceData(row, configClient.getExceptionSql());
				continue;
			}
			
			//调用Service服务
			try
			{
				DataTable table = new DataTable("");
				table.putDataRow(row);
				String opDetail = wfInterfaceConfig.formatTable2Xml(table, serviceClassName);
				clientContext.setOpdetail(opDetail);
				row.put("opDetail", opDetail);
				result = callService(clientContext, row);
				if(result.indexOf("ERROR:") > -1)
				{
					updateDataOutStatus(4,result);
					updateInterfaceData(row, configClient.getExceptionSql());
				}
				else
				{
					if(result!=null)
						updateDataOutStatus(5,"处理成功,返回值："+result);
					else
						updateDataOutStatus(5,"处理成功,返回值：null");
				}
			}
			catch (Exception e)
			{
				String errorString = "调用WebService服务失败！";
				updateDataOutStatus(3,errorString+e.getMessage());
				updateInterfaceData(row, configClient.getExceptionSql());
				continue;
			}
		}
		
		return result;
	}
	
	protected String getServiceClassName(String serviceName, String methodName)
	{
		String serviceClassName = null;
		for(ConfigClient client : wfInterfaceConfig.getClientMap().values())
		{
			if(client.getClientName().equals(serviceName) && client.getMethodName().equals(methodName))
			{
				serviceClassName = client.getImplClassPath();
			}
		}
		return serviceClassName;
	}
	
	/**
	 * 调用WebService服务
	 * @return 调用返回结果
	 */
	protected String callService(WfiDataout clientContext, DataRow row) throws Exception
	{
		wfInterfaceServiceCaller = (IServiceCaller)WebApplicationManager.getBean("wfInterfaceServiceCaller");
		
		try
		{
			wfInterfaceServiceCaller.isAlive(configClient);
			return wfInterfaceServiceCaller.call(configClient, row);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return "ERROR:调用接口失败";
		}
	}
	
	/**
	 * 更新DataOut数据
	 * @param updateType 处理状态1:未处理  2:处理中 3:已处理但未连接上ar服务 4:已处理但处理失败(ar服务错误除外) 5 已完成
	 * @return 是否更新成功
	 * @throws Exception
	 */
	protected void updateDataOutStatus(int updateType,String dealdesc) throws Exception
	{
		//保存接口数据
		try
		{
			StringBuffer dealsc=new StringBuffer();
			if(clientContext.getDealdesc()!=null)
				dealsc.append(clientContext.getDealdesc());
			dealsc.append("; ");
			dealsc.append(updateType);
			dealsc.append(dealdesc);
			
			clientContext.setDealdesc(dealsc.toString());
			if(updateType>-1)
			{
				clientContext.setOpstate(updateType);
			}
			wfiDataoutService.saveWfiDataout(clientContext);
		}
		catch (Exception e)
		{
			String errorString = "保存接口信息失败！";
			reportException(errorString, e);
			throw e;
		}
	}
	
	/**
	 * 处理数据异常的类
	 * @param errorString
	 * @param e
	 * @return
	 */
	private String reportException(String errorString, Exception e)
	{
		RecordLog.printLog(errorString, RecordLog.LOG_LEVEL_ERROR);
		e.printStackTrace();
		dataException(errorString, e);
		return "ERROR:" + errorString + e.getMessage();
	}
	
	private boolean updateInterfaceData(DataRow row, String sql)
	{
		boolean updateResult = false;
		//更新接口表数据
		String updateSql = SqlReplace.stringReplaceAllVar(sql, row.getRowHashMap());
		if(updateSql != null && !updateSql.equals(""))
		{
			DataAdapter adapter = new DataAdapter();
			try
			{
				adapter.execute(updateSql, null);
				updateResult = true;
			}
			catch (Exception e)
			{
				String errorString = "操作完成，更新接口数据失败！";
				reportException(errorString, e);
				updateResult = false;
			}
		}
		return updateResult;
	}
	
	protected abstract boolean init(WfiDataout clientContext, DataRow row);
	
	protected abstract boolean dataHandle(WfiDataout clientContext, DataRow row);
	
	protected abstract boolean dataException(String errorString, Exception ex);

	public IWFInterfaceConfig getWfInterfaceConfig()
	{
		return wfInterfaceConfig;
	}

	public void setWfInterfaceConfig(IWFInterfaceConfig wfInterfaceConfig)
	{
		this.wfInterfaceConfig = wfInterfaceConfig;
	}

	public WfiDataoutService getWfiDataoutService()
	{
		return wfiDataoutService;
	}

	public void setWfiDataoutService(WfiDataoutService wfiDataoutService)
	{
		this.wfiDataoutService = wfiDataoutService;
	}

	public IServiceCaller getWfInterfaceServiceCaller()
	{
		return wfInterfaceServiceCaller;
	}

	public void setWfInterfaceServiceCaller(IServiceCaller wfInterfaceServiceCaller)
	{
		this.wfInterfaceServiceCaller = wfInterfaceServiceCaller;
	}

	public WfAttachmentService getWfAttachmentImpl()
	{
		return wfAttachmentImpl;
	}

	public void setWfAttachmentImpl(WfAttachmentService wfAttachmentImpl)
	{
		this.wfAttachmentImpl = wfAttachmentImpl;
	}

	public AttachmentManagerService getAttachmentManagerService()
	{
		return attachmentManagerService;
	}

	public void setAttachmentManagerService(AttachmentManagerService attachmentManagerService)
	{
		this.attachmentManagerService = attachmentManagerService;
	}
}
