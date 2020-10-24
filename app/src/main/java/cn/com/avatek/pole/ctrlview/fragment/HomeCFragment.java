package cn.com.avatek.pole.ctrlview.fragment;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.SvaApplication;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.constant.Constant;
import cn.com.avatek.pole.ctrlview.activity.CollListActivity;
import cn.com.avatek.pole.ctrlview.activity.FbackActivity;
import cn.com.avatek.pole.ctrlview.activity.LoginMainActivity;
import cn.com.avatek.pole.ctrlview.activity.WebViewActivity;
import cn.com.avatek.pole.entity.UserBean;
import cn.com.avatek.pole.entity.UserResult;
import cn.com.avatek.pole.utils.GlideCircleTransform;
import cn.com.avatek.pole.utils.SharedPreferenceUtil;


/**
 *
 */
public class HomeCFragment extends BaseFragment {

    private View rootView;

    private TextView tv_name,tv_show, tv_parent;
    private ImageView notice_logo;
    private Button btn_logout;

    private RelativeLayout rl_parent, rl_updatepsd, rl_person_detail, rl_feedback, rl_fback;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_mine, container, false);

        initView();

        initEvent();

        return rootView;
    }

    private void initView() {
        tv_name = (TextView) rootView.findViewById(R.id.tv_name);
        tv_show = (TextView) rootView.findViewById(R.id.tv_show);
        notice_logo = (ImageView) rootView.findViewById(R.id.notice_logo);
        btn_logout = (Button) rootView.findViewById(R.id.btn_logout);
        rl_parent = (RelativeLayout) rootView.findViewById(R.id.rl_parent);
        rl_updatepsd = (RelativeLayout) rootView.findViewById(R.id.rl_updatepsd);
        rl_person_detail = (RelativeLayout) rootView.findViewById(R.id.rl_person_detail);
        rl_feedback = (RelativeLayout) rootView.findViewById(R.id.rl_feedback);
        rl_fback = (RelativeLayout) rootView.findViewById(R.id.rl_fback);
        tv_parent = (TextView) rootView.findViewById(R.id.tv_parent);
    }

    private void initEvent() {
        if (SvaApplication.getInstance().getLoginUser() != null) {
            UserResult.ContentBean  userBean = SvaApplication.getInstance().getLoginUser();
            if (userBean.getName() != null) {
                tv_name.setText(userBean.getName());
            } else {
                tv_name.setText("（未获取到名字）");
            }
            if (userBean.getName() != null) {
                tv_show.setText(userBean.getName());
            } else {
                tv_show.setText("");
            }
            if (userBean.getDep_name() != null && !userBean.getDep_name().equals("")) {
//                Glide.with(getActivity()).load(userBean.getAvatar()).into(notice_logo);
                Glide.with(getActivity()).load(userBean.getDep_name()).centerCrop().transform(new GlideCircleTransform(getActivity())).into(notice_logo);
            }


            //意见反馈
            rl_parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), FbackActivity.class);
                    startActivity(intent);
                }
            });

//            rl_updatepsd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), UpdatePSActivity.class);
//                    startActivity(intent);
//                }
//            });
            //分享下载链接
            rl_person_detail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), WebViewActivity.class);
                    intent.putExtra("web_url", ApiAddress.Host+"share_page.php?user_id="+SvaApplication.getInstance().getLoginUser().getUser_id());
                    intent.putExtra("web_title",  "分享应用");
                    startActivity(intent);
                }
            });
            //我的办理
            rl_feedback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), CollListActivity.class);
                    startActivity(intent);
                }
            });
            //设置
            rl_fback.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    Intent intent = new Intent(getActivity(), FbackActivity.class);
//                    startActivity(intent);
                    Intent intent = null;
                    PackageManager packageManager = getActivity().getPackageManager();
                    if (checkPackInfo("com.avatek.guizhou.cms")) {
//                        intent = packageManager.getLaunchIntentForPackage("com.avatek.guizhou.cms");
                        intent = new Intent();
                        String packageName = "com.avatek.guizhou.cms";
                        String className = "com.avatek.guizhou.cms.ui.SplashActivity";
                        intent.setClassName(packageName, className);
                        intent.putExtra("username","test_username");
                        intent.putExtra("token","test_token");
                        intent.putExtra("appid","gzydwgxt");

                    } else {
                        intent= new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri content_url = Uri.parse("http://www.dayo.net.cn/gzcmm/download");
                        intent.setData(content_url);
                    }
                    startActivity(intent);
                }
            });
        }


        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Logout();
                SharedPreferenceUtil.saveData(getActivity(), Constant.FILE_NAME, Constant.KEY_PSD, "");
                SharedPreferenceUtil.saveData(getActivity(), Constant.FILE_NAME, Constant.KEY_USERBEAN, "");
                SvaApplication.getInstance().setLoginUser(new UserResult.ContentBean());
                getActivity().finish();
            }
        });
    }

    //退出登录
    public void Logout() {
        final Intent intent = new Intent();
        if (getActivity() != null) {
            intent.setClass(getActivity(), LoginMainActivity.class);
            startActivity(intent);
            getActivity().finish();
        }

    }


    /**
     * 检查包是否存在
     *
     * @param packname
     * @return
     */
    private boolean checkPackInfo(String packname) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = getActivity().getPackageManager().getPackageInfo(packname, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return packageInfo != null;
    }


}
