package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import com.mapkit.DefaultResourceProxyImpl;
import com.mapkit.util.GeoPoint;
import com.mapkit.views.MapView;
import com.mapkit.views.Projection;
import com.mapkit.views.overlay.Overlay;
import com.mapkit.views.overlay.PathOverlay;
import com.tencent.mapsdkdemo.raster.GPSInfoDB.GPSInfo;

public class SimuActivity extends MapActivity {

	MapView mMapView;

	CCar _SelectedCCar;
	ArrayList<GPSInfo> _gpsInfoList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_simu);

		_SelectedCCar = CVariables.SelectedCCar;
		if (_SelectedCCar == null) {
			finish();
			return;
		}

		TextView title = (TextView) findViewById(R.id.id_title);
		title.setText(_SelectedCCar.getCarame());

		_gpsInfoList = GPSInfoDB.getInstance().getGpsInfoByCarid(
				_SelectedCCar.getCarid());

		for (GPSInfo data : _gpsInfoList) {
			double lon = rechangeLL(data.lon);
			double lat = rechangeLL(data.lat);
			GeoPoint gp = new GeoPoint(lat, lon);
			mMarklist.add(gp);
		}

		mMapView = (MapView) findViewById(R.id.itemizedoverlayview);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件

		// 设置地图类型为平面图
		mMapView.setMapType(MapView.MAP_TYPE_NORMAL);
		double lon = rechangeLL(_SelectedCCar.getLon());
		double lat = rechangeLL(_SelectedCCar.getLat());
		GeoPoint center = new GeoPoint(lat, lon);
		mMapView.getController().setCenter(center);
		mMapView.getController().setZoom(16);

		_carll = center;
		_dir = (int) _SelectedCCar.getHeading();
		drawLine();

		mMapOverlayA = new MapOverlayA(this);
		mMapView.getOverlayManager().add(mMapOverlayA);

		mMapView.invalidate();

		mTimer = new Timer();
		mTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				// _splash++;
				mHandler.sendEmptyMessage(777);
			}
		}, 1, 500);
	}

	public ArrayList<GeoPoint> mMarklist = new ArrayList<GeoPoint>();

	void drawLine() {
		PathOverlay line = new PathOverlay(Color.BLUE, 10.0f,
				new DefaultResourceProxyImpl(this));
		for (GeoPoint gp : mMarklist) {
			line.addPoint(gp);
		}
		mMapView.getOverlays().add(line);
	}

	@Override
	public void finish() {
		if (mTimer != null) {
			mTimer.cancel();
			mTimer = null;
		}
		super.finish();
	}

	// 模拟timer 500ms刷一个位置
	private Timer mTimer = null;
	@SuppressLint("HandlerLeak")
	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 777: {
				doSimu();

			}
				break;
			default:
				break;
			}
		}
	};

	boolean _isSimu = false;
	int _simuIndex = 0;
	int _step = 1;

	protected void doSimu() {
		if (!_isSimu || _gpsInfoList == null || _gpsInfoList.size() < 1) {
			return;
		}
		_simuIndex += _step;
		if (_simuIndex >= _gpsInfoList.size()) {
			_simuIndex = _gpsInfoList.size() - 1;
			_isSimu = false;
		}

		GPSInfo data = _gpsInfoList.get(_simuIndex);
		double lon = rechangeLL(data.lon);
		double lat = rechangeLL(data.lat);
		GeoPoint center = new GeoPoint(lat, lon);
		mMapView.getController().setCenter(center);
		mMapView.getController().setZoom(16);
		_carll = center;
		_dir = (int) data.dir;
		mMapView.postInvalidate();

	}

	public void onBack(View v) {
		finish();
	}

	public void onBeginsimu(View v) {
		_simuIndex = 0;
		_isSimu = true;
	}

	public void onEndsimu(View v) {
		_isSimu = false;
	}

	public void onV1(View v) {
		_step = 1;
	}

	public void onV2(View v) {
		_step = 2;
	}

	public void onV4(View v) {
		_step = 4;
	}

	public void onV8(View v) {
		_step = 8;
	}

	private double rechangeLL(long ll) {
		return ll * 1.0000f / 1000000;
	}

	Bitmap _bmCar;

	private MapOverlayA mMapOverlayA;
	// 车标经纬读
	GeoPoint _carll;
	// 车标的方向
	int _dir = 0;

	class MapOverlayA extends Overlay {

		Point mCarPoint;

		public MapOverlayA(Context ctx) {
			super(ctx);
		}

		@Override
		protected void draw(Canvas c, MapView osmv, boolean shadow) {
			final Projection proj = osmv.getProjection();
			if (proj == null) {
				return;
			}

			// 画有方向的车标
			if (_carll != null) {
				mCarPoint = new Point();
				proj.toPixels(_carll, mCarPoint);
				drawCar(c);
			}
		}

		private void drawCar(Canvas c) {
			if (mCarPoint.x < 0 || mCarPoint.y < 0) {
				return;
			}
			Bitmap bm;
			bm = ((BitmapDrawable) getResources().getDrawable(
					R.drawable.i3_car01)).getBitmap();
			Bitmap rBm = rotateBitmap(bm, _dir);
			c.drawBitmap(rBm, mCarPoint.x - rBm.getWidth() / 2, mCarPoint.y
					- rBm.getHeight() / 2, new Paint());
		}
	}

	public static Bitmap rotateBitmap(Bitmap bitmap, int degree) {
		int width = bitmap.getWidth();
		int height = bitmap.getHeight();
		Matrix matrix = new Matrix();
		matrix.setRotate(degree);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
				matrix, true);
		return newbmp;
	}

}
