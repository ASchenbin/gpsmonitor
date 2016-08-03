package com.tencent.mapsdkdemo.raster;

import java.util.List;

import com.mapkit.views.MapView;

import android.graphics.drawable.Drawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class QSearchDemo extends MapActivity {
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}
}
/*
public class QSearchDemo extends MapActivity {
	MapView mapView;
	Button btnMyLocationOverlay=null;
	Button btnSearch=null;
	Button btnIsKeyAvailable=null;
	
	private QSearch mQsearch;

	@SuppressWarnings("rawtypes")
	private ItemizedOverlay markOverlay;
	private QPoiOverlay myPoiOverlay;
	private SampleMyLocationOverlay myLocationOverlay;
	private QRouteOverlay drivingOverlay;
	private QRouteOverlay busOverlay;
	private QRouteOverlay busLineOverlay;

	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
		this.setContentView(R.layout.qsearchdemo);
		mapView = (MapView)findViewById(R.id.mapviewqsearch);
		mapView.setBuiltInZoomControls(false);
		
		btnMyLocationOverlay=(Button)this.findViewById(R.id.btnMyLocationOverlay);
		btnMyLocationOverlay.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(myLocationOverlay==null)
				{
					myLocationOverlay = new SampleMyLocationOverlay(QSearchDemo.this, mapView);
					myLocationOverlay.enableCompass();
					myLocationOverlay.enableMyLocation();
					mapView.getOverlays().add(myLocationOverlay);
				}
				if (!myLocationOverlay.animateTo())
					Toast.makeText(QSearchDemo.this, "未定位", Toast.LENGTH_LONG).show();
			}});
		
		btnSearch=(Button)this.findViewById(R.id.btnSearch);
		btnSearch.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				QSearchDemo.this.openOptionsMenu();
			}});
			
		btnIsKeyAvailable=(Button)this.findViewById(R.id.btnIsKeyAvailable);
		btnIsKeyAvailable.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View v) {
					boolean boKeyAvailable=mapView.isAppKeyAvailable();
					if(boKeyAvailable==true)
					{
						Toast.makeText(QSearchDemo.this, "鉴权成功！", Toast.LENGTH_LONG).show();
					}
					else
					{
						Toast.makeText(QSearchDemo.this, "鉴权失败，请检查网络连接是否正常！鉴权失败则无法进行查询。", Toast.LENGTH_LONG).show();
					}
				}});
		
		mQsearch = new QSearch(mapView,listenerQsearch);  //程序退出时，务必调用QSearch.clear()清理资源   
		//mQsearch = new QSearch(this,"输入你申请的Key",listenerQsearch);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		menu.add(0, 3, 0, "地址反编码");
		menu.add(0, 4, 0, "POI搜索");
		menu.add(0, 5, 0, "行政区划搜索");
		menu.add(0, 6, 0, "跨城搜索");
		menu.add(0, 7, 0, "周边搜索");
		menu.add(0, 8, 0, "公交路线搜索");
		menu.add(0, 9, 0, "驾车路线搜索");
		menu.add(0, 10, 0, "公交线测试");
		menu.add(0, 11, 0, "智能提示");
		menu.add(0, 12, 0, "地址编码");
		// menu.add(0, 13, 0, "公交站详情测试");
		return true;
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		String title = item.getTitle().toString();
		if (title.equals("地址反编码"))// 3
		{
			mQsearch.reverseGeocode(mapView.getMapCenter());
		} else if (title.equals("POI搜索"))// 4
		{
			mQsearch.setPoiPageCapacity(0);
			mQsearch.setPoiPageCapacity(10);
			// search.poiSearch("北京", "658");
			GeoPoint left = new GeoPoint((int) (30.733682 * 1E6), (int) (120.917325 * 1E6));
			GeoPoint right = new GeoPoint((int) (31.417444 * 1E6), (int) (121.875935 * 1E6));

			mQsearch.poiSearchInBounds(left, right, "浦发银行");
		} else if (title.equals("行政区划搜索"))// 5
		{
			mQsearch.poiSearch("北京", "海淀");
		} else if (title.equals("跨城搜索"))// 6
		{
			mQsearch.setPoiPageCapacity(0);
			mQsearch.setPoiPageCapacity(10);
			mQsearch.poiSearch("北京", "温馨人家");

		} else if (title.equals("周边搜索")) // 7
		{
			mQsearch.setPoiPageCapacity(0);
			mQsearch.setPoiPageCapacity(10);
			mQsearch.roundSearch("北京", mapView.getMapCenter(), 10000, "饭店");
		} else if (title.equals("公交路线搜索"))// 8
		{
			mQsearch.setBusRoutePolicy(QSearch.KQBUS_ROUTE_POLICY_SHORTCUT);
			mQsearch.busRouteSearch("北京","清河", "银科大厦");
		} else if (title.equals("驾车路线搜索"))// 9
		{
			mQsearch.setDrivingRoutePolicy(QSearch.KQDRIVING_ROUTE_POLICY_SHORTCUT);
			mQsearch.driveRouteSearch("北京", "银科大厦","北京", "东直门" );
		} else if (title.equals("公交线测试"))// 10
		{
			// 740
			mQsearch.busLineSearch("13134931090034453261");
		} else if (title.equals("智能提示"))// 11
		{
			mQsearch.smartTripSearch("北京", "as");
		} else if (title.equals("地址编码")) {
			mQsearch.geocoder("北京市海淀区银科大厦");
		}

		return true;
	}

	QSearchListener listenerQsearch = new QSearchListener() {
		@Override
		public void onGetReverseGeocoder(int resultCode, QPlaceMark addr) {
			if (resultCode == QSearchListener.KQRESULT_OK)
				Toast.makeText(
						QSearchDemo.this,
						addr.name + "," + addr.point.getLatitudeE6() + "," + addr.point.getLongitudeE6() + ","
								+ addr.address, Toast.LENGTH_LONG).show();
			else
				Toast.makeText(QSearchDemo.this, "无结果", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetPoiSearch(int resultCode, QPoiResult poiResult) {
			if (resultCode == QSearchListener.KQRESULT_OK) {
				if(myPoiOverlay==null)
				{
					myPoiOverlay = new QPoiOverlay(QSearchDemo.this.getResources().getDrawable(R.drawable.markpoint),QSearchDemo.this, mapView);
					mapView.getOverlays().add(myPoiOverlay);
				}
				if (poiResult.poiResultType == QPoiResult.KQPOIRESULTTYPE_POILIST) {
					QPoiData data = (QPoiData) poiResult.poiResult;
					
					myPoiOverlay.setData(data.poiList);
					myPoiOverlay.animateTo();
				} else if (poiResult.poiResultType == QPoiResult.KQPOIRESULTTYPE_ROUNDPOILIST) {
					QPoiData data = (QPoiData) poiResult.poiResult;
					myPoiOverlay.setData(data.poiList);
					myPoiOverlay.animateTo();

				} else if (poiResult.poiResultType == QPoiResult.KQPOIRESULTTYPE_CITYLIST) {
		
					List<QCityInfo> data = (List<QCityInfo>) poiResult.poiResult;

					String s = "跨城搜索到的结果数目为:" + data.size() + "\n";

					for (int i = 0; i < data.size(); ++i) {
						QCityInfo info = data.get(i);

						s += "省:" + info.provinceName + "城市:" + info.cityName + "PoiNum:" + info.poiNum + "\n";
					}
					Toast.makeText(QSearchDemo.this, s, Toast.LENGTH_LONG).show();
				} else if (poiResult.poiResultType == QPoiResult.KQPOIRESULTTYPE_AREA) {
					QPoiInfo info = (QPoiInfo) poiResult.poiResult;
					String s = info.name + "\n" + info.address;
					Toast.makeText(QSearchDemo.this, s, Toast.LENGTH_LONG).show();
				}

			} else {
				Toast.makeText(QSearchDemo.this, "无结果", Toast.LENGTH_LONG).show();
			}

		}

		@Override
		public void onGetBusLineSearch(int resultCode, QBusLineInfo busLineInfo) {
			if (resultCode == QSearchListener.KQRESULT_OK) {

				busLineOverlay = new QRouteOverlay(mapView);
				busLineOverlay.setRouteNodeList(busLineInfo.busNodeList);
				mapView.getOverlays().add(busLineOverlay);
				busLineOverlay.animateTo(0);

			} else
				Toast.makeText(QSearchDemo.this, "公交线路无结果", Toast.LENGTH_LONG).show();

		}

		@Override
		public void onGetRouteSearchResult(int resultCode, QRouteSearchResult routeSearchResult) {
			// TODO Auto-generated method stub

			if (resultCode == QSearchListener.KQRESULT_OK) {
				int type = routeSearchResult.routeSearchResultType;

				if (type == QRouteSearchResult.KQROUTESEARCHRESULT_BUSROUTE) {
					QBusRoutePlan plan = (QBusRoutePlan) routeSearchResult.routeSearchResult;

					busOverlay = new QRouteOverlay(mapView);
					busOverlay.setRouteNodeList(plan.routeList.get(0).routeNodeList);
					mapView.getOverlays().add(busOverlay);

					Drawable marker = QSearchDemo.this.getResources().getDrawable(R.drawable.markpoint);
					Drawable marker_focus = QSearchDemo.this.getResources().getDrawable(R.drawable.markpoint);

					QBusRouteInfo first = plan.routeList.get(0);
					
					int segmentNum = first.routeSegmentList.size();
					QBusRouteSegment s = null;
					for(int i = 0; i< segmentNum; ++i)
					{
						s = first.routeSegmentList.get(i);
					}
					
					markOverlay = new SampleItemizedOverlay(marker, marker_focus,
							first.routeNodeList.get(0),
							first.routeNodeList.get(first.routeNodeList.size() - 1));

					mapView.getOverlays().add(markOverlay);

					busOverlay.animateTo(0);
				} else if (type == QRouteSearchResult.KQROUTESEARCHRESULT_DRIVEROUTE) {
					QDriveRouteInfo info = (QDriveRouteInfo) routeSearchResult.routeSearchResult;

					drivingOverlay = new QRouteOverlay(mapView);
					drivingOverlay.setRouteNodeList(info.routeNodeList);
					mapView.getOverlays().add(drivingOverlay);
					drivingOverlay.animateTo(0);
					
					int segmentNum = info.routeSegmentList.size();
					QDriveRouteSegment s = null;
					for(int i = 0; i< segmentNum; ++i)
					{
						s = info.routeSegmentList.get(i);
					}
				} 
				else if (type == QRouteSearchResult.KQROUTESEARCHRESULT_BUSLIST)
				{
					Toast.makeText(QSearchDemo.this, "路线搜索结果为选择列表", Toast.LENGTH_LONG).show();
					QRouteQueryResultChoice choice = (QRouteQueryResultChoice)routeSearchResult.routeSearchResult;
					mQsearch.setBusRoutePolicy(QSearch.KQBUS_ROUTE_POLICY_SHORTCUT);
					mQsearch.busRouteSearchWithLocation("北京", choice.startList.get(0), choice.endList.get(0));
					
				}
				else if (type == QRouteSearchResult.KQROUTESEARCHRESULT_DRIVELIST)
				{
					Toast.makeText(QSearchDemo.this, "路线搜索结果为选择列表", Toast.LENGTH_LONG).show();
					
					QRouteQueryResultChoice choice = (QRouteQueryResultChoice)routeSearchResult.routeSearchResult;
					mQsearch.setDrivingRoutePolicy(QSearch.KQDRIVING_ROUTE_POLICY_SHORTCUT);
					mQsearch.driveRouteSearchWithLocation("北京", choice.startList.get(0), "北京",choice.endList.get(0));
				}
	
			} else
				Toast.makeText(QSearchDemo.this, "路线搜索无结果", Toast.LENGTH_LONG).show();
		}

		@Override
		public void onGetSmartTrips(int resultCode, List<String> smartTrips) {
			// TODO Auto-generated method stub
			if (resultCode == QSearchListener.KQRESULT_OK) {
				String s = null;

				int num = smartTrips.size();

				for (int i = 0; i < num; ++i) {
					if (s == null) {
						s = smartTrips.get(i);
					} else {
						s += "\n" + smartTrips.get(i);
					}

				}
				Toast.makeText(QSearchDemo.this, s, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(QSearchDemo.this, "路线搜索无结果", Toast.LENGTH_LONG).show();
			}
		}

		@Override
		public void onGetGeocoder(int resultCode, QGeocoderInfo geocodeInfo) {
			// TODO Auto-generated method stub
			if (resultCode == QSearchListener.KQRESULT_OK) {
				String s = geocodeInfo.province + "\n" + geocodeInfo.city + "\n" + geocodeInfo.district;
				Toast.makeText(QSearchDemo.this, s, Toast.LENGTH_LONG).show();
			} else {
				Toast.makeText(QSearchDemo.this, "无结果", Toast.LENGTH_LONG).show();
			}
		}
	};

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();

		mapView = null;
		markOverlay = null;
		myPoiOverlay = null;
		myLocationOverlay = null;
		drivingOverlay = null;
		busOverlay = null;

		if (mQsearch != null) {
			mQsearch.clear();
			mQsearch = null;
		}
	}

}
*/