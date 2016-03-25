package c.d.e.gdt;

import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class show extends Activity {
	private Activity myact;
	private ADinfo adinfo;
	private Bitmap bm;
	protected int x2;
	protected int x1;
	protected int y1;
	protected int y2;
	private RelativeLayout layout;
	private RelativeLayout mlayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// super.onCreate(savedInstanceState);
		// myact = this;
		if (Parameter.tempaDinfo == null)
			myact.finish();
		adinfo = Parameter.tempaDinfo;
		LogUtil.i("act加载成功");
		bm = null;
		if (adinfo.getImgFile() != null) {
			layout = new RelativeLayout(myact);
			mlayout = new RelativeLayout(myact);
			RelativeLayout.LayoutParams mrelLayoutParams = new LayoutParams(600, 500);
			mrelLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
			RelativeLayout.LayoutParams relLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			myact.setContentView(mlayout, relLayoutParams);
			mlayout.addView(layout, mrelLayoutParams);

			TextView tv2 = new TextView(myact);
			RelativeLayout.LayoutParams tv2p = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			tv2p.addRule(RelativeLayout.ALIGN_TOP);
			tv2p.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			tv2.setText("关闭");
			tv2.setTextSize(18);
			tv2.setTextColor(Color.BLACK);
			tv2.setBackgroundColor(Color.parseColor("#e0DCDCDC"));
			tv2.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					LogUtil.i("点击关闭按钮");
					myact.finish();
				}
			});

			layout.addView(tv2, tv2p);
			switch (adinfo.getCrt_type()) {
			case 1:
				// 文字
				creatText();
				break;
			case 2:
				// 图片
				creatPic();
				break;
			case 3:
			case 7:
				// 图文
				creatPicAndText();
				break;
			default:
				break;
			}
		} else {
			LogUtil.i("adinfo.getImgFile()== null");
			myact.finish();
		}
	}

	private void creatText() {
		LogUtil.i("adinfo.getCrt_type()=" + adinfo.getCrt_type());
		LogUtil.i("desc=" + adinfo.getDesc());
		LogUtil.i("txt=" + adinfo.getTxt());
		myact.finish();
	}

	@SuppressLint("InlinedApi")
	private void creatPicAndText() {
		LogUtil.i("adinfo.getCrt_type()=" + adinfo.getCrt_type());
		LogUtil.i("desc=" + adinfo.getDesc());
		LogUtil.i("txt=" + adinfo.getTxt());
		layout.setBackgroundColor(Color.parseColor("#B0C4DE"));

		int imageViewid = 10034;
		int tvtitleid = imageViewid + 1;
		int tvid = imageViewid + 2;
		try {
			bm = BitmapFactory.decodeFile(adinfo.getImgFile());
		} catch (Exception e) {
		}
		if (bm == null)
			myact.finish();

		ImageView imageView = new ImageView(myact);
		imageView.setId(imageViewid);
		imageView.setImageBitmap(bm);
		RelativeLayout.LayoutParams imgp = new LayoutParams(144, 144);
		imgp.leftMargin = 30;
		imgp.topMargin = 30;
		layout.addView(imageView, imgp);

		TextView tvtitle = new TextView(myact);
		tvtitle.setId(tvtitleid);
		RelativeLayout.LayoutParams tvtitlep = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvtitlep.addRule(RelativeLayout.RIGHT_OF, imageViewid);
		tvtitlep.topMargin = 40;
		tvtitlep.leftMargin = 30;
		tvtitlep.rightMargin = 30;
		tvtitle.setText(adinfo.getTxt());
		tvtitle.setTextColor(Color.BLACK);
		tvtitle.setTextSize(20);
		layout.addView(tvtitle, tvtitlep);

		TextView tv = new TextView(myact);
		tv.setId(tvid);
		RelativeLayout.LayoutParams tvp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
		tvp.addRule(RelativeLayout.BELOW, imageViewid);
		tvp.topMargin = 30;
		tvp.leftMargin = 50;
		tvp.rightMargin = 45;
		tv.setText(adinfo.getDesc());
		tv.setTextColor(Color.BLACK);
		tv.setTextSize(16);
		layout.addView(tv, tvp);
		Button bt = new Button(myact);
		RelativeLayout.LayoutParams btp = new LayoutParams(LayoutParams.WRAP_CONTENT, 80);
		btp.addRule(RelativeLayout.CENTER_HORIZONTAL);
		btp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
		btp.bottomMargin = 20;
		bt.setText("点击进入");
		bt.setTextSize(18);
		bt.setPadding(40, -30, 30, -20);
		bt.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x1 = (int) event.getX();
					y1 = (int) event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					x2 = (int) event.getX();
					y2 = (int) event.getY();
					if (Math.abs(x1 - x2) <= 15 && Math.abs(y1 - y2) <= 15) {
						LogUtil.i("点击上报-坐标:" + x1 + "-" + x2 + "____y:" + y1 + "-" + y2);
						JSONObject clickinfo = new JSONObject();
						try {
							clickinfo.put("down_x", x1 + "");
							clickinfo.put("down_y", y1 + "");
							clickinfo.put("up_x", x2 + "");
							clickinfo.put("up_y", y2 + "");
							Tools.clickrepoct(adinfo, clickinfo);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						myact.finish();
					}
				}
				return true;
			}
		});
		layout.addView(bt, btp);
		Tools.exposure(adinfo);
	}

	private void creatPic() {
		LogUtil.i("adinfo.getCrt_type()=" + adinfo.getCrt_type());
		LogUtil.i("desc=" + adinfo.getDesc());
		LogUtil.i("txt=" + adinfo.getTxt());
		try {
			bm = BitmapFactory.decodeFile(adinfo.getImgFile());
		} catch (Exception e) {
		}
		if (bm == null)
			myact.finish();
		ImageView imageView = new ImageView(myact);
		imageView.setImageBitmap(bm);
		RelativeLayout.LayoutParams imgp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
		// imgp.addRule(RelativeLayout.CENTER_IN_PARENT);
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		layout.addView(imageView, imgp);
		imageView.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (event.getAction() == MotionEvent.ACTION_DOWN) {
					x1 = (int) event.getX();
					y1 = (int) event.getY();
				}
				if (event.getAction() == MotionEvent.ACTION_UP) {
					x2 = (int) event.getX();
					y2 = (int) event.getY();
					if (Math.abs(x1 - x2) <= 15 && Math.abs(y1 - y2) <= 15) {
						LogUtil.i("点击上报-坐标:" + x1 + "-" + x2 + "____y:" + y1 + "-" + y2);
						JSONObject clickinfo = new JSONObject();
						try {
							clickinfo.put("down_x", x1 + "");
							clickinfo.put("down_y", y1 + "");
							clickinfo.put("up_x", x2 + "");
							clickinfo.put("up_y", y2 + "");
							Tools.clickrepoct(adinfo, clickinfo);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						myact.finish();
					}
				}
				return true;
			}
		});
		Tools.exposure(adinfo);
	}

	public void setActivity(Activity paramActivity) {
		myact = paramActivity;
	}
}
