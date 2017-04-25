package com.example.tools;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.os.Environment;

public class CapturePicture {
	public static File picture;
	public static String FileName = System.currentTimeMillis() + ".jpg";
	public static String FilePath = Environment.getExternalStorageDirectory()
			.getAbsolutePath() + "/Temp";

	public static String Capture(Bitmap bitmap) {

		try {

			// picture = new File(Environment.getExternalStorageDirectory(),
			// FileName);
			File dir = new File(FilePath);
			if (!dir.exists()) {
				dir.mkdir();
			}
			picture = new File(dir, FileName);
			if (!picture.exists()) {
				picture.createNewFile();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(picture);
			bitmap.compress(Bitmap.CompressFormat.JPEG, 80, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();
		} catch (Exception e) {
			System.err.println(e.toString());
		}
		return picture.getAbsolutePath();

	}

}
