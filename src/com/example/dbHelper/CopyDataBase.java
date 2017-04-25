package com.example.dbHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import com.example.activity.R;
public class CopyDataBase {
	Context context;

	// 将res/raw中的城市数据库文件复制到安装的程序中的database目录下
	public CopyDataBase(Context context) {
		this.context = context;
		// 数据库的目录
		String dirPath = "/data/data/com.example.activity/databases";
		File dir = new File(dirPath);
		if (!dir.exists()) {
			dir.mkdir();
		}
		// 数据库文件
		File dbfile = new File(dir, "china.db");
		try {
			if (!dbfile.exists()) {
				dbfile.createNewFile();
			}
			// 加载欲导入的数据库
			// InputStream is = this.getApplicationContext().getResources()
			// .openRawResource(R.raw.db_weather);
			InputStream is = context.getResources().openRawResource(
					R.raw.china);
			FileOutputStream fos = new FileOutputStream(dbfile);
			byte[] buffere = new byte[is.available()];
			is.read(buffere);
			fos.write(buffere);
			is.close();
			fos.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
