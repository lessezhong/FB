package com.example.Set;

import com.example.activity.R;

import android.content.Context;

public class Imageset {
	/**
	 * 当前的Context上下文对象
	 */
	private Context context;

//	public Imageset(Context context) {
//		this.context = context;
//	}

	public static int getimage(String WeatherPhenomenonNumber) {
		String weatherPhenomenon;
		if (WeatherPhenomenonNumber != null
				&& !WeatherPhenomenonNumber.equals("")) {
//			System.out
//					.println("Integer.parseInt(WeatherPhenomenonNumber.trim())----"
//							+ Integer.parseInt(WeatherPhenomenonNumber.trim()));
			switch (Integer.parseInt(WeatherPhenomenonNumber.trim())) {
			case 0:
				weatherPhenomenon = "晴";
				return R.drawable.d00;
			case 1:
				weatherPhenomenon = "多云";
				return R.drawable.d01;
			case 2:
				weatherPhenomenon = "阴";
				return R.drawable.d02;
			case 3:
				weatherPhenomenon = "阵雨";
				return R.drawable.d03;
			case 4:
				weatherPhenomenon = "雷阵雨";
				return R.drawable.d04;
			case 5:
				weatherPhenomenon = "雷阵雨伴有冰雹";
				return R.drawable.d05;
			case 6:
				weatherPhenomenon = "雨夹雪";
				return R.drawable.d06;
			case 7:
				weatherPhenomenon = "小雨";
				return R.drawable.d07;
			case 8:
				weatherPhenomenon = "中雨";
				return R.drawable.d08;
			case 9:
				weatherPhenomenon = "大雨";
				return R.drawable.d09;
			case 10:
				weatherPhenomenon = "暴雨";
				return R.drawable.d10;
			case 11:
				weatherPhenomenon = "大暴雨";
				return R.drawable.d11;
			case 12:
				weatherPhenomenon = "特大暴雨";
				return R.drawable.d12;
			case 13:
				weatherPhenomenon = "阵雪";
				return R.drawable.d13;
			case 14:
				weatherPhenomenon = "小雪";
				return R.drawable.d14;
			case 15:
				weatherPhenomenon = "中雪";
				return R.drawable.d15;
			case 16:
				weatherPhenomenon = "大雪";
				return R.drawable.d16;
			case 17:
				weatherPhenomenon = "暴雪";
				return R.drawable.d17;
			case 18:
				weatherPhenomenon = "雾";
				return R.drawable.d18;
			case 19:
				weatherPhenomenon = "冻雨";
				return R.drawable.d19;
			case 20:
				weatherPhenomenon = "沙尘暴";
				return R.drawable.d20;
			case 21:
				weatherPhenomenon = "小到中雨";
				return R.drawable.d21;
			case 22:
				weatherPhenomenon = "中到大雨";
				return R.drawable.d22;
			case 23:
				weatherPhenomenon = "大到暴雨";
				return R.drawable.d23;
			case 24:
				weatherPhenomenon = "暴雨到大暴雨";
				return R.drawable.d24;
			case 25:
				weatherPhenomenon = "大暴雨到特大暴雨";
				return R.drawable.d25;
			case 26:
				weatherPhenomenon = "小到中雪";
				return R.drawable.d26;
			case 27:
				weatherPhenomenon = "中到大雪";
				return R.drawable.d27;
			case 28:
				weatherPhenomenon = "大到暴雪";
				return R.drawable.d28;
			case 29:
				weatherPhenomenon = "浮尘";
				return R.drawable.d29;
			case 30:
				weatherPhenomenon = "扬沙";
				return R.drawable.d30;
			case 31:
				weatherPhenomenon = "强沙尘暴";
				return R.drawable.d31;
			case 53:
				weatherPhenomenon = "霾";
				return R.drawable.d53;
			case 99:
				weatherPhenomenon = "无";
				return R.drawable.undefined;
			default:
				weatherPhenomenon = "date error";
				return R.drawable.undefined;
			}
		} else {
			return R.drawable.undefined;
		}
	}
}
