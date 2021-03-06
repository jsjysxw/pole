package cn.com.avatek.pole.utils;

import android.app.Activity;

import cn.com.avatek.pole.ctrlview.customview.AlertDiaFragment;


/**
 * Created by Shenxw on 16/5/29.
 */
public class DialogUtils {
    private static DialogUtils dialogUtils;

    private DialogUtils(){}

    public static DialogUtils getInstance(){
        if(dialogUtils ==null){
            dialogUtils = new DialogUtils();
        }
        return dialogUtils;
    }

    public AlertDiaFragment showDialog(Activity activity, AlertDiaFragment dialog) {
        if (dialog == null) {
            dialog = new AlertDiaFragment();
        }
        if (!dialog.isAdded()) {
            dialog.show(activity.getFragmentManager(), "123");
        }
        return dialog;
    }
    public void hideDialog(AlertDiaFragment dialog){
        if(dialog!=null&&dialog.isAdded()){
            dialog.dismiss();
        }
    }
}
