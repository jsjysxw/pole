package cn.com.avatek.pole.ctrlview.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.google.gson.Gson;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.adapter.MyAdapter;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.customview.TitleBarView;
import cn.com.avatek.pole.entity.ContBean;
import cn.com.avatek.pole.entity.ListResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;


/**
 * Created by User on 2015/4/29.
 */
public class ClassListActivity extends BaseActivity {

    private TitleBarView title_bar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private MyAdapter mAdapter;
    private List<ContBean> contBeanList = new ArrayList<>();
    private String title  ="";
    private String id  ="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_list_more);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.setActivity(ClassListActivity.this);
        initIntent();
        initListView();
        initWeb();

    }
    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            if (title != null && !title.equals(""))
                title_bar.setTitle(title);
            if (intent.getStringExtra("id") != null && !intent.getStringExtra("id").equals("")){
                id = intent.getStringExtra("id");
            }
        }
    }
    private void initListView() {
        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new MyAdapter(contBeanList);

        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent14 = new Intent(ClassListActivity.this, ItemListActivity.class);
                intent14.putExtra("id", contBeanList.get(position).getItem_id());
                intent14.putExtra("title",title);
                startActivity(intent14);
            }
        });
        mRecyclerView.setAdapter(mAdapter);
        RefreshLayout refreshLayout = (RefreshLayout)findViewById(R.id.refreshLayout);
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

    private void initWeb() {

//        NetManager.sendGet(ApiAddress.get_class_info, "&class_id="+id, new NetCallBack() {
//            @Override
//            public void onError(String error, Call call) {
//                HLog.e("webnet", "response=" + error);
//            }
//
//            @Override
//            public void onSuccess(String response) {
//                HLog.e("webnet", "response=" + response);
//                if(contBeanList!=null){
//                    contBeanList.clear();
//                }
//                Gson gson = new Gson();
//                ListResult listResult = gson.fromJson(response, ListResult.class);
//                if (listResult != null && listResult.getContent() != null && listResult.getContent().size() > 0) {
//                    contBeanList.addAll(listResult.getContent());
//                    mAdapter.notifyDataSetChanged();
//                }
//            }
//        });
    }
}