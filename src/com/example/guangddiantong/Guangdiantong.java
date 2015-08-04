package com.example.guangddiantong;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.json.JSONObject;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.WindowManager;

public class Guangdiantong extends Service {

	public static Guangdiantong guangdiantong;
	private WindowManager wm;
	private int width;
	private int height;
	static MyHandler myHandler;
	public static Context context;
	private final static int SHOW_AD = 0;

	static class MyHandler extends Handler {
		WeakReference<Guangdiantong> gdt;

		MyHandler(Guangdiantong guangdiantong) {
			gdt = new WeakReference<Guangdiantong>(guangdiantong);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_AD:
				ADinfo aDinfo = (ADinfo) msg.obj;
				MyDialog.show(aDinfo);
				switch (aDinfo.getCrt_type()) {
				case 1:
					break;
				case 2:

					break;
				case 3:
				case 7:
					break;
				default:
					break;
				}
				MyDialog.show(aDinfo);
				break;
			}
		}
	}

	public Guangdiantong(Context context) {
		guangdiantong = this;
		myHandler = new MyHandler(guangdiantong);
		Guangdiantong.context = context;
		MyDialog.getinstance(context);
		try {
			Parameter.IMEI = Tools.getIMEI(context);
			LogUtil.i("info", "IMEI:" + Parameter.IMEI);
			Parameter.muid = Tools.MD5(Parameter.IMEI);
		} catch (Exception e) {
			LogUtil.i("info", "md5ʧ��");
		}
		try {
			wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			DisplayMetrics dm = new DisplayMetrics();
			wm.getDefaultDisplay().getMetrics(dm);
			width = wm.getDefaultDisplay().getWidth();
			LogUtil.i("info", "��ȡ��:" + width);
			height = wm.getDefaultDisplay().getHeight();
			LogUtil.i("info", "��ȡ��:" + height);
			Parameter.c_w = width;
			Parameter.c_h = height;
		} catch (Exception e) {
			LogUtil.i("info", "��ȡ���ʧ��");
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	public static Guangdiantong getInstance(Context context) {
		if (Guangdiantongser.guangdiantong == null)
			Guangdiantongser.guangdiantong = new Guangdiantong(context);
		return Guangdiantongser.guangdiantong;
	}

	private static class Guangdiantongser {
		private static Guangdiantong guangdiantong;
	}

	/**
	 * �������ͨ���
	 * 
	 * @param context
	 */
	public static void getinformation(Context context) {
		new Thread() {
			public void run() {
				String info = Tools.getjson(getadurl().trim());
				LogUtil.i("info", "��������:" + info);
				Tools.analytical_adjson(info);
			};
		}.start();
	}

	/**
	 * ��ȡ���ӿ�
	 * 
	 * @return String
	 */
	private static String getadurl() {
		JSONObject json = Parameter.getreq();
		String url = null;
		try {
			url = URLEncoder.encode((Parameter.req + json.toString() + "}"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.i("info", "URL��ʧ��");
			e.printStackTrace();
		}
		LogUtil.i("info", "��ȡ���ӿ�:" + Parameter.url + url);
		LogUtil.i("info", "��ȡ���ӿ�(ԭ):" + Parameter.url + Parameter.req + json.toString() + "}");
		return Parameter.url + url;
	}

	/**
	 * AD��Ϣ��ȡ��ɷ���MESSAGEչʾ
	 * 
	 * @param aDinfos
	 */
	public static void showad(ArrayList<ADinfo> aDinfos) {
		LogUtil.i("info", "׼��չʾ���aDinfos.size:" + aDinfos.size());
		ADinfo aDinfo = null;
		if (aDinfos.size() > 0) {
			aDinfo = aDinfos.get(0);
		}
		if (aDinfo.getImg() != null) {
			if (myHandler == null)
				myHandler = new MyHandler(guangdiantong);
			LogUtil.i("info", "����ļ�����HANDLER����:");
			Message msg = Message.obtain();
			msg.what = SHOW_AD;
			msg.obj = aDinfo;
			myHandler.sendMessage(msg);
		} else {
			LogUtil.i("info", "aDinfo.getImg()Ϊ��");
		}
	}
}
