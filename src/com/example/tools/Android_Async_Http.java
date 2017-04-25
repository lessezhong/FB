package com.example.tools;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

public class Android_Async_Http {
	AsyncHttpClient client;

	public Android_Async_Http() {
		super();
		client = new AsyncHttpClient();
	}

	public void getWeatherAPI(String url) {
		client.post(url, new AsyncHttpResponseHandler() {

			@Override
			public String getCharset() {
				// TODO Auto-generated method stub
				return super.getCharset();
			}

			@Override
			public void onSuccess(String content) {

				super.onSuccess(content);
			}

		});
	}

}
