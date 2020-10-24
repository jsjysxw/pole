package cn.com.avatek.pole.ctrlview.dialogs;

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
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import cn.com.avatek.pole.R;

/**
 * Created by ios on 15/12/11.
 */
public class AlertDiaFragment extends DialogFragment implements View.OnClickListener{
    private View view;
    private TextView tv1, tv2, tv3, tv4;
    private EditText et1;
    private EditText et2;
    private Context context;
    private ConfirmListener confirmListener;

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        context = activity;
        confirmListener = (ConfirmListener) activity;
    }



    public interface ConfirmListener {
        void onConfirmComplete(String next, String info);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_alertdia, container,
                false);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT));

        tv1 = (TextView) view.findViewById(R.id.tv1);
        tv2 = (TextView) view.findViewById(R.id.tv2);
        tv3 = (TextView) view.findViewById(R.id.tv3);
        tv4 = (TextView) view.findViewById(R.id.tv4);

        et1 = (EditText) view.findViewById(R.id.et1);
        et2 = (EditText) view.findViewById(R.id.et2);

        initEvent();
        return view;
    }

    private void initEvent() {
        tv1.setOnClickListener(this);
        tv2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.tv1:
                if (context != null) {
//                    Toast.makeText(context, "标题=" + title + "内容=" + content,
//                            Toast.LENGTH_SHORT).show();
                    // 这里使用了回调确定后执行的事情
                    if(et1.getText().toString().length()<1){
                        Toast.makeText(context, "路线编号不能为空",
                            Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if(et2.getText().toString().length()<1){
                        Toast.makeText(context, "路线名称不能为空",
                                Toast.LENGTH_SHORT).show();
                        return;
                    }

                        confirmListener.onConfirmComplete(et1.getText().toString(), et2.getText().toString());
                        getDialog().cancel();

                }
                break;
            case R.id.tv2:
                getDialog().cancel();
                break;
        }
    }

}

