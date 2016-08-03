package com.tencent.mapsdkdemo.raster;

import com.mapkit.util.GeoPoint;
import com.mapkit.views.MapController;
import com.mapkit.views.MapView;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


/**
 * 地图显示，默认情况下需要继承自mapactivity,继承了mapactivity后，soso mapsdk负责管理地图的生存周期，
 * 如果不继承mapactivity,比如继承自activity,fragment等，需要在父组件的生命周期调用地图的相关接口，比如继承自
 * activity的，则要在activity的ondestroy方法，调用mapview.ondestroy()等。
 * 
 *
 */
public class SosoMapDemo extends MapActivity {

	MapView mMapView;
	MapController mMapController;
	GeoPoint point;

	Button btnTraffic = null;
	Button btnAnimationTo=null;
	Button btnZoomOut=null;

	@Override
	/**
	 *显示地图，启用内置缩放控件，并用MapController控制地图的中心点及Zoom级别
	 */
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mapviewdemo);
		mMapView = (MapView) findViewById(R.id.mapviewtraffic);
		
		btnAnimationTo=(Button)this.findViewById(R.id.btnAnimationTo);
		btnAnimationTo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				GeoPoint ge=new GeoPoint((int) (39.95923 * 1E6), (int) (116.437428 * 1E6)); 
				Runnable runAnimate=new Runnable(){

					@Override
					public void run() {
						// TODO Auto-generated method stub
						Toast.makeText(SosoMapDemo.this, "animation finish", Toast.LENGTH_LONG).show();
					}};
//				mMapView.getController().animateTo(ge,runAnimate);
			}});
		
		btnZoomOut=(Button)this.findViewById(R.id.btnZoomOut);
		btnZoomOut.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mMapView.getController().zoomOut();
			}});
		
		mMapView.setBuiltInZoomControls(true); // 设置启用内置的缩放控件
		mMapController = mMapView.getController(); // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		// 济宁市公安局		
		point = new GeoPoint((int) (35.420818 * 1E6), (int) (116.617229 * 1E6)); // 用给定的经纬度构造一个GeoPoint，单位是微度
		
		mMapController.setCenter(point); 
		mMapController.setZoom(9);
	}
	
}
