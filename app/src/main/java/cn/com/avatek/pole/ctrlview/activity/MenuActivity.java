package cn.com.avatek.pole.ctrlview.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.customview.CommonView;
import cn.com.avatek.pole.ctrlview.customview.OdHonView;
import cn.com.avatek.pole.ctrlview.customview.OdVerView;
import cn.com.avatek.pole.ctrlview.customview.TitleBarView;
import cn.com.avatek.pole.entity.ClassContent;
import cn.com.avatek.pole.entity.WebResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;


/**
 * Created by User on 2015/4/29.
 */
public class MenuActivity extends BaseActivity {

    private TitleBarView title_bar;
    private LinearLayout ll_main;
    private ImageView iv_head;
    private String title  ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        setContentView(R.layout.activity_menu);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        title_bar.setActivity(MenuActivity.this);


        initView();
        initIntent();
        initWeb();
    }

    private void initIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            title = intent.getStringExtra("title");
            if (title != null && !title.equals(""))
                title_bar.setTitle(title);
        }
    }

    private void initWeb() {
        String name = "主页";
        if(title!=null){
            name = title;
        }
//        NetManager.sendGet(ApiAddress.get_page_info, "&name="+name, new NetCallBack() {
//            @Override
//            public void onError(String error, Call call) {
//                HLog.e("webnet", "response=" + error);
//            }
//
//            @Override
//            public void onSuccess(String response) {
//                HLog.e("webnet", "response=" + response);
//                Gson gson = new Gson();
//                WebResult webResult = gson.fromJson(response, WebResult.class);
//                if (webResult != null && webResult.getContent() != null && webResult.getContent().size() > 0) {
//                    for (ClassContent c : webResult.getContent()) {
//                        if ("0".equals(c.getType_id())) {
//                            if (c.getContent() != null && c.getContent().size() > 0) {
//                                String str = c.getContent().get(0).getPic_url();
//                                if (str != null && !str.equals("")) {
//                                    Glide.with(MenuActivity.this).load(str).into(iv_head);
//                                }else {
//                                    Glide.with(MenuActivity.this).load(R.drawable.a).into(iv_head);
//                                }
//                            }
//                        } else if ("1".equals(c.getType_id())) {
//                            if (c.getContent() != null && c.getContent().size() == 3) {
//                                honView(c);
//                            }
//
//                        } else if ("2".equals(c.getType_id())) {
//                            if (c.getContent() != null && c.getContent().size() == 3) {
//                                verView(c);
//                            }
//                        }
//
//                    }
//                }
//            }
//        });
    }

    private void initView() {
        ll_main = (LinearLayout) findViewById(R.id.ll_main);
        iv_head = (ImageView) findViewById(R.id.iv_head);
    }

    private void verView(ClassContent classContent) {
        if (ll_main != null) {
            OdVerView odVerView = new OdVerView(MenuActivity.this);
            odVerView.setDetailData(classContent);
            odVerView.setOnShowListListenner(new CommonView.OnShowListListenner() {
                @Override
                public void onShowChanged(String formid, String liststr,String title) {
                    if ("1".equals(liststr)) {
                        Intent intent14 = new Intent(MenuActivity.this, ClassListActivity.class);
                        intent14.putExtra("id", formid);
                        intent14.putExtra("type", "class_id");
                        startActivity(intent14);
                    } else {
                        Intent intent14 = new Intent(MenuActivity.this, ItemListActivity.class);
                        intent14.putExtra("id", formid);
                        intent14.putExtra("type", "item_id");
                        startActivity(intent14);
                    }
                }
            });
             ll_main.addView(odVerView);

        }
    }

    private void honView(ClassContent classContent) {
        if (ll_main != null) {
            OdHonView odHonView = new OdHonView(MenuActivity.this);
            odHonView.setDetailData(classContent);
            odHonView.setOnShowListListenner(new CommonView.OnShowListListenner() {
                @Override
                public void onShowChanged(String formid, String liststr,String title) {
                    if ("1".equals(liststr)) {
                        Intent intent14 = new Intent(MenuActivity.this, ClassListActivity.class);
                        intent14.putExtra("id", formid);
                        intent14.putExtra("title",title);
                        startActivity(intent14);
                    } else {
                        Intent intent14 = new Intent(MenuActivity.this, ItemListActivity.class);
                        intent14.putExtra("id", formid);
                        intent14.putExtra("title",title);
                        startActivity(intent14);
                    }
                }
            });
            ll_main.addView(odHonView);
        }
    }
}