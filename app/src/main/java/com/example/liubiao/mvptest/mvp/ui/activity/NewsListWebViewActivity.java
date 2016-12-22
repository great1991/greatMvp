package com.example.liubiao.mvptest.mvp.ui.activity;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptext.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liubiao on 2016/12/20.
 */

public class NewsListWebViewActivity extends BaseActivity {
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.action_web_view)
    WebView webView;
    @BindView(R.id.pb)
    ProgressBar progressBar;

    @Override
    public void initInject() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details_webview;
    }

    @Override
    protected void initView() {
        String url = getIntent().getStringExtra("url");
        String title = getIntent().getStringExtra("title");
        toolbar.setTitle(title);

        webView.loadUrl(url);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalFadingEdgeEnabled(false);
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        settings.setSupportMultipleWindows(true);
        //webview调整，适合屏幕
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        //缓存
        settings.setAppCacheEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

                if (newProgress == 100 && progressBar.isShown()) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView.destroy();
    }
}
