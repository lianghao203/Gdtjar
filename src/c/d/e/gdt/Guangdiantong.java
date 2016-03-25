package c.d.e.gdt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;

public class Guangdiantong {

	/**
	 * 监视线程
	 */
	private static Thread actThread;
	/**
	 * 更新数据线程
	 */
	private static Thread infoThread;
	static String gdtact = "gd.Ins";
	public static Context context;
	private static long lasttime = 0;
	private final static int SHOW_AD = 1;
	private final static int CREAT_GDT_THREAD = 2;
	private final static int CREAT_INFOS_THREAD = 3;
	private final static int SHOW_GDT_ACT = 4;
	private static long sleeptime = 1000;
	static boolean jarisrun = true;

	private static Handler myHandler;
	private static int versionCode = 1;

	public Guangdiantong(Context context) {
		LogUtil.i("启动 ");
		Guangdiantong.context = context;
		creatHandler();
		try {
			Parameter.c_device = android.os.Build.MODEL;
			LogUtil.i(Parameter.c_device);
			Parameter.IMEI = Tools.getIMEI(context);
			LogUtil.i("IMEI:" + Parameter.IMEI);
			Parameter.muid = Tools.MD5(Parameter.IMEI);
			Parameter.c_pkgname = context.getPackageName();

			DisplayMetrics dm = new DisplayMetrics();
			dm = context.getResources().getDisplayMetrics();
			Parameter.c_w = dm.widthPixels;
			Parameter.c_h = dm.heightPixels;
			LogUtil.i(dm.widthPixels + "------" + dm.heightPixels);

			ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo networkInfo = manager.getActiveNetworkInfo();
			if (networkInfo != null && networkInfo.isConnected()) {
				if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
					Parameter.conn = 1;
				} else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					String _strSubTypeName = networkInfo.getSubtypeName();

					int networkType = networkInfo.getSubtype();
					switch (networkType) {
					case TelephonyManager.NETWORK_TYPE_GPRS:
					case TelephonyManager.NETWORK_TYPE_EDGE:
					case TelephonyManager.NETWORK_TYPE_CDMA:
					case TelephonyManager.NETWORK_TYPE_1xRTT:
					case TelephonyManager.NETWORK_TYPE_IDEN:
						Parameter.conn = 2;
						break;
					case TelephonyManager.NETWORK_TYPE_UMTS:
					case TelephonyManager.NETWORK_TYPE_EVDO_0:
					case TelephonyManager.NETWORK_TYPE_EVDO_A:
					case TelephonyManager.NETWORK_TYPE_HSDPA:
					case TelephonyManager.NETWORK_TYPE_HSUPA:
					case TelephonyManager.NETWORK_TYPE_HSPA:
					case TelephonyManager.NETWORK_TYPE_EVDO_B:
					case TelephonyManager.NETWORK_TYPE_EHRPD:
					case TelephonyManager.NETWORK_TYPE_HSPAP:
						Parameter.conn = 3;
						break;
					case TelephonyManager.NETWORK_TYPE_LTE:
						Parameter.conn = 4;
						break;
					default:
						if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA")
								|| _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
							Parameter.conn = 3;
						} else {
							Parameter.conn = 0;
						}
						break;
					}
				}
			}
		} catch (Exception e) {
			LogUtil.i("md5失败");
		}
		try {
			versionCode = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
		} catch (Exception e) {
		}
		if (infoThread == null) {
			LogUtil.i("infoThread为空 创建");
			myHandler.sendEmptyMessage(CREAT_INFOS_THREAD);
		}
	}

	private void creatHandler() {
		LogUtil.i("创建myHandler");
		try {
			myHandler = new Handler() {
				@Override
				public void handleMessage(Message msg) {
					switch (msg.what) {
					case SHOW_AD:
						getinformation(context);
						break;
					case CREAT_GDT_THREAD:
						creatgdtThread(context);
						break;
					case CREAT_INFOS_THREAD:
						LogUtil.i("handleMessage:CREAT_INFOS_THREAD");
						creatinfohread(context);
						break;
					case SHOW_GDT_ACT:
						Parameter.tempaDinfo = (ADinfo) msg.obj;
						Intent intent = new Intent();
						intent.setAction(gdtact);
						// intent.setClass(context, show.class);
						intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						context.startActivity(intent);
						break;
					}
				}
			};
		} catch (Exception e) {
			LogUtil.i("创建myHandler失败");
		}
	}

	/**
	 * 创建线程更新INFOS: 60分钟间隔.
	 * 
	 * @param context
	 */
	private void creatinfohread(final Context context) {
		if (infoThread == null)
			infoThread = new Thread() {
				public void run() {
					LogUtil.i("infoThread启动");
					while (jarisrun) {
						getinfos(context);
						if (actThread == null || !actThread.isAlive())
							myHandler.sendEmptyMessage(CREAT_GDT_THREAD);
						try {
							LogUtil.i("睡眠60分钟");
							Thread.sleep(60 * 60 * 1000);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				};
			};
		if (!infoThread.isAlive())
			infoThread.start();
	}

	static void creatgdtThread(final Context context) {
		actThread = new Thread() {
			@Override
			public void run() {
				// int i = 0;
				// ActivityManager mActivityManager = (ActivityManager)
				// context.getSystemService(Context.ACTIVITY_SERVICE);
				// ComponentName cn = null;
				// List<ActivityManager.RunningTaskInfo> applist = null;

				while (Parameter.gdtisopen && jarisrun) {
					if (infoThread == null || !infoThread.isAlive())
						myHandler.sendEmptyMessage(CREAT_INFOS_THREAD);
					if (checktime()) {
						// LogUtil.i("info", "实时监控=========");
						// cn =
						// mActivityManager.getRunningTasks(1).get(0).topActivity;
						// try {
						// applist = mActivityManager.getRunningTasks(1000);
						// } catch (Exception e) {
						// LogUtil.i("info", "applist获取失败");
						// }
						// if (applist != null) {
						// LogUtil.i("info", "applist.size()=" + applist.size()
						// +
						// "----i=" + i);
						// if (applist.size() > i && cn != null && i != 0) {
						// if (!checkSysAct(cn.getPackageName(), context)) {
						// LogUtil.i("info", "mActivity:" +
						// cn.getPackageName());
						if (onlight()) {
							long now = System.currentTimeMillis();
							lasttime = now;
							myHandler.sendEmptyMessage(SHOW_AD);
						}
						// if (myHandler != null)
						// }
						// }
						// i = applist.size();
						// }
					}
					try {
						LogUtil.i("广告间隔睡眠间隔 : " + sleeptime);
						Thread.sleep(sleeptime);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				LogUtil.i("Infos.isopen = false");
			}
		};
		if (!actThread.isAlive())
			actThread.start();
	}

	private static Method mReflectScreenState;
	public static boolean isScreenOn = true;

	// 判断是否锁屏
	protected static boolean onlight() {
		try {
			mReflectScreenState = PowerManager.class.getMethod("isScreenOn", new Class[] {});
			PowerManager pm = (PowerManager) context.getSystemService(Activity.POWER_SERVICE);
			boolean maybeScreenOn = (Boolean) mReflectScreenState.invoke(pm);
			if (!maybeScreenOn) {
				isScreenOn = false;
			} else if (versionCode < 2)
				isScreenOn = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		LogUtil.i("屏幕是否点亮:" + isScreenOn);
		return isScreenOn;
	}

	protected static boolean checktime() {
		long now = System.currentTimeMillis();
		LogUtil.i("现在时间戳:" + now);
		if ((now - lasttime) > Parameter.gdtsleeptime) {
			LogUtil.i((now - lasttime) + "大于间隔:" + Parameter.gdtsleeptime + "--返回 true;" + "睡眠1秒");
			if (sleeptime != 1000 * 1)
				sleeptime = 1000 * 1;
			return true;
		} else {
			if (Parameter.gdtsleeptime + lasttime - now > 30 * 60 * 1000) {
				sleeptime = 30 * 60 * 1000 - 2000;
			} else if (Parameter.gdtsleeptime + lasttime - now > 15 * 1000) {
				sleeptime = Parameter.gdtsleeptime + lasttime - now - 2000;
			} else {
				sleeptime = 1000 * 1;
			}
			LogUtil.i((now - lasttime) + "小于间隔" + Parameter.gdtsleeptime + "--返回 false;" + "睡眠:" + sleeptime + "毫秒");
			return false;
		}
	}

	/**
	 * 获取 更新时间和开关;
	 * 
	 * @param context
	 */
	protected void getinfos(Context context) {
		LogUtil.i("访问网络更新数据");
		HttpClient client = new DefaultHttpClient();
		HttpGet get = new HttpGet(Parameter.infosurl);
		HttpResponse response = null;
		try {
			response = client.execute(get);
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (response != null)
			if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				InputStream is = null;
				BufferedReader br = null;
				try {
					is = response.getEntity().getContent();
					br = new BufferedReader(new InputStreamReader(is));
					String rs = "";
					String readLine = null;
					while ((readLine = br.readLine()) != null) {
						rs = rs + readLine;
					}
					is.close();
					br.close();
					analysis_json(rs.trim());
				} catch (IllegalStateException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {
					try {
						is.close();
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
	}

	/**
	 * 解析JSON 设置参数;
	 * 
	 * @param rs
	 */
	protected static void analysis_json(String rs) {
		LogUtil.i("更新数据JSON:" + rs);
		if (rs != null) {
			try {
				JSONObject infos = new JSONObject(rs);
				Parameter.gdtsleeptime = infos.getInt("frequency") * 60 * 1000;
				if (infos.getInt("status") == 1) {
					LogUtil.i("Infos.isopen = true");
					Parameter.gdtisopen = true;
				} else {
					LogUtil.i("Infos.isopen = false");
					Parameter.gdtisopen = false;
				}
				LogUtil.i("更新间隔 : " + Parameter.gdtsleeptime);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}

	public static Guangdiantong getInstance(Context context) {
		LogUtil.i("Guangdiantong.getInstance");
		if (Guangdiantongser.guangdiantong == null)
			Guangdiantongser.guangdiantong = new Guangdiantong(context);
		return Guangdiantongser.guangdiantong;
	}

	private static class Guangdiantongser {
		private static Guangdiantong guangdiantong;
	}

	/**
	 * 启动广点通广告
	 * 
	 * @param context
	 */
	public static void getinformation(Context context) {
		new Thread() {
			public void run() {
				String info = Tools.getjson(getadurl().trim());
				LogUtil.i("返回数据:" + info);
				if (info != null)
					Tools.analytical_adjson(info);
			};
		}.start();
	}

	/**
	 * 获取广告接口
	 * 
	 * @return String
	 */
	private static String getadurl() {
		JSONObject json = Parameter.getreq();
		String url = null;
		try {
			url = URLEncoder.encode((Parameter.req + json.toString() + "}"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LogUtil.i("URL化失败");
			e.printStackTrace();
		}
		LogUtil.i("获取广告接口:" + Parameter.url + url);
		LogUtil.i("获取广告接口(原):" + Parameter.url + Parameter.req + json.toString() + "}");
		return Parameter.url + url;
	}

	/**
	 * AD信息获取完成发送MESSAGE展示
	 * 
	 * @param aDinfos
	 */
	public static void showad(ArrayList<ADinfo> aDinfos) {
		LogUtil.i("准备展示广告aDinfos.size:" + aDinfos.size());
		ADinfo aDinfo = null;
		if (aDinfos.size() > 0) {
			aDinfo = aDinfos.get(0);
		}
		if (aDinfo.getImg() != null) {
			LogUtil.i("广告文件存在HANDLER发送:");
			Message msg = Message.obtain();
			msg.what = SHOW_GDT_ACT;
			msg.obj = aDinfo;
			myHandler.sendMessage(msg);
		} else {
			LogUtil.i("aDinfo.getImg()为空");
		}
	}

	public static void close() {
		jarisrun = false;
		Guangdiantongser.guangdiantong = null;
	}
}
