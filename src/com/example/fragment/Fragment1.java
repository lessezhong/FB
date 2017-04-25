package com.example.fragment;

import org.apache.http.Header;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.example.activity.R; 
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
public class Fragment1 extends Fragment{
	String hai="hai";
	AsyncHttpClient asyncHttpClient;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		Log.e(hai,"Fragment1 ----oncreat");
		super.onCreate(savedInstanceState);
		
		
	}
	
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main_menu, menu);
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				asyncHttpClient = new AsyncHttpClient();
				asyncHttpClient.get("http://www.baidu.com",
						new AsyncHttpResponseHandler() {
							@Override
							public void onSuccess(int statusCode,
									Header[] arg1, byte[] response) {
								Log.e(hai, getString(statusCode));
								Log.e(hai, response.toString());
								super.onSuccess(statusCode, arg1, response);
							}

							@Override
							public void onFailure(int arg0, Header[] arg1,
									byte[] arg2, Throwable arg3) {
								Log.e(hai,"onFailure"+arg0+arg3.getMessage());
								super.onFailure(arg0, arg1, arg2, arg3);
							}
							

						});

			}
		});
		thread.start();
		return inflater.inflate(R.layout.fragment1, null);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case 1:
			
			break;

		default:
			break;
		}
		return super.onOptionsItemSelected(item);
	}	
}
