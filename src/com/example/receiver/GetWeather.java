package com.example.receiver;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import android.util.Log;

import com.example.entity.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.example.tools.GetWeaherWebTool;
import com.example.tools.WebAccessTools;

public class GetWeather {

	/**
	 * @author hai 获取天气数据的工具类
	 * @param city_num
	 */
	static String Public_Key_Observe;// observe固定的公钥部分
	static String Public_Key_Forecast3d;// forecast3d固定的公钥部分
	static String Public_Key_Index;// index 固定部分
	static String Appid;
	static String Appid_Head;
	static String Private_Key;// 私钥

	static String Key_Observe;// 运算成的密匙
	static String Key_Forecast3d;
	static String Key_Index;

	static String Full_Observe_Url;
	static String Full_Forecast3d_Url;
	static String Full_Index_Url;

	static String Observe = "observe";
	static String Forecast3d = "Forecast3d";
	static String Index = "index";

	/**气象局获取天气数据的静态方法
	 * @author hai
	 * @param city
	 * @return all
	 * @throws Exception
	 */

	public static All GetALLWeaterContent(String city_num) throws Exception {

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String date = simpleDateFormat.format(new Date());
		Appid = "848dc672a901f3bf";
		Appid_Head = Appid.substring(0, 6);
		Private_Key = "bc2785_SmartWeatherAPI_44f5172";

		Public_Key_Observe = "http://webapi.weather.com.cn/data/?areaid="
				+ city_num + "&type=observe&date=" + date + "&appid=" + Appid;

		Key_Observe = SignEncode(Public_Key_Observe, Private_Key);

		Full_Observe_Url = "http://webapi.weather.com.cn/data/?areaid="
				+ city_num + "&type=observe&date=" + date + "&appid="
				+ Appid_Head + "&key=" + Key_Observe;

		Public_Key_Forecast3d = "http://webapi.weather.com.cn/data/?areaid="
				+ city_num + "&type=forecast3d&date=" + date + "&appid="
				+ Appid;

		Key_Forecast3d = SignEncode(Public_Key_Forecast3d, Private_Key);

		Full_Forecast3d_Url = "http://webapi.weather.com.cn/data/?areaid="
				+ city_num + "&type=forecast3d&date=" + date + "&appid="
				+ Appid_Head + "&key=" + Key_Forecast3d;

		Public_Key_Index = "http://webapi.weather.com.cn/data/?areaid="
				+ city_num + "&type=index&date=" + date + "&appid=" + Appid;

		Key_Index = SignEncode(Public_Key_Index, Private_Key);

		Full_Index_Url = "http://webapi.weather.com.cn/data/?areaid="
				+ city_num + "&type=index&date=" + date + "&appid="
				+ Appid_Head + "&key=" + Key_Index;

		Log.e("输出", Full_Observe_Url);
		Log.e("输出", Full_Forecast3d_Url);
		Log.e("输出", Full_Index_Url);
		All all = new All();
		GetWeaherWebTool getWeaherWebTool = new GetWeaherWebTool();
		all.observe = getWeaherWebTool.GetWeaherWebConten(Full_Observe_Url);
		//all.focast3d = getWeaherWebTool.GetWeaherWebConten(Full_Forecast3d_Url);
		all.focast3d=new WebAccessTools().getWebContent(Full_Forecast3d_Url);
		all.index = getWeaherWebTool.GetWeaherWebConten(Full_Index_Url);
		return all;

	}

	/**
	 * 1.签名 2. 加密
	 * 
	 * @author hai
	 * @param public_key
	 * @param private_key
	 * @return 密匙
	 */
	public static String SignEncode(String public_key, String private_key)
			throws GeneralSecurityException, UnsupportedEncodingException {
		Mac mac = null;
		mac = Mac.getInstance("HmacSHA1");
		SecretKeySpec secretKeySpec = new SecretKeySpec(private_key.getBytes(),
				"HmacSHA1");
		mac.init(secretKeySpec);
		return new String(
				Base64.encodeBase64(mac.doFinal(public_key.getBytes()))).trim();
	}
	public static void main(String args[]){
		GetWeather g=	new GetWeather();
		try {
			System.err.println(g.GetALLWeaterContent("101010100"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
