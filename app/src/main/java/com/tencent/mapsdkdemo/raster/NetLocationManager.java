package com.tencent.mapsdkdemo.raster;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class NetLocationManager {
	private static Context mContext;
	private LocationManager gpsLocationManager;
	private LocationManager networkLocationManager;
	private static final int MINTIME = 100;
	private static final int MININSTANCE = 2;
	private static NetLocationManager instance;
	private Location lastLocation = null;
	private static LocationCallBack mCallback;

	public static void init(Context c, LocationCallBack callback) {
		mContext = c;
		mCallback = callback;
	}

	private NetLocationManager() {
		// 基站定位
		try {
			networkLocationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);
			networkLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, MINTIME, MININSTANCE,
					locationListener);
		} catch (Exception e) {
//			return;
		}

		// Gps 定位
		try {
			gpsLocationManager = (LocationManager) mContext
					.getSystemService(Context.LOCATION_SERVICE);
			gpsLocationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, MINTIME, MININSTANCE,
					locationListener);
		} catch (Exception e) {
			return;
		}
	}

	public static NetLocationManager getInstance() {
		if (null == instance) {
			instance = new NetLocationManager();
		}
		return instance;
	}

	private void updateLocation(Location location) {
		lastLocation = location;
		mCallback.onCurrentLocation(location);
	}

	private final LocationListener locationListener = new LocationListener() {

		public void onStatusChanged(String provider, int status, Bundle extras) {
		}

		public void onProviderEnabled(String provider) {
		}

		public void onProviderDisabled(String provider) {
		}

		public void onLocationChanged(Location location) {
			updateLocation(location);
		}
	};

	public Location getMyLocation() {
		return lastLocation;
	}

	private static int ENOUGH_LONG = 1000 * 60;

	public interface LocationCallBack {
		/**
		 * 当前位置
		 * 
		 * @param location
		 */
		void onCurrentLocation(Location location);
	}

	public void destoryLocationManager() {
		if (gpsLocationManager != null) {
			gpsLocationManager.removeUpdates(locationListener);
		}

		if (networkLocationManager != null) {
			networkLocationManager.removeUpdates(locationListener);
		}
	}
	
	public boolean checkGPS() {
		if (gpsLocationManager != null && gpsLocationManager
				.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
			/*Toast.makeText(mContext, R.string.gps_is_turned_on,
					Toast.LENGTH_SHORT).show();*/
			return true;
		}
		return false;
	}
}

