package com.tencent.mapsdkdemo.raster;

import com.mapkit.util.GeoPoint;
import com.mapkit.views.MapController;
import com.mapkit.views.MapView;
import com.mapkit.views.Projection;
import com.mapkit.views.overlay.Overlay;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Point;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class LocationManagerDemo extends MapActivity {

	MapView mMapView;
	MapController mMapController;
	LocationManager locManager = null;
	LocationListener locListener = null;

	Button btnLocationManger = null;
	LocationOverlay locMyOverlay = null;

	private LocationManager networkLocationManager;
	private static final int MINTIME = 2000;
	private static final int MININSTANCE = 2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.locationmanagerdemo);
		mMapView = (MapView) findViewById(R.id.mapviewlocationmanager);

		// 基站定位
		try {
			networkLocationManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			networkLocationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, MINTIME, MININSTANCE,
					locListener);
		} catch (Exception e) {
			return;
		}

		// Gps 定位
		try {
			locManager = (LocationManager) this
					.getSystemService(Context.LOCATION_SERVICE);
			locManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
					MINTIME, MININSTANCE, locListener);
		} catch (Exception e) {
			return;
		}

		btnLocationManger = (Button) this.findViewById(R.id.btngetlocation);
		btnLocationManger.setText("获取我的位置");
		btnLocationManger.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

			}
		});

		mMapView.setBuiltInZoomControls(true);
		mMapController = mMapView.getController();
		GeoPoint point = new GeoPoint((int) (39.91 * 1E6),
				(int) (116.397 * 1E6));

		mMapController.setCenter(point);
		mMapController.setZoom(12);

		locListener = new LocationListener() {

			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				if (locMyOverlay == null) {
					Bitmap bmpMarker = null;
					Resources res = LocationManagerDemo.this.getResources();
					bmpMarker = BitmapFactory.decodeResource(res,
							R.drawable.mark_location);
					locMyOverlay = new LocationOverlay(
							LocationManagerDemo.this, bmpMarker);
					mMapView.getOverlays().add(locMyOverlay);
				}
				double iLongti = location.getLongitude();
				double iLatitu = location.getLatitude();
				locMyOverlay.setGeoCoords(iLongti, iLatitu);
				locMyOverlay.setAccuracy(location.getAccuracy());
				mMapView.invalidate();

				GeoPoint geoAnimationTo = new GeoPoint((int) (iLatitu * 1e6),
						(int) (iLongti * 1e6));
				mMapView.getController().animateTo(geoAnimationTo);
			}

			@Override
			public void onProviderDisabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onProviderEnabled(String arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
				// TODO Auto-generated method stub

			}
		};
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		if (locManager != null) {
			locManager.removeUpdates(locListener);
			locManager = null;
		}

		if (networkLocationManager != null) {
			networkLocationManager.removeUpdates(locListener);
			networkLocationManager = null;
		}
		if (locMyOverlay != null) {
			this.mMapView.getOverlays().remove(locMyOverlay);
		}
		super.onDestroy();
	}

	class LocationOverlay extends Overlay {

		GeoPoint geoPoint;
		Bitmap bmpMarker;
		float fAccuracy = 0f;

		public LocationOverlay(Context ctx, Bitmap mMarker) {
			super(ctx);
			bmpMarker = mMarker;
		}

		public void setGeoCoords(double dLongti, double dLatitu) {
			if (geoPoint == null) {
				geoPoint = new GeoPoint((int) (dLatitu * 1E6),
						(int) (dLongti * 1E6));
			} else {
				geoPoint.setLatitudeE6((int) (dLatitu * 1E6));
				geoPoint.setLongitudeE6((int) (dLongti * 1E6));
			}
		}

		public void setAccuracy(float fAccur) {
			fAccuracy = fAccur;
		}

		@Override
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			if (geoPoint == null) {
				return;
			}
			Projection mapProjection = mapView.getProjection();
			Paint paint = new Paint();
			Point ptMap = mapProjection.toPixels(geoPoint, null);
			paint.setColor(Color.BLUE);
			paint.setAlpha(8);
			paint.setAntiAlias(true);

			float fRadius = mapProjection.metersToEquatorPixels(fAccuracy);
			canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);
			paint.setStyle(Style.STROKE);
			paint.setAlpha(100);
			canvas.drawCircle(ptMap.x, ptMap.y, fRadius, paint);

			if (bmpMarker != null) {
				paint.setAlpha(255);
				canvas.drawBitmap(bmpMarker,
						ptMap.x - bmpMarker.getWidth() / 2,
						ptMap.y - bmpMarker.getHeight() / 2, paint);
			}

			// super.draw(canvas, mapView, shadow);
		}
	}

}
