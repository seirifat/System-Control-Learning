package com.rifatkun.systemcontrollearning;

import java.io.IOException;


import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.Toast;
import android.widget.TabHost.TabSpec;

@SuppressWarnings("deprecation")
public class MainActivity extends TabActivity {

private BluetoothClass _bluetooth;
private TabHost tabhost;
private boolean backButton = false;
private int tabIndex;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//setContentView(R.layout.activity_main);
		initial();
		_bluetooth = new BluetoothClass(this);
		if(!_bluetooth.checkBluetoothDevice())
		{
			finish();
		}
		else
		{
			if(_bluetooth.checkBluetoothStaus()==false)
			{
				startActivityForResult(_bluetooth.TurnOnBluetooth(), 1);
			}
			else
			{
				Toast.makeText(MainActivity.this, "Bluetooth Telah Aktif", Toast.LENGTH_SHORT).show();
//				try {
//					_bluetooth.find();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
		}
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if(backButton )
		{
			try 
			{
				_bluetooth.CloseConection();
			} 
			catch (IOException e) 
			{
				e.printStackTrace();
			}
			super.onBackPressed();
			return;
		}
		
		backButton = true;
		Toast.makeText(MainActivity.this, "Tekan tombol 'Kembali' sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
		
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				backButton = false;
			}
		}, 2000);
		
	}

	private void initial() {
		tabhost = getTabHost();
		
		TabSpec pendahuluanSpec = tabhost.newTabSpec("Pendahuluan");
		pendahuluanSpec.setIndicator("Pendahuluan");
		pendahuluanSpec.setContent(new Intent(this,Pendahuluan.class));
		
		TabSpec pemodelanSpec = tabhost.newTabSpec("Pemodelan");
		pemodelanSpec.setIndicator("Pemodelan");
		pemodelanSpec.setContent(new Intent(this,PemodelanActivity.class));
		
		TabSpec penentuanSpec = tabhost.newTabSpec("Kebutuhan Disain");
		penentuanSpec.setIndicator("Kebutuhan Disain");
		penentuanSpec.setContent(new Intent(this,KebutuhanDisainActivity.class));
		
		TabSpec kontrolSpec = tabhost.newTabSpec("Materi Kontrol");
		kontrolSpec.setIndicator("Materi Kontrol");
		kontrolSpec.setContent(new Intent(this,MateriTab.class));
		
		TabSpec Simulasi = tabhost.newTabSpec("Simulasi");
		Simulasi.setIndicator("Simulasi");
		Simulasi.setContent(new Intent(this,SimulasiTab.class));
		
		TabSpec ujiMateri = tabhost.newTabSpec("Uji Materi");
		ujiMateri.setIndicator("Uji Materi");
		ujiMateri.setContent(new Intent(this,UjiMateriActvity.class));
		
		tabhost.addTab(pendahuluanSpec);
		tabhost.addTab(pemodelanSpec);
		tabhost.addTab(penentuanSpec);
		tabhost.addTab(kontrolSpec);
		tabhost.addTab(Simulasi);
		tabhost.addTab(ujiMateri);
		tabIndex = tabhost.getCurrentTab();
		tabhost.setOnTabChangedListener(new OnTabChangeListener() {
			
			@Override
			public void onTabChanged(String arg0) {
				// TODO Auto-generated method stub
				
				switch (tabhost.getCurrentTab()) {
				case 0:
					tabIndex = tabhost.getCurrentTab();
					break;
				case 1:
					tabIndex = tabhost.getCurrentTab();				
					break;
				case 2:
					tabIndex = tabhost.getCurrentTab();
					break;
				case 3:
					tabIndex = tabhost.getCurrentTab();
					break;
				case 4:
					tabIndex = tabhost.getCurrentTab();
					break;
	
				case 5:
					tabhost.setCurrentTab(tabIndex);
					Intent Iuji = new Intent(MainActivity.this, UjiMateriActvity.class);
					startActivity(Iuji);
					break;

				default:
					break;
				}
			}
		});
		
		tabhost.getTabWidget().getChildAt(0).setBackgroundResource(R.drawable.tab_selector);
		tabhost.getTabWidget().getChildAt(1).setBackgroundResource(R.drawable.tab_selector);
		tabhost.getTabWidget().getChildAt(2).setBackgroundResource(R.drawable.tab_selector);
		tabhost.getTabWidget().getChildAt(3).setBackgroundResource(R.drawable.tab_selector);
		tabhost.getTabWidget().getChildAt(4).setBackgroundResource(R.drawable.tab_selector);
		tabhost.getTabWidget().getChildAt(5).setBackgroundResource(R.drawable.tab_selector);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode == 1)
		{
			if(resultCode == RESULT_OK)
			{
				Toast.makeText(getApplicationContext(), 
						"Bluetooth Telah Aktif", Toast.LENGTH_SHORT).show();
//				try {
//					_bluetooth.find();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
			}
			else if(resultCode == RESULT_CANCELED)
			{
				Toast.makeText(getApplicationContext(),"Error. Aplikasi Membutuhkan Bluetooth", Toast.LENGTH_SHORT).show();
				finish();
			}
		}
	}
	

	
	
	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		try 
		{
			_bluetooth.CloseConection();
		} 
		catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
