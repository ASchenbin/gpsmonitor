package com.tencent.mapsdkdemo.raster;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
public class SAXPraser_LoginHelper extends DefaultHandler
    {
       @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException{
            if(localName.equals("登录状态")){
                String 返回值 = attributes.getValue("返回值");
                if (返回值.equals("登录成功") ==true)
                {
                	String strUserID = attributes.getValue("用户ID");
                	CVariables.UserID = Integer.parseInt(strUserID);                	
                	CVariables.OperationRights = attributes.getValue("操作权限");    
                	String 是否超级用户= attributes.getValue("是否超级用户");
                	if (是否超级用户.equals("True") == true)
                	{
                		CVariables.IsSuperUser = true;
                	}
                	else
                	{
                		CVariables.IsSuperUser = false;
                	}
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