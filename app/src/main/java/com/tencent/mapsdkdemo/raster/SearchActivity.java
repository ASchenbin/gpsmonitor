package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView.OnEditorActionListener;

import com.mapkit.geocode.GeocodeService;
import com.mapkit.geocode.YSPOIData;


@SuppressLint("NewApi")
public class SearchActivity extends Activity {

	AutoCompleteTextView mInput;
	SearchAdapter mAdapter;
	ListView mListView;
	InputMethodManager m;

	GeocodeService _gService = GeocodeService.getInstance();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		mInput = (AutoCompleteTextView) findViewById(R.id.search_input);
		mAdapter = new SearchAdapter(this);
		mListView = (ListView) findViewById(R.id.listview);
		mListView.setAdapter(mAdapter);
		mListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				onListItem(arg2);
			}
		});

		mInput.setThreshold(1000);
		mInput.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_NEXT
						|| actionId == EditorInfo.IME_ACTION_DONE
						|| actionId == EditorInfo.IME_ACTION_GO
						|| actionId == EditorInfo.IME_ACTION_SEARCH) {
					onSearch(null);
				}
				return false;
			}
		});
		mInput.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				onSearch(null);
			}
		});

		mInput.setText("");
		mInput.requestFocus();
		m = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
		m.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

	}

	protected void onListItem(int pos) {
		ArrayList<YSPOIData> poiDatas = _gService.getPoiDatas();
		if (pos < 0 || pos >= poiDatas.size()) {
			return;
		}

		YSPOIData poiData = poiDatas.get(pos);
		Intent intent = new Intent();
		intent.putExtra("poiIndex", pos);
		setResult(600, intent);
		finish();
	}

	public void onSearch(View v) {
		m.hideSoftInputFromWindow(mInput.getWindowToken(),
				InputMethodManager.HIDE_NOT_ALWAYS);

		double lon = 121.476455;
		double lat = 31.231706;
		int range = -1;
		String strKey = mInput.getText().toString();

		new SearchTask(lon, lat, range, strKey) {
			@Override
			protected void onPostExecute(ArrayList<YSPOIData> poiList) {
				super.onPostExecute(poiList);
				mAdapter.setDataList(poiList);
				mAdapter.notifyDataSetChanged();
			}
		}.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
	}

	static class ViewHolder {
		TextView tv_name;
		TextView tv_addr;
		TextView tv_distance;
	}

	public class SearchAdapter extends BaseAdapter {
		private ArrayList<YSPOIData> mPois = new ArrayList<YSPOIData>();
		Context mContext;

		public SearchAdapter(Context context) {
			mContext = context;
		}

		public void setDataList(ArrayList<YSPOIData> pois) {
			if (pois != null && pois.size() > 0) {
				mPois = pois;
			} else {
				mPois.clear();
				for (int i = 0; i < 50; i++) {
					YSPOIData item = new YSPOIData();
					item.name = i + "   止损放sad 发生的";
					item.address = "撒返回后山东丰富";
					item.distanceToCenter = 3 * i;
					mPois.add(item);
				}
			}
		}

		@Override
		public int getCount() {
			if (mPois == null) {
				return 0;
			} else {
				return mPois.size();
			}
		}

		@Override
		public Object getItem(int position) {
			if (mPois == null) {
				return null;
			} else {
				return mPois.get(position);
			}
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder;
			if (null == convertView) {
				convertView = View.inflate(mContext,
						R.layout.adapter_searchresult_item, null);
				holder = new ViewHolder();
				holder.tv_name = (TextView) convertView
						.findViewById(R.id.search_name);
				holder.tv_addr = (TextView) convertView
						.findViewById(R.id.search_addr);
				holder.tv_distance = (TextView) convertView
						.findViewById(R.id.search_distance);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			YSPOIData poi = mPois.get(position);
			holder.tv_name.setText(poi.name);
			holder.tv_addr.setText(poi.address);
			holder.tv_distance.setText(poi.distanceToCenter + "米");
			return convertView;
		}

	}

	public class SearchTask extends AsyncTask<Void, Void, ArrayList<YSPOIData>> {
		double _lon;
		double _lat;
		int _range;
		String _strKey;

		public SearchTask(double lon, double lat, int range, String strKey) {
			_lon = lon;
			_lat = lat;
			_range = range;
			_strKey = strKey;
		}

		@Override
		protected ArrayList<YSPOIData> doInBackground(Void... params) {
			_gService.nearSearch(_lon, _lat, _range, _strKey);
			return _gService.getPoiDatas();
		}
	}

}
