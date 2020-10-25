package cn.com.avatek.pole.ctrlview.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
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
import cn.com.avatek.pole.entity.OrgBean;
import cn.com.avatek.pole.entity.PointResult;
import cn.com.avatek.pole.entity.SimpleResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.orgmap.Main2Activity;
import cn.com.avatek.pole.orgmap.MorgDataBean;
import cn.com.avatek.pole.orgmap.OrgMapView;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;


/**
 * 电杆列表
 * Created by User on 2015/4/29.
 */
public class PointListActivity extends BaseActivity {

    private TitleBarView title_bar;

    private List<OrgBean> list = new ArrayList<>();
    private String line_id = "";
    private String point_id = "";
    private String gen = "";
    private OrgMapView orgMapView;

    private TextView tv_bar_new,tv_bar_del;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_list_point);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        Intent intent = getIntent();
        line_id = intent.getStringExtra("line_id");

        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.setActivity(PointListActivity.this);

        tv_bar_new = (TextView) findViewById(R.id.tv_bar_new);
        tv_bar_del = (TextView) findViewById(R.id.tv_bar_del);

        tv_bar_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent2 = new Intent(PointListActivity.this, AddPointActivity.class);
                intent2.putExtra("point_id", point_id);
                intent2.putExtra("line_id", line_id);
                startActivity(intent2);
            }
        });

        tv_bar_del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(list!=null&&list.size()>1){
                    if(point_id.equals(gen)){
                        Toast.makeText(mContext, "请选择要删除的电杆", Toast.LENGTH_SHORT).show();
                    }else {
                        initDel(point_id);
                    }
                }else {
                    initDel(point_id);
                }
            }
        });
        orgMapView = (OrgMapView) findViewById(R.id.orgMapView);
        initWeb();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(orgMapView!=null){
            initWeb();
        }
    }

    private void initWeb() {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", SvaApplication.getInstance().getLoginUser().getUser_id());
        params.put("line_id", line_id);
        NetManager.sendPost(ApiAddress.point_list, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {
                HLog.e("webnet", "response=" + error);
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet", "response=" + response);
                if (list != null) {
                    list.clear();
                }
                Gson gson = new Gson();
                PointResult listResult = gson.fromJson(response, PointResult.class);

                list = new ArrayList<>();
                if (listResult != null && listResult.getContent() != null) {
                    for (PointResult.ContentBean bean : listResult.getContent()) {
                        OrgBean orgBean = new OrgBean();
                        orgBean.setCid(Integer.parseInt(bean.getPid()));
                        orgBean.setCuuid(Integer.parseInt(bean.getPoint_id()));
                        orgBean.setOrgname(bean.getNum());
                        list.add(orgBean);
                    }
                }
                List<OrgBean> orgBeanss = findTree(list);
                String a = "";
                if (orgBeanss != null && orgBeanss.size() > 0) {
                    point_id = String.valueOf(orgBeanss.get(0).getCuuid());
                    gen = String.valueOf(orgBeanss.get(0).getCuuid());
                    a = gson.toJson(orgBeanss.get(0));
                    MorgDataBean data = gson.fromJson(a, MorgDataBean.class);
                    data.setOrgnameShow(data.getOrgname());
                    orgMapView.setData(data);
                    orgMapView.setOrgMapItemClickListener(new OrgMapView.OnOrgMapItemClickListener() {
                        @Override
                        public void onItemClick(MorgDataBean data) {
                            point_id = data.getCuuid();
                        }
                    });
                }
            }
        });
    }

    public List<OrgBean> findTree(List<OrgBean> orgBeanList) {
        List<OrgBean> list = new ArrayList<>();
        for (OrgBean dept : orgBeanList) {
            if (dept.getCid() == -1) {
                list.add(dept);
            }
        }
        findChildren(list, orgBeanList);
        return list;
    }

    private void findChildren(List<OrgBean> sysDepts, List<OrgBean> depts) {
        for (OrgBean sysDept : sysDepts) {
            List<OrgBean> children = new ArrayList<>();
            for (OrgBean dept : depts) {
                if (sysDept.getCuuid() == dept.getCid()) {
                    children.add(dept);
                }
            }
            sysDept.setChilds(children);
            findChildren(children, depts);
        }
    }

    private void initDel(String point_id) {
        dialog = AvatekDialog.createLoadingDialog(PointListActivity.this, "加载中...");
        dialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("point_id", point_id);
        NetManager.sendPost(ApiAddress.point_delete, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(PointListActivity.this, "获取失败",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet","response="+response);
                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                try {
                    Gson gson  = new Gson();
                    SimpleResult webResult = gson.fromJson(response, SimpleResult.class);
                    initWeb();
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PointListActivity.this, "解析出错", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}