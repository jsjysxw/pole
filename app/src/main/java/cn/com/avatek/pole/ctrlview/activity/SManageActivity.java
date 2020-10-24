package cn.com.avatek.pole.ctrlview.activity;


import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.adapter.viewadapter.LvAdapter;
import cn.com.avatek.pole.adapter.viewadapter.ViewHolder;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.entity.CrmMenuEntity;
import cn.com.avatek.pole.entity.IconFunc;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;

public class SManageActivity extends BaseActivity {

    private LinearLayout ll_back,ll_content;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smanage);

        initViews();
        initNetData();
    }


    private void initViews() {
        ll_back = (LinearLayout) findViewById(R.id.ll_back);
        ll_content = (LinearLayout) findViewById(R.id.ll_content);

        ll_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
    public void setGvHeightBasedOnChildren(GridView gridView) {
        // 获取ListView对应的Adapter
        ListAdapter listAdapter = gridView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            // listAdapter.getCount()返回数据项的数目
            View listItem = listAdapter.getView(i, null, gridView);
            // 计算子项View 的宽高
            listItem.measure(0, 0);
            // 统计gv 为4列时的总高度
            if (i % 4 == 0)
                totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = gridView.getLayoutParams();
        if (Build.VERSION.SDK_INT >= 16)
            params.height = totalHeight + (gridView.getVerticalSpacing() * (listAdapter.getCount() - 1));
        else params.height = totalHeight;
        // listView.getDividerHeight()获取子项间分隔符占用的高度
        // params.height最后得到整个ListView完整显示需要的高度
        gridView.setLayoutParams(params);
    }

    private void initMainView(List<CrmMenuEntity> crmMenuEntities){

        if(crmMenuEntities!=null&&crmMenuEntities.size()>0) {
            for (CrmMenuEntity crm :crmMenuEntities) {
                LayoutInflater myInflater = LayoutInflater.from(mContext);
                View convertView = myInflater.inflate(R.layout.item_onemodel, null);
                TextView textView = (TextView) convertView.findViewById(R.id.textView);
                textView.setText(crm.getText());
                GridView gridView = (GridView) convertView.findViewById(R.id.gridView);
                List<CrmMenuEntity.ContentEntity> gvTypeList = new ArrayList<>();
                if(crm.getContent()!=null){
                    gvTypeList = crm.getContent();
                }
                LvAdapter<CrmMenuEntity.ContentEntity> gvAdapter = new LvAdapter<CrmMenuEntity.ContentEntity>(this, gvTypeList
                        , R.layout.item_func) {
                    @Override
                    public void convert(ViewHolder helper, CrmMenuEntity.ContentEntity item) {
                        helper.setText(R.id.tv_function_title, item.getName());
                        Glide.with(mContext).load(item.getIcon()).into((ImageView) helper.getView(R.id.iv_function_icon));
                    }
                };
                gridView.setAdapter(gvAdapter);
                gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        CrmMenuEntity.ContentEntity contentEntity = (CrmMenuEntity.ContentEntity) parent.getAdapter().getItem(position);
                        openWebview(contentEntity);
                    }
                });
                setGvHeightBasedOnChildren(gridView);
                ll_content.addView(convertView);
            }
        }
    }

    List<CrmMenuEntity> crmMenuEntityList = new ArrayList<>();
    //获取列表
    private void initNetData() {
        dialog = AvatekDialog.createLoadingDialog(mContext, "加载中...");
        dialog.show();
        NetManager.sendGet(ApiAddress.home_page, "", new NetCallBack() {
            @Override
            public void onError(String error, Call call) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(mContext, "获取失败",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet","response="+response);



                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();

                try {
                    Gson gson  = new Gson();
                    IconFunc webResult = gson.fromJson(response, IconFunc.class);
                    if (webResult != null && webResult.getContent() != null && webResult.getContent().getWorks().size() > 0) {
                        List<IconFunc.ContentBean.WorksBean> contentEntityList =webResult.getContent().getWorks();
                        if(contentEntityList!=null&&contentEntityList.size()>0){

                            List<String> slist = new ArrayList<>();
                            for (IconFunc.ContentBean.WorksBean f:contentEntityList){
                                if(!slist.contains(f.getType_name())){
                                    slist.add(f.getType_name()) ;
                                }
                            }
                            if(slist!=null&&slist.size()>0){
                                for (String k:slist) {
                                    CrmMenuEntity crmMenuEntity1 = new CrmMenuEntity();
                                    crmMenuEntity1.setText(k);
                                    crmMenuEntityList.add(crmMenuEntity1);
                                }
                            }
                            for (IconFunc.ContentBean.WorksBean c:contentEntityList){
                                for (CrmMenuEntity d:crmMenuEntityList) {

                                    if (c.getType_name() != null) {

                                        if (c.getType_name().equals(d.getText())) {
                                            List<CrmMenuEntity.ContentEntity> cList = d.getContent();
                                        if(cList==null) {
                                            cList = new ArrayList<>();
                                        }
                                            CrmMenuEntity.ContentEntity l = d.new ContentEntity();
                                            l.setUrl(c.getUrl());
                                            l.setIcon(c.getIcon());
                                            l.setName(c.getName());
                                            cList.add(l);
                                        d.setContent(cList);
                                        }

                                    }
                                }



                            }
                        }

                        initMainView(crmMenuEntityList);
                    }else {
                        Toast.makeText(mContext, "无数据", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(mContext, "解析出错", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }




    private void openWebview(CrmMenuEntity.ContentEntity item)
    {
//        Intent intent =new Intent(FuncListActivity.this,WebActivity.class);
        Intent intent =new Intent(mContext,WebViewActivity.class);
        intent.putExtra("web_url", item.getUrl());
        intent.putExtra("web_title", item.getName());
        startActivity(intent);
    }

}

