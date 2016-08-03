package com.tencent.mapsdkdemo.raster;

import com.mapkit.util.GeoPoint;
import com.mapkit.views.MapView;
import com.mapkit.views.overlay.MyLocationOverlay;

import android.content.Context;
import android.widget.Toast;


public class SampleMyLocationOverlay extends MyLocationOverlay {
	private Context mContext;

	public SampleMyLocationOverlay(Context context, MapView mapView) {
		super(context, mapView);
		mContext = context;
	}

	public boolean animateTo() {
		GeoPoint p = getMyLocation();
		if (p != null) {
			mMapView.getController().animateTo(p);
			return true;
		}
		return false;
	}

	public boolean dispatchTap() {
		Toast.makeText(mContext, "点中了我的位置", Toast.LENGTH_LONG).show();
		return true;
	}
}
