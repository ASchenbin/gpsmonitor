package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.ListActivity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class SelectCarListActivity extends ListActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		SimpleAdapter adapter = new SimpleAdapter(this,getData(),R.layout.vlist,
				new String[]{"title","info"},
				new int[]{R.id.title,R.id.info});
		setListAdapter(adapter);
	}

	private List<Map<String, Object>> getData() {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("title", "G1");
		map.put("info", "google 1");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G2");
		map.put("info", "google 2");
		list.add(map);

		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		map = new HashMap<String, Object>();
		map.put("title", "G3");
		map.put("info", "google 3");
		list.add(map);		
		
		return list;
	}
}
