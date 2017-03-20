package com.AmoRgbLight.bluetooth.le;

import java.nio.ByteBuffer;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;

import android.graphics.Paint;

import android.os.Bundle;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceView;
import android.view.View;
import android.widget.CompoundButton;

import android.widget.SeekBar;
import android.widget.ToggleButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import android.os.Bundle;  
import android.os.Handler;  
import android.os.Message; 



public class RgbActivity extends Activity implements View.OnClickListener{
	private final static String TAG = "MainActivity";
	private final String ACTION_NAME_RSSI = "AMOMCU_RSSI"; 	// 其他文件广播的定义必须一致
	private final String ACTION_CONNECT = "AMOMCU_CONNECT"; 	// 其他文件广播的定义必须一致

	private SeekBar seekBar_brightness;
	private TextView textView_brightness; 
	
	private SeekBar seekBar_red;
	private SeekBar seekBar_green;
	private SeekBar seekBar_blue;
	private TextView textView_red; 
	private TextView textView_green;
	private TextView textView_blue;

	
	int mWhite = 0;			//白色 0~255  ------这个颜色在我们的amomcu的蓝牙灯板子上无效，不过考虑到有些朋友想利用，我这里是留出了这个接口
	int mRed = 255;			//红色 0~255
	int mGreen = 0;			//绿色 0~255
	int mBlue = 0;			//蓝色 0~255
	
	int mBrightness = 100;	    			// 这个是亮度的意思 0~100; 0最黑， 100最亮
	int mBrightness_old = mBrightness;	    // 这个是亮度的意思 0~100; 0最黑， 100最亮

	public final static int WIDTH =256;
	public final static int HEIGHT = 36;	
	private Paint mPaint  = new Paint();
	private byte[] rgb565VideoData = new byte[WIDTH*HEIGHT*4];
	private ByteBuffer image_byteBuf = ByteBuffer.wrap(rgb565VideoData);
	private Bitmap prev_image = Bitmap.createBitmap(WIDTH, HEIGHT, Bitmap.Config.ARGB_8888); 	
	
    // 根据rssi 值计算距离， 只是参考作用， 不准确---amomcu
	static final int rssibufferSize = 10;
	int[] rssibuffer = new int[rssibufferSize];
	int rssibufferIndex = 0;
	boolean rssiUsedFalg = false;
	
	// 用于消息更新参数
	public static final int REFRESH = 0x000001;  
    private Handler mHandler = null;  	
    
    // 开关灯
	ToggleButton toggle_onoff;
	int light_onoff = 1;
	
