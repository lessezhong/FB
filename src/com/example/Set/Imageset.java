package com.example.Set;

import com.example.activity.R;

import android.content.Context;

public class Imageset {
	/**
	 * ��ǰ��Context�����Ķ���
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
				weatherPhenomenon = "��";
				return R.drawable.d00;
			case 1:
				weatherPhenomenon = "����";
				return R.drawable.d01;
			case 2:
				weatherPhenomenon = "��";
				return R.drawable.d02;
			case 3:
				weatherPhenomenon = "����";
				return R.drawable.d03;
			case 4:
				weatherPhenomenon = "������";
				return R.drawable.d04;
			case 5:
				weatherPhenomenon = "��������б���";
				return R.drawable.d05;
			case 6:
				weatherPhenomenon = "���ѩ";
				return R.drawable.d06;
			case 7:
				weatherPhenomenon = "С��";
				return R.drawable.d07;
			case 8:
				weatherPhenomenon = "����";
				return R.drawable.d08;
			case 9:
				weatherPhenomenon = "����";
				return R.drawable.d09;
			case 10:
				weatherPhenomenon = "����";
				return R.drawable.d10;
			case 11:
				weatherPhenomenon = "����";
				return R.drawable.d11;
			case 12:
				weatherPhenomenon = "�ش���";
				return R.drawable.d12;
			case 13:
				weatherPhenomenon = "��ѩ";
				return R.drawable.d13;
			case 14:
				weatherPhenomenon = "Сѩ";
				return R.drawable.d14;
			case 15:
				weatherPhenomenon = "��ѩ";
				return R.drawable.d15;
			case 16:
				weatherPhenomenon = "��ѩ";
				return R.drawable.d16;
			case 17:
				weatherPhenomenon = "��ѩ";
				return R.drawable.d17;
			case 18:
				weatherPhenomenon = "��";
				return R.drawable.d18;
			case 19:
				weatherPhenomenon = "����";
				return R.drawable.d19;
			case 20:
				weatherPhenomenon = "ɳ����";
				return R.drawable.d20;
			case 21:
				weatherPhenomenon = "С������";
				return R.drawable.d21;
			case 22:
				weatherPhenomenon = "�е�����";
				return R.drawable.d22;
			case 23:
				weatherPhenomenon = "�󵽱���";
				return R.drawable.d23;
			case 24:
				weatherPhenomenon = "���굽����";
				return R.drawable.d24;
			case 25:
				weatherPhenomenon = "���굽�ش���";
				return R.drawable.d25;
			case 26:
				weatherPhenomenon = "С����ѩ";
				return R.drawable.d26;
			case 27:
				weatherPhenomenon = "�е���ѩ";
				return R.drawable.d27;
			case 28:
				weatherPhenomenon = "�󵽱�ѩ";
				return R.drawable.d28;
			case 29:
				weatherPhenomenon = "����";
				return R.drawable.d29;
			case 30:
				weatherPhenomenon = "��ɳ";
				return R.drawable.d30;
			case 31:
				weatherPhenomenon = "ǿɳ����";
				return R.drawable.d31;
			case 53:
				weatherPhenomenon = "��";
				return R.drawable.d53;
			case 99:
				weatherPhenomenon = "��";
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
