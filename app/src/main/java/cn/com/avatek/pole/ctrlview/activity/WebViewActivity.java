package cn.com.avatek.pole.ctrlview.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import cn.com.avatek.pole.R;
import cn.com.avatek.pole.utils.AvatekDialog;


public class WebViewActivity extends BaseActivity {

    private WebView mWebView;
    private String webUrl, webTitle;
    private TextView tvTitle;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setStatusLine();
        }
        Intent i = getIntent();
        webUrl = i.getStringExtra("web_url");
        webTitle = i.getStringExtra("web_title");
        tvTitle = (TextView) findViewById(R.id.tv_title);
        mWebView = (WebView) findViewById(R.id.wv_main);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setSavePassword(false);
        mWebView.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
        mWebView.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mWebView.getSettings().setUseWideViewPort(true);
        mWebView.setWebChromeClient(new WebChromeClient());
        if (webTitle != null) {
            tvTitle.setText(webTitle);
        }
        mWebView.addJavascriptInterface(this, "JsUseAndroid");
        mWebView.loadUrl(webUrl);
        llBackLayout = (LinearLayout) findViewById(R.id.ll_sales_list_back);
        llBackLayout.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initWebView();
    }

    @JavascriptInterface
    public void closePage() {
        finish();
    }

    private void initWebView() {
        dialog = AvatekDialog.createLoadingDialog(WebViewActivity.this,
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
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
//                super.onReceivedError(view, errorCode, description, failingUrl);
                mWebView.loadUrl("javascript:document.body.innerHTML=''");
                if (dialog != null && dialog.isShowing()) {
                    dialog.dismiss();
                }
                Toast.makeText(WebViewActivity.this, "加载失败", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
