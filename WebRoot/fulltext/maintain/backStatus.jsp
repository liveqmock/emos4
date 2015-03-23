<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.ultrapower.eoms.fulltext.control.*" %>
<%@ page import="com.ultrapower.eoms.fulltext.common.cache.*" %>
<%@ page import="com.ultrapower.eoms.fulltext.model.*" %>
<%@ page import="com.ultrapower.eoms.fulltext.dao.MaintainRecordDao" %>
<%@ page import="com.ultrapower.eoms.fulltext.manager.MaintainIndex" %>
<%@ page import="java.io.File" %>
<%@ page import="java.text.SimpleDateFormat" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>操作接收处理</title>
  </head>
  
  <body>
    <%
		InitController init = InitController.getInstance();
		if(init.isInitStatus()){
			String tempOp = request.getParameter("op");
			String op = tempOp==null?"":tempOp;
			
			if("statusReset".equals(op)){//索引维护状态重置
				IndexScheduler s = IndexScheduler.getScheduler();
				if(s!=null){
					s.setCompleteLastMaintain(true);
					out.print("索引维护状态已重置！");
				}
			}else if("destoryWriter".equals(op)){//销毁写索引器
				IndexWriterPool iwp = IndexWriterPool.getInstance();
				if(iwp!=null){
					String cid = request.getParameter("cid");
					if(cid==null){
						iwp.forceDestory();
					}else{
						iwp.forceDestory(cid);
					}
					out.print("写索引器已经被销毁！");
				}
			}else if("destoryReader".equals(op)){//销毁读索引器
				IndexReaderPool irp = IndexReaderPool.getInstance();
				if(irp!=null){
					String cid = request.getParameter("cid");
					if(cid==null){
						irp.forceDestory();
					}else{
						irp.forceDestory(cid);
					}
					out.print("读索引器已经被销毁！");
				}
			}else if("delIndexFile".equals(op)){//删除索引文件
				String cid = request.getParameter("cid");
				if(cid!=null){
					if("@ALLCID@".equals(cid)){
						IndexWriterPool iwp = IndexWriterPool.getInstance();
						if(iwp!=null){
							iwp.forceDestory();
							out.print("所有写索引器已经被销毁！");
						}
						IndexReaderPool irp = IndexReaderPool.getInstance();
						if(irp!=null){
							irp.forceDestory(cid);
							out.print("所有读索引器已经被销毁！");
						}
						List<String> cids = SystemContext.getAllPhysicCategoryKey();
						if(cids!=null){
							int len = cids.size();
							IndexCategory ca;
							for(int i=0;i<len;i++){
								ca = SystemContext.getPhysicalCategory(cids.get(i));
								if(ca!=null){
									List<IndexPath> pathes = ca.getIndexPath();
									if(pathes!=null){
										int len2 = pathes.size();
										IndexPath path;
										for(int j=0;j<len2;j++){
											path = pathes.get(j);
											File pf = new File(path.getPath());
											try{
												org.apache.commons.io.FileUtils.deleteDirectory(pf);
											}catch(Exception e){
												out.println("删除路径["+path+"]出现如下错误："+e.getMessage());
											}
										}
									}
								}
							}
						}
						out.print("删除完毕！");
					}else{
						IndexWriterPool iwp = IndexWriterPool.getInstance();
						if(iwp!=null){
							iwp.forceDestory(cid);
							out.print("当前索引类别的写索引器已经被销毁！");
						}
						IndexReaderPool irp = IndexReaderPool.getInstance();
						if(irp!=null){
							irp.forceDestory(cid);
							out.print("当前索引类别的读索引器已经被销毁！");
						}
						IndexCategory ca = SystemContext.getPhysicalCategory(cid);
						if(ca!=null){
							List<IndexPath> pathes = ca.getIndexPath();
							if(pathes!=null){
								int len = pathes.size();
								IndexPath path;
								for(int i=0;i<len;i++){
									path = pathes.get(i);
									File pf = new File(path.getPath());
									try{
										org.apache.commons.io.FileUtils.deleteDirectory(pf);
									}catch(Exception e){
										out.println("删除路径["+path+"]出现如下错误："+e.getMessage());
									}
								}
							}
						}
					}
				}
				out.println("删除完毕！");
			}else if("reInitSystem".equals(op)){//重新初始化系统
				init.initSystem();
				init.initReader();
				out.println("系统已经重新初始化完成！");
			}else if("delMaintainRecord".equals(op)){//删除索引维护记录
				String cid = request.getParameter("cid");
				if(cid!=null){
					MaintainRecordDao dao = new MaintainRecordDao();
					if("@ALLCID@".equals(cid)){
						dao.deleteRecord();
					}else{
						dao.deleteRecord(cid);
					}
					out.println("已经删除！");
				}
			}else if("getLastDataDate".equals(op)){
				String cid = request.getParameter("cid");
				if(cid!=null){
					MaintainRecordDao dao = new MaintainRecordDao();
					long seconds = dao.getLastTransDataDate(cid);
					if(seconds==-1){
						out.println("还没有维护历史记录！");
					}else{
						SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
						out.println("最后一次索引数据日期为："+sdf.format(new Date(seconds*1000)));
					}
				}
			}else if("forceStopSchedule".equals(op)){
				IndexScheduler sche = IndexScheduler.getScheduler();
				sche.forceStopeSchedule();
				out.println("已经强制停止索引维护线程！");
			}else if("forceStopIndexCycle".equals(op)){
				MaintainIndex.cycleSwitch = false;
				out.println("已经强制退出索引循环！");
			}
		}else{
			out.println("请先初始化！");
		}
	%>
  </body>
</html>
