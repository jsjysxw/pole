package cn.com.avatek.pole.ctrlview.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
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
import cn.com.avatek.pole.SvaApplication;
import cn.com.avatek.pole.adapter.viewadapter.LvAdapter;
import cn.com.avatek.pole.adapter.viewadapter.ViewHolder;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.activity.LoginMainActivity;
import cn.com.avatek.pole.ctrlview.activity.WebViewActivity;
import cn.com.avatek.pole.entity.CrmMenuEntity;
import cn.com.avatek.pole.entity.IconFunc;
import cn.com.avatek.pole.entity.UserBean;
import cn.com.avatek.pole.entity.UserResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;


/**
 * Created by shenxw on 2016/11/30.
 */

public class HomeMainFragment extends BaseFragment implements View.OnClickListener
{
    private View rootView;
    private LinearLayout ll_content;
    private WebView mWebView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
        initView();



        initEvent();
        initWeb();

        return rootView;
    }



    private void initEvent() {



    }

    private void initView() {

        ll_content = (LinearLayout) rootView.findViewById(R.id.ll_content);
        mWebView = (WebView) rootView.findViewById(R.id.wv_main);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.loadUrl(ApiAddress.Host+"home_news.php");
        initWebView();
    }



    @Override
    public void onClick(View view) {
//        switch (view.getId()){
//            case R.id.iv_bar_left:
//
//                break;
//            case R.id.iv_bar_right:
//
//                break;
//            case R.id.title_view_frag:
//
//                break;
//
//        }
    }


    private void initWebView() {
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("javascript:document.body.innerHTML=''");
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    List<CrmMenuEntity> crmMenuEntityList = new ArrayList<>();

    Dialog dialog;
    //获取列表
    private void initWeb() {
        dialog = AvatekDialog.createLoadingDialog(getActivity(), "加载中...");
        dialog.show();
        NetManager.sendGet(ApiAddress.home_page, "", new NetCallBack() {
            @Override
            public void onError(String error, Call call) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(getActivity(), "获取失败",
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
                        Toast.makeText(getActivity(), "无数据", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "解析出错", Toast.LENGTH_SHORT).show();
                }



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
            if (i % 5 == 0)
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
                LayoutInflater myInflater = LayoutInflater.from(getActivity());
                View convertView = myInflater.inflate(R.layout.item_onemodel, null);
                TextView textView = (TextView) convertView.findViewById(R.id.textView);
                textView.setText(crm.getText());
                GridView gridView = (GridView) convertView.findViewById(R.id.gridView);
                List<CrmMenuEntity.ContentEntity> gvTypeList = new ArrayList<>();
                if(crm.getContent()!=null){
                    gvTypeList = crm.getContent();
                }
                LvAdapter<CrmMenuEntity.ContentEntity> gvAdapter = new LvAdapter<CrmMenuEntity.ContentEntity>(getActivity(), gvTypeList
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

    private void openWebview(CrmMenuEntity.ContentEntity item)
    {
        UserResult.ContentBean bean= SvaApplication.getInstance().getLoginUser();

        if(bean!=null&&bean.getUser_id()!=null&&!bean.getUser_id().equals("")) {
            Intent intent = new Intent(getActivity(), WebViewActivity.class);
            intent.putExtra("web_url", item.getUrl()+"?user_id="+SvaApplication.getInstance().getLoginUser().getUser_id());
            intent.putExtra("web_title", item.getName());
            startActivity(intent);
        }else {
            startActivity( new Intent(getActivity(), LoginMainActivity.class));
        }
    }

}
