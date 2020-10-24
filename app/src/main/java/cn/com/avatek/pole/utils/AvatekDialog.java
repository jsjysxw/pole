package cn.com.avatek.pole.utils;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import cn.com.avatek.pole.R;


public class AvatekDialog {

	@SuppressWarnings("deprecation")
	public static Dialog createLoadingDialog(Context context, String msg) {

		LayoutInflater inflater = LayoutInflater.from(context);
		// 加载loading_dialog.xml
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

		// loading_dialog.xml中的LinearLayout
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

		// loading_dialog.xml中的TextView
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);// 设置加载信息（如：登录中，请稍候...）

		// 创建自定义样式loading_dialog
		Dialog loadingDialog = new Dialog(context, R.style.avadialog);
		loadingDialog.setCancelable(true);
		// 设置布局
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		return loadingDialog;

	}
	@SuppressWarnings("deprecation")
	public static Dialog createLoadingDialog(Context context, String msg, int flag) {

		LayoutInflater inflater = LayoutInflater.from(context);
		// 加载loading_dialog.xml
		View v = inflater.inflate(R.layout.loading_dialog, null);// 得到加载view

		// loading_dialog.xml中的LinearLayout
		LinearLayout layout = (LinearLayout) v.findViewById(R.id.dialog_view);// 加载布局

		// loading_dialog.xml中的TextView
		TextView tipTextView = (TextView) v.findViewById(R.id.tipTextView);// 提示文字
		tipTextView.setText(msg);// 设置加载信息（如：登录中，请稍候...）
		tipTextView.setTextColor(context.getResources().getColor(R.color.white));
		// 创建自定义样式loading_dialog
		Dialog loadingDialog = new Dialog(context, R.style.avadialog);
		loadingDialog.setCancelable(true);
		// 设置布局
		loadingDialog.setContentView(layout, new LinearLayout.LayoutParams(
				LinearLayout.LayoutParams.FILL_PARENT,
				LinearLayout.LayoutParams.FILL_PARENT));
		return loadingDialog;

	}
}
