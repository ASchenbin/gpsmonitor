package com.tencent.mapsdkdemo.raster;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;

import com.mapkit.mapsource.MapABCTileSource;
import com.mapkit.mapsource.MapSatelliteSource;
import com.mapkit.mapsource.MapSatelliteTextSource;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;


public class WelcomeActivity extends Activity {

	private ProgressBar progress;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.welcome);

        progress = (ProgressBar) this.findViewById(R.id.progress);
        startNavi();
    }

    private void startAMap() {
    	progress.setVisibility(View.INVISIBLE);
    	if (realPicURL.length() > 3) {
    		final SharedPreferences.Editor edit = mPrefs.edit();
    		edit.putString(MAPURL, realPicURL);
    		edit.commit();
    		
    		if (realPicURL.compareTo(picURL1) == 0) {
    			MapABCTileSource.HTTPMAPURL = "http://172.41.1.2";
    			MapSatelliteTextSource.HTTPMAPURL = "http://172.41.1.2";
    			MapSatelliteSource.HTTPMAPURL = "http://172.41.1.2";
    		}
    	} else {
    		Toast.makeText(this, "请检查网络，重启动地图。", Toast.LENGTH_LONG).show();
//    		WelcomeActivity.this.finish();
//    		return;
    	}
    	
        Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
        WelcomeActivity.this.finish();
        startActivity(intent);
    }

	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				startAMap();
				break;
			}
		}
	};
	
	private SharedPreferences mPrefs;
    private static final String picURL1 = "http://172.41.1.2:8681/GetSDDitu?z=11&y=309&x=1663";
    private static final String picURL0 = "http://172.31.1.2:8681/GetSDDitu?z=11&y=309&x=1663";
    private Bitmap bitmap = null;
    public static final String MAPURL = "mapurl";
    public static final String YOUS = "yous";
    String realPicURL = "";
    
    private static final String picIp1 = "http://172.41.1.2:8681";
    private static final String picIp0 = "http://172.31.1.2:8681";
    
    private void startNavi() {
    	mPrefs = getSharedPreferences(YOUS, Context.MODE_PRIVATE);
    	realPicURL = mPrefs.getString(MAPURL, "");
    	if (realPicURL.length() < 1) {
    		Toast.makeText(this, "初次使用搜索地图服务器，请耐心等候...", Toast.LENGTH_LONG).show();
    	}
    	
    	progress.setVisibility(View.VISIBLE);
    	new Thread() {
			@Override
			public void run() {
				if (realPicURL.length() > 1) {
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
					return;
				}

				for (int i = 0; i < 2; i++) {
					realPicURL = picURL0;
					if (i == 1) {
						realPicURL = picURL1;
					}
					DefaultHttpClient httpclient = new DefaultHttpClient();
					httpclient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 3000); 
					httpclient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 3000);
					HttpGet httpget = new HttpGet(realPicURL);
					try {
						HttpResponse resp = httpclient.execute(httpget);
						// 判断是否正确执行
						if (HttpStatus.SC_OK == resp.getStatusLine()
								.getStatusCode()) {
							// 向handler发送消息，执行显示图片操作
							Message msg = new Message();
							msg.what = 1;
							handler.sendMessage(msg);
							break;
						} else {
							realPicURL = "";
						}
					} catch (Exception e) {
						e.printStackTrace();
						setTitle(e.getMessage());
						realPicURL = "";
					} finally {
						httpclient.getConnectionManager().shutdown();
					}
				}
				
				if (realPicURL.length() < 1) {
					Message msg = new Message();
					msg.what = 1;
					handler.sendMessage(msg);
				}
			}
		}.start();
		/*new Thread() {
			@Override
			public void run() {
				for (int i = 0; i < 2; i++) {
					realPicURL = picURL0;
					if (i == 1) {
						realPicURL = picURL1;
					}
					String ip = picIp0;
					if (i == 1) {
						ip = picIp1;
					}

					if (ping(ip)) {
						break;
					} else {
						realPicURL = "";
					}
				}
				Message msg = new Message();
				msg.what = 1;
				handler.sendMessage(msg);
			}
		}.start();*/
    }
    
	private boolean ping(String ip) {
		String result = null;
		try {
			Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 " + ip);// ping3次
			InputStream input = p.getInputStream();
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while ((content = in.readLine()) != null) {
				stringBuffer.append(content);
			}
			Log.i("TTT", "result content : " + stringBuffer.toString());
			// PING的状态
			int status = p.waitFor();
			if (status == 0) {
				result = "successful~";
				return true;
			} else {
				result = "failed~ cannot reach the IP address";
			}
		} catch (IOException e) {
			result = "failed~ IOException";
		} catch (InterruptedException e) {
			result = "failed~ InterruptedException";
		} finally {
			Log.i("TTT", "result = " + result);
		}

		return false;
	}

}