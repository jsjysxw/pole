package cn.com.avatek.pole.utils;


///**
// * toast 复写类，多次点击不会重复长时间显示
// * @author chenming.ma
// */
//public class NewToast {
//
//	protected static Toast toast = null;
//
//	public static void makeText(Context context, String s, int duration) {
//		try {
//			if (null == context || null == s) {
//				return;
//			}
//			View v = LayoutInflater.from(context).inflate(R.layout.transient_notification, null);
//			TextView txt = (TextView) v.findViewById(R.id.notifactiontxt);
////			txt.setText(s);
//			Drawable drawable1 = SvaApplication.getContext().getResources().getDrawable(R.drawable.dialog_default_icon);
//			drawable1.setBounds(0, 0,
//					DensityUtil.dip2px(SvaApplication.getContext(), SvaApplication.getContext().getResources().getDimensionPixelSize(R.dimen.size_matrrix10)),
//					DensityUtil.dip2px(SvaApplication.getContext(), SvaApplication.getContext().getResources().getDimensionPixelSize(R.dimen.size_matrrix10)));
//			txt.setCompoundDrawables(drawable1, null, null, null);
//			txt.setText(s);
//			if (toast == null) {
//				toast = Toast.makeText(context, s, duration);
//			}
//			toast.setView(v);
//			toast.show();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	public static void makeText(Context context, int resId) {
//		makeText(context, context.getString(resId), Toast.LENGTH_SHORT);
//	}
//
//	public static void makeText(Context context, String str) {
//		makeText(context, str, Toast.LENGTH_SHORT);
//	}
//	public static void makeText(String str) {
//		makeText(SvaApplication.getContext(), str, Toast.LENGTH_SHORT);
//	}
//}
