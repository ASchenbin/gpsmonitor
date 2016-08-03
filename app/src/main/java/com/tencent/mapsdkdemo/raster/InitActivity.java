package com.tencent.mapsdkdemo.raster;
import android.app.Activity;
import cn.com.cybertech.pdk.widget.AccountMappingDialogFragment;
import cn.com.cybertech.pdk.widget
	.AccountMappingDialogFragment.AccountMappding;

//public class InitActivity extends ActionBarActivity implements AccountMappding {
//	@Override
//	publicvoid doMapping(String accessToken, CharSequence account,
//			CharSequence pwd) {
//		/*
//		 * // 绑定APP与PSTORE的账号 new AsyncTask<Params, Progress, Result>() {
//		 * 
//		 * @Override protected Result doInBackground(Void... params) { //
//		 * 连接服务器，调用接口，绑定账号 return result; } }.execute();
//		 */
//	}
//}

//实现账号绑定接口
public class InitActivity extends Activity implements AccountMappding {
	@Override
	public void doMapping(String accessToken, CharSequence account,
			CharSequence pwd) {
		/*
		 * // 绑定APP与PSTORE的账号 new AsyncTask<Params, Progress, Result>() {
		 * 
		 * @Override protected Result doInBackground(Void... params) { //
		 * 连接服务器，调用接口，绑定账号 return result; } }.execute();
		 */
	}
}


