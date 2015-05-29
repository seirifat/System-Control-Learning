package com.rifatkun.systemcontrollearning;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;
import android.app.TabActivity;
import android.content.Intent;

@SuppressWarnings("deprecation")
public class SimulasiTab extends TabActivity {

	private TabHost tabhost;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//setContentView(R.layout.activity_simulasi_tab);
		initial();
	}


private void initial() {
	tabhost = getTabHost();
	
	TabSpec SimulasiPID = tabhost.newTabSpec("Simulasi PID");
	SimulasiPID.setIndicator("Simulasi PID");
	SimulasiPID.setContent(new Intent(this,SimulasiPID.class));
	
	TabSpec SimulasiFuzzy = tabhost.newTabSpec("Simulasi Fuzzy");
	SimulasiFuzzy.setIndicator("Simulasi Fuzzy");
	SimulasiFuzzy.setContent(new Intent(this,SimulasiFuzzy.class));
	
	tabhost.addTab(SimulasiPID);
	tabhost.addTab(SimulasiFuzzy);
}

}
