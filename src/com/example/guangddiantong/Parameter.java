package com.example.guangddiantong;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Environment;

public class Parameter {

	static String IMEI = null;
	static String downloadmulu = Environment.getExternalStorageDirectory() + "/gdt/";

	static String adexposureurl = "http://v.gdt.qq.com/gdt_stats.fcg?count=1&viewid0="; // �ع��ַ
	static String advertisingurl = "http://mi.gdt.qq.com/gdt_mview.fcg?"; // ��ȡ����ַ
	// static String advertisingur_testl =
	// "http://test.mi.gdt.qq.com/gdt_mview.test.fcg?";
	static String adposcount = "adposcount=" + "1"; // ���� ��������Ĺ��λ������ֻ����1
	static String count = "&count=" + "1"; // ���� ���λ��������ֻ����1
	static String posid = "&posid=" + "6050204534213771"; // ���� ���λid
	static String posw = "&posw=" + "600"; // ���� ���λ��������д��ֵ����¼
	static String posh = "&posh=" + "500"; // ���� ���λ�ߡ�������д��ֵ����¼
	static String charset = "&charset=" + "utf8"; // ���� ������ݵ����ݱ��롣ֻ����utf8
	// ���� �����ز����ݻ���GDT��Ⱦ�õ�html������ȡֵ:html��json
	static String datafmt = "&datafmt=" + "json";
	static String ext = "&ext="; // ���� ���������չ����,����Ϊjson��,ʹ��encodeURIComponent����

	static String url = advertisingurl + adposcount + count + posid + posw + posh + charset + datafmt + ext;

	static String req = "{\"req\":"; // ����

	static String apiver = "1.6"; // ���� api �汾������ major.minor,����ȡֵ:1.4
	static String appid = "1104796722"; // ���� ���ͨ�����appid
	static String c_os = "android"; // ���� ����ϵͳ������ȡֵ: ios android
	static int muidtype = 1; // ���� �ƶ��ն˱�ʶ���͡�����ȡֵ: 1:imei 2:ifa
	static String muid = ""; // ���� md5����ƶ��ն˱�ʶ,��ʶ������muidtype����
	static int conn = 1; // ���� ������ʽ������ȡֵ: 0:unknown 1:wifi 2:2g 3:3g 4:4g
	static int carrier = 0; // ���� ��Ӫ�̡�����ȡֵ: 0:unknown 1:�ƶ� 2:��ͨ 3:����
	static int c_w = 640; // ���� ��Ļ��ȡ�豸��������
	static int c_h = 960; // ���� ��Ļ�ߡ�ȡ�豸�������ء��߶�ʼ�մ��ڿ�ȡ�
	static String c_device = "SHV-E250S"; // ���� �豸Ʒ�ƺ��ͺ�
	// ���� app����������Android,��Ӧ�õ�packageName;����iOS,��Bundle identifier��
	static String c_pkgname = "com.example.guangdiantong";
	// ���� ���λ���͡�����ȡֵ:1:banner 2:���� 3:Ӧ��ǽ 4:����5:feed
	static int postype = 2;
	// ѡ�� ����ֶν�����������������棬�����������͹��ʱ����
	static boolean inline_full_screen = true;
	// ѡ�� �豸������������ȡֵ��0, iphone 4s��Ļ�����Լ���home�����¡�90,˳ʱ����ת90�ȡ�180 270
	static int c_ori = 0;
	static String remoteip = ""; // ѡ�� �ն��û�IPv4��ַ
	static int lat = 39980504; // ѡ�� γ��*1,000,000�����ھ�ȷʶ�����
	static int lng = 116300183; // ѡ�� ����*1,000,000�����ھ�ȷʶ�����
	// ѡ�� ��ȡ��γ��(lat/lng)��ʱ�䡣��ֵΪ��GMT 1970-01-01 00:00:00����ĺ���ֵ��
	static double coordtime = 1369817179012d;
	static String useragent = ""; // ѡ�� �ն��û�HTTP����ͷ�е�User-Agent�ֶ�
	static String referer = ""; // ѡ�� �ն��û�HTTP����ͷ�е�referer�ֶ�
	static String c_osver = ""; // ѡ�� os�汾
	static String screen_density = ""; // ѡ�� ��Ļ�ܶȣ�һ���߼����ص��ڼ���ʵ������

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
