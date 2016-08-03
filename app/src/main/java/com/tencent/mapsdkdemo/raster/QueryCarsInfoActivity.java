package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AutoCompleteTextView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class QueryCarsInfoActivity 	extends Activity {
	    ListView ListViewMonitorCars;    
		AutoCompleteTextView mAutoCompleteTextView;
		QueryCarsInfoAdapter mQueryCarsInfoAdapter;	
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			setContentView(R.layout.querycarsinfo);
		    Button buttonClose=(Button)findViewById(R.id.buttonCloseQueryCarsInfo);
		    buttonClose.setOnClickListener(new View.OnClickListener(){
	            @Override
	            public void onClick(View arg0) {
	            	QueryCarsInfoActivity.this.finish();
	            }
	        });	          
			
	        Button btnEmpty=(Button)findViewById(R.id.btnEmpty_QueryCarsInfoActivity);
	        btnEmpty.setOnClickListener(new View.OnClickListener(){
	            @Override
	            public void onClick(View arg0) {
	            	mAutoCompleteTextView.setText("");
	            }
	        });
		    
		    ListViewMonitorCars=(ListView)findViewById(R.id.ListViewMonitorCars_QueryCarsInfo);
		    List<HashMap<String, Object>> ListHashMapMonitorCCar = new ArrayList<HashMap<String, Object>>();
			for (Iterator Iterator = CVariables.mHashMapMonitor.values().iterator(); Iterator.hasNext();) {
				CCar car =(CCar)Iterator.next();
	            HashMap<String, Object> map = new HashMap<String, Object>();
	            map.put("mid", car.getMid());
	            map.put("car_name", car.getCarame());
	            try
	            {	            
	            	String infotime = DateFormat.format("yyyy-MM-dd kk:mm:ss", car.getDate()).toString();
	            	map.put("infotime", infotime);
	            }
	            catch(Exception e)
	            {
	            	map.put("infotime", "");
	            }
	            map.put("status", car.getStatus());
	            map.put("speed", car.getSpeed() + "公里/小时");

	            map.put("location", car.getLocation());
	            ListHashMapMonitorCCar.add(map);				
			}
		    
			mQueryCarsInfoAdapter = new QueryCarsInfoAdapter(QueryCarsInfoActivity.this,ListHashMapMonitorCCar);	
			ListViewMonitorCars.setAdapter(mQueryCarsInfoAdapter);
		    ListViewMonitorCars.setChoiceMode(ListView.CHOICE_MODE_SINGLE);  
		    
		    ListViewMonitorCars.setOnItemClickListener(new OnItemClickListener() {
	            @Override 
	             public void onItemClick(AdapterView<?> arg0, View arg1, int position, long id) {  
	            	mQueryCarsInfoAdapter.cur_pos = position;// 更新当前行  
	            }  

		    }); 

			
			mAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView_QueryCarsInfoActivity);

		    CVariables.adapter监控车牌  = new com.tencent.mapsdkdemo.raster.ArrayAdapter<String>(this,R.layout.simple_dropdown_item_1line,CVariables.List监控车牌);
		    mAutoCompleteTextView.setAdapter(CVariables.adapter监控车牌);


		    mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
	            @Override  //这段是起什么作用；里面从参数怎么理解呀；谢谢 
	            public void onTextChanged(CharSequence s, int start, int before, int count) { 
	            	String car_name = s.toString();
	            	if (count < 5)
	            	{	            		
	            		return;
	            	}
	            	
	            	for(int j = 0 ;j < mQueryCarsInfoAdapter.getCount();j++){
	            		HashMap<String, Object> map=(HashMap<String, Object>)mQueryCarsInfoAdapter.getItem(j);
	           		
	            		if ( map.containsValue(car_name) ==true)
	            		{
	            			ListViewMonitorCars.setSelection(j);
	            			return;
	            		}
	            	}        	
	            } 
	             
	            @Override          //这段是起什么作用；里面从参数怎么理解呀；谢谢
	            public void beforeTextChanged(CharSequence s, int start, int count, int after) { 
	                // TODO Auto-generated method stub                 
	            } 
	             
	            @Override     //这段是起什么作用；里面从参数怎么理解呀；谢谢 
	            public void afterTextChanged(Editable s) { 
	                // TODO Auto-generated method stub 
	                 
	            } 
	        }) ;
		}

}


