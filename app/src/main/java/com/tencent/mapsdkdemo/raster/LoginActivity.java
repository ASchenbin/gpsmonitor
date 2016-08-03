package com.tencent.mapsdkdemo.raster;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

//import com.tencent.mapsdkdemo.version.UpdateManager;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
         
        setContentView(R.layout.activity_login);
        
        GPSInfoDB.getInstance().initDB();
        
        //UpdateManager.getUpdateManager().checkAppUpdate(this, false);
        
        Button buttonCancel=(Button)findViewById(R.id.buttonCancel_LoginActivity);
        buttonCancel.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {            	
            	boolean bbbbb = false;
            	if (bbbbb)
            	{
	            	try {
	        			@SuppressWarnings("rawtypes")
	        			Class clazz = Class.forName("com.tencent.mapsdkdemo.raster." + "SelectCarListActivity");
	           			android.content.Intent intent = new android.content.Intent(LoginActivity.this, SelectCarListActivity.class);
	        			startActivity(intent);
	        			LoginActivity.this.finish();
	        		} catch (Exception e) {
	        			bbbbb = true;
	        		}
	            	
	    		//	Intent intent = new Intent(LoginActivity.this, SelectCarListActivity.class);
	    		//	startActivity(intent);
	            	return;
            	}  
            	new AlertDialog.Builder(LoginActivity.this)
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
                        System.exit(0);
                    }
            	}) .show();
            }
        });        
        
        CVariables.res = getResources();
        Date curDate  = new Date(System.currentTimeMillis());//获取当前时间 
        CVariables.CurDate = curDate;
        int TextSize = dip2px(16);
        CVariables.PaintText.setTextSize(TextSize);
        CVariables.PaintText.setColor(Color.BLACK);

		CVariables.PaintRectGreen.setColor(Color.argb(0xff, 0x8a, 0xeb, 0x5b));
		CVariables.PaintRectYellow.setColor(Color.argb(0xff, 0xFC, 0xEC, 0x2D));
		CVariables.PaintRectRed.setColor(Color.argb(0xff, 0xFC, 0x11, 0x11));
 
 //       CVariables.Bitmap = BitmapFactory.decodeResource(CVariables.res, R.drawable.normal_0);

        EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
        EditText editTextUserName = (EditText)findViewById(R.id.editTextUserName);       
    	
        ReadSharedPreferencesForUser();/////jlmingyu     //////123456
        ReadSharedPreferencesForMap();
    	if (CVariables.Username!=null && CVariables.Username !="" && CVariables.Password!=null && CVariables.Password !="")
    	{
    		editTextUserName.setText(CVariables.Username);
    		editTextPassword.setText(CVariables.Password);
    	}
        
        Matrix matrix = new Matrix();

        int png = 32 * 32;
        if (png == 128 * 128)
        {
            Bitmap bmpnormal_run = BitmapFactory.decodeResource(CVariables.res, R.drawable.down_greenx32);
            Bitmap bmpnormal_stop = BitmapFactory.decodeResource(CVariables.res, R.drawable.ball_greenx32);

            Bitmap bmpoffline_run = BitmapFactory.decodeResource(CVariables.res, R.drawable.down_yellowx32);
            Bitmap bmpoffline_stop = BitmapFactory.decodeResource(CVariables.res, R.drawable.ball_yellowx32);
            
            int width = bmpnormal_run.getWidth() ;
            CVariables.BitmapWidth = px2dip(width);
            CVariables.BitmapWidth = width;
            
//	        matrix.preScale(0.25f, 0.25F);  ////128x128
        	
        	
//	        CVariables.BitmapRunNormal = Bitmap.createBitmap(bmpnormal_run, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
//	        CVariables.BitmapStopNormal = Bitmap.createBitmap(bmpnormal_stop, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
	
//	        CVariables.BitmapRunOffline = Bitmap.createBitmap(bmpoffline_run, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
//	        CVariables.BitmapStopOffline = Bitmap.createBitmap(bmpoffline_stop, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
	    }
        else
        {
            Bitmap bmpnormal_run = BitmapFactory.decodeResource(CVariables.res, R.drawable.down_greenx32);
            Bitmap bmpnormal_stop = BitmapFactory.decodeResource(CVariables.res, R.drawable.ball_greenx32);
            Bitmap bmpnormal_selected = BitmapFactory.decodeResource(CVariables.res, R.drawable.up_redx32);

            Bitmap bmpoffline_run = BitmapFactory.decodeResource(CVariables.res, R.drawable.down_yellowx32);
            Bitmap bmpoffline_stop = BitmapFactory.decodeResource(CVariables.res, R.drawable.ball_yellowx32);
            Bitmap bmpoffline_selected = BitmapFactory.decodeResource(CVariables.res, R.drawable.ball_redx32);
      
            
            int width = bmpnormal_run.getWidth() ;
            CVariables.BitmapWidth = px2dip(width);
            CVariables.BitmapWidth = width;
           
            matrix.preScale(1f, 1F);  ////32x32
            matrix.preRotate(180, CVariables.BitmapWidth /2, CVariables.BitmapWidth /2);  ////32x32
	        CVariables.BitmapRunNormal = Bitmap.createBitmap(bmpnormal_run, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
	        CVariables.BitmapStopNormal = Bitmap.createBitmap(bmpnormal_stop, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
	        
	        CVariables.BitmapRunSeleted = bmpnormal_selected;	
//	        CVariables.BitmapRunOffline = bmpoffline_run;   
	        CVariables.BitmapStopOffline = bmpoffline_stop;   
	        CVariables.BitmapStopSeleted = bmpoffline_selected;   
 	        
	        
	        CVariables.BitmapRunOffline = Bitmap.createBitmap(bmpoffline_run, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
//	        CVariables.BitmapStopOffline = Bitmap.createBitmap(bmpoffline_stop, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
//	        CVariables.BitmapStopSeleted = Bitmap.createBitmap(bmpoffline_selected, 0, 0, (int)CVariables.BitmapWidth , (int)CVariables.BitmapWidth , matrix, true);   
        }
        
        Button  buttonLogin=(Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View arg0) {       		
        		boolean is = false;
            	if (is == true)
            	{
            		String strPath = getApplicationContext().getFilesDir().getAbsolutePath();

	            	try {
	        			@SuppressWarnings("rawtypes")
	        			Class clazz = Class.forName("com.tencent.mapsdkdemo.raster." + "ItemizedOverlayDemo");
	           			android.content.Intent intent = new android.content.Intent(LoginActivity.this, clazz);
	        			startActivity(intent);
	        			LoginActivity.this.finish();
	        		} catch (Exception e) {
	        			
	        		}
            		return;
            	}
                textViewLoginStatus=(TextView)findViewById(R.id.textViewLoginStatus);
                EditText editTextPassword = (EditText)findViewById(R.id.editTextPassword);
                EditText editTextUserName = (EditText)findViewById(R.id.editTextUserName);
                
                CVariables.Username = editTextUserName.getText().toString();
                CVariables.Password = editTextPassword.getText().toString();   
                //if (CVariables.Username.length() < 2) {
                //	CVariables.Username = "liu";
                //    CVariables.Password = "123321";   
                //}
                login_sax();
                
             //   handler.postDelayed(Runnable登录操作过程, 2000);//每两秒执行一次runnable.
            }
        });
    }

    
	public static int px2dip(float pxValue)
	{
		final float scale = CVariables.res.getDisplayMetrics().density;
		return (int)(pxValue / scale +0.5f); 
	}
	
	public static int dip2px(float dipValue)
	{ 
		final float scale = CVariables.res.getDisplayMetrics().density; 
		return (int)(dipValue * scale +0.5f);
	}
	
    void  ReadSharedPreferencesForMap()
    {
	    SharedPreferences   map = getSharedPreferences("map",0);
	    CVariables.centerx = Integer.parseInt(map.getString("centerx","117224400"));
	    CVariables.centery = Integer.parseInt(map.getString("centery","36269900")); 
	    CVariables.zoomlevel = Integer.parseInt(map.getString("zoomlevel1","6")); 
    }
    
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		//按下键盘上返回按钮
		if(keyCode == KeyEvent.KEYCODE_BACK){
        	new AlertDialog.Builder(LoginActivity.this)
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
                    System.exit(0);
                }
        	}) .show();
        	
			return true;

		}else{
			return super.onKeyDown(keyCode, event);
		}
	}
    
    private String result;//保存get返回的字符串
    private  TextView textViewLoginStatus;

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    Handler handler = null;
    
    private void login_sax()
    {
        handler = new Handler();
        new Thread() {
            public void run() {
                // String str = "http://122.139.57.157:9002/Login?UserName=liu&Password=8989821&Version=20121111&time=174326";
                String strUrl = CVariables.LoginServerIP + "/Login?UserName=" + CVariables.Username +"&Password=" + CVariables.Password + "&Version=20121111&time=174326";
                HttpResponse httpResponse = null;
                HttpGet get = new HttpGet(strUrl);
                try {
                    DefaultHttpClient defaultHttpClient  = new DefaultHttpClient();
                    defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
                    defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
                    httpResponse = defaultHttpClient.execute(get);
                } catch (ClientProtocolException e) {
                    //   e.printStackTrace();

                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLoginStatus.setText("网络异常，登录失败");
                        }
                    });
                    return;
                } catch (IOException e) {
                    //   e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLoginStatus.setText("网络异常，登录失败");
                        }
                    });
                    return;
                }
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        //// result = EntityUtils.toString(httpResponse.getEntity());
                        HttpEntity httpEntity = httpResponse.getEntity();
                        
                        InputStream inputStream = httpEntity.getContent();
                        SAXParserFactory factory = SAXParserFactory.newInstance();
                        SAXParser parser = factory.newSAXParser();                            
                		XMLReader xmlReader=parser.getXMLReader();
                		//实例化handler，事件处理器
                		final SAXPraser_LoginHelper helperHandler=new SAXPraser_LoginHelper();
                		//解析器注册事件
                		xmlReader.setContentHandler(helperHandler);
                		//读取文件流
                		InputSource is=new InputSource(inputStream); 
                		xmlReader.parse(is);
                        inputStream.close();

                    } catch (ParseException e) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("解析异常，登录失败");
                            }
                        });
                    } catch (IOException e) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("网络异常，登录失败");
                            }
                        });
                    }  
                    catch(SAXException e){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("网络异常，登录失败");
                            }
                        });
                        return;
                    }
                    catch(ParserConfigurationException e){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("登录XML解析异常");
                            }
                        });
                        
                        return;
                    }
                    
                    if (CVariables.UserID>0)
                    {
                        CheckBox CheckBoxRememberMM =(CheckBox)findViewById(R.id.checkBoxRememberMM);

                    	if (CheckBoxRememberMM.isChecked()==true)
                    	{
                    		WriteSharedPreferencesForUser(CVariables.Username,CVariables.Password);
                    	}
                        
                        CheckBox CheckBoxRefreshCars =(CheckBox)findViewById(R.id.checkBoxRefreshCars);
                    	if (CheckBoxRefreshCars.isChecked()==true)
                    	{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textViewLoginStatus.setText("登录成功,下载车辆列表...");
                                }
                            });
                    		getcarlist_sax();
                    	}
                    	else
                    	{
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    textViewLoginStatus.setText("登录成功,读取车辆列表...");
                                }
                            });
                    		getcarlist_sax_fromFile( );
                    	}
                    }
                    else
                    {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("登录失败,用户名或密码错误");
                            }
                        });
                    }
                }
                else
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLoginStatus.setText("网络异常，登录失败");
                        }
                    });
                }
            }

        }.start();
    }
    
    void  ReadSharedPreferencesForUser()
    {
	    SharedPreferences   user = getSharedPreferences("user_info",0);
	    CVariables.Username = user.getString("user","");
	    CVariables.Password = user.getString("password","");
    }

    void  WriteSharedPreferencesForUser(String  strName,String strPassword)
    {
	    SharedPreferences   user = getSharedPreferences("user_info",0);
	    SharedPreferences.Editor editor = user.edit();
	    editor.putString("user", strName);
	    editor.putString("password" ,strPassword);
	    editor.commit();
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
	    
	    CVariables.List监控车牌.clear();
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
	            CVariables.List监控车牌.add(mid_carname[1]);
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
 
    public void writeFileForCarList(String fileName,InputStream inputStream){
    	
        try{        	
			 FileOutputStream fout = openFileOutput(fileName, MODE_PRIVATE );
			 ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
		   int ch;  
		   while ((ch = inputStream.read()) != -1) {  
		    bytestream.write(ch);  
		   }  
	           byte buffer[] = bytestream.toByteArray();  
	           bytestream.close(); 
	         fout.write(buffer);
	         fout.close(); 
	         getcarlist_sax_fromBuffer(buffer);
         } 
        catch(Exception e){ 
         e.printStackTrace(); 
        }
    }   


    public InputStream readFileForCarList(String fileName){
        try{ 
        	FileInputStream fin = openFileInput(fileName);
        	return fin;
        } 

        catch(Exception e){ 

         e.printStackTrace(); 

        }
        return null;
    }
    
    private void getcarlist_sax()
    {
        new Thread() {
            public void run() {
                // String str = "http://122.139.57.157:9002/Login?UserName=liu&Password=8989821&Version=20121111&time=174326";
                String strUrl =CVariables.GetDataServerIP +  "/GetCarListStreamAndroid?UserID=" + CVariables.UserID + "&Password=" + CVariables.Password;
                HttpResponse httpResponse = null;
                HttpGet get = new HttpGet(strUrl);
                try {
                    DefaultHttpClient defaultHttpClient  = new DefaultHttpClient();
                    defaultHttpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 5000);
                    defaultHttpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 5000);
                    httpResponse = defaultHttpClient.execute(get);
                } catch (ClientProtocolException e) {
                    //   e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLoginStatus.setText("网络异常，获取车辆列表失败");
                        }
                    });
                    return;
                } catch (IOException e) {
                    //   e.printStackTrace();
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLoginStatus.setText("网络异常，获取车辆列表失败");
                        }
                    });

                    return;
                }
                
                if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                    try {
                        //// result = EntityUtils.toString(httpResponse.getEntity());
                        HttpEntity httpEntity = httpResponse.getEntity();
                        InputStream inputStream = httpEntity.getContent();                	
                		 
                        GZIPInputStream gis = new GZIPInputStream(inputStream);                        
                        	boolean bbbbbb = false;
                            if (bbbbbb ==true)
                            {                		
	                            SAXParserFactory factory = SAXParserFactory.newInstance();
	                            SAXParser parser = factory.newSAXParser();                            
	                    		XMLReader xmlReader=parser.getXMLReader();
	                    		//实例化handler，事件处理器
	                    		final SAXPraser_CarListHelper helperHandler=new SAXPraser_CarListHelper();
	                    		//解析器注册事件
	                    		xmlReader.setContentHandler(helperHandler);
	                    		//读取文件流
	                    		InputSource is=new InputSource(gis);
	                    		CVariables.mHashMap.clear();
	                    		xmlReader.parse(is);
	                    		gis.close();
	                            inputStream.close(); 
                            }                    		
                            else
                            {   
                   			 ByteArrayOutputStream bytestream = new ByteArrayOutputStream();  
	                  		   int ch;  
	                  		   while ((ch = gis.read()) != -1) {  
	                  		    bytestream.write(ch);  
	                  		   }  
                  	           byte buffer[] = bytestream.toByteArray();  
                  	           bytestream.close(); 
	                    		gis.close();
	                            inputStream.close(); 
	                            
		               			 FileOutputStream fout = openFileOutput("cars", MODE_PRIVATE );
		            	         fout.write(buffer);
		            	         fout.close(); 
                  	     //       writeFileToCarListFromBuffer("carlist",buffer);
	                           	getcarlist_sax_fromBuffer(buffer);
	                           	return;
                            }
                            
                            
                    } catch (ParseException e) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("网络异常，获取车辆列表失败");
                            }
                        });
                    } catch (IOException e) {

                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("网络异常，获取车辆列表失败");
                            }
                        });
                    }  
                    catch(SAXException e){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("网络异常，获取车辆列表失败");
                            }
                        });
                    }
                    catch(ParserConfigurationException e){
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("车辆列表XML解析异常");
                            }
                        });
                    }
                }
                else
                {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            textViewLoginStatus.setText("网络异常，获取车辆列表失败");
                        }
                    });
                }
            }
        }.start();
    }
    
    private void getcarlist_sax_fromFile( )
    {
    	final InputStream inputStream = readFileForCarList("cars");
        new Thread() {
            public void run() {
       	if (inputStream!=null)
    	{
 			try {
// 				GZIPInputStream gis = new GZIPInputStream(inputStream);     
 	            SAXParserFactory factory = SAXParserFactory.newInstance();
 	            SAXParser parser = factory.newSAXParser();
				XMLReader xmlReader = parser.getXMLReader();        		

        		//实例化handler，事件处理器
        		final SAXPraser_CarListHelper helperHandler = new SAXPraser_CarListHelper();
        		//解析器注册事件
        		xmlReader.setContentHandler(helperHandler);
        		//读取文件流
        		InputSource is=new InputSource(inputStream);
        		CVariables.mHashMap.clear();
        		xmlReader.parse(is);    
//        		gis.close();
                inputStream.close();
                
                if (CVariables.mHashMap.size()>0)
                {
            	    ReadSharedPreferencesForMonitorCars();	    
            	    CVariables.mHashMapMonitor.clear();
            	    CVariables.List监控车牌.clear();
            		for (Iterator Iterator = CVariables.mHashMap.values().iterator(); Iterator.hasNext();) {
            			CCar car =(CCar)Iterator.next();
                        HashMap<String, Object> map = new HashMap<String, Object>();
                        map.put("mid", car.getMid());
                        map.put("car_name", car.getCarame());

            			if (car.getIsmonitored()== true)
            			{
            				CVariables.mHashMapMonitor.put(car.getMid(), car);
            				CVariables.List监控车牌.add(car.getCarame());
            			}
            		}
            		
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("正在加载主界面...");
                            }
                        });
                        
                		try {
                			@SuppressWarnings("rawtypes")
                			Class clazz = Class.forName("com.tencent.mapsdkdemo.raster." + "ItemizedOverlayDemo");
                			android.content.Intent  Intent= new android.content.Intent(LoginActivity.this, clazz);
                			startActivity(Intent);
                			LoginActivity.this.finish();
                		} catch (Exception e) {                			
                    }
                	return;
                }

			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			} 
    	}
       	else
       	{
       		getcarlist_sax();
       	}
       	}}.start();
    }
    
    private void getcarlist_sax_fromBuffer( byte buffer[])
    {
    	final ByteArrayInputStream inputStream = new ByteArrayInputStream(buffer);  
        new Thread() {
            public void run() {
       	if (inputStream!=null)
    	{
 			try {
// 				GZIPInputStream gis = new GZIPInputStream(inputStream);     
 	            SAXParserFactory factory = SAXParserFactory.newInstance();
 	            SAXParser parser = factory.newSAXParser();
				XMLReader xmlReader = parser.getXMLReader();

        		//实例化handler，事件处理器
        		final SAXPraser_CarListHelper helperHandler = new SAXPraser_CarListHelper();
        		//解析器注册事件
        		xmlReader.setContentHandler(helperHandler);
        		//读取文件流
        		InputSource is=new InputSource(inputStream);
        		CVariables.mHashMap.clear();
        		xmlReader.parse(is);
//        		gis.close();
                inputStream.close();
                
                if (CVariables.mHashMap.size()>0)
                {                                        	
                    if (CVariables.mHashMap.size()>0)
                    {               
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                textViewLoginStatus.setText("正在加载主界面...");
                            }
                        });
                        
                	    ReadSharedPreferencesForMonitorCars();	    
                	    CVariables.mHashMapMonitor.clear();
                	    CVariables.List监控车牌.clear();
                		for (Iterator Iterator = CVariables.mHashMap.values().iterator(); Iterator.hasNext();) {
                			CCar car =(CCar)Iterator.next();
                            HashMap<String, Object> map = new HashMap<String, Object>();
                            map.put("mid", car.getMid());
                            map.put("car_name", car.getCarame());

                			if (car.getIsmonitored()== true)
                			{
                				CVariables.mHashMapMonitor.put(car.getMid(), car);
                				CVariables.List监控车牌.add(car.getCarame());
                			}
                		}
                        
                        
                		try {
                			@SuppressWarnings("rawtypes")
                			Class clazz = Class.forName("com.tencent.mapsdkdemo.raster." + "ItemizedOverlayDemo");
                			android.content.Intent  Intent= new android.content.Intent(LoginActivity.this, clazz);
                			startActivity(Intent);
                			LoginActivity.this.finish();
                		} catch (Exception e) {
                			
                		}
                    }
                	return;
                }
			} catch (ParserConfigurationException e) {
				e.printStackTrace();
			} catch (SAXException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

    	}}}.start();
    }  
}