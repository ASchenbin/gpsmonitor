package com.tencent.mapsdkdemo.raster;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import com.mapkit.ResourceProxy;
import com.mapkit.Wgtochina;
import com.mapkit.Wgtochina.LonLatDouble;
import com.mapkit.util.BoundingBoxE6;
import com.mapkit.util.GeoPoint;
import com.mapkit.views.MapController;
import com.mapkit.views.MapView;
import com.mapkit.views.Projection;
import com.mapkit.views.overlay.ItemizedOverlayWithFocus;
import com.mapkit.views.overlay.Overlay;
import com.mapkit.views.overlay.OverlayItem;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mapsdkdemo.raster.MapOverlay.OnTapListener;
import com.tencent.mapsdkdemo.raster.NetLocationManager.LocationCallBack;

/**
 * ItemizedOverlay实现地图上给定位置用自定义图片标三个点
 */
public class ItemizedOverlayDemo extends MapActivity implements
		LocationCallBack {
	MapView mMapView;
	MapController mMapController;
	View viewTip = null;
	MapOverlay mapOverlay = null;

	final private Spinner spinner = null;
	Handler handler = new Handler();
	AutoCompleteTextView mAutoCompleteTextView;

	TextView mTextViewMid;
	TextView mTextViewCar_name;
	TextView mTextViewInfotime;
	TextView mTextViewStatus;
	TextView mTextViewSpeed;
	TextView mTextViewLocation;
	CheckBox mCheckBoxLocationInvisible;

	private NetLocationManager mNetLocation;

	final Runnable Runnable取定位 = new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			// 要做的事情
			new Thread() {
				@Override
				public void run() {
					handler.post(new Runnable() {
						@Override
						public void run() {
							if (CVariables.adapter监控车牌 != null) {
								if (CVariables.adapter监控车牌.getCount() > 0) {
									mAutoCompleteTextView
											.setAdapter(CVariables.adapter监控车牌);
								}
							}
						}
					});

					// String str =
					// "http://122.139.57.157:9002/Login?UserName=liu&Password=8989821&Version=20121111&time=174326";
					String strUrl = CVariables.GetDataServerIP
							+ "/GetCarsTrackStreamAndroidByTicks?UserID="
							+ CVariables.UserID + "&Ticks="
							+ CVariables.Ticks_max + "&Password="
							+ CVariables.Password;
					HttpResponse httpResponse = null;
					HttpGet get = new HttpGet(strUrl);
					try {
						DefaultHttpClient defaultHttpClient = new DefaultHttpClient();
						defaultHttpClient.getParams().setParameter(
								CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
						defaultHttpClient.getParams().setParameter(
								CoreConnectionPNames.SO_TIMEOUT, 5000);
						httpResponse = defaultHttpClient.execute(get);
					} catch (ClientProtocolException e) {

						return;
					} catch (IOException e) {
						return;
					}

					if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
						try {
							// // result =
							// EntityUtils.toString(httpResponse.getEntity());
							HttpEntity httpEntity = httpResponse.getEntity();
							InputStream inputStream = httpEntity.getContent();
							GZIPInputStream gis = new GZIPInputStream(
									inputStream);

							SAXParserFactory factory = SAXParserFactory
									.newInstance();
							SAXParser parser = factory.newSAXParser();
							XMLReader xmlReader = parser.getXMLReader();
							// 实例化handler，事件处理器
							final SAXPraser_CarTrackHelper helperHandler = new SAXPraser_CarTrackHelper();
							// 解析器注册事件
							xmlReader.setContentHandler(helperHandler);
							// 读取文件流
							InputSource is = new InputSource(gis);
							xmlReader.parse(is);

							gis.close();
							inputStream.close();
						} catch (ParseException e) {

						} catch (IOException e) {

						} catch (SAXException e) {

							return;
						} catch (ParserConfigurationException e) {

						}
					} else {

					}
					handler.post(new Runnable() {
						@Override
						public void run() {
							try {
								if (CVariables.SelectedCCar != null) {
									mTextViewMid
											.setText(CVariables.SelectedCCar
													.getMid());
									mTextViewCar_name
											.setText(CVariables.SelectedCCar
													.getCarame());
									mTextViewInfotime
											.setText(DateFormat
													.format("yyyy-MM-dd kk:mm:ss",
															CVariables.SelectedCCar.infotime)
													.toString());
									mTextViewStatus
											.setText(CVariables.SelectedCCar
													.getStatus());
									mTextViewSpeed.setText(""
											+ CVariables.SelectedCCar
													.getSpeed() + "公里/小时");
									mTextViewLocation
											.setText(CVariables.SelectedCCar
													.getLocation());

									if (mCheckBoxLocationInvisible.isChecked() == true) {
										GeoPoint geopoint = new GeoPoint(
												CVariables.SelectedCCar
														.getLat(),
												CVariables.SelectedCCar
														.getLon());
										mMapView.getController().setCenter(
												geopoint);
									}
								} else {
									mTextViewLocation.setText("-");
									mTextViewMid.setText("-");
									mTextViewCar_name.setText("-");
									mTextViewInfotime.setText("-");
									mTextViewStatus.setText("-");
									mTextViewSpeed.setText("-");
								}

								mMapView.invalidate();
							} catch (Exception e) {
							}

						}
					});

				}
			}.start();

			handler.postDelayed(this, 10000);
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.itemizedoverlay);

		mAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView_ItemizedOverlayDemo);
		mCheckBoxLocationInvisible = (CheckBox) findViewById(R.id.checkBoxLocationInvisible);

		mTextViewMid = (TextView) findViewById(R.id.TextViewmid_ItemizedOverlayDemo);
		mTextViewCar_name = (TextView) findViewById(R.id.TextViewcar_name_ItemizedOverlayDemo);
		mTextViewInfotime = (TextView) findViewById(R.id.TextViewinfotime_ItemizedOverlayDemo);
		mTextViewStatus = (TextView) findViewById(R.id.TextViewstatus_ItemizedOverlayDemo);
		mTextViewSpeed = (TextView) findViewById(R.id.TextViewspeed_ItemizedOverlayDemo);
		mTextViewLocation = (TextView) findViewById(R.id.TextViewlocation_ItemizedOverlayDemo);

		NetLocationManager.init(this, this);// 初始化
		mNetLocation = NetLocationManager.getInstance();
		Resources res = this.getResources();
		_bmCar = BitmapFactory.decodeResource(res,
				R.drawable.i3_car00);

		
		CVariables.adapter监控车牌 = new com.tencent.mapsdkdemo.raster.ArrayAdapter<String>(
				this, R.layout.simple_dropdown_item_1line, CVariables.List监控车牌);
		mAutoCompleteTextView.setAdapter(CVariables.adapter监控车牌);

		mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
			@Override
			// 这段是起什么作用；里面从参数怎么理解呀；谢谢
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				String car_name = s.toString();
				if (count < 5) {
					CVariables.SelectedCCar = null;
					return;
				}

				for (Iterator Iterator = CVariables.mHashMapMonitor.values()
						.iterator(); Iterator.hasNext();) {
					CCar car = (CCar) Iterator.next();

					if (car.getCarame().equals(car_name)) {
						CVariables.SelectedCCar = car;
						mMapView.getController().setZoom(14);
						GeoPoint GeoPointCenter = new GeoPoint(
								CVariables.SelectedCCar.getLat(),
								CVariables.SelectedCCar.getLon());
						mMapView.getController().setCenter(GeoPointCenter);
						return;
					}
				}

				CVariables.SelectedCCar = null;
			}

			@Override
			// 这段是起什么作用；里面从参数怎么理解呀；谢谢
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
			}

			@Override
			// 这段是起什么作用；里面从参数怎么理解呀；谢谢
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub

			}
		});

		// simpleShowList();
		// 为所有列表项注册上下文菜单
		Button btnSetup = (Button) findViewById(R.id.btnSetup_ItemizedOverlayDemo);
		this.registerForContextMenu(btnSetup);
		btnSetup.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				onMonitor();

			}
		});

		Button buttonCancel = (Button) findViewById(R.id.buttonCancel_ItemizedOverlayDemo);
		buttonCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				new AlertDialog.Builder(ItemizedOverlayDemo.this)
						.setTitle("提示")
						.setMessage("确定退出?")
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

									}
								})
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
										GeoPoint GeoPointCenter = mMapView
												.getMapCenter();
										CVariables.centerx = GeoPointCenter
												.getLongitudeE6();
										CVariables.centery = GeoPointCenter
												.getLatitudeE6();
										CVariables.zoomlevel = mMapView
												.getZoomLevel();
										WriteSharedPreferencesForMap(
												CVariables.centerx,
												CVariables.centery,
												CVariables.zoomlevel);
										handler.removeCallbacks(Runnable取定位);
										System.exit(0);
									}
								}).show();
			}
		});

		Button btnEmpty = (Button) findViewById(R.id.btnEmpty_ItemizedOverlayDemo);
		btnEmpty.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				mAutoCompleteTextView.setText("");
				mTextViewMid.setText("-");
				mTextViewCar_name.setText("-");
				mTextViewInfotime.setText("-");
				mTextViewStatus.setText("-");
				mTextViewSpeed.setText("-");
				mTextViewLocation.setText("-");

			}
		});

		LayoutInflater layoutInfla = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
		viewTip = layoutInfla.inflate(R.layout.layouttipview, null);

		mMapView = (MapView) findViewById(R.id.itemizedoverlayview);
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		mMapController = mMapView.getController();
		GeoPoint GeoPointCenter = new GeoPoint((CVariables.centery),
				(CVariables.centerx));
		mMapController.setCenter(GeoPointCenter);
		mMapController.setZoom(CVariables.zoomlevel);

		Drawable marker = getResources().getDrawable(R.drawable.markpoint);
		// 得到需要标在地图上的资源
		marker.setBounds(0, 0, marker.getIntrinsicWidth(),
				marker.getIntrinsicHeight()); // 为maker定义位置和边界
		mapOverlay = new MapOverlay(marker, this);
		mapOverlay.setOnTapListener(onTapListener);
		mMapView.getOverlays().add(mapOverlay);

		handler.postDelayed(Runnable取定位, 1000);// 每两秒执行一次runnable.

		// startLocation();
		
		mMapOverlayA = new MapOverlayA(this);
		mMapView.getOverlayManager().add(mMapOverlayA);
	}

	//监控输入的车牌
	protected void onMonitor() {
		//G27:辽G7676A
		monitorCar = mAutoCompleteTextView.getText().toString();
		
//		Intent intent = new Intent(this, SelectCarListActivity.class);
//		startActivityForResult(intent, 0);
		Intent intent = new Intent(this, SelectMonitorCars.class);
		startActivityForResult(intent, 0);	
//		Intent intent = new Intent(this, QueryCarsInfoActivity.class);
//		startActivityForResult(intent, 0);	
	}
	
	String monitorCar = "";

	private ResourceProxy mResourceProxy;

	// 定义图层上可点击的icon
	public class CustomMarker extends ItemizedOverlayWithFocus<OverlayItem>
			implements
			com.mapkit.views.overlay.ItemizedIconOverlay.OnItemGestureListener<OverlayItem> {
		public CustomMarker(List<OverlayItem> aList,
				OnItemGestureListener<OverlayItem> aOnItemTapListener,
				ResourceProxy pResourceProxy) {
			super(aList, aOnItemTapListener, pResourceProxy);
		}

		@Override
		public void addItem(int location, OverlayItem item) {
			super.addItem(location, item);
		}

		@Override
		protected boolean onTap(int index) {
			return super.onTap(index);
		}

		@Override
		public boolean onSingleTapUp(MotionEvent event, MapView mapView) {
			return super.onSingleTapUp(event, mapView);
		}

		@Override
		public int size() {
			return super.size();
		}

		@Override
		public boolean onItemLongPress(int arg0, OverlayItem arg1) {
			return true;
		}

		@Override
		public boolean onItemSingleTapUp(int arg0, OverlayItem arg1) {
			return true;
		}

	}

	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
			ContextMenuInfo menuInfo) {

		// set context menu title
		menu.setHeaderTitle("操作设置");
		// add context menu item
		menu.add(0, 1, Menu.NONE, "选择监控车辆");
		menu.add(0, 2, Menu.NONE, "查看车辆信息");
		// menu.add(0, 3, Menu.NONE, "车辆轨迹回放");

	}

	void ReadSharedPreferencesForMap() {
		SharedPreferences map = getSharedPreferences("map", 0);
		CVariables.centerx = Integer.parseInt(map.getString("centerx",
				"125324400"));
		CVariables.centery = Integer.parseInt(map.getString("centery",
				"43869900"));
		CVariables.centery = Integer.parseInt(map.getString("zoomlevel", "10"));
	}

	void WriteSharedPreferencesForMap(int centerx, int centery, int zoomlevel) {
		SharedPreferences map = getSharedPreferences("map", 0);
		SharedPreferences.Editor editor = map.edit();
		editor.putString("centerx", "" + centerx);
		editor.putString("centery", "" + centery);
		editor.putString("zoomlevel", "" + zoomlevel);

		editor.commit();
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// 得到当前被选中的item信息
		AdapterContextMenuInfo menuInfo = (AdapterContextMenuInfo) item
				.getMenuInfo();

		switch (item.getItemId()) {
		case 1:
			try {
				@SuppressWarnings("rawtypes")
				Class clazz = Class.forName("com.tencent.mapsdkdemo.raster."
						+ "SelectCarListActivity");
				android.content.Intent intent = new android.content.Intent(
						ItemizedOverlayDemo.this, SelectMonitorCars.class);
				startActivity(intent);
			} catch (Exception e) {

			}
			// do something
			break;
		case 2:
			// do something
			try {
				@SuppressWarnings("rawtypes")
				Class clazz = Class.forName("com.tencent.mapsdkdemo.raster."
						+ "QueryCarsInfoActivity");
				android.content.Intent intent = new android.content.Intent(
						ItemizedOverlayDemo.this, QueryCarsInfoActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				String strError = e.getMessage();
				strError = strError + "";
			}
			break;
		case 3:
			try {
				@SuppressWarnings("rawtypes")
				Class clazz = Class.forName("com.tencent.mapsdkdemo.raster."
						+ "ReplayoverlayActivity");
				android.content.Intent intent = new android.content.Intent(
						ItemizedOverlayDemo.this, ReplayoverlayActivity.class);
				startActivity(intent);
			} catch (Exception e) {
				String strError = e.getMessage();
				strError = strError + "";
			}
			// do something
			break;
		case 4:
			// do something
			break;
		default:
			return super.onContextItemSelected(item);
		}
		return true;
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// 按下键盘上返回按钮
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			new AlertDialog.Builder(ItemizedOverlayDemo.this)
					.setTitle("提示")
					.setMessage("确定退出?")
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {

								}
							})
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog,
										int which) {
									GeoPoint GeoPointCenter = mMapView
											.getMapCenter();
									CVariables.centerx = GeoPointCenter
											.getLongitudeE6();
									CVariables.centery = GeoPointCenter
											.getLatitudeE6();
									CVariables.zoomlevel = mMapView
											.getZoomLevel();
									WriteSharedPreferencesForMap(
											CVariables.centerx,
											CVariables.centery,
											CVariables.zoomlevel);
									handler.removeCallbacks(Runnable取定位);
									GPSInfoDB.getInstance().unInitDB();
									System.exit(0);
								}
							}).show();

			return true;

		} else {

			return super.onKeyDown(keyCode, event);

		}
	}

	OnTapListener onTapListener = new OnTapListener() {

		@Override
		public void onTap(OverlayItem itemTap) {
			// TODO Auto-generated method stub
			if (viewTip == null || itemTap == null) {
				return;
			}
			if (mMapView.indexOfChild(viewTip) == -1) {

			} else {

			}
		}

		@Override
		public void onEmptyTap(GeoPoint pt) {
			// TODO Auto-generated method stub
			if (mMapView.indexOfChild(viewTip) >= 0) {
				mMapView.removeView(viewTip);
			}
		}
	};

	// 车标经纬读
	GeoPoint _carll;

	@Override
	public void onCurrentLocation(Location location) {
		if (location == null) {
			return;
		}

		boolean isRealLonLat = false;
// 真实的经纬度
		if(isRealLonLat == true)
		{
			double lon = location.getLongitude();
			double lat = location.getLatitude();
			_carll = new GeoPoint(lat, lon);			
		}
		else
		{
			LonLatDouble ll = Wgtochina.getInstance().new LonLatDouble();
			ll.lon = location.getLongitude();
			ll.lat = location.getLatitude();
			Wgtochina.getInstance().wgtochina(ll);
			double lat = ll.lat;
			double lon = ll.lon;
			_carll = new GeoPoint(lat, lon);
		}
	}
	
	
	Bitmap _bmCar;
	
	private MapOverlayA mMapOverlayA;
	// 在图层mMapOverlay里使用，外部设置的需要绘制的矩形框
	private BoundingBoxE6 sCentralParkBoundingBox;
	
	class MapOverlayA extends Overlay {

		GeoPoint mTopLeft;
		GeoPoint mBottomRight;
		Point mTopLeftPoint = new Point();
		Point mBottomRightPoint = new Point();
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
			
			if (sCentralParkBoundingBox == null) {
				return;
			}

			mTopLeft = new GeoPoint(sCentralParkBoundingBox.getLatNorthE6(),
					sCentralParkBoundingBox.getLonWestE6());
			mBottomRight = new GeoPoint(
					sCentralParkBoundingBox.getLatSouthE6(),
					sCentralParkBoundingBox.getLonEastE6());

			proj.toPixels(mTopLeft, mTopLeftPoint);
			proj.toPixels(mBottomRight, mBottomRightPoint);

			Rect area = new Rect(mTopLeftPoint.x, mTopLeftPoint.y,
					mBottomRightPoint.x, mBottomRightPoint.y);
			Paint p = new Paint();
			p.setColor(Color.argb(80, 255, 0, 0));
			p.setStyle(Paint.Style.STROKE);
			// 设置边框宽度
			p.setStrokeWidth(6);
			c.drawRect(area, p);
		}
	}
	
	public void onQuyuxuanze(View v) {
		final Projection pj = mMapView.getProjection();
		int Width = mMapView.getWidth();
		int Height = mMapView.getHeight();
		// left+top
		CVariables.g0 = (GeoPoint) pj.fromPixels(Width/4, Height/2-Width/4, null);
		// right+bottom
		CVariables.g1 = (GeoPoint) pj.fromPixels(Width*3/4, Height/2+Width/4, null);

		sCentralParkBoundingBox = new BoundingBoxE6(CVariables.g0.getLatitude(),
				CVariables.g1.getLongitude(), CVariables.g1.getLatitude(), CVariables.g0.getLongitude());
		mMapView.invalidate();
	}
	
	public void onQuyuquxiao(View v) {
		CVariables.g0 = null;
		CVariables.g1 = null;
		sCentralParkBoundingBox = null;
		mMapView.invalidate();
	}

	public void onGuijihuifang(View v) {
		if (CVariables.SelectedCCar == null) {
			Toast.makeText(this, "请选择监控车辆！", Toast.LENGTH_LONG).show();
			return;
		}
		Intent intent = new Intent(this, SimuActivity.class);
		startActivityForResult(intent, 1);
	}
	

}
