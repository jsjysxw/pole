package cn.com.avatek.pole.module.upgrade;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import cn.com.avatek.pole.R;


/**
 * Created by pengjia
 */
public class XWBDialog extends Dialog {
	public final static int RESUTL_CONFIRM = 0x000999;
	public final static int RESUTL_CANCEL = -0x000001;
	public final static int STYLE_ONE_BUTTON = 0x0001;
	public final static int STYLE_TWO_BUTTON = 0x0002;

	private TextView title, desc = null;
	private Button cancel, confirm = null;
	private MyDialogOnClick mMyDialogOnClick;

	public interface MyDialogOnClick {
		public void onClick(int result);
	}

	public XWBDialog(Context context) {
		super(context);
		init();
	}

	public XWBDialog(Context context, String title, String message, String confirm, String cancel) {
		super(context);
		init();
		bindData(title, message, confirm, cancel);
	}

	public XWBDialog(Context context, int style, MyDialogOnClick myDialogOnClick) {
		super(context);
		init();
		this.mMyDialogOnClick = myDialogOnClick;
		setStyle(style);
	}

	private void bindData(String str_title, String str_message, String str_confirm, String str_cancel) {
		title.setText(str_title);
		desc.setText(str_message);
		confirm.setText(str_confirm);
		cancel.setText(str_cancel);
	}

	public XWBDialog(Context context, int style, MyDialogOnClick myDialogOnClick, String title, String message, String confirm, String cancel) {
		super(context);
		init();
		this.mMyDialogOnClick = myDialogOnClick;
		setStyle(style);
		bindData(title, message, confirm, cancel);
	}

	public void setMyDialogOnClick(MyDialogOnClick dialogOnClick) {
		this.mMyDialogOnClick = dialogOnClick;
	}

	public void init() {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(new ColorDrawable());
		this.setContentView(R.layout.upgrade_my_dialog);
		desc = (TextView) this.findViewById(R.id.description);
		title = (TextView) this.findViewById(R.id.title);
		cancel = (Button) this.findViewById(R.id.cancel);
		confirm = (Button) this.findViewById(R.id.confirm);
		confirm.setOnClickListener(myConfirmOnClickListener);
		cancel.setOnClickListener(myCancelOnClickListener);
	}

	/**
	 * 是否允许触摸以及返回键取消
	 */
	public void setOnTouchCancel(boolean offOn) {
		if (offOn) {
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
	}

	private void setStyle(int style) {
		switch (style) {
		case STYLE_ONE_BUTTON:
			cancel.setVisibility(View.GONE);
			break;
		case STYLE_TWO_BUTTON:

			break;
		}
	}

	View.OnClickListener myCancelOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mMyDialogOnClick != null) {
				mMyDialogOnClick.onClick(RESUTL_CANCEL);
				
				return;
			}
			dismiss();
			
		}
	};

	View.OnClickListener myConfirmOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (mMyDialogOnClick != null) {
				mMyDialogOnClick.onClick(RESUTL_CONFIRM);
				return;
			}
			dismiss();
		}
	};

	public void setTitle(String str) {
		title.setText(str);
	}

	public void setMessage(String str) {
		cancel.setText(str);
	}

	public void setCancel(String str) {
		desc.setText(str);
	}

	public void setConfirm(String str) {
		confirm.setText(str);
	}

}
