package com.ultrapower.eoms.ftrmaintain.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.io.Writer;

import com.ultrapower.eoms.common.core.component.data.DataRow;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfDoloadBean;
import com.ultrapower.eoms.common.plugin.swfupload.utils.SwfuploadUtil;
import com.ultrapower.eoms.fulltext.common.util.interfaces.ICustomFileSourceCreater;
import com.ultrapower.eoms.fulltext.model.IndexCategory;

/**
 * @author RenChenglin
 * @date 2011-9-7 下午01:32:36
 * @version
 * 附件获取实现类
 */
public class EOMS4AttFileSourceCreater implements ICustomFileSourceCreater {

	public File getRealFile(DataRow sqlvalue, IndexCategory category, String tempDir) {
		if(sqlvalue==null||category==null)
			return null;
		File tempPath = new File(tempDir);
		if(!tempPath.exists())
			return null;
		File realFile = new File(tempPath,sqlvalue.getString("realname"));
		if(realFile.exists())
			return realFile;
		SwfDoloadBean bean = new SwfDoloadBean();
		bean.setSaveName(sqlvalue.getString("realname"));
		bean.setSavePath(sqlvalue.getString("ppath"));
		bean.setCustomDownPath(tempDir);
		SwfuploadUtil su = new SwfuploadUtil();
		InputStream ins = su.download(bean);
		if(ins==null)
			return null;
		try {
			ins.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return realFile;
	}

	public boolean deleteFileAfterUsed() {
		//使用完以后可以删除file实体
		return true;
	}
	
	public static void create(){
		File file = new File("d:\\test");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			Writer writer = new FileWriter(file);
			writer.write("神州泰岳软件股份有限公司");
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		File file = new File("d:\\test");
		create();
		try {
			StringBuffer buffer = new StringBuffer();
			Reader read = new FileReader(file);
			char[] cb = new char[1024];
			while(true){
				int i = read.read(cb);
				if(i==-1)
					break;
				buffer.append(new String(cb,0,i));
			}
			read.close();
			System.out.println(buffer.toString());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
