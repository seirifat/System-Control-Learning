package com.rifatkun.systemcontrollearning;

import android.os.Bundle;
import android.webkit.WebView;
import android.app.Activity;

public class PID extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pid);
		WebView wv1 = (WebView)findViewById(R.id.webView1);
		wv1.loadUrl("file:///android_asset/materipid/MateriPID.htm");
	}


}
