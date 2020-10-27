package cn.com.avatek.pole.ctrlview.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.RelativeLayout;
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
import cn.com.avatek.pole.adapter.MaterAdapter;
import cn.com.avatek.pole.adapter.MyAdapter;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.customview.TitleBarView;
import cn.com.avatek.pole.ctrlview.dialogs.AlertDia1Fragment;
import cn.com.avatek.pole.ctrlview.dialogs.AlertDiaFragment;
import cn.com.avatek.pole.entity.ContBean;
import cn.com.avatek.pole.entity.ListResult;
import cn.com.avatek.pole.entity.MaterBean;
import cn.com.avatek.pole.entity.MaterLResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;


/**
 * Created by User on
 */
public class SelectMaterLActivity extends BaseActivity implements AlertDia1Fragment.ConfirmListener {

    private TitleBarView title_bar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MaterAdapter mAdapter;
    private List<MaterBean> contBeanList = new ArrayList<>();
    private List<MaterBean> saveBeanList = new ArrayList<>();

    private RelativeLayout rl_cl, rl_clz;
    private TextView tv_cl, tv_clz;
    private View v_cl, v_clz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_list_sm);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }

        getIntData();
        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.setActivity(SelectMaterLActivity.this);
        title_bar.getBtnSubmit().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dealPre();
                setFinish();
            }
        });
        initListView();
        initWeb("cl");
    }

    private void getIntData() {

        String consumable = "";
        Intent intent = getIntent();
        if (intent != null) {
            consumable = intent.getStringExtra("cl");
        }
        if (consumable != null && !consumable.equals("")) {
            String[] strings = consumable.split(";");
            if (strings.length > 0) {
                for (int i = 0; i < strings.length; i++) {
                    String str = strings[i];
                    if (str != null && !str.equals("")) {
                        String[] strs = str.split(":");
                        if (strs.length == 2) {
                            MaterBean c = new MaterBean();
                            c.setId(strs[0]);
                            c.setNum(Integer.parseInt(strs[1]));
                            saveBeanList.add(c);
                        }
                    }
                }
            }
        }
    }

    private void setFinish() {
        String str = "";
        for (MaterBean c : saveBeanList) {
            if (c.getNum() > 0) {
                if (str.equals("")) {
                    str = c.getId() + ":" + c.getNum();
                } else {
                    str += ";" + c.getId() + ":" + c.getNum();
                }
            }
        }

        Intent intent1 = new Intent();
        intent1.putExtra("cl", str);
        setResult(22, intent1);
        finish();
    }

    private void initListView() {
        rl_cl = (RelativeLayout) findViewById(R.id.rl_cl);
        rl_clz = (RelativeLayout) findViewById(R.id.rl_clz);
        tv_cl = (TextView) findViewById(R.id.tv_cl);
        tv_clz = (TextView) findViewById(R.id.tv_clz);
        v_cl = (View) findViewById(R.id.v_cl);
        v_clz = (View) findViewById(R.id.v_clz);

        rl_cl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_cl.setTextColor(getResources().getColor(R.color.theme_color));
                v_cl.setVisibility(View.VISIBLE);
                tv_clz.setTextColor(getResources().getColor(R.color.black));
                v_clz.setVisibility(View.GONE);
                initWeb("cl");
            }
        });

        rl_clz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv_clz.setTextColor(getResources().getColor(R.color.theme_color));
                v_clz.setVisibility(View.VISIBLE);
                tv_cl.setTextColor(getResources().getColor(R.color.black));
                v_cl.setVisibility(View.GONE);
                initWeb("clz");
            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MaterAdapter(contBeanList);

        mAdapter.setOnItemClickListener(new MaterAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                if ("0".equals(contBeanList.get(position).getType())) {
                    initWeb(contBeanList.get(position).getId());
                } else {
                    Bundle data = new Bundle();
                    data.putInt("position", position);
                    AlertDia1Fragment dialog1 = new AlertDia1Fragment();
                    dialog1.setCancelable(true);
                    dialog1.setArguments(data);
                    dialog1.show(getFragmentManager(), "123");
                }

            }
        });
        mRecyclerView.setAdapter(mAdapter);
        RefreshLayout refreshLayout = (RefreshLayout) findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(1000);//传入false表示刷新失败
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadMore(1000);//传入false表示加载失败
            }
        });
    }

    //
    private void initWeb(final String iscl) {

        dealPre();

        String pid = "-1";
        if (!iscl.equals("cl") && !iscl.equals("clz")) {
            pid = iscl;
        }
        dialog = AvatekDialog.createLoadingDialog(SelectMaterLActivity.this, "登录中...");
        dialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", SvaApplication.getInstance().getLoginUser().getUser_id());
        params.put("pid", pid);
        NetManager.sendPost(ApiAddress.material_group_list, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                HLog.e("webnet", "response=" + error);
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet", "response=" + response);
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (contBeanList != null) {
                    contBeanList.clear();
                }
                Gson gson = new Gson();
                MaterLResult listResult = gson.fromJson(response, MaterLResult.class);

                if (listResult != null && listResult.getContent() != null) {

                    if (iscl.equals("clz")) {
                        if (listResult.getContent().getGroup() != null) {
                            for (MaterLResult.ContentBean.GroupBean g : listResult.getContent().getGroup()) {
                                MaterBean materBean = new MaterBean();
                                materBean.setId(g.getGroup_id());
                                materBean.setName(g.getName());
                                materBean.setNum(0);
                                materBean.setType("1");
                                contBeanList.add(materBean);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    } else {
                        if (listResult.getContent().getGroup() != null) {
                            for (MaterLResult.ContentBean.MaterialBean g : listResult.getContent().getMaterial()) {
                                MaterBean materBean = new MaterBean();
                                materBean.setId(g.getMaterial_id());
                                materBean.setName(g.getName());
                                materBean.setNum(0);
                                materBean.setType(g.getType_id());
                                contBeanList.add(materBean);
                            }
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    dealAfter();
                }
            }
        });
    }

    @Override
    public void onConfirmComplete(String next, int info) {
        if (!next.equals("")) {
            contBeanList.get(info).setNum(Integer.parseInt(next));
            mAdapter.notifyDataSetChanged();
        }
    }

    /**
     * 更换列表之前，操作savelist
     * 1。将与保存的相同的数据num替换掉
     * 2。将显示的lis中tnum不为0的加入
     */
    private void dealPre() {

        for (MaterBean b : contBeanList) {
            int d = 0;
            for (MaterBean g : saveBeanList) {
                if (b.getId().equals(g.getId())) {
                    g.setNum(b.getNum());
                    d++;
                }
            }
            if (d == 0 && b.getNum() > 0) {
                saveBeanList.add(b);
            }
        }
    }

    /**
     * 更换列表之前，操作显示list
     * 1。将savelist中相同id的num数据更新到显示列表上去
     */
    private void dealAfter() {
        for (MaterBean b : contBeanList) {
            for (MaterBean g : saveBeanList) {
                if (b.getId().equals(g.getId())) {
                    b.setNum(g.getNum());
                }
            }
        }
    }
}