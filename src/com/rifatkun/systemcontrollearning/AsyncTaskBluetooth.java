package com.rifatkun.systemcontrollearning;

import java.io.IOException;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.Toast;

public class AsyncTaskBluetooth extends AsyncTask<Void, Void, String>{

	 private Context context;
	 private Button btnConnect;
	 private boolean stsBTNconn;
	 private ProgressDialog progresDialog;
	 private BluetoothClass _bluetooth;
	
	public AsyncTaskBluetooth(Context context,Button btnConnect,boolean stsBTNconn, BluetoothClass _bluetooth)
	{
		
		this.context = context;
		this.btnConnect = btnConnect;
		this.stsBTNconn = stsBTNconn;
		progresDialog = new ProgressDialog(this.context);
		this._bluetooth = _bluetooth;
	}
	
	@Override 
    protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		//Toast.makeText(context, "Terhubung", Toast.LENGTH_SHORT).show();
		progresDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		//progresDialog.setTitle("Harap Tunggu");
		progresDialog.setMessage("Menghubungkan");
		progresDialog.setCancelable(true);
		progresDialog.show();
	}
	
	@Override 
    protected String doInBackground(Void... params) {
		// TODO Auto-generated method stub
		String r = "0";
		try {
			if(_bluetooth.find())
			{
				if(_bluetooth.openBT())
				{
					r="1";
				}
				else
				{
					r="2";
				}
			}
			else
			{
				r="3";
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			r = e.toString();
		}
		
		return r;
	}



	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		progresDialog.dismiss();
		if(result == "1")
		{
			btnConnect.setText("Disconnect");
			//this.stsBTNconn = false;
			Toast.makeText(context, "Terhubung", Toast.LENGTH_SHORT).show();
			_bluetooth.beginListenForData();
		}
		else if(result == "2")
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
        	builder.setMessage("Gagal Terhubung");
        	builder.setNeutralButton("Kembali", null);
        	builder.show();
		}
		else if(result == "3")
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
        	builder.setMessage("Blutooth belum pair");
        	builder.setNeutralButton("Kembali", null);
        	builder.show();
		}
		else
		{
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
        	builder.setMessage("Error : "+result);
        	builder.setNeutralButton("Kembali", null);
        	builder.show();
		}
	}


	public boolean statusBTN()
	{
		return stsBTNconn;
	}



}
