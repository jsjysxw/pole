package cn.com.avatek.pole.ctrlview.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
import cn.com.avatek.pole.adapter.HomeAdapter;
import cn.com.avatek.pole.adapter.MyAdapter;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.customview.TitleBarView;
import cn.com.avatek.pole.ctrlview.dialogs.AlertDiaFragment;
import cn.com.avatek.pole.entity.ContBean;
import cn.com.avatek.pole.entity.HomeFunc;
import cn.com.avatek.pole.entity.SimpleResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.module.upgrade.XWBCheckUpdate;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.ExitAppUtils;
import cn.com.avatek.pole.utils.HLog;
import cn.com.avatek.pole.utils.PermissionHelper;
import okhttp3.Call;

public class PoleMainActivity extends BaseActivity implements AlertDiaFragment.ConfirmListener{

    private PermissionHelper mHelper;
    private ImageView notice_logo;
    private TextView tv_name;
    private TextView tv_show;

    private TextView tv11;
    private TextView tv12;
    private TextView tv13;


    private Button btn_newline;

    private TitleBarView title_bar;
    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private HomeAdapter mAdapter;
    private List<HomeFunc.ContentBean.LinesBean> contBeanList = new ArrayList<>();


    @Override
    protected void onResume() {
        super.onResume();
        if(mAdapter!=null){
            initWeb();
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pole);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        initView();
        XWBCheckUpdate mHJCheckUpdate = new XWBCheckUpdate(PoleMainActivity.this);
        mHJCheckUpdate.checkVersion("mHJCheckUpdate");

        initEvent();

        initWeb();

        mHelper = new PermissionHelper(this);

        mHelper.requestPermissions("请授予[读写]权限！",
                new PermissionHelper.PermissionListener() {
                    @Override
                    public void doAfterGrand(String... permission) {

                    }

                    @Override
                    public void doAfterDenied(String... permission) {

                    }
                }, Manifest.permission.CAMERA, Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION);

    }

