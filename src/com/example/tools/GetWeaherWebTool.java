package com.example.tools;
import java.io.*;
import java.net.*;

public class GetWeaherWebTool {

	public GetWeaherWebTool() {
		super();

	}

	public String GetWeaherWebConten(String url) throws Exception {
		StringBuffer sb = new StringBuffer();
		String line = null;
		BufferedReader buffer = null;
		HttpURLConnection urlConn=null;
		try {
			URL url1 = new URL(url);
			urlConn = (HttpURLConnection) url1
					.openConnection();
			buffer = new BufferedReader(new InputStreamReader(
					urlConn.getInputStream(), "UTF-8"));
			while ((line = buffer.readLine()) != null) {
				sb.append(line);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				urlConn.disconnect();
				buffer.close();

			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();

	}
}
