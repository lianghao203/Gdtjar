package com.example.guangddiantong;

/**
 * ��һ�����Ϣ
 * 
 * @author lianghao
 * 
 * @��ע��
 * 
 *      1��������datafmt=htmlʱ�����᷵��btn_render�����Ժ���ֶ�
 * 
 *      2��������datafmt=jsonʱ��������html_snippet�ֶ�.
 * 
 *      3��ext�е��ֶ�ֻ��feed���͹��᷵��.
 * 
 *      4��banner������棬crt_type��ͬʱ��img txtdesc�������ֶο�����ֵ���ء�crt_type=1ʱ��txt
 *      des��Ч��crt_type=2ʱ��img��Ч��crt_type=3��7ʱ��img txt desc����ֵ����Ч
 * 
 *      5��feed���ͨ���ɿ������Լ���Ⱦ��appname txt iconurl
 *      img�ֱ�Я��������������ͼƬ��Ϣ����androidapp���滹��Я��appscore num_app_users
 */
public class ADinfo {
	int acttype; // ���ڵ���ϱ�

	// �����߸��ݸ��ֶ�ֵ����ѡ����в�ͬ��ʽ����Ⱦ 0 unknown 1 ��ͨ����2 android app 3 ios app
	int btn_render;
	int crt_type; // �ز����� 1 ���� 2 ͼƬ 3 ͼ�� 7 ͼ��
	String desc; // ����ز��İ�2
	String img; // ��Ҫչʾ��ͼƬurl
	String imgFile;
	String img2;
	String img2File;
	boolean is_full_screen_interstitial; // �Ƿ��ǲ��������棨������������λ��Ч��
	String rl; // ����ƴ�ӵ���ϱ�url
	String targetid; // ����ת���ϱ�
	String txt; // ����ز��İ�1
	String viewid; // ����ع�id�����ڹ���ع��ϱ�
	String html_snippet; // ������datafmtΪhtmlʱ�����ֶδ����Ⱦ�����html����Ƭ��
	String snapshot1; // �����������1����������ͼ����ʽ��Ч
	String snapshot2; // �����������2����������ͼ����ʽ��Ч��
	String dstlink; // ��app�����ص�ַ
	String clickid; // clickid�軺�����������ں���ת���ϱ�

	public String getDstlink() {
		return dstlink;
	}

	public void setDstlink(String dstlink) {
		this.dstlink = dstlink;
	}

	public String getClickid() {
		return clickid;
	}

	public void setClickid(String clickid) {
		this.clickid = clickid;
	}

	public String getImgFile() {
		return imgFile;
	}

	public void setImgFile(String imgFile) {
		this.imgFile = imgFile;
	}

	public String getImg2File() {
		return img2File;
	}

	public void setImg2File(String img2File) {
		this.img2File = img2File;
	}

	public int getActtype() {
		return acttype;
	}

	public void setActtype(int acttype) {
		this.acttype = acttype;
	}

	public int getBtn_render() {
		return btn_render;
	}

	public void setBtn_render(int btn_render) {
		this.btn_render = btn_render;
	}

	public int getCrt_type() {
		return crt_type;
	}

	public void setCrt_type(int crt_type) {
		this.crt_type = crt_type;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getImg2() {
		return img2;
	}

	public void setImg2(String img2) {
		this.img2 = img2;
	}

	public boolean isIs_full_screen_interstitial() {
		return is_full_screen_interstitial;
	}

	public void setIs_full_screen_interstitial(boolean is_full_screen_interstitial) {
		this.is_full_screen_interstitial = is_full_screen_interstitial;
	}

	public String getRl() {
		return rl;
	}

	public void setRl(String rl) {
		this.rl = rl;
	}

	public String getTargetid() {
		return targetid;
	}

	public void setTargetid(String targetid) {
		this.targetid = targetid;
	}

	public String getTxt() {
		return txt;
	}

	public void setTxt(String txt) {
		this.txt = txt;
	}

	public String getViewid() {
		return viewid;
	}

	public void setViewid(String viewid) {
		this.viewid = viewid;
	}

	public String getHtml_snippet() {
		return html_snippet;
	}

	public void setHtml_snippet(String html_snippet) {
		this.html_snippet = html_snippet;
	}

	public String getSnapshot1() {
		return snapshot1;
	}

	public void setSnapshot1(String snapshot1) {
		this.snapshot1 = snapshot1;
	}

	public String getSnapshot2() {
		return snapshot2;
	}

	public void setSnapshot2(String snapshot2) {
		this.snapshot2 = snapshot2;
	}
}
