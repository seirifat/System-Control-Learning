package com.rifatkun.systemcontrollearning;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;

public class PemodelanActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pemodelan);
		WebView wv1 = (WebView)findViewById(R.id.webView1);
		wv1.loadUrl("file:///android_asset/pemodelan/File1-Pemodelan.htm"); 
    }

}
