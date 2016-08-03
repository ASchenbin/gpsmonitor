package com.tencent.mapsdkdemo.raster;
import java.util.List;
import java.util.Map;

import java.util.HashMap;

import android.content.Context;

import android.view.LayoutInflater;

import android.view.View;

import android.view.ViewGroup;

import android.widget.BaseAdapter;

import android.widget.CheckBox;

import android.widget.CompoundButton;

import android.widget.CompoundButton.OnCheckedChangeListener;

import android.widget.TextView;

public class CheckboxAdapter extends BaseAdapter {

	Context context;
	
	List<Map<String, Object>> listData;
	
	//记录checkbox的状态	
	Map<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	
	//构造函数	
	public CheckboxAdapter(Context context,List<Map<String, Object>> listData) {
		this.context = context;
		this.listData = listData;		
	}
	
	@Override
	public int getCount() {
		return listData.size();
	}
	
	@Override
	public Object getItem(int position) {
		return listData.get(position);
	}
	
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	// 重写View
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {// TODO Auto-generated method stub
	
		LayoutInflater mInflater = LayoutInflater.from(context);
		
		convertView = mInflater.inflate(R.layout.vlist, null);					
		
		CheckBox check = (CheckBox) convertView.findViewById(R.id.car_name);
		check.setText((String) listData.get(position).get("car_name"));		
		
		TextView TextViewcar_name = (TextView) convertView.findViewById(R.id.mid);
		TextViewcar_name.setText((String) listData.get(position).get("mid"));
		
		
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {		
			@Override		
			public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {
				if (isChecked) {
					state.put(position, isChecked);
				} else {
					state.remove(position);
				}
			}		
		});
		
		check.setChecked((state.get(position) == null ? false : true));		
		return convertView;	
	}
}

