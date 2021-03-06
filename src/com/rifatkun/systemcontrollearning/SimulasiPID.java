package com.rifatkun.systemcontrollearning;

import java.io.IOException;
import org.achartengine.GraphicalView;
import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class SimulasiPID extends Activity{

	//private String dataTeks;
	private static GraphicalView view;
	private BluetoothClass _bluetooth;
	//private TextView tv01;
	private LinearLayout GraphLayout;
	private Point p;
	private int i;
	private Handler handler;
	private boolean backButton = false;
	private boolean stsBTNconn = true;
	private boolean stsBTNmulai = true;
	private Runnable timedTask;
	private GrafikAlgoPID gaPID;
	private DataAlgoritma dataAlgoritma;
	//private boolean b=true;
	private AsyncTaskBluetooth atb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simulasi_pid);
		Initial();
		
		
	}
	
	private void Initial() {
		// TODO Auto-generated method stub
		_bluetooth = new BluetoothClass(SimulasiPID.this);
		
		handler = new Handler();
		
		//Nilai
		final EditText etNilaiP = (EditText)findViewById(R.id.etNilaiP);
		final EditText etNilaiI = (EditText)findViewById(R.id.etNilaiI);
		final EditText etNilaiD = (EditText)findViewById(R.id.etNilaiD);
		final EditText etNilaiC = (EditText)findViewById(R.id.etNilaiC);
		
		final Button btnConnect = (Button)findViewById(R.id.btnConnect);
		final Button btnProses = (Button)findViewById(R.id.btnProses);

		i = 0;
		GraphLayout = (LinearLayout)findViewById(R.id.PIDGraphView);
		gaPID = new GrafikAlgoPID();
		dataAlgoritma = new DataAlgoritma();
		
		btnConnect.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(stsBTNconn==true)
				{
					atb = new AsyncTaskBluetooth(getParent().getParent(),btnConnect,stsBTNconn,_bluetooth);
					atb.execute();
					stsBTNconn = false;
					//Toast.makeText(SimulasiPID.this, String.valueOf(stsBTNconn), Toast.LENGTH_SHORT).show();
				}
				else
				{
					try {
						_bluetooth.CloseConection();
						btnConnect.setText("Connect");
						stsBTNconn = true;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		btnProses.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if(stsBTNconn==false)
				{
					if(etNilaiC.getText().toString().isEmpty() ||
							etNilaiD.getText().toString().isEmpty() ||
							etNilaiI.getText().toString().isEmpty() ||
							etNilaiP.getText().toString().isEmpty())
					{
						Toast.makeText(SimulasiPID.this, "Nilai Tidak Boleh Kosong", Toast.LENGTH_SHORT).show();
					}
					else
					{
						if(stsBTNmulai==true)
						{
							try {
								//if(stsHandler == false)
								//{
									stsBTNmulai = false;
									btnProses.setText("Berhenti");
									
									new Handler().postDelayed(new Runnable() {			
										@Override
										public void run() {
											// TODO Auto-generated method stub
											handler.post(timedTask);
										}
									}, 1500);
									//stsHandler = true;
								//}
									
								//Pilih Algoritma
								//Send Nilai
									_bluetooth.sendData("U",
											Short.parseShort(etNilaiC.getText().toString()),
											Short.parseShort(etNilaiP.getText().toString()),
											Short.parseShort(etNilaiI.getText().toString()),
											Short.parseShort(etNilaiD.getText().toString()));
									
										
									
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						else
						{
							handler.removeCallbacks(timedTask);
							stsBTNmulai = true;
							//stsHandler = false;
							btnProses.setText("Proses");
						}
					}
				}
				else
				{
					//Toast.makeText(SimulasiPID.this, "Perangkat Belum Terhubung", Toast.LENGTH_SHORT).show();
					AlertDialog.Builder builder = new AlertDialog.Builder(getParent().getParent());
		        	builder.setMessage("Perangkat Belum Terhubung");
		        	builder.setNeutralButton("Kembali", null);
		        	builder.show();
				}
			}
		});
		
		
		
		//handler.post(runData);
		timedTask = new Runnable(){
			@Override
			  public void run() {
			   // TODO Auto-generated method stub
				if(_bluetooth.get_data()!=null && _bluetooth.get_data()!="")
				{
					p = dataAlgoritma.getDataFromReciver(i,Float.parseFloat(_bluetooth.get_data()));
					gaPID.addViewPoints(p);
					view.repaint();
					i++;
				}
				_bluetooth.set_data("");
			    handler.postDelayed(this, 50);
			  }};
	}

	@Override
	public void onBackPressed() {
		
		if(backButton)
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
		
		this.backButton = true;
		Toast.makeText(SimulasiPID.this, "Tekan tombol 'Kemabali' sekali lagi untuk keluar", Toast.LENGTH_SHORT).show();
		
		new Handler().postDelayed(new Runnable() {
			
			@Override
			public void run() {
				backButton = false;
			}
		}, 2000);
		
	}
	
	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		view = gaPID.getView(this);
		GraphLayout.addView(view);
		//setContentView(view);
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

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
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
