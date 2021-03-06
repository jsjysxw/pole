package cn.com.avatek.pole.ctrlview.customview;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.avatek.pole.R;


/**
 * Created by ios on 15/12/11.
 */
public class AlertDiaFragment extends DialogFragment implements View.OnClickListener{
    private View view;
    private TextView tv1, tv2, tv3;
    private EditText et4;
    private Context context;
    private Spinner spinner;
//    private String title;
//    private String content;
    private ConfirmListener confirmListener;
    private int i;

    /**
     * 这里官方是不建议构造方法里面放参数，这里先这样，那种方式有点烦
     * 到项目中再去用规范的方法
     */
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context = activity;
        confirmListener = (ConfirmListener) activity;
    }



    public interface ConfirmListener {
        void onConfirmComplete(int i, int j);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
//        Bundle data = getArguments();
//        title = data.getString("title");
//        content = data.getString("content");
        view = inflater.inflate(R.layout.fragment_alertdia1, container,
                false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));
//        Window window = getDialog().getWindow();
//        window.setGravity(Gravity.TOP);
        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        et4 = (EditText) view.findViewById(R.id.et4);
        spinner = (Spinner) view.findViewById(R.id.spinner1);


        initEvent();
//        initTitle();
        return view;
    }

    private void initEvent() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

//    private void initTitle() {
//        if (title != null && content != null) {
//            tv3.setText(title);
//            et4.setText(content);
//        }
//    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv1:
                if (context != null) {
                    if(et4.getText().toString().trim().equals("")){
                        Toast.makeText(context, "内容不能为空！",
                            Toast.LENGTH_SHORT).show();
                    }else {
//                    Toast.makeText(context, "标题=" + title + "内容=" + content,
//                            Toast.LENGTH_SHORT).show();
                        // 这里使用了回调确定后执行的事情
                        confirmListener.onConfirmComplete(Integer.parseInt(et4.getText().toString().trim()),spinner.getSelectedItemPosition()+1);
                    }
                }
                break;
            case R.id.tv2:
                getDialog().cancel();
                break;
        }
    }

}

