package cn.com.avatek.pole.module.upgrade;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import cn.com.avatek.pole.R;


/**
 * 下载进度显示
 * 
 * @author pengjia
 * 
 */
public class LoadingDialog extends Dialog {

	private Button confirm = null;
	private ProgressBar pro_bar;
	private TextView progress_txt;

	public LoadingDialog(Context context) {
		super(context);
		init();
	}

	public void init() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(new ColorDrawable());
		this.setContentView(R.layout.upgrade_my_loading_dialog);
		pro_bar = (ProgressBar) this.findViewById(R.id.pro_bar);
		confirm = (Button) this.findViewById(R.id.confirm);
		progress_txt = (TextView) this.findViewById(R.id.progress);
		setCanceledOnTouchOutside(false);
		setCancelable(false);

		
		setOnKeyListener(new OnKeyListener() {
			@Override
			public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
				if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_BACK) {
					return true;
				} else {
					return false;
				}
			}
		});
	}

	public void setProgress(int progress) {
		pro_bar.setProgress(progress);
		progress_txt.setText(progress + ".0 %");
	}

}