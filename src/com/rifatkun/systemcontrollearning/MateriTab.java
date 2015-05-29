package com.rifatkun.systemcontrollearning;

import android.os.Bundle;
import android.app.TabActivity;
import android.content.Intent;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MateriTab extends TabActivity {

	private TabHost tabhost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_materi_tab);
		initial();
	}
	
	private void initial() {
		tabhost = getTabHost();
		
		TabSpec KontrolPID = tabhost.newTabSpec("Kontrol PID");
		KontrolPID.setIndicator("Kontrol PID");
		KontrolPID.setContent(new Intent(this,PID.class));
		
		TabSpec KontrolFuzzy = tabhost.newTabSpec("Kontrol Fuzzy");
		KontrolFuzzy.setIndicator("Kontrol Fuzzy");
		KontrolFuzzy.setContent(new Intent(this,Fuzzy.class));
		
		tabhost.addTab(KontrolPID);
		tabhost.addTab(KontrolFuzzy);
	}
	
}
