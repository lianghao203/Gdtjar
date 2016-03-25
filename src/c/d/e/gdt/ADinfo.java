package c.d.e.gdt;

/**
 * 单一广告信息
 * 
 * @author lianghao
 * 
 * @备注：
 * 
 *      1。请求中datafmt=html时，不会返回btn_render及其以后的字段
 * 
 *      2。请求中datafmt=json时，不返回html_snippet字段.
 * 
 *      3。ext中的字段只有feed类型广告会返回.
 * 
 *      4。banner插屏广告，crt_type不同时，img txtdesc这三个字段可能无值返回。crt_type=1时，txt
 *      des有效；crt_type=2时，img有效；crt_type=3或7时，img txt desc三个值都有效
 * 
 *      5。feed广告通常由开发者自己渲染，appname txt iconurl
 *      img分别携带两段文字两张图片信息，对androidapp类广告还会携带appscore num_app_users
 */
public class ADinfo {
	int acttype; // 用于点击上报

	// 开发者根据该字段值可以选择进行不同样式的渲染 0 unknown 1 普通链接2 android app 3 ios app
	int btn_render;
	int crt_type; // 素材类型 1 文字 2 图片 3 图文 7 图文
	String desc; // 广告素材文案2
	String img; // 将要展示的图片url
	String imgFile;
	String img2;
	String img2File;
	boolean is_full_screen_interstitial; // 是否是插屏大规格广告（仅插屏大规格广告位有效）
	String rl; // 用于拼接点击上报url
	String targetid; // 用于转化上报
	String txt; // 广告素材文案1
	String viewid; // 广告曝光id，用于广告曝光上报
	String html_snippet; // 当请求datafmt为html时，该字段存放渲染结果的html代码片段
	String snapshot1; // 插屏大规格截屏1（插屏大规格图文样式有效
	String snapshot2; // 插屏大规格截屏2（插屏大规格图文样式有效）
	String dstlink; // 该app的下载地址
	String clickid; // clickid需缓存下来，用于后续转化上报

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
