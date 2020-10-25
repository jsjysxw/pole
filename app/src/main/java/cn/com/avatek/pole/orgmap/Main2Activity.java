package cn.com.avatek.pole.orgmap;


import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.entity.OrgBean;

public class Main2Activity extends AppCompatActivity {
    /**
     * 是否是横屏
     * 默认false
     */
    boolean mIsPreviewLand = false;

    OrgMapView orgMapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        orgMapView = (OrgMapView) findViewById(R.id.orgMapView);

        List<OrgBean> orgBeans = new ArrayList<>();
        OrgBean orgBean1 = new OrgBean();
        orgBean1.setCid(11);
        orgBean1.setCuuid(21);
        orgBean1.setOrgname("2号");

        OrgBean orgBean2 = new OrgBean();
        orgBean2.setCid(11);
        orgBean2.setCuuid(22);
        orgBean2.setOrgname("3号");
        orgBeans.add(orgBean1);
        orgBeans.add(orgBean2);

        OrgBean orgBean = new OrgBean();
        orgBean.setCid(1);
        orgBean.setCuuid(11);
        orgBean.setOrgname("1号");
        orgBean.setChilds(orgBeans);



        /*设置数据源*/
        String val = orgMapView.getOriginalFundData(this);
        Gson gson = new Gson();
        String a = gson.toJson(orgBean);

        MorgDataBean data = gson.fromJson(a, MorgDataBean.class);
        Log.e("val","val"+data.getOrgname());
        data.setOrgnameShow(data.getOrgname());
        orgMapView.setData(data);
        orgMapView.setOrgMapItemClickListener(new OrgMapView.OnOrgMapItemClickListener() {
            @Override
            public void onItemClick(MorgDataBean data) {
                Toast.makeText(Main2Activity.this, "data="+data.getOrgname(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void expand(View view) {
        setRequestedOrientation(!mIsPreviewLand ? ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE : ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mIsPreviewLand = !mIsPreviewLand;
    }
}
