package cn.com.avatek.pole.adapter.viewadapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.avatek.pole.SvaApplication;


public class ViewHolder {
	private final SparseArray<View> mViews;
	private int mPosition;
	private Context mContext;
	private View mConvertView;

	private ViewHolder(Context context, ViewGroup parent, int layoutId,
                       int position) {
		this.mPosition = position;
		this.mContext = context;
		this.mViews = new SparseArray<View>();
		mConvertView = LayoutInflater.from(context).inflate(layoutId, parent,
				false);
		mConvertView.setTag(this);
	}

	/**
	 * get one ViewHolder object
	 * 
	 * @param context
	 * @param convertView
	 * @param parent
	 * @param layoutId
	 * @param position
	 * @return
	 */
	public static ViewHolder get(Context context, View convertView,
                                 ViewGroup parent, int layoutId, int position) {

		if (convertView == null) {
			return new ViewHolder(context, parent, layoutId, position);
		}
		return (ViewHolder) convertView.getTag();
	}

	/**
	 * get some view by its id;if not exist,then add to array
	 * 
	 * @param viewId
	 * @return
	 */
	public <T extends View> T getView(int viewId) {

		View view = mViews.get(viewId);
		if (view == null) {
			view = mConvertView.findViewById(viewId);
			mViews.put(viewId, view);
		}
		return (T) view;
	}

	public View getConvertView() {
		return mConvertView;
	}

	/**
	 * set text to TextView
	 * 
	 * @param viewId
	 * @param text
	 * @return
	 */
	public ViewHolder setText(int viewId, String text) {
		TextView view = getView(viewId);
		view.setText(text);
		return this;
	}

	public ViewHolder setTextColor(int viewId, int text) {
		TextView view = getView(viewId);
		view.setTextColor(text);
		return this;
	}

	public ViewHolder setBackground(int viewId, int id) {
		TextView view = getView(viewId);
		view.setBackgroundResource(id);
		return this;
	}

	public ViewHolder setRlBackground(int viewId, int id) {
		RelativeLayout view = getView(viewId);
		view.setBackgroundResource(id);
		return this;
	}

	/**
	 * set image to ImageView by drawableId
	 * 
	 * @param viewId
	 * @param drawableId
	 * @return
	 */
	public ViewHolder setImageResource(int viewId, int drawableId) {
		ImageView view = getView(viewId);
		view.setImageResource(drawableId);

		return this;
	}

	/**
	 * set image to ImageView by Bitmap
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ViewHolder setImageBitmap(int viewId, Bitmap bm) {
		ImageView view = getView(viewId);
		view.setImageBitmap(bm);
		return this;
	}

	/**
	 * set image to ImageView by url
	 * 
	 * @param viewId
	 * @param
	 * @return
	 */
	public ViewHolder setImageByUrl(int viewId, String url) {
		Glide.with(SvaApplication.getContext()).load(url).into((ImageView) getView(viewId));
		return this;
	}

	public int getPosition() {
		return mPosition;
	}
}
