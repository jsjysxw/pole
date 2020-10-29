package cn.com.avatek.pole.ctrlview.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.SvaApplication;
import cn.com.avatek.pole.adapter.MyAdapter;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.customview.TitleBarView;
import cn.com.avatek.pole.entity.ContBean;
import cn.com.avatek.pole.entity.ListResult;
import cn.com.avatek.pole.entity.SimpleResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.HLog;
import cn.com.avatek.pole.utils.IDropdownItemClickListener;
import cn.com.avatek.pole.utils.TypePopupwindow;
import okhttp3.Call;


/**
 * Created by User on 2015/4/29.
 */
public class AddPointActivity extends BaseActivity implements View.OnClickListener{

    /**
     * num = 杆号
     dist = 距离(第一个点默认为0)
     x = x坐标
     y = y坐标
     cross = 跨越（选择：河流、光纤、电力线路、公路）
     voltage = 电压（选择220V、400V、10kV）
     point_type = 电杆类型（选择标准台架变、9米电杆、10米电杆、12米电杆、15米电杆、利旧、四线门型架、二线门型架、门型杆）
     rematks = 备注
     pid = 上一级ID（第一个为-1）
     line_id = 所属线路ID
     materials = 材料（格式为材料1ID：数量;材料2ID:数量;...）
     */
    private TitleBarView title_bar;

    private EditText num,dist,coorx,coory,rematks;
    private TextView cross,voltage,point_type,pid,line_id,materials;

    private String p_id = "";
    private String l_id = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_point_add);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        Intent intent = getIntent();
        if(intent!=null){
            p_id = intent.getStringExtra("point_id");
            l_id = intent.getStringExtra("line_id");
        }

        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.setActivity(AddPointActivity.this);
        title_bar.getBtnSubmit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkData();
            }
        });
        initView1();

        initEvent1();
    }

    private void initEvent1() {
        cross.setOnClickListener(this);
        voltage.setOnClickListener(this);
        point_type.setOnClickListener(this);
        materials.setOnClickListener(this);
    }

    /**
     * private EditText num,dist,coorx,coory,rematks;
     private TextView cross,voltage,point_type,pid,line_id,materials;
     */
    private void initView1() {
        num = (EditText) findViewById(R.id.num);
        dist = (EditText) findViewById(R.id.dist);
        coorx = (EditText) findViewById(R.id.coorx);
        coory = (EditText) findViewById(R.id.coory);
        rematks = (EditText) findViewById(R.id.rematks);
        cross = (TextView) findViewById(R.id.cross);
        voltage = (TextView) findViewById(R.id.voltage);
        point_type = (TextView) findViewById(R.id.point_type);
        pid = (TextView) findViewById(R.id.pid);
        line_id = (TextView) findViewById(R.id.line_id);
        materials = (TextView) findViewById(R.id.materials);

        line_id.setText("线路ID："+l_id);
        if(p_id!=null&&!p_id.equals("")){
            pid.setText("父节点ID："+p_id);
        }else {
            pid.setText("父节点：self");
        }

    }

    private void checkData(){
        if ("".equals(num.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请填写杆号", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(dist.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请填写距离", Toast.LENGTH_SHORT).show();
            return;
        }

//        if ("".equals(coorx.getText().toString())) {
//            Toast.makeText(getApplicationContext(), "请填写x坐标", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if ("".equals(coory.getText().toString())) {
//            Toast.makeText(getApplicationContext(), "请填写y坐标", Toast.LENGTH_SHORT).show();
//            return;
//        }

        if ("".equals(rematks.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请填写备注", Toast.LENGTH_SHORT).show();
            return;
        }

        if ("".equals(materials.getText().toString())||"请选择".equals(materials.getText().toString())) {
            Toast.makeText(getApplicationContext(), "请选择材料", Toast.LENGTH_SHORT).show();
            return;
        }

        title_bar.getBtnSubmit().setClickable(false);

        submitWeb();
    }

    private void submitWeb() {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", SvaApplication.getInstance().getLoginUser().getUser_id());
        params.put("num", num.getText().toString());
        params.put("dist", dist.getText().toString());
//        params.put("x", coorx.getText().toString());
//        params.put("y", coory.getText().toString());
        params.put("rematks", rematks.getText().toString());
        params.put("cross", cross.getText().toString());
        params.put("voltage", voltage.getText().toString());
        params.put("point_type", point_type.getText().toString());
        params.put("pid", p_id.equals("")?"-1":p_id);
        params.put("line_id", l_id);
        params.put("materials", materials.getText().toString());
        NetManager.sendPost(ApiAddress.insert_point, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {
                HLog.e("webnet", "response=" + error);
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet", "response=" + response);
                Gson gson = new Gson();
                SimpleResult listResult = gson.fromJson(response, SimpleResult.class);
                if (listResult != null && listResult.getState() > 0) {
                    Toast.makeText(mContext, "创建成功", Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(mContext, "创建失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * cross = 跨越（选择：河流、光纤、电力线路、公路）
     voltage = 电压（选择220V、400V、10kV）
     point_type = 电杆类型（选择标准台架变、9米电杆、10米电杆、12米电杆、15米电杆、利旧、四线门型架、二线门型架、门型杆）
     * @param view
     */
    @Override
    public void onClick(View view) {
        List<String> sList = new ArrayList<String>();
        switch (view.getId()){
            case R.id.cross:
                sList.add("河流");
                sList.add("光纤");
                sList.add("电力线路");
                sList.add("公路");
                showPopup(sList, cross);
                break;
            case R.id.voltage:
                sList.add("220V");
                sList.add("400V");
                sList.add("10kV");
                showPopup(sList, voltage);
                break;
            case R.id.point_type:
                sList.add("标准台架变");
                sList.add("9米电杆");
                sList.add("10米电杆");
                sList.add("12米电杆");
                sList.add("15米电杆");
                sList.add("利旧");
                sList.add("四线门型架");
                sList.add("二线门型架");
                sList.add("门型杆");
                showPopup(sList, point_type);
                break;
            case R.id.materials:

                Intent intent = new Intent(AddPointActivity.this,SelectMaterLActivity.class);
                if(!materials.getText().toString().trim().equals("")&&!materials.getText().toString().trim().equals("请选择")){
                    intent.putExtra("cl",materials.getText().toString().trim());
                }
                startActivityForResult(intent,11);
                break;

            default:
                break;

        }
    }

    private TypePopupwindow popup;

    private void showPopup(List<String> sList, final TextView tv) {

        int[] location = new int[2];
        tv.getLocationInWindow(location);
        location[1] += tv.getHeight();
        popup = new TypePopupwindow(AddPointActivity.this, sList);
        popup.showAt(location, tv.getWidth(), tv.getHeight(), false);
        popup.setItemClickListener(new IDropdownItemClickListener() {

            @Override
            public void onItemClick(String text, int position) {
                tv.setText(text);
            }
        });
    }

    /**
     * 点击空白区域隐藏键盘.
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent me) {
        if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            View v = getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
            if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                hideKeyboard(v.getWindowToken());   //收起键盘
            }
        }
        return super.dispatchTouchEvent(me);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }
    /**
     * 获取InputMethodManager，隐藏软键盘
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==11&&resultCode==22){
            if(data!=null){
                String str = data.getStringExtra("cl");
                materials.setText(str);
            }
        }
    }
}