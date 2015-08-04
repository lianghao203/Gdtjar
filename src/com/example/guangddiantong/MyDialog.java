package com.example.guangddiantong;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

import com.example.guangdiantong.R;

public class MyDialog {
	private static AlertDialog alertDialog;
	static Context context;
	private static float x1;
	private static float x2;
	private static float y1;
	private static float y2;
	private static LayoutParams canclelp;
	private static RelativeLayout rlImgcancle;
	private static ImageView cancleimg;
	private static ImageView img;

	public static void getinstance(Context context) {
		MyDialog.context = context;
		if (alertDialog == null) {
			LogUtil.i("info", "alertDialog获取实例");
			AlertDialog.Builder builder = new AlertDialog.Builder(context);
			alertDialog = builder.create();
			alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
			alertDialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN,
					WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN);
			alertDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ERROR);
			// alertDialog.setCancelable(false);
			img = new ImageView(context);
			cancleimg = new ImageView(context);
			cancleimg.setImageResource(R.drawable.cancel);
			rlImgcancle = new RelativeLayout(context);
			rlImgcancle.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			canclelp = new RelativeLayout.LayoutParams(70, 70);
			canclelp.addRule(RelativeLayout.ALIGN_PARENT_TOP);
			canclelp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			rlImgcancle.addView(cancleimg, canclelp);
			cancleimg.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					if (alertDialog != null)
						if (alertDialog.isShowing())
							alertDialog.dismiss();
				}
			});
		}
	}

	/**
	 * 显示dialog
	 * 
	 * @param aDinfo
	 */
	public static void show(final ADinfo aDinfo) {
		LogUtil.i("info", "alertDialog展示图片最后检查:" + aDinfo.getImgFile());
		if (aDinfo.getImgFile() != null) {
			if (alertDialog == null)
				getinstance(Guangdiantong.context);
			LogUtil.i("info", "alertDialog展示图片");
			Bitmap bitmap = BitmapFactory.decodeFile(aDinfo.imgFile);

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
			// if (bitmap.getWidth() < Parameter.c_w / 2) {
			// LogUtil.i("info", "尺寸过小,不显示:" + bitmap.getWidth());
			// if (aDinfo.img2File != null) {
			// Bitmap bitmap2 = BitmapFactory.decodeFile(aDinfo.img2File);
			// LogUtil.i("info", "尺寸过小,不显示,img2信息" + bitmap2.getWidth());
			// }
			// return;
			// }
			if (img == null)
				img = new ImageView(context);
			img.setImageBitmap(bitmap);
			img.setScaleType(ScaleType.FIT_XY);
			LayoutParams imglp = new RelativeLayout.LayoutParams(Parameter.c_w - 10, (Parameter.c_w - 10)
					* bitmap.getHeight() / bitmap.getWidth());
			imglp.addRule(RelativeLayout.CENTER_VERTICAL);
			alertDialog.show();
			alertDialog.setContentView(img, imglp);

			alertDialog.addContentView(rlImgcancle, new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT));

			img.setOnTouchListener(new OnTouchListener() {

				@Override
				public boolean onTouch(View v, MotionEvent event) {
					if (event.getAction() == MotionEvent.ACTION_DOWN) {
						x1 = event.getX();
						y1 = event.getY();
					}
					if (event.getAction() == MotionEvent.ACTION_UP) {
						x2 = event.getX();
						y2 = event.getY();
						if (Math.abs(x1 - x2) <= 15 && Math.abs(y1 - y2) <= 15) {
							LogUtil.i("info", "点击上报-坐标:" + x1 + "-" + x2 + "____y:" + y1 + "-" + y2);
							JSONObject clickinfo = new JSONObject();
							try {
								clickinfo.put("down_x", x1 + "");
								clickinfo.put("down_y", y1 + "");
								clickinfo.put("up_x", x2 + "");
								clickinfo.put("up_y", y2 + "");
								Tools.clickrepoct(aDinfo, clickinfo);
							} catch (JSONException e) {
								e.printStackTrace();
							}
							alertDialog.dismiss();
						}
					}
					return true;
				}
			});
			Tools.exposure(aDinfo);
		} else {
			LogUtil.i("info", "aDinfo.getImgFile()为空");
		}
	}

	void showtype2() {

	}
}
