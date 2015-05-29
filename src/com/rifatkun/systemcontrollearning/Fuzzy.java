package com.rifatkun.systemcontrollearning;

import android.os.Bundle;
import android.app.Activity;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;

public class Fuzzy extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_fuzzy);
		
		WebView wv1 = (WebView)findViewById(R.id.FuzzywebView);
		wv1.loadUrl("file:///android_asset/materifuzzy/materifuzzy.htm");
	}

}
