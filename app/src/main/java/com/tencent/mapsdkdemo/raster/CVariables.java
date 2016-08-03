package com.tencent.mapsdkdemo.raster;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.mapkit.util.GeoPoint;

import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.BitmapFactory;

public class CVariables {
	public static HashMap<String,CCar> mHashMap = new HashMap<String,CCar>();	
	public static HashMap<String,CCar> mHashMapMonitor = new HashMap<String,CCar>();

	public static String OperationRights = "";
	public static int UserID = -1;
	public static String Username = "";
	public static String Password = "";
	
	public static int centerx = 125324400;
	public static int centery = 43869900;
	public static int zoomlevel = 10;
	
	public static Boolean IsSuperUser = false;
	public static Resources res = null;
	public static android.graphics.Matrix Matrix = new android.graphics.Matrix();
	public static android.graphics.Paint PaintRectGreen = new android.graphics.Paint();
	public static android.graphics.Paint PaintRectYellow = new android.graphics.Paint();
	public static android.graphics.Paint PaintRectRed = new android.graphics.Paint();
	public static android.graphics.Paint PaintText = new android.graphics.Paint();
	
	public static android.graphics.Bitmap BitmapRunNormal = null;
	public static android.graphics.Bitmap BitmapStopNormal = null;
	
	public static android.graphics.Bitmap BitmapRunOffline = null;
	public static android.graphics.Bitmap BitmapStopOffline = null;
	
	public static android.graphics.Bitmap BitmapRunSeleted = null;
	public static android.graphics.Bitmap BitmapStopSeleted = null;	
	public static SimpleDateFormat SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static float BitmapWidth = 0;
    public static Date CurDate ;
    public static  android.content.Intent IntentLoginActivity = null;
    public static List<String> List车牌 = new ArrayList<String>();
    public static List<String> List监控车牌 = new ArrayList<String>();
    public static com.tencent.mapsdkdemo.raster.ArrayAdapter<String> adapter监控车牌 =null;
//    public static String LoginServerIP = "http://115.28.150.63:9002";
//    public static String GetDataServerIP = "http://115.28.150.63:9001";
    
/* 外网
    public static String LoginServerIP = "http://115.28.44.144:9002";
    public static String GetDataServerIP = "http://115.28.44.144:9001";
*/
    
//    public static String LoginServerIP = "http://222.175.182.222:9002";
//    public static String GetDataServerIP = "http://222.175.182.222:9001";   
    
//    public static String LoginServerIP = "http://115.28.9.164:9002";
//   public static String GetDataServerIP = "http://115.28.9.164:9001";   

// 内网
    public static String LoginServerIP = "http://172.31.1.2:9002";
    public static String GetDataServerIP = "http://172.31.1.2:9001";   
//    
 
    public static CCar SelectedCCar = null;
    public static long vid = 1;
    public static long Ticks_max =0;
    
    //选中区域
    public static GeoPoint g0;//左上角
    public static GeoPoint g1;//右下角
}


