package com.tencent.mapsdkdemo.raster;

import java.util.Date;

import android.graphics.Rect;
import android.graphics.RectF;

public class CCar {
	private String mid;
	private String car_id;
	private String car_name;
	private Boolean ismonitored;
	private String car_owner;

	public Date infotime;
	private int lon;
	private int lat;
	public float speed;
	public float heading;
	private String status;
	private String location;
	
	public RectF textRectF; 
	
	public String getMid() {
		return mid;
	}
	public void setMid(String mid) {
		this.mid = mid;
	}
	
	public String getCarid() {
		return car_id;
	}
	public void setCarid(String car_id) {
		this.car_id = car_id;
	}

	public String getCarame() {
		return car_name;
	}
	public void setCarname(String car_name) {
		this.car_name = car_name;
	}
	
	public String getCarowner() {
		return car_owner;
	}
	public void setCarowner(String car_owner) {
		this.car_owner = car_owner;
	}
	
	public Boolean getIsmonitored() {
		return ismonitored;
	}
	
	public void setIsmonitored(Boolean ismonitored) {
		this.ismonitored = ismonitored;
	}
	
	public Date getDate() {
		return infotime;
	}
	public void setDate(Date infotime) {
		this.infotime = infotime;
	}
	
	public int getLon() {
		return lon;
	}
	public void setLon(int lon) {
		this.lon = lon;
	}
	
	public int getLat() {
		return lat;
	}
	public void setLat(int lat) {
		this.lat = lat;
	}
	
	public float getSpeed() {
		return speed;
	}
	public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	public float getHeading() {
		return heading;
	}
	public void setHeading(float heading) {
		this.heading = heading;
	}

	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	
	public RectF getTextRect() {
		return textRectF;
	}
	public void setTextRect(Rect textRect) {
		this.textRectF = new RectF();
		this.textRectF.left = textRect.left;
		this.textRectF.right = textRect.right;
		this.textRectF.top = textRect.top;
		this.textRectF.bottom = textRect.bottom;
	}
}