	// 此 文件中一开始就最先执行的函数就是  onCreate
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.amo_rgb_activity);

		seekBar_brightness = (SeekBar) findViewById(R.id.seekbar_brightness);
		textView_brightness = (TextView) findViewById(R.id.txt_brightness);
		
		seekBar_red = (SeekBar) findViewById(R.id.seekbar_red);
		seekBar_green = (SeekBar) findViewById(R.id.seekbar_green);
		seekBar_blue = (SeekBar) findViewById(R.id.seekbar_blue);
		textView_red = (TextView) findViewById(R.id.txt_color_red);
		textView_green = (TextView) findViewById(R.id.txt_color_green);
		textView_blue = (TextView) findViewById(R.id.txt_color_blue);	

		
		registerBoradcastReceiver();	
		
		// 操作
		mHandler = new Handler() {  
	        @Override  
	        public void handleMessage(Message msg) {  
	            if (msg.what == REFRESH) {  
	            	SetColor2Device(mWhite, mRed, mGreen, mBlue, mBrightness);  
					UpdateProgress();
					UpdateText();
	            }  
	            super.handleMessage(msg);  
	        }  
	    };      

		
		// 连读百分比函数 		
		seekBar_brightness.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			//第一个时OnStartTrackingTouch,在进度开始改变时执行
			@Override
	        public void onStartTrackingTouch(SeekBar seekBar) {
			   // TODO Auto-generated method stub	       
	        }			 
			//第二个方法onProgressChanged是当进度发生改变时执行
	        @Override
	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {      
		        int i= seekBar.getProgress();
		        textView_brightness.setText("Brightness(P0_6 PWM): "+Integer.toHexString(i).toUpperCase() + "%");
				Log.i(TAG, "brightness = " + Integer.toHexString(i).toUpperCase());
				mBrightness = (byte) i;
	            UpdateAllParameter();
	        }	        
			//第三个是onStopTrackingTouch,在停止拖动时执行
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {
	        }
		});	
		
		// 红色分量 监听
		seekBar_red.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			//第一个时OnStartTrackingTouch,在进度开始改变时执行			 
		    @Override
	        public void onStartTrackingTouch(SeekBar seekBar) {
			   // TODO Auto-generated method stub	       
	        }			 
			//第二个方法onProgressChanged是当进度发生改变时执行
	        @Override
	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        // TODO Auto-generated method stub		        
		        int i= seekBar.getProgress();
		        //textView_red.setTextColor(0xFFFF0000);
		        textView_red.setText("P0_7 PWM:"+Integer.toHexString(i).toUpperCase());
				Log.i(TAG, "color red = " + Integer.toHexString(i).toUpperCase());
				mRed = i;				
				UpdateAllParameter();
	        }	        
			//第三个是onStopTrackingTouch,在停止拖动时执行
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {
	        }
		});			
		
		// 绿色分量监听
		seekBar_green.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			//第一个时OnStartTrackingTouch,在进度开始改变时执行			 
			@Override
	        public void onStartTrackingTouch(SeekBar seekBar) {
			   // TODO Auto-generated method stub	       
	        }			 
			//第二个方法onProgressChanged是当进度发生改变时执行
	        @Override
	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        // TODO Auto-generated method stub		        
		        int i= seekBar.getProgress();
		        //textView_green.setTextColor(0xFF00FF00);
		        textView_green.setText("P1_0 PWM:"+Integer.toHexString(i).toUpperCase());
		        Log.i(TAG, "color green = " + Integer.toHexString(i).toUpperCase());
				mGreen  = i;
	        
	            UpdateAllParameter();
	        }	        
			//第三个是onStopTrackingTouch,在停止拖动时执行
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {
	        }
		});		
		
		// 蓝色分量监听
		seekBar_blue.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){
			//第一个时OnStartTrackingTouch,在进度开始改变时执行
		   @Override
	        public void onStartTrackingTouch(SeekBar seekBar) {
			   // TODO Auto-generated method stub	       
	        }			 
			//第二个方法onProgressChanged是当进度发生改变时执行
	        @Override
	        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
		        // TODO Auto-generated method stub		        
		        int i= seekBar.getProgress();
		        //textView_blue.setTextColor(0xFF0000FF);
		        textView_blue.setText("P1_1 PWM:"+Integer.toHexString(i).toUpperCase());
		        Log.i(TAG, "color blue = " + Integer.toHexString(i).toUpperCase());
				mBlue  = i;
				
	            UpdateAllParameter();
	        }	        
			//第三个是onStopTrackingTouch,在停止拖动时执行
	        @Override
	        public void onStopTrackingTouch(SeekBar seekBar) {    
	        }
		});
		
		
		toggle_onoff=(ToggleButton)findViewById(R.id.togglebutton_onoff);		  
		toggle_onoff.setOnCheckedChangeListener(new OnCheckedChangeListener() {	             
            @Override
            public void onCheckedChanged(CompoundButton arg0, boolean arg1) {
            	Log.i(TAG, "onCheckedChanged  arg1= " + arg1);
            	
            	if(arg1 == true)
            	{
            		light_onoff = 1;
            		mBrightness = mBrightness_old;    
            		if(mBrightness == 0)// 避免灯不开
            		{
            			mBrightness = 5;
            		}
            	}
            	else
            	{   
            		light_onoff = 0;
            		mBrightness_old = mBrightness;
            		mBrightness = 0;            		
            	}
            	
            	seekBar_brightness.setProgress(mBrightness);
            	UpdateAllParameter();
        	}
	    });	        

	    				

		ReadParameter();	//读出参数
		if(light_onoff == 1)		toggle_onoff.setChecked(true);
		else 						toggle_onoff.setChecked(false);

	    // 更新 参数
	    UpdateAllParameter();			
	}
	
 	public void onClick(View arg0) {
	} 		
	
	// 接收 rssi 的广播
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			String action = intent.getAction();
				
			if (action.equals(ACTION_NAME_RSSI)) {
				int rssi = intent.getIntExtra("RSSI", 0);

				// 以下这些参数我 amomcu 自己设置的， 不太具有参考意义，
				//实际上我的本意就是根据rssi的信号前度计算以下距离， 
				//以便达到定位目的， 但这个方法并不准  ---amomcu---------20150411
				
				int rssi_avg = 0;
				int distance_cm_min = 10; // 距离cm -30dbm
				int distance_cm_max_near = 1500; // 距离cm -90dbm
				int distance_cm_max_middle = 5000; // 距离cm -90dbm
				int distance_cm_max_far = 10000; // 距离cm -90dbm
				int near = -72;
				int middle = -80;
				int far = -88;
				double distance = 0.0f;

				if (true) {
					rssibuffer[rssibufferIndex] = rssi;
					rssibufferIndex++;

					if (rssibufferIndex == rssibufferSize)
						rssiUsedFalg = true;

					rssibufferIndex = rssibufferIndex % rssibufferSize;

					if (rssiUsedFalg == true) {
						int rssi_sum = 0;
						for (int i = 0; i < rssibufferSize; i++) {
							rssi_sum += rssibuffer[i];
						}

						rssi_avg = rssi_sum / rssibufferSize;

						if (-rssi_avg < 35)
							rssi_avg = -35;

						if (-rssi_avg < -near) {
							distance = distance_cm_min
									+ ((-rssi_avg - 35) / (double) (-near - 35))
									* distance_cm_max_near;
						} else if (-rssi_avg < -middle) {
							distance = distance_cm_min
									+ ((-rssi_avg - 35) / (double) (-middle - 35))
									* distance_cm_max_middle;
						} else {
							distance = distance_cm_min
									+ ((-rssi_avg - 35) / (double) (-far - 35))
									* distance_cm_max_far;
						}
					}
				}

				getActionBar().setTitle(
						"RSSI: " + rssi_avg + " dbm");
			}
			else if(action.equals(ACTION_CONNECT)){
				int status = intent.getIntExtra("CONNECT_STATUC", 0);
				if(status == 0)	{				
					getActionBar().setTitle("已断开连接");
					finish();
				}
				else{
					getActionBar().setTitle("已连接灯泡");
				}
			}
		}
	};

	// 注册广播
	public void registerBoradcastReceiver() {
		IntentFilter myIntentFilter = new IntentFilter();
		myIntentFilter.addAction(ACTION_NAME_RSSI);
		myIntentFilter.addAction(ACTION_CONNECT);
		// 注册广播
		registerReceiver(mBroadcastReceiver, myIntentFilter);
	}
	
	// 设置参数到蓝牙设备中
	private void SetColor2Device(int white, int red, int green,int blue, int brightness)
	{		
		byte[] PwmValue = new byte[4];		

		// 特别注意以下的语句转换， amo 在这里调试了好久  ------！！！！！！！！！
		PwmValue[0] = (byte)(((white & 0xFF) * brightness/100) & 0xff);
		PwmValue[1] = (byte)(((red & 0xFF) * brightness/100) & 0xff);
		PwmValue[2] = (byte)(((green & 0xFF) * brightness/100) & 0xff);
		PwmValue[3] = (byte)(((blue & 0xFF) * brightness/100) & 0xff);

		Log.i(TAG, "----------mRed " + mRed);
		
		DeviceScanActivity.WriteCharX(
				DeviceScanActivity.gattCharacteristic_charA,
				PwmValue);
	}
	
	// 获取颜色值的字节数组
	private byte[] GetColorByte()
	{		
		byte[] PwmValue = new byte[4];
		
		PwmValue[0] = (byte)(mWhite & 0xff);
		PwmValue[1] = (byte)(mRed & 0xff);
		PwmValue[2] = (byte)(mGreen & 0xff);
		PwmValue[3] = (byte)(mBlue & 0xff);		
		
		return PwmValue;
	}
	
	// 获取整形值
	private int GetColorInt()
	{		
		byte[] PwmValue = GetColorByte();
		int color = Utils.byteArrayToInt(PwmValue, 0);		
		return color;
	}	
	
	// 更新颜色分量进度
	private void UpdateProgress()
	{	
		seekBar_red.setProgress(mRed);
		seekBar_green.setProgress(mGreen);
		seekBar_blue.setProgress(mBlue);
		seekBar_brightness.setProgress(mBrightness);
	}
	
	// 更新颜色分量进度
	private void UpdateText()
	{
		byte[] PwmValue = new byte[4];
//		int color = GetColorInt();		
		// 特别注意以下的语句转换， amo 在这里调试了好久  ------！！！！！！！！！
		PwmValue[0] = (byte)(((mWhite & 0xFF) * mBrightness/100) & 0xff);
		PwmValue[1] = (byte)(((mRed & 0xFF) * mBrightness/100) & 0xff);
		PwmValue[2] = (byte)(((mGreen & 0xFF) * mBrightness/100) & 0xff);
		PwmValue[3] = (byte)(((mBlue & 0xFF) * mBrightness/100) & 0xff);	

	}
	
	// 发送消息， 以便更新参数	
	private void UpdateAllParameter()
	{	
		Message msg = new Message();  
		msg.what = REFRESH;  
        mHandler.sendMessage(msg);  
	}	
	
	// 线程， 主要用于灯的闪动