    private void initView() {

        notice_logo = (ImageView) findViewById(R.id.notice_logo);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_show = (TextView) findViewById(R.id.tv_show);
        tv11 = (TextView) findViewById(R.id.tv11);
        tv12 = (TextView) findViewById(R.id.tv12);
        tv13 = (TextView) findViewById(R.id.tv13);
        btn_newline = (Button) findViewById(R.id.btn_newline);

        tv_name.setText(SvaApplication.getInstance().getLoginUser().getName());
        tv_show.setText(SvaApplication.getInstance().getLoginUser().getDep_name());



        mRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        //创建默认的线性LayoutManager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //如果可以确定每个item的高度是固定的，设置这个选项可以提高性能
        mRecyclerView.setHasFixedSize(true);
        //创建并设置Adapter
        mAdapter = new HomeAdapter(contBeanList);

        mAdapter.setOnItemClickListener(new HomeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Intent intent1 = new Intent(PoleMainActivity.this,PointListActivity.class);
                intent1.putExtra("line_id",contBeanList.get(position).getLine_id());
                startActivity(intent1);
            }
        });

        mAdapter.setOnItemLongClickListener(new HomeAdapter.OnItemLongClickListener() {
            @Override
            public void onItemLongClick(View view, final int position) {
                new AlertDialog.Builder(PoleMainActivity.this)
                        .setTitle("提示")
                        .setMessage("确定要删除吗？")
                        .setPositiveButton("确定",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        deleteWeb(contBeanList.get(position).getLine_id());

                                    }
                                })
                        .setNegativeButton("取消",
                                new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(
                                            DialogInterface dialog,
                                            int which) {
                                        dialog.dismiss();
                                    }
                                }).create().show();
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


        btn_newline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDiaFragment dialog1 = new AlertDiaFragment();
                dialog1.setCancelable(true);
                dialog1.show(getFragmentManager(), "123");
            }
        });
    }

    private void initEvent() {

    }
    Dialog dialog;
    //获取列表
    private void initWeb() {
        Map<String, String> params = new HashMap<>();
        params.put("user_id", SvaApplication.getInstance().getLoginUser().getUser_id());
        NetManager.sendPost(ApiAddress.home_page, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {
                Toast.makeText(PoleMainActivity.this, "获取失败",
                        Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(String response) {
                HLog.e("webnet","response="+response);
                if(contBeanList!=null){
                    contBeanList.clear();
                }
                try {
                    Gson gson  = new Gson();
                    HomeFunc webResult = gson.fromJson(response, HomeFunc.class);
                    if (webResult != null && webResult.getContent() != null && webResult.getContent().getLines().size() > 0) {
                        List<HomeFunc.ContentBean.LinesBean> contentEntityList =webResult.getContent().getLines();
                        if(contentEntityList!=null&&contentEntityList.size()>0){
                            contBeanList.addAll(contentEntityList);
                            mAdapter.notifyDataSetChanged();
                        }

                    }else {
                        Toast.makeText(PoleMainActivity.this, "无数据", Toast.LENGTH_SHORT).show();
                    }
                    if (webResult != null&&webResult.getContent()!=null&&webResult.getContent().getNum()!=null) {
                        tv11.setText(webResult.getContent().getNum().getNow_num());
                        tv12.setText(webResult.getContent().getNum().getAll_num());
                        tv13.setText(webResult.getContent().getNum().getEnd_num());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PoleMainActivity.this, "解析出错", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    /**判断两次连续返回的时间间隔 */
    private static long exitClickTime;
    public static boolean isFastDoubleClick(int s) {
        long time = System.currentTimeMillis();
        long timeD = time - exitClickTime;
        if (0 < timeD && timeD < s) {
            return true;
        }
        exitClickTime = time;
        return false;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                if (!isFastDoubleClick(3600)) {
                    Toast.makeText(PoleMainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
                } else {
                    ExitAppUtils.getInstance().exit();
                    System.exit(0);
                    finish();
                }
                return true;
            } else {
                getSupportFragmentManager().popBackStack();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfirmComplete(String next, String info) {
        newLine(next,info);
    }

    private void newLine(String num1,String name1) {
        dialog = AvatekDialog.createLoadingDialog(PoleMainActivity.this, "加载中...");
        dialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("user_id", SvaApplication.getInstance().getLoginUser().getUser_id());
        params.put("num", num1);
        params.put("name", name1);
        NetManager.sendPost(ApiAddress.create_line, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(PoleMainActivity.this, "获取失败",
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
                    if (webResult != null && webResult.getState() > 0) {
                        Toast.makeText(PoleMainActivity.this, "新建成功", Toast.LENGTH_SHORT).show();
                        Intent intent1 = new Intent(PoleMainActivity.this,PointListActivity.class);
                        intent1.putExtra("line_id",String.valueOf(webResult.getState()));
                        startActivity(intent1);
                    }else {
                        Toast.makeText(PoleMainActivity.this, "新建失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PoleMainActivity.this, "解析出错", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }


    private void deleteWeb(String line_id) {
        dialog = AvatekDialog.createLoadingDialog(PoleMainActivity.this, "加载中...");
        dialog.show();
        Map<String, String> params = new HashMap<>();
        params.put("line_id", line_id);
        NetManager.sendPost(ApiAddress.line_delete, params, new NetCallBack() {
            @Override
            public void onError(String error, Call call) {

                if (dialog != null && dialog.isShowing())
                    dialog.dismiss();
                Toast.makeText(PoleMainActivity.this, "获取失败",
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
                    if (webResult != null && webResult.getState() > 0) {
                        Toast.makeText(PoleMainActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        initWeb();
                    }else {
                        Toast.makeText(PoleMainActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(PoleMainActivity.this, "解析出错", Toast.LENGTH_SHORT).show();
                }



            }
        });

    }
}