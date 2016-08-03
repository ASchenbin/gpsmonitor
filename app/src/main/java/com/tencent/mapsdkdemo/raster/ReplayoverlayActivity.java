package com.tencent.mapsdkdemo.raster;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RotateDrawable;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.text.format.Time;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 *ItemizedOverlay实现地图上给定位置用自定义图片标三个点 
 */
public class ReplayoverlayActivity extends Activity {
	View viewTip=null;
	final private Spinner spinner=null;
	Handler handler = new Handler();
	AutoCompleteTextView mAutoCompleteTextView;
	
    final Runnable Runnable取定位=new Runnable(){    	
		@Override
		public void run() {
		// TODO Auto-generated method stub
		//要做的事情			
		       new Thread() {
		            public void run() {         				
	                    handler.post(new Runnable() {
	                        @Override
	                        public void run() {
	                        	if (CVariables.adapter监控车牌!=null)
	                        	{
	                        		if (CVariables.adapter监控车牌.getCount()>0)
	                        		{
	                        			mAutoCompleteTextView.setAdapter(CVariables.adapter监控车牌);
	                        		}
	                        	}
	                        }
	                    });	            	
		            	
		                // String str = "http://122.139.57.157:9002/Login?UserName=liu&Password=8989821&Version=20121111&time=174326";
		                String strUrl =CVariables.GetDataServerIP  + "/GetCarsTrackStreamAndroidByTicks?UserID=" + CVariables.UserID + "&Ticks=" +CVariables.Ticks_max + "&Password=" + CVariables.Password;
		                HttpResponse httpResponse = null;
		                HttpGet get = new HttpGet(strUrl);
		                try {
		                    DefaultHttpClient defaultHttpClient  = new DefaultHttpClient();
		                    defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
		                    defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
		                    httpResponse = defaultHttpClient.execute(get);
		                } catch (ClientProtocolException e) {

		                    return;
		                } catch (IOException e) {
		                    return;
		                }
		                
		                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
		                    try {
		                        //// result = EntityUtils.toString(httpResponse.getEntity());
		                        HttpEntity httpEntity = httpResponse.getEntity();
		                        InputStream inputStream = httpEntity.getContent();
		                        GZIPInputStream gis = new GZIPInputStream(inputStream);

		                            SAXParserFactory factory = SAXParserFactory.newInstance();
		                            SAXParser parser = factory.newSAXParser();                            
		                    		XMLReader xmlReader=parser.getXMLReader();
		                    		//实例化handler，事件处理器
		                    		final SAXPraser_CarTrackHelper helperHandler=new SAXPraser_CarTrackHelper();
		                    		//解析器注册事件
		                    		xmlReader.setContentHandler(helperHandler);
		                    		//读取文件流
		                    		InputSource is=new InputSource(gis);
		                    		xmlReader.parse(is);
		                            gis.close();
		                            inputStream.close(); 
		                    } catch (ParseException e) {

		                    } catch (IOException e) {

		                    }  
		                    catch(SAXException e){

		                        return;
		                    }
		                    catch(ParserConfigurationException e){

		                    }
		                }
		                else
		                {

		                }
	                    handler.post(new Runnable() {
	                        @Override
	                        public void run() {
	                        	try
	                        	{
	                        	}
	                        	catch (Exception e) {	                    			
	                    		}
	                        	
	                        }
	                    });	   
		                
		            }
		        }.start();
		 
		        
		handler.postDelayed(this, 10000);
		}
		};
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.replayoverlay);

		mAutoCompleteTextView = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1_SelectReplayCar);
	
    
	    CVariables.adapter监控车牌  = new com.tencent.mapsdkdemo.raster.ArrayAdapter<String>(this,R.layout.simple_dropdown_item_1line,CVariables.List监控车牌);
	    mAutoCompleteTextView.setAdapter(CVariables.adapter监控车牌);
	    
	    mAutoCompleteTextView.addTextChangedListener(new TextWatcher() {
            @Override  //这段是起什么作用；里面从参数怎么理解呀；谢谢 
            public void onTextChanged(CharSequence s, int start, int before, int count) { 
            	String car_name = s.toString();
            	if (count < 5)
            	{
            		CVariables.SelectedCCar = null;  
            		return;
            	}
            	
    			for (Iterator Iterator = CVariables.mHashMapMonitor.values().iterator(); Iterator.hasNext();) {
    				CCar car =(CCar) Iterator.next();            		
           		
            		if ( car.getCarame().equals(car_name))
            		{
            			CVariables.SelectedCCar = car;
            			return;
            		}
            	}
    			
    			CVariables.SelectedCCar = null;            	
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


        
        Button btnEmpty=(Button)findViewById(R.id.buttonEmpty_SelectReplayCar);
        btnEmpty.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {
            	mAutoCompleteTextView.setText("");

            	
            }
        });      
		
		Drawable marker = getResources().getDrawable(R.drawable.markpoint);  //得到需要标在地图上的资源
		
		marker.setBounds(0, 0, marker.getIntrinsicWidth(), marker.getIntrinsicHeight());   //为maker定义位置和边界

		handler.postDelayed(Runnable取定位, 1000);//每两秒执行一次runnable.
	} 
		
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	        ContextMenuInfo menuInfo) {

	    // set context menu title
	    menu.setHeaderTitle("操作设置");
	    // add context menu item
	    menu.add(0, 1, Menu.NONE, "选择监控车辆");
	    menu.add(0, 2, Menu.NONE, "查看车辆信息");
	}
	
    void  ReadSharedPreferencesForMap()
    {
	    SharedPreferences   map = getSharedPreferences("map",0);
	    CVariables.centerx = Integer.parseInt(map.getString("centerx","125324400"));
	    CVariables.centery = Integer.parseInt(map.getString("centery","43869900")); 
	    CVariables.centery = Integer.parseInt(map.getString("zoomlevel","10")); 
    }
	
    void  WriteSharedPreferencesForMap(int  centerx,int centery,int zoomlevel)
    {
	    SharedPreferences   map = getSharedPreferences("map",0);
	    SharedPreferences.Editor editor = map.edit();
	    editor.putString("centerx", "" + centerx);
	    editor.putString("centery" ,"" + centery);
	    editor.putString("zoomlevel" ,"" + zoomlevel);

	    editor.commit();
    }

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
        	new AlertDialog.Builder(ReplayoverlayActivity.this)
        	.setTitle("提示") 
        	.setMessage("确定退出?") 
        	.setNegativeButton("取消", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    
                }                     
        	})
        	.setPositiveButton("确定", new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                	handler.removeCallbacks(Runnable取定位);
                    System.exit(0);
                }
        	}) .show();
        	
			return true;

		}else{		

			return super.onKeyDown(keyCode, event);

		}
	}
}




