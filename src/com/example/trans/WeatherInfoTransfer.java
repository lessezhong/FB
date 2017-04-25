package com.example.trans;

/**
 * 转换天气气象编码
 * @author hai
 *
 */
public class WeatherInfoTransfer {
	/**
	 * 通过气象编号得到气象
	 * 
	 * @author hai
	 * @param WeatherPhenomenonNumber
	 * @return 对应的天气气象 String
	 */
	public static String getWeatherPhenomenon(String WeatherPhenomenonNumber) {
		String weatherPhenomenon;
		if (WeatherPhenomenonNumber != null
				&& !WeatherPhenomenonNumber.equals("")) {
//			System.out
//					.println("Integer.parseInt(WeatherPhenomenonNumber.trim())----"
//							+ Integer.parseInt(WeatherPhenomenonNumber.trim()));
			switch (Integer.parseInt(WeatherPhenomenonNumber.trim())) {
			case 0:
				weatherPhenomenon = "晴";
				return weatherPhenomenon;
			case 1:
				weatherPhenomenon = "多云";
				return weatherPhenomenon;
			case 2:
				weatherPhenomenon = "阴";
				return weatherPhenomenon;
			case 3:
				weatherPhenomenon = "阵雨";
				return weatherPhenomenon;
			case 4:
				weatherPhenomenon = "雷阵雨";
				return weatherPhenomenon;
			case 5:
				weatherPhenomenon = "雷阵雨伴有冰雹";
				return weatherPhenomenon;
			case 6:
				weatherPhenomenon = "雨夹雪";
				return weatherPhenomenon;
			case 7:
				weatherPhenomenon = "小雨";
				return weatherPhenomenon;
			case 8:
				weatherPhenomenon = "中雨";
				return weatherPhenomenon;
			case 9:
				weatherPhenomenon = "大雨";
				return weatherPhenomenon;
			case 10:
				weatherPhenomenon = "暴雨";
				return weatherPhenomenon;
			case 11:
				weatherPhenomenon = "大暴雨";
				return weatherPhenomenon;
			case 12:
				weatherPhenomenon = "特大暴雨";
				return weatherPhenomenon;
			case 13:
				weatherPhenomenon = "阵雪";
				return weatherPhenomenon;
			case 14:
				weatherPhenomenon = "小雪";
				return weatherPhenomenon;
			case 15:
				weatherPhenomenon = "中雪";
				return weatherPhenomenon;
			case 16:
				weatherPhenomenon = "大雪";
				return weatherPhenomenon;
			case 17:
				weatherPhenomenon = "暴雪";
				return weatherPhenomenon;
			case 18:
				weatherPhenomenon = "雾";
				return weatherPhenomenon;
			case 19:
				weatherPhenomenon = "冻雨";
				return weatherPhenomenon;
			case 20:
				weatherPhenomenon = "沙尘暴";
				return weatherPhenomenon;
			case 21:
				weatherPhenomenon = "小到中雨";
				return weatherPhenomenon;
			case 22:
				weatherPhenomenon = "中到大雨";
				return weatherPhenomenon;
			case 23:
				weatherPhenomenon = "大到暴雨";
				return weatherPhenomenon;
			case 24:
				weatherPhenomenon = "暴雨到大暴雨";
				return weatherPhenomenon;
			case 25:
				weatherPhenomenon = "大暴雨到特大暴雨";
				return weatherPhenomenon;
			case 26:
				weatherPhenomenon = "小到中雪";
				return weatherPhenomenon;
			case 27:
				weatherPhenomenon = "中到大雪";
				return weatherPhenomenon;
			case 28:
				weatherPhenomenon = "大到暴雪";
				return weatherPhenomenon;
			case 29:
				weatherPhenomenon = "浮尘";
				return weatherPhenomenon;
			case 30:
				weatherPhenomenon = "扬沙";
				return weatherPhenomenon;
			case 31:
				weatherPhenomenon = "强沙尘暴";
				return weatherPhenomenon;
			case 53:
				weatherPhenomenon = "霾";
				return weatherPhenomenon;
			case 99:
				weatherPhenomenon = "无";
				return weatherPhenomenon;
			default:
				weatherPhenomenon = "date error";
				return weatherPhenomenon;
			}
		} else {
			return "";
		}
	}

	/**
	 * 通过风向编号得到风向
	 * @author hai
	 * @param windDirectionNumber
	 * @return
	 */
	public static String getWindDirection(String windDirectionNumber) {
		String windDirection;
		if (windDirectionNumber != null && !windDirectionNumber.equals("")) {

			switch (Integer.parseInt(windDirectionNumber)) {
			case 0:
				windDirection = "无持续风向";
				return windDirection;
			case 1:
				windDirection = "东北风";
				return windDirection;
			case 2:
				windDirection = "东风";
				return windDirection;
			case 3:
				windDirection = "东南风";
				return windDirection;
			case 4:
				windDirection = "南风";
				return windDirection;
			case 5:
				windDirection = "西南风";
				return windDirection;
			case 6:
				windDirection = "西风";
				return windDirection;
			case 7:
				windDirection = "西北风";
				return windDirection;
			case 8:
				windDirection = "北风";
				return windDirection;
			case 9:
				windDirection = "旋转风";
				return windDirection;
			default:
				windDirection = "date error";
				return windDirection;
			}
		} else {
			return "";
		}
	}

}
