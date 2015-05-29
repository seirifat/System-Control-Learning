package com.rifatkun.systemcontrollearning;

import android.os.Bundle;
import android.app.Activity;
import android.webkit.WebView;

public class KebutuhanDisainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_kebutuhan_disain);
		WebView wv1 = (WebView)findViewById(R.id.webView1);
		wv1.loadUrl("file:///android_asset/kebutuhandisain/File1-KebutuhanDisain.htm");
	}
}
