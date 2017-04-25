package others;

import android.content.Context;
import android.os.AsyncTask;

public class MyAsycTask extends AsyncTask<String, Void, Void>{
	Context context;
	public MyAsycTask(Context c) {
		context=c;
	}

	@Override
	protected Void doInBackground(String... params) {
		// TODO Auto-generated method stub
		return null;
	}
	private void onsuccess() {
		

	}

}
