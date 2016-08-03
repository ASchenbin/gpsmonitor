package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class SelectMonitorCars extends Activity {
	CheckboxAdapter mNotMonitorListItemAdapter;
	CheckboxAdapter mMonitorListItemAdapter;
    ListView ListViewNotMonitorCars;
    ListView ListViewMonitorCars;
    
	List<Map<String, Object>> ListMapMonitorCarname = new ArrayList<Map<String, Object>>();
	List<Map<String, Object>> ListMapNotMonitorCarname = new ArrayList<Map<String, Object>>();

	@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		
//		ListView listView = new ListView(this);
//		listView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,getData()));
//		setContentView(listView);
		
//		SimpleAdapter adapter = new SimpleAdapter(this,CVariables.ListMapMonitorCarname,R.layout.vlist,	new String[]{"selected","car_name"},new int[]{R.id.selected,R.id.car_name});	
		
//		ArrayAdapter ArrayAdapter = new ArrayAdapter (this,R.layout.listviewitem,CVariables.ListCarname);
		setContentView(R.layout.selectcarlist);
	    Button buttonClose=(Button)findViewById(R.id.buttonClose_SelectMonitorCars);
	    buttonClose.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	SelectMonitorCars.this.finish();
            }
        });
	    
	    Button buttonSelect =(Button)findViewById(R.id.buttonSelect_SelectMonitorCars);
	    buttonSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	addItems();
            }
        });
	    
	    Button buttonCancelSelect =(Button)findViewById(R.id.buttonCancelSelect_SelectMonitorCars);
	    buttonCancelSelect.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	deleteItems();
            }
        });
	    
	    final AutoCompleteTextView AutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1_SelectMonitorCars);  

	    com.tencent.mapsdkdemo.raster.ArrayAdapter<String> adapter = new com.tencent.mapsdkdemo.raster.ArrayAdapter<String>(this,R.layout.simple_dropdown_item_1line,CVariables.List车牌);
          AutoCompleteTextView.setAdapter(adapter);     
		
  	    Button buttonEmpty =(Button)findViewById(R.id.buttonEmpty_SelectMonitorCars);
  	    buttonEmpty.setOnClickListener(new View.OnClickListener(){
              @Override
              public void onClick(View arg0) {
            	  AutoCompleteTextView.setText("");
              }
          });
          
  	    ListViewNotMonitorCars=(ListView)findViewById(R.id.ListViewNotMonitorCars_SelectMonitorCars);
		
	    ListViewMonitorCars=(ListView)findViewById(R.id.ListViewMonitorCars_SelectMonitorCars);

	    ReadSharedPreferencesForMonitorCars();
	    CVariables.mHashMapMonitor.clear();
	    int i = 0;
		for (Iterator Iterator = CVariables.mHashMap.values().iterator(); Iterator.hasNext();) {
			CCar car =(CCar)Iterator.next();
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("mid", car.getMid());
            map.put("car_name", car.getCarame());
            
            int size = CVariables.mHashMapMonitor.size();
            if (size >= 600)
            	break;
            
          if (CVariables.SelectedCCar == car)
//			if (car.getIsmonitored()== true)
			{
				ListMapMonitorCarname.add(map);
				CVariables.mHashMapMonitor.put(car.getMid(), car);				
			}
			else
			{
				ListMapNotMonitorCarname.add(map);
			}
			
			i++;
		}
	    
		mMonitorListItemAdapter = new CheckboxAdapter(this, ListMapMonitorCarname);
		ListViewMonitorCars.setAdapter(mMonitorListItemAdapter);
		
		mNotMonitorListItemAdapter = new CheckboxAdapter(this, ListMapNotMonitorCarname);
		ListViewNotMonitorCars.setAdapter(mNotMonitorListItemAdapter);		
		
		AutoCompleteTextView.addTextChangedListener(new TextWatcher() {
             
            @Override  //这段是起什么作用；里面从参数怎么理解呀；谢谢 
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            	String car_name = s.toString();
            	if (count < 3)
            		return;
            	
            	
            	for(int j = 0 ;j < mMonitorListItemAdapter.getCount();j++){
            		HashMap<String, Object> map=(HashMap<String, Object>)mMonitorListItemAdapter.getItem(j);
           		
            		if ( map.containsValue(car_name) ==true)
            		{
            			ListViewMonitorCars.setSelection(j);
            			return;
            		}
            	}
            	
            	for(int j = 0 ;j < mNotMonitorListItemAdapter.getCount();j++){
            		HashMap<String, Object> map=(HashMap<String, Object>)mNotMonitorListItemAdapter.getItem(j);
            		if ( map.containsValue(car_name) ==true)
            		{
            			ListViewNotMonitorCars.setSelection(j);
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
	
	private void addItems()
	{
		Map<Integer, Boolean> NotMonitorState = mNotMonitorListItemAdapter.state;
		for(int j = mNotMonitorListItemAdapter.getCount()-1 ;j >=0;j--){
			if (NotMonitorState.containsKey(j)==true)
			{
				HashMap<String, Object> map=(HashMap<String, Object>) mNotMonitorListItemAdapter.getItem(j);
				ListMapNotMonitorCarname.remove(j);
				NotMonitorState.remove(j);
				ListMapMonitorCarname.add(map);
			}		
		}
		mNotMonitorListItemAdapter.notifyDataSetChanged();
		mMonitorListItemAdapter.notifyDataSetChanged();
		
		ListViewMonitorCars.setAdapter(mMonitorListItemAdapter);		
		ListViewNotMonitorCars.setAdapter(mNotMonitorListItemAdapter);

		StringBuilder StringBuilder = new StringBuilder("");
		for(int i=0;i<mMonitorListItemAdapter.getCount();i++)
		{
			HashMap<String, Object> map=(HashMap<String, Object>) mMonitorListItemAdapter.getItem(i);
			StringBuilder.append(map.get("mid") + "|" + map.get("car_name") +",");
		}
		
		CVariables.mHashMapMonitor.clear();
		CVariables.List监控车牌.clear();
		for(int i=0; i< mMonitorListItemAdapter.getCount();i++)
		{
			HashMap<String, Object> map=(HashMap<String, Object>) mMonitorListItemAdapter.getItem(i);
			String mid =(String) map.get("mid");
            if (CVariables.mHashMap.containsKey(mid)==true)
            {
	            CCar car = CVariables.mHashMap.get(mid);
	            car.setIsmonitored(true);
	            CVariables.List监控车牌.add(car.getCarame());
	            CVariables.mHashMapMonitor.put(mid, car);
            }
		}
		
	    CVariables.adapter监控车牌  = new com.tencent.mapsdkdemo.raster.ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,CVariables.List监控车牌);
	    CVariables.adapter监控车牌.notifyDataSetChanged();
		
		for(int i=0; i< mNotMonitorListItemAdapter.getCount();i++)
		{
			HashMap<String, Object> map=(HashMap<String, Object>) mNotMonitorListItemAdapter.getItem(i);
			String mid =(String) map.get("mid");
            if (CVariables.mHashMap.containsKey(mid)==true)
            {
	            CCar car = CVariables.mHashMap.get(mid);
	            car.setIsmonitored(false);
            }
		}
		
		WriteSharedPreferencesForMonitorCars(StringBuilder.toString());
		CVariables.Ticks_max=0;
	}   
	  
	private void deleteItems()
	{
		Map<Integer, Boolean> MonitorState = mMonitorListItemAdapter.state;
		for(int j = mMonitorListItemAdapter.getCount()-1 ;j >=0;j--){
			if (MonitorState.containsKey(j)==true)
			{
				HashMap<String, Object> map=(HashMap<String, Object>) mMonitorListItemAdapter.getItem(j);
				ListMapMonitorCarname.remove(j);
				MonitorState.remove(j);
				ListMapNotMonitorCarname.add(map);
			}
		}
		
		mNotMonitorListItemAdapter.notifyDataSetChanged();
		mMonitorListItemAdapter.notifyDataSetChanged();
		
		ListViewMonitorCars.setAdapter(mMonitorListItemAdapter);
		ListViewNotMonitorCars.setAdapter(mNotMonitorListItemAdapter);
		
		StringBuilder StringBuilder = new StringBuilder("");
		for(int i=0;i<mMonitorListItemAdapter.getCount();i++)
		{
			HashMap<String, Object> map=(HashMap<String, Object>) mMonitorListItemAdapter.getItem(i);
			StringBuilder.append(map.get("mid") + "|" + map.get("car_name") +",");
		}
		
		CVariables.mHashMapMonitor.clear();
		CVariables.List监控车牌.clear();
		for(int i=0; i< mMonitorListItemAdapter.getCount();i++)
		{
			HashMap<String, Object> map=(HashMap<String, Object>) mMonitorListItemAdapter.getItem(i);
			String mid =(String) map.get("mid");
            if (CVariables.mHashMap.containsKey(mid)==true)
            {
	            CCar car = CVariables.mHashMap.get(mid);
	            car.setIsmonitored(true);
	            CVariables.mHashMapMonitor.put(mid, car);
	            CVariables.List监控车牌.add(car.getCarame());
            }
		}
		
	    CVariables. adapter监控车牌  = new com.tencent.mapsdkdemo.raster.ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,CVariables.List监控车牌);
	    CVariables. adapter监控车牌.notifyDataSetChanged();
		
		for(int i=0; i< mNotMonitorListItemAdapter.getCount();i++)
		{
			HashMap<String, Object> map=(HashMap<String, Object>) mNotMonitorListItemAdapter.getItem(i);
			String mid =(String) map.get("mid");
            if (CVariables.mHashMap.containsKey(mid)==true)
            {
	            CCar car = CVariables.mHashMap.get(mid);
	            car.setIsmonitored(false);
            }
		}
		WriteSharedPreferencesForMonitorCars(StringBuilder.toString());
		CVariables.Ticks_max=0;
	}
	
	private List<String> getData(){		
		List<String> data = new ArrayList<String>();
		data.add("test11");
		data.add("test12");
		data.add("test13");
		data.add("test14");
		return data;
	}
	
    void  ReadSharedPreferencesForMonitorCars()
    {
	    SharedPreferences   sharedPreferences = getSharedPreferences("monitor_cars",0);
	    String strCars = sharedPreferences.getString("cars","");
	    if (strCars==null)
	    	return;
	    
	    String cars[]  = strCars.split(",");
	    if (cars.length ==0)
	    	return;

	    for(String strCar : cars)
	    {
	    	String mid_carname[] = strCar.split("\\|");
	    	if (mid_carname==null || mid_carname.length==1)
	    	{
	    		continue;
	    	}
	    	
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("mid", mid_carname[0]);
            map.put("car_name", mid_carname[1]);
            if (CVariables.mHashMap.containsKey(mid_carname[0])==true)
            {
	            CCar car = CVariables.mHashMap.get(mid_carname[0]);
	            car.setIsmonitored(true);
            }
	    }
    }

    void  WriteSharedPreferencesForMonitorCars(String  cars)
    {
	    SharedPreferences   sharedPreferences = getSharedPreferences("monitor_cars",0);
	    SharedPreferences.Editor editor = sharedPreferences.edit();
	    editor.putString("cars", cars);
	    editor.commit();
    }
}
