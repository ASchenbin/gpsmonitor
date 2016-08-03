package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.widget.TextView;
import android.widget.Toast;

public class SampleItemizedOverlay {
	public SampleItemizedOverlay() {
		
	}
}
/*
public class SampleItemizedOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> mMarkers = new ArrayList<OverlayItem>();
	private Drawable marker;
	private Drawable marker_focus;
	private Context context;

	MapView mapView;
	TextView tv;

	public SampleItemizedOverlay(Drawable defaultMarker, Drawable focusMarker,
			GeoPoint point1,GeoPoint point2) {
		super(defaultMarker);
		// 保存标记图片
		marker = defaultMarker;
		marker_focus = focusMarker;
		// 以指定纬度和经度建立一个GeoPoint实例
//		GeoPoint point1 = new GeoPoint((int) (39.908716 * 1E6),
//				(int) (116.397529 * 1E6));
//		GeoPoint point2 = new GeoPoint((int) (32.808716 * 1E6),
//				(int) (116.497529 * 1E6));
		// 添加一个图层项
		mMarkers.add(new OverlayItem(point1, "北京天安门~", "北京长安街"));
		mMarkers.add(new OverlayItem(point2, "这是哪里？", "不知道，好像在南方"));
		
		if (defaultMarker != null)
			boundCenterBottom(marker);

		if (focusMarker != null)
			boundCenterBottom(marker_focus);

		for (int i = 0; i < mMarkers.size(); i++) {
			mMarkers.get(i).setMarker(marker);
		}

		// 调用父类方法 放置标记
		populate();

//		tv = new TextView(mapView.getContext());
//		tv.setBackgroundColor(Color.GREEN);
//		mapView.addView(tv, new MapView.LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT, null, 0, -20,
//				MapView.LayoutParams.CENTER));
//		tv.setText("test");
//		this.mapView = mapView;
	}

	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// TODO Auto-generated method stub
		super.draw(canvas, mapView, shadow);
		context = mapView.getContext();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return mMarkers.get(i);
	}

	@Override
	public int size() {
		return mMarkers.size();
	}

	@Override
	protected boolean onTap(int index) {
		OverlayItem item = mMarkers.get(index);

		// 去除前一个焦点
		OverlayItem currentItem = getFocus();
		if (currentItem != null) {
			if (marker != null) {
				boundCenterBottom(marker);// 图片是显示在一个矩形区域，这句是将图片中心点作为其在屏幕的坐标
				currentItem.setMarker(marker);
			}
		}

		// 设置新的焦点
		if (marker_focus != null) {
			boundCenterBottom(marker_focus);
			item.setMarker(marker_focus);
		}
		setFocus(item);// 设置为焦点

		Toast.makeText(context, item.getTitle() + "\n" + item.getSnippet(),
				Toast.LENGTH_SHORT).show();

//		tv.setText(item.getTitle() + "\n" + item.getSnippet());
//		MapView.LayoutParams geoLP = (MapView.LayoutParams) tv
//				.getLayoutParams();
//		geoLP.point = item.getPoint();
		//mapView.updateViewLayout(tv, geoLP);

		return true;
	}
}
*/
