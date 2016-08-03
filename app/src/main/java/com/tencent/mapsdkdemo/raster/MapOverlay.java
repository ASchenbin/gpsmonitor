package com.tencent.mapsdkdemo.raster;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
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

import com.mapkit.api.IMapView;
import com.mapkit.util.BoundingBoxE6;
import com.mapkit.util.GeoPoint;
import com.mapkit.util.ResourceProxyImpl;
import com.mapkit.views.MapView;
import com.mapkit.views.Projection;
import com.mapkit.views.overlay.ItemizedOverlay;
import com.mapkit.views.overlay.OverlayItem;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.net.ParseException;


class MapOverlay extends ItemizedOverlay<OverlayItem> {
	private List<OverlayItem> itemList = new ArrayList<OverlayItem>();
//	private Context mContext;
	private OnTapListener tapListener=null;

	private double mLat1 = 43.8870; // point1纬度
	private double mLon1 = 125.3246; // point1经度

	public MapOverlay(Drawable marker, Context context) {
//		super(boundCenterBottom(marker));
		super(marker, new ResourceProxyImpl(context));
		// 用给定的经纬度构造GeoPoint，单位是微度 (度 * 1E6)
		populate();  //createItem(int)方法构造item。一旦有了数据，在调用其它方法前，首先调用这个方法
	}
	
