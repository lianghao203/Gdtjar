package com.example.guangddiantong;

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
	 * ��ȡ�豸IMEI--ʧ��: �Լ�����.
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
	 * MD5���ַ���
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
	 * ������ȡ���JSON
	 * 
	 * @param info
	 */
	public static void analytical_adjson(String info) {
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
				LogUtil.i("info", "��ȡ������ݳɹ�");
				String data = object.getString("data");
				JSONObject adjsons = new JSONObject(data);
				Iterator<?> iterator = adjsons.keys();
				String posid = (String) iterator.next();
				LogUtil.i("info", "posid:" + posid);
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
						LogUtil.i("info", "acttype�ֶ�:" + object3.getInt("acttype"));
						aDinfo.setBtn_render(object3.getInt("btn_render"));
						aDinfo.setCrt_type(object3.getInt("crt_type"));
						LogUtil.i("info", "crt_type�ֶ�:" + object3.getInt("crt_type"));
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
							LogUtil.i("info", "����IMG2:" + object3.getString("img2"));
						} catch (Exception e) {
						}
						aDinfos.add(aDinfo);
					}
					aDinfos = downloadimg(aDinfos);
					Guangdiantong.showad(aDinfos);
				} else {
					LogUtil.i("info", "��ȡ����б�ʧ��");
					LogUtil.i("info", "��ȡ�������ʧ�� msg1:" + msg1);
				}
			} else {
				LogUtil.i("info", "��ȡ�������ʧ��.");
				LogUtil.i("info", "��ȡ�������ʧ�� rpt:" + rpt);
				LogUtil.i("info", "��ȡ�������ʧ�� msg:" + msg);
				LogUtil.i("info", "��ȡ�������ʧ�� reqinterval:" + reqinterval);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param aDinfos
	 * @return
	 */
	private static ArrayList<ADinfo> downloadimg(final ArrayList<ADinfo> aDinfos) {
		for (int i = 0; i < aDinfos.size(); i++) {
			ADinfo aDinfo = aDinfos.get(i);
			if (aDinfo.getImg() != null && !aDinfo.getImg().equals("")) {
				LogUtil.i("info", "Img");
				aDinfos.get(i).setImgFile(getbms(aDinfo.getImg()));
			}
			if (aDinfo.getImg2() != null && !aDinfo.getImg2().equals("")) {
				LogUtil.i("info", "Img2");
				aDinfos.get(i).setImg2File(getbms(aDinfo.getImg2()));
			}
		}
		return aDinfos;
	}

	/**
	 * ������ַ
	 * 
	 * @param string
	 * @return
	 */
	protected static String getbms(String string) {
		LogUtil.i("info", "ͼƬ���ص�ַ:" + string);
		String imgfile = null;

		Pattern p = Pattern.compile("([^\\/]*?\\.jpg)", Pattern.CASE_INSENSITIVE);
		Matcher m = p.matcher(string);
		if (m.find()) {
			imgfile = downloadFile(string, m.group(1), "jpg", Parameter.downloadmulu);
		} else {
			Pattern p1 = Pattern.compile("([^\\/]*?\\.png)", Pattern.CASE_INSENSITIVE);
			Matcher m1 = p1.matcher(string);
			if (m1.find()) {
				imgfile = downloadFile(string, m1.group(1), "png", Parameter.downloadmulu);
			} else {
				Pattern p2 = Pattern.compile("([^\\/]*?\\.gif)", Pattern.CASE_INSENSITIVE);
				Matcher m2 = p2.matcher(string);
				if (m.find()) {
					imgfile = downloadFile(string, m2.group(1), "gif", Parameter.downloadmulu);
				} else {
					imgfile = downloadFile(string, string.substring(string.lastIndexOf("/")), null,
							Parameter.downloadmulu);
				}
			}
		}

		// String[] strings = string.split("/");
		// for (String aa : strings) {
		// LogUtil.i("info", "ͼƬ��ַ���ֶ�:" + aa);
		// if (aa.endsWith(".jpg")) {
		// LogUtil.i("info", "JPG�ļ�:" + aa);
		// imgfile = downloadFile(string, aa, "jpg", Parameter.downloadmulu);
		// } else if (aa.endsWith(".png")) {
		// imgfile = downloadFile(string, aa, "png", Parameter.downloadmulu);
		// }
		// }
		return imgfile;
	}

	/**
	 * ����ͼƬ
	 * 
	 * @param url
	 *            ���ص�ַ
	 * @param name
	 *            ͼƬ����
	 * @param type
	 *            ͼƬ����
	 * @param mulu
	 *            ����Ŀ¼
	 * @return
	 */
	private static String downloadFile(String url, String name, String type, String mulu) {
		File filedir = new File(mulu);

		File file = null;
		File filetemp = null;
		if (type != null) {
			file = new File(mulu + name);
			filetemp = new File(mulu + name + ".temp");
		} else {

		}
		LogUtil.i("info", "��ʼ����ͼƬ:" + name);
		LogUtil.i("info", "��ʼ����ͼƬ��ַ:" + url);
		try {
			if (!filedir.exists())
				filedir.mkdir();
			URL imgurl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgurl.openConnection();
			conn.connect();
			int filelength = conn.getContentLength();
			// if (file.exists() && file.length() == conn.getContentLength()) {
			// LogUtil.i("info", "ͼƬ����:" + file.getAbsolutePath());
			// return file.getAbsolutePath();
			// if (file.exists())
			// file.delete();
			// } else {
			file.delete();
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
				LogUtil.i("info", "ͼƬ���سɹ�:" + file.getAbsolutePath());
				return file.getAbsolutePath();
			} else {
				return null;
			}
			// }
		} catch (ClientProtocolException e) {
			LogUtil.i("info", "����ʧ��");
			e.printStackTrace();
		} catch (IOException e) {
			LogUtil.i("info", "����ʧ��");
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * �ع��ϱ�
	 * 
	 * @param aDinfo
	 *            �ع���ܴ����ӹ����Ӧviewid�ֶλ�ȡ
	 */
	public static void exposure(final ADinfo aDinfo) {
		new Thread() {
			@Override
			public void run() {
				String url = null;
				try {
					url = Parameter.adexposureurl + URLDecoder.decode(aDinfo.getViewid(), "UTF-8");
				} catch (UnsupportedEncodingException e) {
					LogUtil.i("info", "�ع��ϱ�:URLת��ʧ��");
					e.printStackTrace();
				}
				LogUtil.i("info", "�ع��ϱ�:" + url);
				String info = getjson(url);
				LogUtil.i("info", "�ع���Ӧ:" + info);
				super.run();
			}
		}.start();
	}

	/**
	 * ����ϱ�
	 * 
	 * @param aDinfo
	 *            �����Ϣ
	 * @param clickinfo
	 *            ���λ����Ϣ
	 */
	public static void clickrepoct(final ADinfo aDinfo, final JSONObject clickinfo) {
		int type = aDinfo.getActtype();
		if (type == 1) {
			new Thread() {
				public void run() {
					try {
						String url = aDinfo.getRl() + "&s=" + URLEncoder.encode(clickinfo.toString(), "UTF-8");
						LogUtil.i("info", "����ϱ�:" + url);
						String apkjson = getjson(url);
						LogUtil.i("info", "����ϱ�--����APKJSON:" + apkjson);
						getapkinfo(aDinfo, apkjson);
					} catch (UnsupportedEncodingException e) {
						e.printStackTrace();
					}
				};
			}.start();
		} else if (type == 0 || type == 18) {
			String url = aDinfo.getRl() + "&s=" + clickinfo.toString();
			LogUtil.i("info", "����ϱ��Զ���תϵͳIE:" + url);
			ShowInSysie(url);
		}
	}

	/**
	 * ����APK����
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
				LogUtil.i("info", "APKJSON����ʧ��ret:" + ret);
				LogUtil.i("info", "APKJSON����ʧ��date:" + jsonObject.getString("data"));
			}
		} catch (JSONException e) {
			LogUtil.i("info", "APKJSON����ʧ��");
			e.printStackTrace();
		}
	}

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
		downloadFile(url, APKname, "apk", Parameter.downloadmulu);
	}

	/**
	 * ϵͳ����� ��ʾURL
	 * 
	 * @param url
	 */
	protected static void ShowInSysie(String url) {
		Uri uri = Uri.parse(url);
		Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		Guangdiantong.context.startActivity(intent);
	}

	/**
	 * ��URL ��ȡJSON
	 * 
	 * @param url
	 *            ���ʵ�ַ
	 * @return json ����JSON�ַ���
	 */
	public static String getjson(String url) {
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
		return builder.toString().trim();
	}
}
