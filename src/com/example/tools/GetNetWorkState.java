package com.example.tools;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo.State;

public class GetNetWorkState {
	Context context;
	public ConnectivityManager conMan;

	/*
	 * ÅÐ¶ÏÊÖ»úµÄÍøÂç×´Ì¬
	 */
	public GetNetWorkState(Context context) {
		this.context = context;
	}

	public boolean getNetWorkState() {
		conMan = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		State mobile = conMan.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				.getState();
		String na=mobile.toString();
		State wifi = conMan.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
				.getState();
		if (mobile.toString().equals("DISCONNECTED")
				&& wifi.toString().equals("DISCONNECTED")) {
			return false;
		} else {
			return true;
		}

	}
}
