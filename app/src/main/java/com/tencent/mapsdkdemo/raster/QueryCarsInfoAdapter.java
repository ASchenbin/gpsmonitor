package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class QueryCarsInfoAdapter  extends BaseAdapter {
    private LayoutInflater inflater;
    public long cur_pos;
    private List<HashMap<String, Object>> mListHashMapMonitorCCar = new ArrayList<HashMap<String, Object>>();
    public QueryCarsInfoAdapter(Context context,List<HashMap<String, Object>> ListHashMapMonitorCCar) {
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mListHashMapMonitorCCar = ListHashMapMonitorCCar;
    }
   
    @Override
    public int getCount() {
        return mListHashMapMonitorCCar.size();
    }
   
    @Override
    public Object getItem(int position) {
        return mListHashMapMonitorCCar.get(position);
    }
   
    @Override
    public long getItemId(int position) {
        return position;
    }
    
    
   
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {    	
        convertView = inflater.inflate(R.layout.querycarsinfoitem, null, false);
        
		TextView TextViewMid = (TextView) convertView.findViewById(R.id.TextViewmid_QueryCarsInfo);		
		TextViewMid.setText((String) mListHashMapMonitorCCar.get(position).get("mid"));
  
		TextView TextViewCar_name = (TextView) convertView.findViewById(R.id.TextViewcar_name_QueryCarsInfo);		
		TextViewCar_name.setText((String) mListHashMapMonitorCCar.get(position).get("car_name"));
  	
		TextView TextViewInfotime = (TextView) convertView.findViewById(R.id.TextViewinfotime_QueryCarsInfo);		
		TextViewInfotime.setText((String) mListHashMapMonitorCCar.get(position).get("infotime"));

		
		TextView TextViewStatus = (TextView) convertView.findViewById(R.id.TextViewstatus_QueryCarsInfo);		
		TextViewStatus.setText((String) mListHashMapMonitorCCar.get(position).get("status"));

		
		TextView TextViewSpeed = (TextView) convertView.findViewById(R.id.TextViewspeed_QueryCarsInfo);		
		TextViewSpeed.setText((String) mListHashMapMonitorCCar.get(position).get("speed"));

		TextView TextViewLocation = (TextView) convertView.findViewById(R.id.TextViewlocation_QueryCarsInfo);		
		TextViewLocation.setText((String) mListHashMapMonitorCCar.get(position).get("location"));
		
		
 //       if (position == cur_pos) {// 如果当前的行就是ListView中选中的一行，就更改显示样式
 //           convertView.setBackgroundColor(Color.GREEN);// 更改整行的背景色
 //       }
        return convertView;
    }
}