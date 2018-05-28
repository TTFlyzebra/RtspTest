package com.flyzebra.flydown.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;

/** 
* 功能说明：
* @author 作者：FlyZebra 
* @version 创建时间：2017年3月21日 下午4:26:29  
*/
public class FileUtils {
	
	/**
	 * 以字符串方式一次读取文件所有内容
	 * @param fileName 要读取的文件的文件路径
	 * @return 以字符串方式返回文件内容
	 */
	public static String readFile(String fileName){
		if(fileName!=null&&fileName.equals("")){
			File file = new File(fileName);
			return readFile(file);
		}else{
			return null;
		}
	}
	
	/**
	 * 以字符串方式一次读取文件所有内容
	 * @param file
	 * @return 以字符串方式返回文件内容
	 */
	public static String readFile(File file){
		String readStr = null;
		if(file.exists()){
			InputStream ins = null;
			InputStreamReader streamReader = null;
			BufferedReader reader = null;
			try {
				ins = new FileInputStream(file);
				streamReader = new InputStreamReader(ins);
	            reader = new BufferedReader(streamReader);
	            String line = null;
	            StringBuilder stringBuilder = new StringBuilder();
	            while ((line = reader.readLine()) != null) {
	                stringBuilder.append(line);
	            }
	            readStr = stringBuilder.toString();
			} catch (Exception e) {
				TLog.d("Reading File onFailed! %s",e.toString());
				e.printStackTrace();
			} finally {
				CloseableUtil.Close(reader);
				CloseableUtil.Close(streamReader);
				CloseableUtil.Close(ins);
			}
		}
		return readStr;
	}
	
	/**
	 * 在线程中删除指定的文件
	 * @param fileName
	 */
	public static void delFileInTread(final String fileName){
		new Thread(new Runnable() {
			@Override
			public synchronized void run() {
				File file = new File(fileName);
				if(file.exists()){
					file.delete();
				}
			}
		}).start();
	}
}
