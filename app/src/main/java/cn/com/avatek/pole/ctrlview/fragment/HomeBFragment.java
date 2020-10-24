package cn.com.avatek.pole.ctrlview.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.SvaApplication;
import cn.com.avatek.pole.constant.ApiAddress;
import cn.com.avatek.pole.ctrlview.activity.ClassListActivity;
import cn.com.avatek.pole.ctrlview.activity.ItemListActivity;
import cn.com.avatek.pole.ctrlview.customview.CommonView;
import cn.com.avatek.pole.ctrlview.customview.OdHonView;
import cn.com.avatek.pole.ctrlview.customview.OdVerView;
import cn.com.avatek.pole.entity.ClassContent;
import cn.com.avatek.pole.entity.WebResult;
import cn.com.avatek.pole.manage.NetCallBack;
import cn.com.avatek.pole.manage.NetManager;
import cn.com.avatek.pole.utils.AvatekDialog;
import cn.com.avatek.pole.utils.HLog;
import okhttp3.Call;


/**
 *
 */
public class HomeBFragment extends BaseFragment {

    private View rootView;
    private WebView mWebView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_3, container, false);
        mWebView = (WebView) rootView.findViewById(R.id.wv_main);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setWebChromeClient(new WebChromeClient());

        mWebView.loadUrl(ApiAddress.Host+"news.php?user_id="+ SvaApplication.getInstance().getLoginUser().getUser_id());

        initWebView();

        return rootView;
    }
    Dialog dialog;

    private void initWebView() {
        dialog = AvatekDialog.createLoadingDialog(getActivity(),
                "加载中...");
        dialog.show();
        mWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                dialog.setCancelable(true);
                dialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("javascript:document.body.innerHTML=''");
                if (dialog!=null&&dialog.isShowing()){
                    dialog.dismiss();
                }
                Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
