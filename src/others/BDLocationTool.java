package others;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.example.Set.MyConfiguration;
import com.example.activity.ShowProvinceActivity;

public class BDLocationTool {
	LocationClient myLocationClient;
	Context context;
	public StringBuffer sb = new StringBuffer(256);
	public BDLocation location;
	public String simpleName;
	ProgressDialog progressDialog;

	public BDLocationTool(Context context) {
		this.context = context;
		myLocationClient = new LocationClient(context);
		myLocationClient.setAK("lohqPAWXx4UpDR90K6Ef7M6Y");
		myLocationClient.registerLocationListener(new MyLocationListenner());

	}

	public void StartLoact() {
		progressDialog = new ProgressDialog(context);
		progressDialog.setMessage("���ڶ�λ��");
		progressDialog.show();
		setLocationOption();
		myLocationClient.start();
	//	myLocationClient.requestLocation();
		

	}

	public class MyLocationListenner implements BDLocationListener {
		@Override
		/**
		 * ��ȡ��λ���
		 */
		public void onReceiveLocation(BDLocation location) {
			if (location == null) {

			}

			if (location.getLocType() == BDLocation.TypeGpsLocation) {
				sb.append("time : ");
				sb.append(location.getTime());
				sb.append("\n error code : ");
				sb.append(location.getLocType());
				sb.append("\n latitude : ");
				sb.append(location.getLatitude());
				sb.append("\n lontitude : ");
				sb.append(location.getLongitude());
				sb.append("\n radius : ");
				sb.append(location.getRadius());
				sb.append("\n speed : ");
				sb.append(location.getSpeed());
				sb.append("\n satellite : ");
				sb.append(location.getSatelliteNumber());
			} else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// ��������
				/**
				 * ��ʽ����ʾ��ַ��Ϣ
				 */
				simpleName = location.getCity().substring(0, 2);
				sb.append(location.getProvince());
				sb.append(location.getCity());
				sb.append(location.getDistrict());
			}

			Log.i("TAG", sb.toString());
			progressDialog.hide();
			Dialog();//��ʾѡ��Ի���

		}

		@Override
		public void onReceivePoi(BDLocation bd) {
			String a = bd.getAltitude() + "onReceivePoiγ��";
			Log.d("TAG", a);
		}

	}

	private void setLocationOption() {
		LocationClientOption option = new LocationClientOption();
		option.setServiceName("com.baidu.location.service_v2.9");
		option.setAddrType("all");
		option.setPoiNumber(10);
		option.disableCache(true);
		myLocationClient.setLocOption(option);
	}

	private void Dialog() {
		progressDialog.hide();
		AlertDialog.Builder builder = new Builder(context);
		builder.setTitle("����ǰ��λ��Ϊ");
		builder.setMessage(sb.toString());
		builder.setPositiveButton("��", new chosecity());
		builder.setNegativeButton("���Լ�ѡ��",
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						Intent intent = new Intent(context,
								ShowProvinceActivity.class);
						context.startActivity(intent);

					}
				});
		builder.show();
		builder.create();
	}

	/* ��ת����ʾʡ�ݵ�activity */
	class chosecity implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface arg0, int arg1) {
			
			SharedPreferences.Editor editor;
			MyConfiguration configuration = new MyConfiguration(context);
			editor = configuration.editPreference();
			editor.putString("location", sb.toString());
			editor.commit();
			Intent intent = new Intent();
			intent.putExtra("name", simpleName);
			intent.setClass(context, ShowProvinceActivity.class);
			context.startActivity(intent);

		}
	}

	public String SimpleName() {
		return simpleName;

	}
}
