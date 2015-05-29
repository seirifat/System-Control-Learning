package com.rifatkun.systemcontrollearning;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Set;
import java.util.UUID;

//import org.apache.http.impl.client.RequestWrapper;

//import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.widget.Toast;

public class BluetoothClass{

	private BluetoothAdapter btAdapter;
	private BluetoothDevice btDevice;
	private BluetoothSocket btSocket;
	private Context context;
	private boolean btStatus;
	private boolean findStatus;
	private boolean connectSts;
	private OutputStream mmOutputStream;
	private InputStream mmInputStream;
	private Thread workerThread;
	private byte[] readBuffer;
	private int readBufferPosition;
	private volatile boolean stopWorker;
	private byte delimiter;
	private String _data;
	public boolean dataAccess;
	
	public BluetoothClass(Context context) {
		btAdapter = BluetoothAdapter.getDefaultAdapter();
		connectSts = false;
		dataAccess = false;
		this.context = context;
	}

	public String get_data() {
		return _data;
	}

	public void set_data(String _data) {
		this._data = _data;
	}

	public boolean checkBluetoothDevice()
	{
		if(btAdapter==null)
		{
			Toast.makeText(context, "Perangkat Tidak Mendukung Bluetooth", Toast.LENGTH_LONG).show();
			return false; 
		}
		else
		{
			return true;
		}
	}
	
	public boolean checkBluetoothStaus()
	{
		if(checkBluetoothDevice())
		{
			if(btAdapter.isEnabled())
			{
				btStatus = true;
			}
			else
			{
				btStatus = false;
			}
		}
		else
		{
			btStatus = false;
		}
		return btStatus;
	}
	
	
	public void TurnOffBluetooth()
	{
		btAdapter.disable();
	}
	
	public Intent TurnOnBluetooth()
	{
		Intent enBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
		return enBT;
	}
	
	public boolean find() throws IOException
	{
		btDevice = null;
		Set<BluetoothDevice> pairedDevices = btAdapter.getBondedDevices();
	    if(pairedDevices.size() > 0)
	    {
	        for(BluetoothDevice device : pairedDevices)
	        {
	            if(device.getAddress().equals("98:D3:31:30:0B:DE")) //Device
	        	//if(device.getAddress().equals("E4:D5:3D:CD:54:8B")) //Laptop
	        	//if(device.getAddress().equals("38:EC:E4:DF:74:3D")) //young
	            {
	            	btDevice = device;
	            	//Toast.makeText(context, "Bluetooth Ditemukan", Toast.LENGTH_SHORT).show();
	                break;
	            }
	        }
	        if(btDevice!=null)
	        {
	        	findStatus = true;
	        }
	        else
	        {
	        	//AlertDialog.Builder builder = new AlertDialog.Builder(context);
	        	//builder.setMessage("Bluetooth Tidak Ditemukan\n Alat Peraga");
	        	findStatus = false;
	        }
	    }
	    else
        {
        	//AlertDialog.Builder builder = new AlertDialog.Builder(context);
        	//builder.setMessage("Tidak Terhubung Dengan\n Alat Peraga");
        	findStatus = false;
        }
	    
		return findStatus;
	    	    
	}
	
	public boolean openBT() throws IOException
	{
		try {
		
			//Standard SerialPortService ID
			UUID uuid = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); 
		    
		    btSocket = btDevice.createRfcommSocketToServiceRecord(uuid);        
		    btSocket.connect();
		    mmOutputStream = btSocket.getOutputStream();
		    mmInputStream = btSocket.getInputStream();
		    //Toast.makeText(context, "Bluetooth Terhubung", Toast.LENGTH_SHORT).show();
		    connectSts = true;
		    //beginListenForData();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//Toast.makeText(context, "Perangkat Tidak Terhubung", Toast.LENGTH_SHORT).show();
			connectSts = false;
		}