//	public class MyThread extends Thread {
//        public void run() {
//            while (!Thread.currentThread().isInterrupted()) {
//            	if(light_onoff == 1
//            	&& light_flash == 1)
//            	{
//	            	mRed += (int)(Math.random() * 255);
//	            	mRed %= 255;
//
//	            	mGreen += (int)(Math.random() * 255);
//	            	mGreen %= 255;
//
//	            	mBlue += (int)(Math.random() * 255);
//	            	mBlue %= 255;
//
//	            	mBrightness += (int)(Math.random() * 100);
//	            	mBrightness %= 255;
//
//	                Message msg = new Message();
//	                msg.what = REFRESH;
//	                mHandler.sendMessage(msg);
//            	}
//                try {
//                    Thread.sleep(light_flash_time_ms);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
//   }
	
   // 写参数到存储器
   private void writeParameter()
   {
		SharedPreferences.Editor sharedata = getSharedPreferences("data", 0).edit();  
		sharedata.putInt("mWhite", mWhite);
		sharedata.putInt("mRed", mRed);
		sharedata.putInt("mGreen", mGreen);
		sharedata.putInt("mBlue", mBlue);
		sharedata.putInt("mBrightness", mBrightness);
		sharedata.putInt("mBrightness_old", mBrightness_old);
		
		sharedata.putInt("light_onoff", light_onoff);
	
		sharedata.commit();	
  }
   // 读出参数
   private void ReadParameter()
   {
	   SharedPreferences sharedata = getSharedPreferences("data", 0);  
	   mWhite = sharedata.getInt("mWhite", 0);
	   mRed = sharedata.getInt("mRed", 255);
	   mGreen = sharedata.getInt("mGreen", 0);
	   mBlue = sharedata.getInt("mBlue", 0);
	   mBrightness = sharedata.getInt("mBrightness", 0);
	   mBrightness_old = sharedata.getInt("mBrightness_old", 0);
	   
	   light_onoff = sharedata.getInt("light_onoff", 1);
   }
   
	@Override
	protected void onStop() {
		Log.i(TAG, "---> onStop");
		super.onStop();
		writeParameter();
	}

	@Override
	protected void onDestroy() {
		Log.i(TAG, "---> onDestroy");
		super.onDestroy();
		writeParameter();
	}
}
