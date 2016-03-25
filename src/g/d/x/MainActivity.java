package g.d.x;

import c.d.e.gdt.Guangdiantong;
import c.d.e.gdt.LogUtil;

import com.example.guangdiantong.R;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button bt;
	private TextView tv1;
	private static Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = this;
		setContentView(R.layout.activity_main);
		bt = (Button) findViewById(R.id.button1);
		tv1 = (TextView) findViewById(R.id.textView1);
		Guangdiantong.getInstance(context);
		bt.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LogUtil.i("BUTTON键点击");
				new dd().init(context, null, null);
			}
		});
	}
}
