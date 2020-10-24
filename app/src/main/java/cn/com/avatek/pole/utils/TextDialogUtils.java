package cn.com.avatek.pole.utils;

import android.app.Activity;

import cn.com.avatek.pole.ctrlview.customview.AlertTextDialog;


/**
 * Created by Shenxw on 16/5/29.
 */
public class TextDialogUtils {
    private static TextDialogUtils dialogUtils;

    private TextDialogUtils(){}

    public static TextDialogUtils getInstance(){
        if(dialogUtils ==null){
            dialogUtils = new TextDialogUtils();
        }
        return dialogUtils;
    }

    public AlertTextDialog showDialog(Activity activity, AlertTextDialog dialog, String title, String Content) {
        if (dialog == null) {
            dialog = new AlertTextDialog();
        }
        if (!dialog.isAdded()) {
            dialog.setTitleText(title);
            dialog.setContentText(Content);
            dialog.show(activity.getFragmentManager(), "123");
        }
        return dialog;
    }
    public void hideDialog(AlertTextDialog dialog){
        if(dialog!=null&&dialog.isAdded()){
            dialog.dismiss();
        }
    }
}
