package com.example.activity;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.example.tools.FormFile;
import com.example.tools.SocketHttpRequester;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class ShareActivity extends Activity {
	Button buttonShare;
	Button buttonShare_others;
	Intent intent;
	String shareData;// 气象数据
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 20 * 1000; // 超时时间
	private static final String CHARSET = "utf-8"; // 设置编码
	File upLoadFile;// 上传的图片文件
	String RequestURL = "http://192.168.137.1:8080/SSHDemo/UpLoad.action";

	ImageView imageView;
	EditText editText;
	static String result = null;// 返回的结果
	String flag = "";
	Bitmap bitmap;
	String pictruePath;// 截图文件路径
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.shareactivity);
		context = ShareActivity.this;
		intent = getIntent();
		shareData = intent.getStringExtra("today");
		pictruePath = intent.getStringExtra("pictruePath");
		// upLoadFile = new File(Environment.getExternalStorageDirectory(),
		// "a.mp3");

		buttonShare = (Button) findViewById(R.id.button_share_wuyou);
		buttonShare_others = (Button) findViewById(R.id.button_share);
		editText = (EditText) findViewById(R.id.getshareweatherdata);
		editText.setText(shareData);
		imageView = (ImageView) findViewById(R.id.sharepicture);
		try {
			bitmap = BitmapFactory.decodeStream(new FileInputStream(new File(
					pictruePath)));

		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		imageView.setImageBitmap(bitmap);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/* 打开图片文件夹 */
				Intent intent = new Intent();
				/* 开启Pictures画面Type设定为image */
				intent.setType("image/*");
				/* 使用Intent.ACTION_GET_CONTENT这个Action */
				intent.setAction(Intent.ACTION_GET_CONTENT);
				/* 取得相片后返回本画面 */
				startActivityForResult(intent, 1);
			}
		});
		buttonShare_others.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent intent2;
				intent2 = getIntent();
				intent2.setAction(Intent.ACTION_SEND);
				intent2.setType("text/plain");
				Uri uri = Uri.fromFile(new File(pictruePath));
				//intent2.putExtra(Intent.EXTRA_TEXT, "Shared from 吾友天气"
				//		+ shareData);// 气象数据
				 intent2.putExtra(Intent.EXTRA_STREAM, uri);
				Intent intent3 = Intent.createChooser(intent2, "分享");
				startActivity(intent3);

			}
		});
		// 吾友分享
		buttonShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/* 启动网络上传 */
				upLoadFile = new File(pictruePath);
				Log.d("uploadFile", "文件是存在" + upLoadFile);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.d("输出", "uri-------------->" + uri.toString());
			ContentResolver contentResolver = this.getContentResolver();
			String[] proj = { MediaStore.Images.Media.DATA };
			Cursor cursor = contentResolver.query(uri, proj, null, null, null);
			int index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToNext();
			pictruePath = cursor.getString(index);
			try {
				Bitmap bitmap = BitmapFactory.decodeStream(contentResolver
						.openInputStream(uri));
				imageView.setImageBitmap(bitmap);
			} catch (Exception e) {
				// TODO: handle exception
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	Runnable runnable = new Runnable() {

		@Override
		public void run() {
			Message msg = new Message();
			uploadFile(upLoadFile);
			handler.sendMessage(msg);

		}
	};
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			AlertDialog.Builder builder = new Builder(context);
			builder.setTitle("说明");
			builder.setMessage("分享成功");
			builder.setNegativeButton("确定", new OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// finish();

				}
			});
			builder.show();
			builder.create();
			super.handleMessage(msg);
		}

	};

	/**
	 * 上传图片到服务器
	 * 
	 * @param imageFile
	 *            包含路径
	 */
	public String uploadFile(File imageFile) {
		Log.i(TAG, "upload start");
		try {

			String requestUrl = "http://192.168.137.1:8080/SSHDemo/UpLoadAction.action";
			// 请求普通信息
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", "张三");
			params.put("pwd", "zhangsan");
			params.put("age", "21");
			params.put("fileName", imageFile.getName());
			// 上传文件
			FormFile formfile = new FormFile(imageFile.getName(), imageFile,
					"image", "application/octet-stream");

			flag = SocketHttpRequester.post(requestUrl, params, formfile);
			Log.i(TAG, "upload success");
		} catch (Exception e) {
			Log.i(TAG, "upload error");
			e.printStackTrace();
		}
		Log.i(TAG, "upload end");
		return flag;
	}

	public static int uploadFile(File file, String RequestURL) {
		int res = 0;

		// String BOUNDARY = UUID.randomUUID().toString(); // 边界标识 随机生成
		final String BOUNDARY = "---------------------------7da2137580612"; // 数据分隔线
		String PREFIX = "--";
		String LINE_END = "\r\n";
		// String CONTENT_TYPE = "multipart/form-data"; // 内容类型
		String CONTENT_TYPE = "application/octet-stream";
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // 允许输入流
			conn.setDoOutput(true); // 允许输出流
			conn.setUseCaches(false); // 不允许使用缓存
			conn.setRequestMethod("POST"); // 请求方式
			conn.setRequestProperty("Charset", CHARSET); // 设置编码
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			if (file != null) {
				/**
				 * 当文件不为空时执行上传
				 */
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * 这里重点注意： name里面的值为服务器端需要key 只有这个key 才可以得到对应的文件
				 * filename是文件的名字，包含后缀名
				 */
				sb.append("Content-Disposition: form-data; name=\"upload\"\r\n");
				sb.append("Content-Type: application/octet-stream; charset="
						+ CHARSET + LINE_END);
				sb.append(LINE_END);
				dos.write(sb.toString().getBytes());
				InputStream is = new FileInputStream(file);
				byte[] bytes = new byte[1024];
				int len = 0;
				while ((len = is.read(bytes)) != -1) {
					dos.write(bytes, 0, len);
				}
				is.close();
				dos.write(LINE_END.getBytes());
				byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINE_END)
						.getBytes();
				dos.write(end_data);
				dos.flush();
				/**
				 * 获取响应码 200=成功 当响应成功，获取响应的流
				 */
				res = conn.getResponseCode();
				Log.d(TAG, "response code:" + res);
				if (res == 200) {
					Log.e(TAG, "request success");
					BufferedReader buffer;
					String line = null;
					StringBuffer sb1 = new StringBuffer();
					buffer = new BufferedReader(new InputStreamReader(
							conn.getInputStream(), "UTF-8"));
					while ((line = buffer.readLine()) != null) {
						sb1.append(line);
					}
					result = sb1.toString();
					Log.e(TAG, "result : " + result);
				} else {
					Log.e(TAG, "request error");
				}
			}
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return res;
	}

}
