package com.rifatkun.systemcontrollearning;

import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebView;

public class Pendahuluan extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pendahuluan);
		WebView wv1 = (WebView)findViewById(R.id.wvPendahuluan);
		wv1.loadUrl("file:///android_asset/pendahuluan/pendahuluan.htm"); 
	}

}