	    return connectSts;
	}
	
	public void beginListenForData()
	{
	    final Handler handler = new Handler(); 
	    delimiter = '\n';
	    _data = "";
	    dataAccess = true;
	    stopWorker = false;
	    readBufferPosition = 0;
	    readBuffer = new byte[1024];
	    workerThread = new Thread(new Runnable()
	    {
	        public void run()
	        {                
	           while(!Thread.currentThread().isInterrupted() && !stopWorker)
	           {
	                try 
	                {
	                    int bytesAvailable = mmInputStream.available();                        
	                    if(bytesAvailable > 0)
	                    {
	                        byte[] packetBytes = new byte[bytesAvailable];
	                        mmInputStream.read(packetBytes);
	                        for(int i=0;i<bytesAvailable;i++)
	                        {
	                            byte b = packetBytes[i];
	                            if(b == delimiter)
	                            {
	                            	byte[] encodedBytes = new byte[readBufferPosition];
	                            	System.arraycopy(readBuffer, 0, encodedBytes, 0, encodedBytes.length);
	                            	final String data = new String(encodedBytes, "US-ASCII");
	                            	
	                            	readBufferPosition = 0;

	                                handler.post(new Runnable()
	                                {
	                                    public void run()
	                                    {
	                                    	//f = (float) 5.0;
	                                        _data = data;
	                                        
	                                    }
	                                });
	                                
	                            }
	                            else
	                            {
	                                readBuffer[readBufferPosition++] = b;
	                            }
	                        }
	                    }
	                } 
	                catch (IOException ex) 
	                {
	                    stopWorker = true;
	                    dataAccess = false;
	                }
	           }
	        }
	    });

	    workerThread.start();
	}
	
	@SuppressWarnings("deprecation")
	public void stopWorkThread()
	{
		workerThread.stop();
	}
	
	public void startWorkThred()
	{
		workerThread.start();
	}
	
	public void sendData(String s,short c, short p, short i, short d) throws IOException
	{
		//s += "\n";
	    mmOutputStream.write(s.getBytes());
	    
	    //PID Mode
	    //Tersedia apabila alat peraga sudah bisa menjalankan 2 algoritma
	    //mmOutputStream.write('p');
	    
	    mmOutputStream.write((byte) (c >> 8));
	    mmOutputStream.write((byte) (c));
	    mmOutputStream.write((byte) (p >> 8));
	    mmOutputStream.write((byte) (p));
	    mmOutputStream.write((byte) (i >> 8));
	    mmOutputStream.write((byte) (i));
	    mmOutputStream.write((byte) (d >> 8));
	    mmOutputStream.write((byte) (d));
	    mmOutputStream.write(
	    		c^(c>>8)^
	    		p^(p>>8)^
	    		i^(i>>8)^
	    		d^(d>>8)
	    		);
	    //delimiter = ':';
	    Toast.makeText(context, "terkirim", Toast.LENGTH_SHORT).show();
	}
	
	public void sendData2(String s,short PSI, short DPSI, short X) throws IOException
	{
		//s += "\n";
	    mmOutputStream.write(s.getBytes());
	    
	    //Fuzzy Mode
	    //mmOutputStream.write('f');
	    
	    mmOutputStream.write((byte) (PSI >> 8));
	    mmOutputStream.write((byte) (PSI));
	    mmOutputStream.write((byte) (DPSI >> 8));
	    mmOutputStream.write((byte) (DPSI));
	    mmOutputStream.write((byte) (X >> 8));
	    mmOutputStream.write((byte) (X));
	    mmOutputStream.write(
	    		PSI^(PSI>>8)^
	    		DPSI^(DPSI>>8)^
	    		X^(X>>8)
	    		);
	    
	    //delimiter = ':';
	    Toast.makeText(context, "terkirim", Toast.LENGTH_SHORT).show();
	}
	
	public void CloseConection() throws IOException
	{
		if(connectSts == true)
		{
			stopWorker = true;
			mmOutputStream.close();
			mmInputStream.close();
			btSocket.close();
			connectSts = false;
			Toast.makeText(context, "Koneksi Ditutup", Toast.LENGTH_SHORT).show();
		}
	}
		
}
