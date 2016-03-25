package c.d.e.gdt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.telephony.TelephonyManager;

public class Tools {

	/**
	 * 获取设备IMEI--失败: 自己创建.
	 * 
	 * @param context
	 * @return String
	 */
	public static String getIMEI(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		if (null == imei || "".equals(imei)) {
			String BuildUUid = "35" + Build.BOARD.length() % 10 + Build.BRAND.length() % 10 + Build.CPU_ABI.length()
					% 10 + Build.DEVICE.length() % 10 + Build.DISPLAY.length() % 10 + Build.HOST.length() % 10
					+ Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 + Build.MODEL.length() % 10
					+ Build.PRODUCT.length() % 10 + Build.TAGS.length() % 10 + Build.TYPE.length() % 10
					+ Build.USER.length() % 10;
			return BuildUUid;
		}
		return imei;
	}

	/**
	 * MD5化字符串
	 * 
	 * @param str
	 * @return String
	 */
	public static String MD5(String str) {
		try {
			MessageDigest md5 = MessageDigest.getInstance("MD5");
			char[] charArray = str.toCharArray();
			byte[] byteArray = new byte[charArray.length];
			for (int i = 0; i < charArray.length; i++) {
				byteArray[i] = (byte) charArray[i];
			}
			byte[] md5Bytes = md5.digest(byteArray);
			StringBuffer hexValue = new StringBuffer();
			for (int i = 0; i < md5Bytes.length; i++) {
				int val = ((int) md5Bytes[i]) & 0xff;
				if (val < 16) {
					hexValue.append("0");
				}
				hexValue.append(Integer.toHexString(val));
			}
			return hexValue.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
	}

	/**
	 * 解析获取广告JSON
	 * 
	 * @param info
	 */
	public static void analytical_adjson(String info) {
		if (info != null) {
			info = info.substring(info.indexOf("{"), info.lastIndexOf("}") + 1);
			try {
				JSONObject object = new JSONObject(info);
				int ret = object.getInt("ret");
				int rpt = 0;
				String msg = null;
				int reqinterval = 0;
				try {
					rpt = object.getInt("rpt");
					msg = object.getString("msg");
					reqinterval = object.getInt("reqinterval");
				} catch (Exception e) {
				}
				if (ret == 0) {
					LogUtil.i("获取广告数据成功");
					String data = object.getString("data");
					JSONObject adjsons = new JSONObject(data);
					Iterator<?> iterator = adjsons.keys();
					String posid = (String) iterator.next();
					LogUtil.i("posid:" + posid);
					String jsons = adjsons.getString(posid);
					JSONObject object1 = new JSONObject(jsons);
					int ret1 = 0;
					String msg1 = null;
					try {
						msg1 = object1.getString("msg");
						ret1 = object1.getInt("ret");
					} catch (Exception e) {
					}
					if (ret1 == 0) {
						ArrayList<ADinfo> aDinfos = new ArrayList<ADinfo>();
						String list = object1.getString("list");
						JSONArray array = new JSONArray(list);
						JSONObject object3 = null;
						ADinfo aDinfo;
						for (int i = 0; i < array.length(); i++) {
							aDinfo = new ADinfo();
							object3 = array.getJSONObject(i);
							aDinfo.setActtype(object3.getInt("acttype"));
							LogUtil.i("acttype字段:" + object3.getInt("acttype"));
							aDinfo.setBtn_render(object3.getInt("btn_render"));
							aDinfo.setCrt_type(object3.getInt("crt_type"));
							LogUtil.i("crt_type字段:" + object3.getInt("crt_type"));
							switch (aDinfo.getCrt_type()) {
							case 1:
								aDinfo.setDesc(object3.getString("desc"));
								aDinfo.setTxt(object3.getString("txt"));
								break;
							case 2:
								aDinfo.setImg(object3.getString("img"));
								break;
							case 3:
							case 7:
								aDinfo.setTxt(object3.getString("txt"));
								aDinfo.setImg(object3.getString("img"));
								aDinfo.setDesc(object3.getString("desc"));
								break;
							default:
								break;
							}
							aDinfo.setIs_full_screen_interstitial(object3.getBoolean("is_full_screen_interstitial"));
							aDinfo.setRl(object3.getString("rl"));
							aDinfo.setTargetid(object3.getString("targetid"));
							aDinfo.setViewid(object3.getString("viewid"));
							try {
								aDinfo.setImg2(object3.getString("img2"));
								LogUtil.i("包含IMG2:" + object3.getString("img2"));
							} catch (Exception e) {
							}
							aDinfos.add(aDinfo);
						}
						aDinfos = downloadimg(aDinfos);
						Guangdiantong.showad(aDinfos);
					} else {
						LogUtil.i("获取广告列表失败");
						LogUtil.i("获取广告数据失败 msg1:" + msg1);
					}
				} else {
					LogUtil.i("获取广告数据失败.");
					LogUtil.i("获取广告数据失败 rpt:" + rpt);
					LogUtil.i("获取广告数据失败 msg:" + msg);
					LogUtil.i("获取广告数据失败 reqinterval:" + reqinterval);
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 下载图片
	 * 
	 * @param aDinfos
	 * @return
	 */
	private static ArrayList<ADinfo> downloadimg(final ArrayList<ADinfo> aDinfos) {
		for (int i = 0; i < aDinfos.size(); i++) {
			ADinfo aDinfo = aDinfos.get(i);
			if (aDinfo.getImg() != null && !aDinfo.getImg().equals("")) {
				LogUtil.i("Img");
				aDinfos.get(i).setImgFile(getbms(aDinfo.getImg()));
			}
			if (aDinfo.getImg2() != null && !aDinfo.getImg2().equals("")) {
				LogUtil.i("Img2");
				aDinfos.get(i).setImg2File(getbms(aDinfo.getImg2()));
			}
		}
		return aDinfos;
	}

	/**
	 * 解析地址
	 * 
	 * @param string
	 * @return
	 */
	protected static String getbms(String string) {
		LogUtil.i("图片下载地址:" + string);
		String imgfile = null;

		Pattern p = Pattern.compile("[^\\/]*?\\.jpg", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(string);
		if (m.find()) {
			imgfile = downloadFile(string, m.group(0), "jpg", Parameter.downloadmulu);
		} else {
			Pattern p1 = Pattern.compile("[^\\/]*?\\.png", Pattern.CASE_INSENSITIVE);
			Matcher m1 = p1.matcher(string);
			if (m1.find()) {
				imgfile = downloadFile(string, m1.group(0), "png", Parameter.downloadmulu);
			} else {
				Pattern p2 = Pattern.compile("[^\\/]*?\\.gif", Pattern.CASE_INSENSITIVE);
				Matcher m2 = p2.matcher(string);
				if (m.find()) {
					imgfile = downloadFile(string, m2.group(0), "gif", Parameter.downloadmulu);
				} else {
					imgfile = downloadFile(string, string.substring(string.lastIndexOf("/")), null,
							Parameter.downloadmulu);
				}
			}
		}
		return imgfile;
	}

	/**
	 * 下载文件
	 * 
	 * @param url
	 *            下载地址
	 * @param name
	 *            图片名称
	 * @param type
	 *            图片种类
	 * @param mulu
	 *            下载目录
	 * @return
	 */
	private static String downloadFile(String url, String name, String type, String mulu) {
		File filedir = new File(mulu);
		if (!filedir.exists())
			filedir.mkdir();
		File filetemp = new File(mulu + name + ".temp");

		File file = null;
		// if (type != null) {
		// file = new File(mulu + name);
		// }
		LogUtil.i("开始下载:" + name);
		LogUtil.i("开始下载地址:" + url);
		try {
			URL imgurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgurl.openConnection();
			conn.connect();
			int filelength = conn.getContentLength();
			// if (file.exists() && file.length() == conn.getContentLength()) {
			// LogUtil.i( "图片存在:" + file.getAbsolutePath());
			// return file.getAbsolutePath();
			// if (file.exists())
			// file.delete();
			// } else {
			if (type == null) {
				String pictype = checktype(conn.getHeaderFields().toString());
				if (pictype == null)
					return null;
				file = new File(mulu + name + "." + pictype);
			} else {
				file = new File(mulu + name);
			}
			if (file.exists())
				file.delete();
			if (filetemp.exists())
				filetemp.delete();
			InputStream is = conn.getInputStream();
			FileOutputStream fos = new FileOutputStream(filetemp);
			int len = 0;
			byte[] buffer = new byte[1024];
			while ((len = is.read(buffer)) != -1) {
				fos.write(buffer, 0, len);
			}
			is.close();
			fos.close();
			if (filetemp.length() == filelength) {
				filetemp.renameTo(file);
				LogUtil.i("下载成功:" + file.getAbsolutePath());
				return file.getAbsolutePath();
			} else {
				return null;
			}
			// }
		} catch (ClientProtocolException e) {
			LogUtil.i("下载失败");
			e.printStackTrace();
		} catch (IOException e) {
			LogUtil.i("下载失败");
			e.printStackTrace();
		}
		return null;
	}

	private static String checktype(String string) {
		String replacement = "Content-Type=\\[.*?\\/(.*?)\\]";
		Pattern p = Pattern.compile(replacement);
		Matcher m = p.matcher(string);
		if (m.find()) {
			return m.group(1);
		}
		return null;
	}

	/**
	 * 曝光上报
	 * 
	 * @param aDinfo
	 *            曝光加密串，从广告响应viewid字段获取
	 */
	public static void exposure(final ADinfo aDinfo) {
		new Thread() {
			@Override
			public void run() {
				String url = null;
				try {
					url = Parameter.adexposureurl + URLDecoder.decode(aDinfo.getViewid(), "UTF-8");
					LogUtil.i("曝光上报:" + url);
					HttpClient client = new DefaultHttpClient();
					HttpGet myget = new HttpGet(url.trim());
					try {
						HttpResponse response = client.execute(myget);
						if (response.getStatusLine().getStatusCode() != 204)
							LogUtil.i("曝光失败:" + response.getStatusLine().getStatusCode());
					} catch (ClientProtocolException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} catch (UnsupportedEncodingException e) {
					LogUtil.i("曝光上报:URL转换失败");
					e.printStackTrace();
				}
				super.run();
			}
		}.start();
	}

	/**
	 * 点击上报
	 * 
	 * @param aDinfo
	 *            广告信息
	 * @param clickinfo
	 *            点击位置信息
	 */
	public static void clickrepoct(final ADinfo aDinfo, final JSONObject clickinfo) {
		final int type = aDinfo.getActtype();
		LogUtil.i("getActtype:" + type);
		if (type == 1) {
			new Thread() {
				public void run() {
					try {
						String url = aDinfo.getRl() + "&acttype=" + type + "&s="
								+ URLEncoder.encode(clickinfo.toString(), "UTF-8");
						LogUtil.i("点击上报:" + url);
						String apkjson = getjson(url);
						LogUtil.i("点击上报--返回APKJSON:" + apkjson);
						if (apkjson != null)
							getapkinfo(aDinfo, apkjson);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				};
			}.start();
		} else if (type == 0 || type == 18) {
			String url = aDinfo.getRl() + "&s=" + clickinfo.toString();
			LogUtil.i("点击上报自动跳转系统IE:" + url);
			ShowInSysie(url);
		}
	}

	/**
	 * 解析APK参数
	 * 
	 * @param aDinfo
	 * @param apkjson
	 */
	protected static void getapkinfo(ADinfo aDinfo, String apkjson) {
		try {
			apkjson = apkjson.substring(apkjson.indexOf("{"), apkjson.lastIndexOf("}") + 1);
			JSONObject jsonObject = new JSONObject(apkjson);
			int ret = jsonObject.getInt("ret");
			if (ret == 0) {
				JSONObject object = new JSONObject(jsonObject.getString("data"));
				aDinfo.setDstlink(object.getString("dstlink"));
				aDinfo.setClickid(object.getString("clickid"));
				downloadAPK(aDinfo);
			} else {
				LogUtil.i("APKJSON解析失败ret:" + ret);
				LogUtil.i("APKJSON解析失败date:" + jsonObject.getString("data"));
			}
		} catch (JSONException e) {
			LogUtil.i("APKJSON解析失败");
			e.printStackTrace();
		}
	}

	/**
	 * aDinfo下载APK
	 */
	private static void downloadAPK(ADinfo aDinfo) {
		String url = aDinfo.getDstlink();
		String[] strings = url.split("/");
		String APKname = null;
		String apk = "[aA][Pp][Kk]";
		for (String aa : strings) {
			if (aa.endsWith("." + apk)) {
				APKname = aa;
			}
		}
		final String downstartup = Parameter.addownloadapkurl + "?actionid=5&targettype=6&tagetid="
				+ aDinfo.getTargetid() + "&clickid=" + aDinfo.getClickid();
		LogUtil.i("开始下载APK上报:" + downstartup);
		new Thread() {
			public void run() {
				getjson(downstartup.trim());
			};
		}.start();
		String apkfileString = downloadFile(url, APKname, "apk", Parameter.downloadmulu);
		if (apkfileString != null) {
			final String infos = Parameter.addownloadapkurl + "?actionid=7&targettype=6&tagetid="
					+ aDinfo.getTargetid() + "&clickid=" + aDinfo.getClickid();
			LogUtil.i("下载完成APK上报:" + downstartup);
			new Thread() {
				public void run() {
					getjson(infos.trim());
				};
			}.start();
			openapk(aDinfo, apkfileString);
		}
	}

	private static void openapk(ADinfo aDinfo, String apkfileString) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		intent.setDataAndType(Uri.parse("file://" + apkfileString), "application/vnd.android.package-archive");
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Guangdiantong.context.startActivity(intent);
	}

	/**
	 * 系统浏览器 显示URL
	 * 
	 * @param url
	 */
	protected static void ShowInSysie(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		Guangdiantong.context.startActivity(intent);
	}

	/**
	 * 从URL 获取JSON
	 * 
	 * @param url
	 *            访问地址
	 * @return json 返回JSON字符串
	 */
	public static String getjson(String url) {
		if (url == null)
			return null;
		BufferedReader reader = null;
		HttpClient client = new DefaultHttpClient();
		HttpGet myget = new HttpGet(url.trim());
		HttpResponse response;
		StringBuilder builder = null;
		String line = "";
		try {
			response = client.execute(myget);
			reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
			builder = new StringBuilder();
			while ((line = reader.readLine()) != null) {
				builder.append(line);
			}
			reader.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null)
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		if (builder != null)
			return builder.toString().trim();
		return null;
	}
}
