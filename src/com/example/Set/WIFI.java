package com.example.Set;

import android.content.Context;
import android.net.wifi.WifiManager;

public class WIFI {
	private WifiManager manager;
	int wifiState;
	Context context;

	public WIFI(Context context) {
		super();
		this.context = context;
	}

	public WIFI() {
		this.wifiState = manager.getWifiState();

	}

	public int getWifiState() {
		return wifiState;
	}

	public void setWifiState(int wifiState) {
		this.wifiState = wifiState;
	}

}
