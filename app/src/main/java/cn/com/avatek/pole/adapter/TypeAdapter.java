package cn.com.avatek.pole.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import cn.com.avatek.pole.R;

public class TypeAdapter extends SuperAdapter<String>{

	public TypeAdapter(Activity context, List<String> data, int layoutRes) {
		super(context, data, layoutRes);
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void setData(int pos, View convertView, String itemData) {
		// TODO Auto-generated method stub
		TextView tv = getViewFromHolder(convertView, R.id.tv_type);
		tv.setText(itemData);
	}



}
