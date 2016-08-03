package com.tencent.mapsdkdemo.raster;
import java.text.ParseException;
import java.util.Date;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.tencent.mapsdkdemo.raster.GPSInfoDB.GPSInfo;
public class SAXPraser_CarTrackHelper extends DefaultHandler
    {	    
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
        	if(localName.equals("track")){
                String mid = attributes.getValue("mid"); 
                if (CVariables.mHashMapMonitor.containsKey(mid) ==false)
                	return;
                
                CCar car = CVariables.mHashMapMonitor.get(mid);
                car.setMid(mid);
                
//                Date curDate  = new Date(System.currentTimeMillis() + 3600 * 8 * 1000);//获取当前时间 
                Date curDate  = new Date(System.currentTimeMillis());//获取当前时间 
                String strinfotime = attributes.getValue("time");
                try {
                  Date	infotime = CVariables.SimpleDateFormat.parse(strinfotime);
                  car.setDate(infotime);
				} catch (ParseException e) {
					car.setDate(curDate);
				}
                
                if (mid == "15024408129")
                {
                	int test = 0;
                	test++;
                	test++;
                }
                
                if (CVariables.Ticks_max==0)
                	CVariables.Ticks_max=1;                
                
                String strTicks =  attributes.getValue("ticks"); 
                if (strTicks!=null)
                {
                	long ticks = Long.parseLong(strTicks);
                	if (ticks>CVariables.Ticks_max)
                	{
                		CVariables.Ticks_max = ticks;
                	}
                }
                
                String lon = attributes.getValue("longitude"); 
                double  dlon =  Double.parseDouble(lon) / 60 * 1E6;
                car.setLon((int)dlon);
                
                String lat = attributes.getValue("latitude"); 
                double  dlat =  Double.parseDouble(lat) / 60 * 1E6;
                car.setLat((int)dlat);             
                
                
                
                String speed = attributes.getValue("speed"); 
                car.setSpeed(Float.parseFloat(speed));
                
                String heading = attributes.getValue("heading"); 
                car.setHeading(Float.parseFloat(heading));
                
                String status = attributes.getValue("status"); 
                car.setStatus(status);
                
                String location = attributes.getValue("status_ex"); 
                car.setLocation(location);  
                
                GPSInfo gpsInfo = new GPSInfo();
                gpsInfo.carid = car.getCarid();
                gpsInfo.carname = car.getCarame();
                gpsInfo.lon = car.getLon(); 
                gpsInfo.lat = car.getLat(); 
                gpsInfo.vel = car.getSpeed();  
                gpsInfo.dir = car.getHeading(); 
//                gpsInfo.timestamp = car.getDate().getTime();
                gpsInfo.timestamp = System.currentTimeMillis();

                GPSInfoDB.getInstance().saveGpsInfo(gpsInfo);
                
            }
        	else if(localName.equals("tracks") ==true)
        	{
        	//     Date curDate  = new Date(System.currentTimeMillis() + 3600 * 8 * 1000);//获取当前时间 
        	     Date curDate  = new Date(System.currentTimeMillis());//获取当前时间 
                 String strinfotime = attributes.getValue("CurrentDateTime"); 
                try {
                  Date	infotime = CVariables.SimpleDateFormat.parse(strinfotime);
                  CVariables.CurDate = infotime;                  
				} catch (ParseException e) {
					CVariables.CurDate = curDate;
				}
        	}
        }

        @Override
            public void characters(char ch [ ], int start, int length) throws SAXException{
            }

        @Override
            public void endElement(String uri, String localName, String qName) throws SAXException{                    
            
            }
    }