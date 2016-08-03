package com.tencent.mapsdkdemo.raster;

import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class SMainActivity extends Activity {

	// 服务器上的图片地址
//	private static final String picURL = "http://emap1.mapabc.com/mapabc/maptile?x=13729&y=6692&z=14";
	private static final String picURL = "http://172.31.1.2:8682/GetSDDitu?z=15&y=4841&x=27130";

	private Button btnFirst;
	private FrameLayout frameLayout;
	private Bitmap bitmap = null;
	private ProgressBar progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
/*		setContentView(R.layout.activity_smain);

		btnFirst = (Button) this.findViewById(R.id.btnFirst);
		frameLayout = (FrameLayout) this.findViewById(R.id.frameLayout);
		progress = (ProgressBar) this.findViewById(R.id.progress);
		btnFirst.setOnClickListener(new Button.OnClickListener() {
			@Override
			public void onClick(View v) {
				onSeach();
			}
		});*/
	}

	// 这里重写handleMessage方法，接受到子线程数据后更新UI
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				// 关闭
				//ImageView view = (ImageView) frameLayout
				//		.findViewById(R.id.image);
				//view.setImageBitmap(bitmap);
				//progress.setVisibility(View.INVISIBLE);// 隐藏PrograssBar

				break;
			}
		}
	};

	void onSeach() {
		progress.setVisibility(View.VISIBLE);// 显示PrograssBar

		new Thread() {
			@Override
			public void run() {
				DefaultHttpClient httpclient = new DefaultHttpClient();

				HttpGet httpget = new HttpGet(picURL);

				try {
					HttpResponse resp = httpclient.execute(httpget);
					// 判断是否正确执行
					if (HttpStatus.SC_OK == resp.getStatusLine()
							.getStatusCode()) {
						// 将返回内容转换为bitmap
						HttpEntity entity = resp.getEntity();
						InputStream in = entity.getContent();
						bitmap = BitmapFactory.decodeStream(in);

						// 向handler发送消息，执行显示图片操作
						Message msg = new Message();
						msg.what = 1;
						handler.sendMessage(msg);
					}
					
				} catch (Exception e) {
					e.printStackTrace();
					setTitle(e.getMessage());
				} finally {
					httpclient.getConnectionManager().shutdown();
				}
			}
		}.start();
		
	}
}