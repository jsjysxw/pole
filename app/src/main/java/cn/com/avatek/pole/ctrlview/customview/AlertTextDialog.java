package cn.com.avatek.pole.ctrlview.customview;

import android.app.DialogFragment;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import cn.com.avatek.pole.R;


/**
 * Created by ios on 15/12/11.
 */
public class AlertTextDialog extends DialogFragment implements View.OnClickListener {
    private View view;
    private TextView fag_tv1, fag_tv2, contentText, titleText;
    private String content;
    private AlertTextListener alertTextListener;
    private String contentTe = "";
    private String titleTe = "";

    public void setOnAlertTextListener(AlertTextListener alertTextListener) {
        this.alertTextListener = alertTextListener;
    }

    public interface AlertTextListener {
        void onAlertTextComplete1();
        void onAlertTextComplete2();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        Bundle data = getArguments();
        //保留参数，传过来的id
        if (data != null) {
            content = data.getString("content");
        }
        view = inflater.inflate(R.layout.text_alertdialog, container,
                false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
        fag_tv1 = (TextView) view.findViewById(R.id.fag_tv1);
        fag_tv2 = (TextView) view.findViewById(R.id.fag_tv2);
        contentText = (TextView) view.findViewById(R.id.contentText);
        titleText = (TextView) view.findViewById(R.id.titleText);

        initEvent();

        initView();
        return view;
    }

    private void initView() {
        if (contentTe != null && !contentTe.equals("")) {
            contentText.setText(contentTe);
        }
        if (titleTe != null && !titleTe.equals("")) {
            titleText.setText(titleTe);
        }
    }

    private void initEvent() {
        fag_tv1.setOnClickListener(this);
        fag_tv2.setOnClickListener(this);
    }

    public void setContentText(String text) {
        if (text != null && !text.equals("")) {
            contentTe = text;
        }
    }

    public void setTitleText(String text) {
        if (text != null && !text.equals("")) {
            titleTe = text;
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fag_tv1:
                if (alertTextListener != null) {
                    // 这里使用了回调取消后执行的事情
                    alertTextListener.onAlertTextComplete1();
                }
                break;
            case R.id.fag_tv2:
                if (alertTextListener != null) {
                    // 这里使用了回调确定后执行的事情
                    alertTextListener.onAlertTextComplete2();
                }
                break;
        }
    }

}

