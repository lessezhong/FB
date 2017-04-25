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
 * @author hai ��վ���ʹ����࣬����Android���������
 * 
 */
public class Webtools {

	/**
	 * ��ǰ��Context�����Ķ���
	 */
	private Context context;

	/**
	 * ����һ����վ���ʹ�����
	 * 
	 * @param context
	 *            ��¼��ǰActivity�е�Context�����Ķ���
	 */
	public Webtools(Context context) {
		this.context = context;
	}

	/**
	 * ���ݸ�����url��ַ�������磬�õ���Ӧ����(����ΪGET��ʽ����)
	 * web��������Ӧ������
	 * @param url
	 *            ָ����url��ַ
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