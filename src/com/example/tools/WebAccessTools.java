package com.example.tools;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;

import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.util.Log;

/**
 * 
 * @author hai 锟斤拷站锟斤拷锟绞癸拷锟斤拷锟洁，锟斤拷锟斤拷Android锟斤拷锟斤拷锟斤拷锟斤拷锟�
 * 
 */
public class WebAccessTools {

	/**
	 * 锟斤拷前锟斤拷Context锟斤拷锟斤拷锟侥讹拷锟斤拷
	 */
	private Context context;

	/**
	 * 锟斤拷锟斤拷一锟斤拷锟斤拷站锟斤拷锟绞癸拷锟斤拷锟斤拷
	 * 
	 * @param context
	 *            锟斤拷录锟斤拷前Activity锟叫碉拷Context锟斤拷锟斤拷锟侥讹拷锟斤拷
	 */
	public WebAccessTools() {

	}

	/**
	 * 锟斤拷莞锟斤拷url锟斤拷址锟斤拷锟斤拷锟斤拷锟界，锟矫碉拷锟斤拷应锟斤拷锟斤拷(锟斤拷锟斤拷为GET锟斤拷式锟斤拷锟斤拷)
	 * 
	 * @param url
	 *            指锟斤拷锟斤拷url锟斤拷址
	 * @return web锟斤拷锟斤拷锟斤拷锟斤拷应锟斤拷锟斤拷锟捷ｏ拷为<code>String</code>锟斤拷锟酵ｏ拷锟斤拷锟斤拷锟斤拷失锟斤拷时锟斤拷锟斤拷锟斤拷为null
	 */
	public String getWebContent(String url) {
		// 锟斤拷锟斤拷一锟斤拷http锟斤拷锟斤拷锟斤拷锟�
		HttpGet request = new HttpGet(url);
		// 锟斤拷锟斤拷HttpParams锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷HTTP锟斤拷锟斤拷
		HttpParams params = new BasicHttpParams();
		// 锟斤拷锟斤拷锟斤拷锟接筹拷时锟斤拷锟斤拷应锟斤拷时
		HttpConnectionParams.setConnectionTimeout(params, 7000);

		// 锟斤拷锟斤拷一锟斤拷锟斤拷锟斤拷锟斤拷蚀锟斤拷锟斤拷锟斤拷
		HttpClient httpClient = new DefaultHttpClient(params);
		try {
			// 执锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�
			HttpResponse response = httpClient.execute(request);
			// 锟叫讹拷锟角凤拷锟斤拷锟斤拷晒锟�
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				// 锟斤拷锟斤拷锟接︼拷锟较�

				String content = EntityUtils.toString(response.getEntity(),
						"UTF-8");
				return content;
			} else {
				// 锟斤拷锟斤拷锟斤拷失锟杰ｏ拷使锟斤拷Toast锟斤拷示锟斤拷示锟斤拷息
//				Toast.makeText(context, "锟斤拷锟斤拷锟斤拷锟绞э拷埽锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟借备!",
//						Toast.LENGTH_LONG).show();
				Log.d("锟斤拷锟�", "锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷锟�");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// 锟酵凤拷锟斤拷锟斤拷锟斤拷锟斤拷锟斤拷源
			httpClient.getConnectionManager().shutdown();
		}
		return null;
	}
}