package g.d.x;

import c.d.e.gdt.Guangdiantong;
import c.d.e.gdt.LogUtil;
import android.content.Context;

public class dd implements d {

	@Override
	public void closeJAR(Context context) {
		Guangdiantong.close();
	}

	@Override
	public void init(Context context, String appid, String initinfos) {
		LogUtil.i("调用JAR包成功");
		Guangdiantong.getInstance(context);
	}

	@Override
	public void USER_PRESENT() {
		Guangdiantong.isScreenOn = true;
	}
}
