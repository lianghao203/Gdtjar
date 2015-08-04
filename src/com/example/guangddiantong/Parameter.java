package com.example.guangddiantong;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

public class Parameter {

	static String IMEI = null;
	static String downloadmulu = Environment.getExternalStorageDirectory() + "/gdt/";

	static String adexposureurl = "http://v.gdt.qq.com/gdt_stats.fcg?count=1&viewid0="; // 曝光地址
	static String advertisingurl = "http://mi.gdt.qq.com/gdt_mview.fcg?"; // 获取广告地址
	// static String advertisingur_testl =
	// "http://test.mi.gdt.qq.com/gdt_mview.test.fcg?";
	static String adposcount = "adposcount=" + "1"; // 必填 本次请求的广告位个数。只能填1
	static String count = "&count=" + "1"; // 必填 广告位广告个数。只能填1
	static String posid = "&posid=" + "6050204534213771"; // 必填 广告位id
	static String posw = "&posw=" + "600"; // 必填 广告位宽。可能填写的值见附录
	static String posh = "&posh=" + "500"; // 必填 广告位高。可能填写的值见附录
	static String charset = "&charset=" + "utf8"; // 必填 广告内容的数据编码。只能填utf8
	// 必填 返回素材数据还是GDT渲染好的html。可能取值:html或json
	static String datafmt = "&datafmt=" + "json";
	static String ext = "&ext="; // 必填 广告请求扩展内容,内容为json串,使用encodeURIComponent编码

	static String url = advertisingurl + adposcount + count + posid + posw + posh + charset + datafmt + ext;

	static String req = "{\"req\":"; // 必填

	static String apiver = "1.6"; // 必填 api 版本。形如 major.minor,可能取值:1.4
	static String appid = "1104796722"; // 必填 广点通分配的appid
	static String c_os = "android"; // 必填 操作系统。可能取值: ios android
	static int muidtype = 1; // 必填 移动终端标识类型。可能取值: 1:imei 2:ifa
	static String muid = ""; // 必填 md5后的移动终端标识,标识类型由muidtype决定
	static int conn = 1; // 必填 联网方式。可能取值: 0:unknown 1:wifi 2:2g 3:3g 4:4g
	static int carrier = 0; // 必填 运营商。可能取值: 0:unknown 1:移动 2:联通 3:电信
	static int c_w = 640; // 必填 屏幕宽。取设备物理像素
	static int c_h = 960; // 必填 屏幕高。取设备物理像素。高度始终大于宽度。
	static String c_device = "SHV-E250S"; // 必填 设备品牌和型号
	// 必填 app包名。对于Android,是应用的packageName;对于iOS,是Bundle identifier。
	static String c_pkgname = "com.example.guangdiantong";
	// 必填 广告位类型。可能取值:1:banner 2:插屏 3:应用墙 4:开屏5:feed
	static int postype = 2;
	// 选填 这个字段仅用于请求插屏大规格广告，请求其他类型广告时不填
	static boolean inline_full_screen = true;
	// 选填 设备横竖屏。可能取值：0, iphone 4s屏幕正对自己，home键靠下。90,顺时针旋转90度。180 270
	static int c_ori = 0;
	static String remoteip = ""; // 选填 终端用户IPv4地址
	static int lat = 39980504; // 选填 纬度*1,000,000，用于精确识别地域。
	static int lng = 116300183; // 选填 经度*1,000,000，用于精确识别地域。
	// 选填 获取经纬度(lat/lng)的时间。其值为从GMT 1970-01-01 00:00:00至今的毫秒值。
	static double coordtime = 1369817179012d;
	static String useragent = ""; // 选填 终端用户HTTP请求头中的User-Agent字段
	static String referer = ""; // 选填 终端用户HTTP请求头中的referer字段
	static String c_osver = ""; // 选填 os版本
	static String screen_density = ""; // 选填 屏幕密度，一个逻辑像素等于几个实际像素

	public static JSONObject getreq() {
		JSONObject req = new JSONObject();
		try {
			req.put("apiver", apiver);
			req.put("appid", appid);
			req.put("c_os", c_os);
			req.put("muidtype", muidtype);
			req.put("muid", muid);
			req.put("conn", conn);
			req.put("carrier", carrier);
			req.put("c_w", c_w);
			req.put("c_h", c_h);
			req.put("c_device", c_device);
			req.put("c_pkgname", c_pkgname);
			req.put("postype", postype);
			// req.put("inline_full_screen", inline_full_screen);
			// req.put("c_ori", c_ori);
			// req.put("remoteip", remoteip);
			// req.put("lat", lat);
			// req.put("lng", lng);
			// req.put("coordtime", coordtime);
			// req.put("useragent", useragent);
			// req.put("referer", referer);
			// req.put("c_osver", c_osver);
			// req.put("screen_density", screen_density);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return req;
	}

}
