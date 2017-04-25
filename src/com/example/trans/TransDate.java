package com.example.trans;

import java.util.Calendar;
import java.util.Date;

import com.example.entity.Day;

import android.content.Context;

public class TransDate {
	/**
	 * @param args
	 */
	public static String str[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五",
			"星期六" };
	public static int week;

	public TransDate(Context context) {

		super();
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		week = calendar.get(Calendar.DAY_OF_WEEK) - 1;
	}

	public  Day yunsuan() {
		Day day = new Day();
		int flag = -1;
		switch (week) {
		case 0:
			flag = 0;
			break;
		case 1:
			flag = 1;
			break;
		case 2:
			flag = 2;
			break;
		case 3:
			flag = 3;
			break;
		case 4:
			flag = 4;
			break;
		case 5:
			flag = 5;
			break;
		case 6:
			flag = 6;
			break;
		default:
			break;
		}
		if (flag != -1) {
			day.day_0 = str[flag];
			day.day_1 = str[(flag + 1) % 7];
			day.day_2 = str[(flag + 2) % 7];
			day.day_3 = str[(flag + 3) % 7];
			day.day_4 = str[(flag + 4) % 7];
			day.day_5 = str[(flag + 5) % 7];
			day.day_6 = str[(flag + 6) % 7];
			return day;

		} else {
//			System.out.println("今天是——————>未知");
			return null;
		}

	}

}
