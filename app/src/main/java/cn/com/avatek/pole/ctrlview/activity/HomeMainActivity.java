package cn.com.avatek.pole.ctrlview.activity;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.SvaApplication;
import cn.com.avatek.pole.ctrlview.fragment.HomeAFragment;
import cn.com.avatek.pole.ctrlview.fragment.HomeBFragment;
import cn.com.avatek.pole.ctrlview.fragment.HomeCFragment;
import cn.com.avatek.pole.ctrlview.fragment.HomeMainFragment;
import cn.com.avatek.pole.entity.UserBean;
import cn.com.avatek.pole.entity.UserResult;
import cn.com.avatek.pole.module.upgrade.XWBCheckUpdate;
import cn.com.avatek.pole.utils.ExitAppUtils;
import cn.com.avatek.pole.utils.PermissionHelper;

public class HomeMainActivity extends BaseActivity {

    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private RadioGroup radioGroup;
    private RadioButton tab_home;
    private RadioButton tab_notice;
    private RadioButton tab_person;
    private RadioButton tab_campus;
    private ArrayList<Integer> rbIds;
    private PermissionHelper mHelper;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        initView();
        XWBCheckUpdate mHJCheckUpdate = new XWBCheckUpdate(HomeMainActivity.this);
        mHJCheckUpdate.checkVersion("mHJCheckUpdate");
        init();

        initEvent();

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


    private void initEvent() {
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                viewPager.setCurrentItem(rbIds.indexOf(i));
            }
        });
    }

    private void initView() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        radioGroup = (RadioGroup) findViewById(R.id.tab_menu);
        tab_home = (RadioButton) findViewById(R.id.tab_home);
        tab_notice = (RadioButton) findViewById(R.id.tab_notice);
        tab_campus = (RadioButton) findViewById(R.id.tab_campus);
        tab_person = (RadioButton) findViewById(R.id.tab_person);
        viewPager = (ViewPager) findViewById(R.id.viewPager);

    }


    private void init(){
        Bitmap a = null;
        tab_home.setButtonDrawable(new BitmapDrawable(a));
        Drawable drawableAdd = getResources().getDrawable(R.drawable.main_zy);
        if (drawableAdd != null) {
            drawableAdd.setBounds(0, 0, 50
                    , 50);
            tab_home.setCompoundDrawables(null, drawableAdd, null, null);
            tab_home.setCompoundDrawablePadding(5);
        }

        tab_notice.setButtonDrawable(new BitmapDrawable(a));
        Drawable drawableAdd2 = getResources().getDrawable(R.drawable.main_xx);
        if (drawableAdd2 != null) {
            drawableAdd2.setBounds(0, 0, 50
                    , 50);
            tab_notice.setCompoundDrawables(null, drawableAdd2, null, null);
            tab_notice.setCompoundDrawablePadding(5);
        }

        tab_campus.setButtonDrawable(new BitmapDrawable(a));
        Drawable drawableAdd3 = getResources().getDrawable(R.drawable.main_xy);
        if (drawableAdd3 != null) {
            drawableAdd3.setBounds(0, 0, 50
                    , 50);
            tab_campus.setCompoundDrawables(null, drawableAdd3, null, null);
            tab_campus.setCompoundDrawablePadding(5);
        }
        tab_person.setButtonDrawable(new BitmapDrawable(a));
        Drawable drawableAdd4 = getResources().getDrawable(R.drawable.main_grzx);
        if (drawableAdd4 != null) {
            drawableAdd4.setBounds(0, 0, 50
                    , 50);
            tab_person.setCompoundDrawables(null, drawableAdd4, null, null);
            tab_person.setCompoundDrawablePadding(5);
        }





        rbIds=new ArrayList<>();
        rbIds.add(R.id.tab_home);
        rbIds.add(R.id.tab_notice);
        rbIds.add(R.id.tab_campus);
        rbIds.add(R.id.tab_person);

        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(new HomeMainFragment());
        fragmentList.add(new HomeAFragment());
        fragmentList.add(new HomeBFragment());
        fragmentList.add(new HomeCFragment());

        viewPager.setOffscreenPageLimit(4);
        viewPager.setAdapter(new MyFragmentPageAdapter(getSupportFragmentManager()));
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                UserResult.ContentBean bean= SvaApplication.getInstance().getLoginUser();
                if(bean!=null&&bean.getUser_id()!=null&&!bean.getUser_id().equals("")) {
                    radioGroup.check(rbIds.get(i));
                }else {
                    if(i==3) {
                        startActivity(new Intent(HomeMainActivity.this, LoginMainActivity.class));
                        radioGroup.check(rbIds.get(0));
                    }else {
                        radioGroup.check(rbIds.get(i));
                    }
                }

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        radioGroup.check(rbIds.get(0));
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
                    Toast.makeText(HomeMainActivity.this, "再按一次退出", Toast.LENGTH_SHORT).show();
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

    private class MyFragmentPageAdapter extends FragmentPagerAdapter {

        public MyFragmentPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }




    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}