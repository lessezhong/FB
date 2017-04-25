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
	String shareData;// ��������
	private static final String TAG = "uploadFile";
	private static final int TIME_OUT = 20 * 1000; // ��ʱʱ��
	private static final String CHARSET = "utf-8"; // ���ñ���
	File upLoadFile;// �ϴ���ͼƬ�ļ�
	String RequestURL = "http://192.168.137.1:8080/SSHDemo/UpLoad.action";

	ImageView imageView;
	EditText editText;
	static String result = null;// ���صĽ��
	String flag = "";
	Bitmap bitmap;
	String pictruePath;// ��ͼ�ļ�·��
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
				/* ��ͼƬ�ļ��� */
				Intent intent = new Intent();
				/* ����Pictures����Type�趨Ϊimage */
				intent.setType("image/*");
				/* ʹ��Intent.ACTION_GET_CONTENT���Action */
				intent.setAction(Intent.ACTION_GET_CONTENT);
				/* ȡ����Ƭ�󷵻ر����� */
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
				//intent2.putExtra(Intent.EXTRA_TEXT, "Shared from ��������"
				//		+ shareData);// ��������
				 intent2.putExtra(Intent.EXTRA_STREAM, uri);
				Intent intent3 = Intent.createChooser(intent2, "����");
				startActivity(intent3);

			}
		});
		// ���ѷ���
		buttonShare.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				/* ���������ϴ� */
				upLoadFile = new File(pictruePath);
				Log.d("uploadFile", "�ļ��Ǵ���" + upLoadFile);
				Thread thread = new Thread(runnable);
				thread.start();
			}
		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK) {
			Uri uri = data.getData();
			Log.d("���", "uri-------------->" + uri.toString());
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
			builder.setTitle("˵��");
			builder.setMessage("����ɹ�");
			builder.setNegativeButton("ȷ��", new OnClickListener() {

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
	 * �ϴ�ͼƬ��������
	 * 
	 * @param imageFile
	 *            ����·��
	 */
	public String uploadFile(File imageFile) {
		Log.i(TAG, "upload start");
		try {

			String requestUrl = "http://192.168.137.1:8080/SSHDemo/UpLoadAction.action";
			// ������ͨ��Ϣ
			Map<String, String> params = new HashMap<String, String>();
			params.put("username", "����");
			params.put("pwd", "zhangsan");
			params.put("age", "21");
			params.put("fileName", imageFile.getName());
			// �ϴ��ļ�
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

		// String BOUNDARY = UUID.randomUUID().toString(); // �߽��ʶ �������
		final String BOUNDARY = "---------------------------7da2137580612"; // ���ݷָ���
		String PREFIX = "--";
		String LINE_END = "\r\n";
		// String CONTENT_TYPE = "multipart/form-data"; // ��������
		String CONTENT_TYPE = "application/octet-stream";
		try {
			URL url = new URL(RequestURL);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(TIME_OUT);
			conn.setConnectTimeout(TIME_OUT);
			conn.setDoInput(true); // ����������
			conn.setDoOutput(true); // ���������
			conn.setUseCaches(false); // ������ʹ�û���
			conn.setRequestMethod("POST"); // ����ʽ
			conn.setRequestProperty("Charset", CHARSET); // ���ñ���
			conn.setRequestProperty("connection", "keep-alive");
			conn.setRequestProperty("Content-Type", CONTENT_TYPE + ";boundary="
					+ BOUNDARY);
			if (file != null) {
				/**
				 * ���ļ���Ϊ��ʱִ���ϴ�
				 */
				DataOutputStream dos = new DataOutputStream(
						conn.getOutputStream());
				StringBuffer sb = new StringBuffer();
				sb.append(PREFIX);
				sb.append(BOUNDARY);
				sb.append(LINE_END);
				/**
				 * �����ص�ע�⣺ name�����ֵΪ����������Ҫkey ֻ�����key �ſ��Եõ���Ӧ���ļ�
				 * filename���ļ������֣�������׺��
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
				 * ��ȡ��Ӧ�� 200=�ɹ� ����Ӧ�ɹ�����ȡ��Ӧ����
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
