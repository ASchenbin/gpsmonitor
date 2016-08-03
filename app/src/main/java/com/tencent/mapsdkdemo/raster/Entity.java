package com.tencent.mapsdkdemo.raster;

import java.io.Serializable;

public class Entity implements Serializable{
	private static final long serialVersionUID = 1L;
	public final static String UTF8 = "UTF-8";
	protected int id;

	public int getId() {
		return id;
	}

	protected String cacheKey;

	public String getCacheKey() {
		return cacheKey;
	}

	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
}