	@Override
	public void draw(Canvas canvas, MapView mapView, boolean shadow) {
		// Projection接口用于屏幕像素点坐标系统和地球表面经纬度点坐标系统之间的变换
		Projection projection = mapView.getProjection(); 
		if (CVariables.mHashMapMonitor.size()==0)
			return;
		
		try
		{
			BoundingBoxE6 sCentralParkBoundingBox = null;
			if (CVariables.g0 != null && CVariables.g1 != null) {
				sCentralParkBoundingBox = new BoundingBoxE6(CVariables.g0.getLatitude(),
						CVariables.g1.getLongitude(), CVariables.g1.getLatitude(), CVariables.g0.getLongitude());
			}
	
			for (Iterator Iterator = CVariables.mHashMapMonitor.values().iterator(); Iterator.hasNext();) {
				CCar car =(CCar) Iterator.next();
				GeoPoint GeoPointCenter = new GeoPoint(car.getLat(),
						car.getLon());
				if (sCentralParkBoundingBox != null && !sCentralParkBoundingBox.contains(GeoPointCenter)) {
					continue;
				}
				if (CVariables.SelectedCCar != car) {
					drawcar(car,canvas,mapView,shadow,projection,false);
				}
			}
			
			if (CVariables.SelectedCCar != null) {
				drawcar(CVariables.SelectedCCar, canvas, mapView, shadow,
						projection, true);
			}

			super.draw(canvas, mapView, shadow);
		}
		catch (Exception e)
		{
			
		}
	}	

	
	private void drawcar(CCar car ,Canvas canvas, MapView mapView, boolean shadow,Projection projection,boolean Isselected)
	{		
		// 把经纬度变换到相对于MapView左上角的屏幕像素坐标	
		try
		{			
			int lat = car.getLat();
			int lon = car.getLon();
			
			if (lat==0 || lon ==0)
				return;
			
			GeoPoint p1 = new GeoPoint( lat, lon);
			Point point = projection.toPixels(p1, null);
			
			//返回包围整个字符串的最小的一个Rect区域
			RectF  rect = new RectF();
			rect.set(car.textRectF);
			rect.offset(point.x - rect.width() / 2, point.y + CVariables.BitmapWidth );
	
		
			if (car.getSpeed()>5)
			{
	            CVariables.Matrix.reset();
		        // 缩放原图  
//	            CVariables.Matrix.setTranslate(point.x - CVariables.BitmapWidth/2 + 1, point.y - CVariables.BitmapWidth/2);  ////原来的
	            CVariables.Matrix.setTranslate(point.x - CVariables.BitmapWidth/2, point.y - CVariables.BitmapWidth/2); 
	            CVariables.Matrix.preRotate(car.heading, CVariables.BitmapWidth/2, CVariables.BitmapWidth/2); 
	
	            if (CVariables.CurDate.before(car.infotime))
	            {	
	            	if (Isselected==true)
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectRed);
	            		canvas.drawBitmap(CVariables.BitmapRunSeleted, CVariables.Matrix, null);
	            	}
	            	else
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectGreen);
	            		canvas.drawBitmap(CVariables.BitmapRunNormal, CVariables.Matrix, null);
	            	}			        
	            }
	            else
	            {
	            	if (Isselected==true)
	            	{	            		
	            		canvas.drawRect(rect, CVariables.PaintRectRed);
	            		canvas.drawBitmap(CVariables.BitmapRunSeleted, CVariables.Matrix, null);
	            	}
	            	else
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectYellow); 	
	            		canvas.drawBitmap(CVariables.BitmapRunOffline, CVariables.Matrix, null);
	            	}
	            }
	            
				canvas.drawText(car.getCarame(), rect.left  , rect.bottom - 2 , CVariables.PaintText); // 绘制文本
			}
			else
			{				
	            CVariables.Matrix.reset();
		        // 缩放原图  
//	            CVariables.Matrix.setTranslate(point.x - CVariables.BitmapWidth/2 + 1, rect.top - CVariables.BitmapWidth); ////原来的

	            CVariables.Matrix.setTranslate(point.x - CVariables.BitmapWidth/2, rect.top - CVariables.BitmapWidth); 
	            
	            if (CVariables.CurDate.before(car.infotime))
	            {
	            	if (Isselected==true)
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectRed);
	                   	canvas.drawBitmap(CVariables.BitmapStopSeleted, CVariables.Matrix, null);
	            	}
	            	else
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectGreen);
	                   	canvas.drawBitmap(CVariables.BitmapStopNormal, CVariables.Matrix, null);
	               	}
	            }
	            else
	            {
	            	if (Isselected==true)
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectRed); 
	                   	canvas.drawBitmap(CVariables.BitmapStopSeleted, CVariables.Matrix, null);
	            	}
	            	else
	            	{
	            		canvas.drawRect(rect, CVariables.PaintRectYellow);
	                   	canvas.drawBitmap(CVariables.BitmapStopOffline, CVariables.Matrix, null);
	            	}
	            }
	            
				canvas.drawText(car.getCarame(), rect.left , rect.bottom -2, CVariables.PaintText); // 绘制文本	
			}
		}
		catch (Exception e)
		{
			
		}
	}

	@Override
	protected OverlayItem createItem(int i) {
		// TODO Auto-generated method stub
		return itemList.get(i);
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return itemList.size();
	}

	@Override
	protected boolean onTap(int i) {
		OverlayItem itemSelect=itemList.get(i);
		setFocus(itemSelect);
		if(tapListener!=null)
		{
			tapListener.onTap(itemSelect);
		}
		return true;
	}
	
//	@Override
	public void onEmptyTap(GeoPoint pt) {
		// TODO Auto-generated method stub
//		super.onEmptyTap(pt);
		
		if(tapListener!=null)
		{
			tapListener.onEmptyTap(pt);
		}
	}

	public interface OnTapListener
	{
		void onTap(OverlayItem itemTap);
		void onEmptyTap(GeoPoint pt);
	}
	
	public void setOnTapListener(OnTapListener listnerTap)
	{
		tapListener=listnerTap;
	}
	
	public void getcartrack_sax()
    {
        new Thread() {
            @Override
			public void run() {
                // String str = "http://122.139.57.157:9002/Login?UserName=liu&Password=8989821&Version=20121111&time=174326";
                String strUrl = CVariables.GetDataServerIP  + "/GetCarsTrackStreamAndroidByTicks?UserID=" + CVariables.UserID + "&Ticks=" +1 + "&Password=" + CVariables.Password;
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
            }
        }.start();
    }

	@Override
	public boolean onSnapToItem(int arg0, int arg1, Point arg2, IMapView arg3) {
		// TODO Auto-generated method stub
		return false;
	}
}

