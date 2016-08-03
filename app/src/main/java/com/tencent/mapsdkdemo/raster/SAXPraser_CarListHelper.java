package com.tencent.mapsdkdemo.raster;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.graphics.Rect;
import android.graphics.RectF;
public class SAXPraser_CarListHelper extends DefaultHandler
    {	    
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
            if(localName.equals("Car")){                
                CCar car = new CCar();
                String mid = attributes.getValue("mid"); 
                car.setMid(mid);
                
                String car_id = attributes.getValue("car_id"); 
                car.setCarid(car_id);
                
                String car_name = attributes.getValue("car_name"); 
                car.setCarname(car_name); 
                Rect rect = new Rect();      
                CVariables.PaintText.getTextBounds(car_name, 0, car_name.length(),rect);
                
                car.setTextRect(rect);
                
                String car_owner = attributes.getValue("car_owner"); 
                car.setCarowner(car_owner);
                
                // caogh modified begin
                // 20160129
                //car.setIsmonitored(false);
                car.setIsmonitored(true);
                // end
                
                CVariables.List车牌.add(car_name);

                CVariables.mHashMap.put(mid, car);
            }
        }

        @Override
            public void characters(char ch [ ], int start, int length) throws SAXException{
            }

        @Override
            public void endElement(String uri, String localName, String qName) throws SAXException{                    
            
            }
    }