package com.rifatkun.systemcontrollearning;



import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class UjiMateriActvity extends Activity {

	private int nilai;
	private int point;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_uji_materi_actvity);
	
		final RadioButton rb01 = (RadioButton)findViewById(R.id.Rsatu1);
		final RadioButton rb02 = (RadioButton)findViewById(R.id.Rdua2);
		final RadioButton rb03 = (RadioButton)findViewById(R.id.Rtiga2);
		final RadioButton rb04 = (RadioButton)findViewById(R.id.Rempat1);
		final RadioButton rb05 = (RadioButton)findViewById(R.id.Rlima2);
		final RadioButton rb06 = (RadioButton)findViewById(R.id.Renam2);
		final RadioButton rb07 = (RadioButton)findViewById(R.id.Rtujuh1);
		final RadioButton rb08 = (RadioButton)findViewById(R.id.Rdelapan2);
		final RadioButton rb09 = (RadioButton)findViewById(R.id.Rsembilan1);
		final RadioButton rb10 = (RadioButton)findViewById(R.id.Rsepuluh2);
		
		
		point = 0;
		
		Button btn = (Button)findViewById(R.id.BTNSelesaiUji);
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if(rb01.isChecked())
				{
					point += 10;
				}
				if(rb02.isChecked())
				{
					point += 10;
				}
				if(rb03.isChecked())
				{
					point += 10;
				}
				if(rb04.isChecked())
				{
					point += 10;
				}
				if(rb05.isChecked())
				{
					point += 10;
				}
				if(rb06.isChecked())
				{
					point += 10;
				}
				if(rb07.isChecked())
				{
					point += 10;
				}
				if(rb08.isChecked())
				{
					point += 10;
				}
				if(rb09.isChecked())
				{
					point += 10;
				}
				if(rb10.isChecked())
				{
					point += 10;
				}
				nilai = point;
				
				AlertDialog.Builder builder = new AlertDialog.Builder(UjiMateriActvity.this);
	        	builder.setTitle("Hasil");
				builder.setMessage("Nilai : "+nilai);
	        	builder.setNeutralButton("Selesai", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						finish();
					}
				});
	        	builder.show();
			}
		});
		
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//this.finish();
	}

	@SuppressWarnings("unused")
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if(false)
		{
			super.onBackPressed();
			return;
		}
		
	}

}
