package com.ultrapower.eoms.msextend.notice.util;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import com.ultrapower.eoms.msextend.notice.model.NoticeLevelManage;



public class NoticeUtil {

	/**
	 * 增加多类型模版的判断，如果是KM则所有KM_XX类似的都过滤掉(当时定义，包括CM)
	 * @param ss
	 * @return
	 */
	private static List<String> filterMulTemplate(List<String> ss){
		ListIterator<String> listIt = ss.listIterator();
		while(listIt.hasNext()){
			String s = listIt.next();
//			if(s.length()>3 &&(s.subSequence(0, 3).equals(Constants.CMDBTYPE_KM+"_") || s.subSequence(0, 3).equals(Constants.CMDBTYPE_CM+"_"))){
//				listIt.remove();
//			}
		}
		return ss;
	}
	
	public static String List2String(List<String> ss){
		if(ss.size()==0){
			return "";
		}
		filterMulTemplate(ss);
		StringBuffer sb = new StringBuffer();
		for(Iterator<String> it =ss.iterator();it.hasNext();){
			sb.append(it.next()).append(",");
		}
		String temp = sb.toString();
		return temp.substring(0,sb.length()-1);
	}
	
	public static String[] List2Array(List<String> ss){
		filterMulTemplate(ss);
		String[] array = new String[ss.size()];
		int i=0;
		for(Iterator<String> it =ss.iterator();it.hasNext();){
			array[i]=it.next();
			i++;
		}
		return array;
	}
	
	public static List<NoticeLevelManage> convertNoticeTypeList(List<NoticeLevelManage> noticeTypeList){
		ListIterator<NoticeLevelManage> it = noticeTypeList.listIterator();
		while(it.hasNext()){
			NoticeLevelManage noticeType = it.next();
			//如果是知识类型的通知，则需要将其所有子类型的通知也加入进通知系统
//			if(noticeType.getNoticeType().equals(Constants.CMDBTYPE_KM)){
//				//it.remove();
//				Tree tree = TreeFactory.getTree(Constants.ROOT, Constants.CMDBTYPE_KM);
//				VoTemplateNode templateNode = tree.getTempleteInfoByName(Constants.CI);
//				String KM_CI = templateNode.getTemplate().getTemplate().getAlias();
//				NoticeType newNoticeType = new NoticeType(noticeType);
//				newNoticeType.setNoticeType(Constants.CMDBTYPE_KM+"_"+KM_CI);
//				it.add(newNoticeType);
//				List<VoTemplateNode> childTemplates = tree.getAllChildren(templateNode, new ArrayList<VoTemplateNode>());//当前模板的子孙模板
//				for(VoTemplateNode  voTemplateNode:childTemplates){
//					String KM_SON = voTemplateNode.getTemplate().getTemplate().getAlias();
//					NoticeType sonNoticeType = new NoticeType(noticeType);
//					sonNoticeType.setNoticeType(Constants.CMDBTYPE_KM+"_"+KM_SON);
//					it.add(sonNoticeType);
//				}
//			}
		}
		return noticeTypeList;
	}
}
