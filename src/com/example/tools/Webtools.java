package com.example.tools;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

/**
 * 
 * @author hai 网站访问工具类，用于Android的网络访问
 * 
 */
public class Webtools {

	/**
	 * 当前的Context上下文对象
	 */
	private Context context;

	/**
	 * 构造一个网站访问工具类
	 * 
	 * @param context
	 *            记录当前Activity中的Context上下文对象
	 */
	public Webtools(Context context) {
		this.context = context;
	}

	/**
	 * 根据给定的url地址访问网络，得到响应内容(这里为GET方式访问)
	 * web服务器响应的内容
	 * @param url
	 *            指定的url地址
	 * @return sb.toString
	 * 
	 */
	public String getWebContent(String url) {

		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		try {
			URL url1 = new URL(url);
			HttpURLConnection urlConn = (HttpURLConnection) url1
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream()));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				buffer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}
//	Handler handler=new Handler(){
//
//		@Override
//		public void handleMessage(Message msg) {
//			// TODO Auto-generated method stub
//			super.handleMessage(msg);
//			handler.post(thread);
//		}
//		
//	};
//	Thread thread=new Thread(new Runnable() {
//		
//		@Override
//		public void run() {
//			// TODO Auto-generated method stub
//			Message message=new Message();
//			handler.sendMessage(message);
//		}
//	});
	
	
	
	
	
}