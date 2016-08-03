package com.tencent.mapsdkdemo.raster;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class GPSInfoDB {

	private static GPSInfoDB instance = new GPSInfoDB();

	public static GPSInfoDB getInstance() {
		return instance;
	}
	
	public static class GPSInfo extends Entity {
		private static final long serialVersionUID = 2L;
		public int lon = 0; 
		public int lat = 0; 
		public double vel = 0.0; 
		public double dir = 0.0; 
		public long timestamp = 0;
		public String carid = "";
		public String carname = "";
		
		public GPSInfo() {
			
		}

		public JSONObject toJson() throws JSONException {
			JSONObject jobj = new JSONObject();
			jobj.put("date", timestamp);
			jobj.put("lng", lon);
			jobj.put("lat", lat);
			jobj.put("vel", vel);
			jobj.put("dir", dir);
			jobj.put("carid", carid);
			jobj.put("carname", carname);
			return jobj;
		}
		
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;

			if (obj == null)
				return false;

			if (getClass() != obj.getClass())
				return false;

			GPSInfo other = (GPSInfo) obj;

			if (timestamp != other.timestamp)
				return false;
			if (!carid.equalsIgnoreCase(other.carid))
				return false;

			return true;
		}
	}

	private SQLiteDatabase mDb = null;
	private static final String TABLENAME = "gpsinfo";
	private static final String DBNAME = "gpsinfo.bin";

	public boolean initDB() {
		try {
			if (mDb == null) {
				String DbPath = Environment.getExternalStorageDirectory() + "/YOUS/" + DBNAME;
//				final File file = new File(DbPath);
				mDb = SQLiteDatabase.openOrCreateDatabase(DbPath, null);
				// |timestamp|lon|lat|vel|dir|username|
				String sql = "CREATE TABLE IF NOT EXISTS [gpsinfo]("
						+ "[_id] INTEGER PRIMARY KEY AUTOINCREMENT,"
						+ "[timestamp] INTEGER," + "[lon] INTEGER,"
						+ "[lat] INTEGER," + "[vel] TINYINT,"
						+ "[dir] SMALLINT," + "[carid] TEXT," + "[carname] TEXT)";

				mDb.execSQL(sql);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		
		deleteGpsInfo();
		
		return true;
	}
	
	public void unInitDB() {
		if (mDb != null && mDb.isOpen()) {
			mDb.close();
			SQLiteDatabase.releaseMemory();
		}
	}

	private static final int LL = 1000000;

	private int changeLL(double ll) {
		return (int) (ll * LL);
	}

	private double rechangeLL(long ll) {
		return ll * 1.0000f / LL;
	}

	public void saveGpsInfo(GPSInfo gpsInfo) {
		if (gpsInfo == null || mDb == null) {
			return;
		}

		try {
			ContentValues cValue = new ContentValues();
			cValue.put("timestamp", gpsInfo.timestamp);
//			cValue.put("lon", changeLL(gpsInfo.lon));
//			cValue.put("lat", changeLL(gpsInfo.lat));
			cValue.put("lon", gpsInfo.lon);
			cValue.put("lat", gpsInfo.lat);
			cValue.put("vel", (int) gpsInfo.vel);
			cValue.put("dir", (int) gpsInfo.dir);
			cValue.put("carid", gpsInfo.carid);
			cValue.put("carname", gpsInfo.carname);
			mDb.insert(TABLENAME, null, cValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<GPSInfo> getGpsInfo(long fromTimestamp, long toTimestamp,
			int maxnum) {
		ArrayList<GPSInfo> GPSInfoList = new ArrayList<GPSInfo>();
		if (mDb == null) {
			return GPSInfoList;
		}

		String SELECT_SQL = "select * from " + TABLENAME
				+ " where timestamp > " + fromTimestamp;

		if (toTimestamp > 0) {
			SELECT_SQL = "select * from " + TABLENAME + " where timestamp > "
					+ fromTimestamp + " and timestamp < " + toTimestamp;
		}

		try {
			Cursor cursor = mDb.rawQuery(SELECT_SQL, null);
			while (cursor.moveToNext()) {
				GPSInfo g = new GPSInfo();
				g.timestamp = cursor
						.getLong(cursor.getColumnIndex("timestamp"));
//				g.lon = rechangeLL(cursor.getLong(cursor.getColumnIndex("lon")));
//				g.lat = rechangeLL(cursor.getLong(cursor.getColumnIndex("lat")));
				g.lon = cursor.getInt(cursor.getColumnIndex("lon"));
				g.lat = cursor.getInt(cursor.getColumnIndex("lat"));
				g.vel = cursor.getInt(cursor.getColumnIndex("vel"));
				g.dir = cursor.getInt(cursor.getColumnIndex("dir"));
				g.carid = cursor.getString(cursor.getColumnIndex("carid"));
				g.carname = cursor.getString(cursor.getColumnIndex("carname"));
				GPSInfoList.add(g);

				if (maxnum > 0 && GPSInfoList.size() > maxnum) {
					break;
				}
			}
		} catch (Exception e) {

		}

		return GPSInfoList;
	}

	public void deleteGpsInfo() {
		if (mDb == null) {
			return;
		}

		String DbPath = Environment.getExternalStorageDirectory() + "/YOUS/" + DBNAME;
		double fileSize = FileSizeUtil.getFileOrFilesSize(DbPath, 3);
		//如果DB超过10MB才删除前5天的
		if (fileSize < 10) {
			return;
		}
		
		long currentTimestamp = System.currentTimeMillis();
		currentTimestamp -= 1000 * 60 * 60 * 24 * 5;
		if (currentTimestamp < 0) {
			return;
		}
		try {
			mDb.delete(TABLENAME, "timestamp <" + currentTimestamp, null);
		} catch (Exception e) {

		}
	}

	public ArrayList<GPSInfo> getGpsInfoByCarid(String carId) {
		ArrayList<GPSInfo> GPSInfoList = new ArrayList<GPSInfo>();
		if (mDb == null || carId == null || carId.length() < 1) {
			return GPSInfoList;
		}

		String SELECT_SQL = "select * from " + TABLENAME
				+ " where carId = " + carId;
		try {
			Cursor cursor = mDb.rawQuery(SELECT_SQL, null);
			while (cursor.moveToNext()) {
				GPSInfo g = new GPSInfo();
				g.timestamp = cursor
						.getLong(cursor.getColumnIndex("timestamp"));
				g.lon = cursor.getInt(cursor.getColumnIndex("lon"));
				g.lat = cursor.getInt(cursor.getColumnIndex("lat"));
				g.vel = cursor.getInt(cursor.getColumnIndex("vel"));
				g.dir = cursor.getInt(cursor.getColumnIndex("dir"));
				g.carid = cursor.getString(cursor.getColumnIndex("carid"));
				g.carname = cursor.getString(cursor.getColumnIndex("carname"));
				GPSInfoList.add(g);
			}
		} catch (Exception e) {

		}

		return GPSInfoList;
	}
}
