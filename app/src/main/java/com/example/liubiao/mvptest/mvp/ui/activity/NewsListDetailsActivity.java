package com.example.liubiao.mvptest.mvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;
import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.common.Constant;
import com.example.liubiao.mvptest.mvp.entity.NewsDetail;
import com.example.liubiao.mvptest.mvp.present.NewsDetailsPresentImpl;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptest.mvp.view.NewsDetailView;
import com.example.liubiao.mvptest.utils.MyUtils;
import com.example.liubiao.mvptest.widget.URLImageGetter;
import com.example.liubiao.mvptext.R;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liubiao on 2016/12/19.
 */

public class NewsListDetailsActivity extends BaseActivity<NewsDetailsPresentImpl> implements NewsDetailView {
    @Inject
    NewsDetailsPresentImpl present;
    @BindView(R.id.iv)
    ImageView iv;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar_layout)
    CollapsingToolbarLayout toolbarLayout;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.btn_share)
    FloatingActionButton btnShare;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_body)
    TextView tvBody;
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    private String mShareLink;
    private String mNewsTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_details;
    }

    @Override
    protected void initView() {
        String postid = getIntent().getStringExtra("postid");
        presentImpl=present;
        presentImpl.setPostId(postid);
        presentImpl.attchView(this);
    }


    @Override
    public void onListenerData(NewsDetail data) {
        mShareLink = data.getShareLink();
        mNewsTitle = data.getTitle();
        String newsSource = data.getSource();
        String newsTime = MyUtils.formatTime(data.getPtime());
        String newsBody = data.getBody();
        String NewsImgSrc = getImgSrcs(data);

        toolbarLayout.setTitle(mNewsTitle);
        toolbarLayout.setExpandedTitleColor(ContextCompat.getColor(this, R.color.white));
        toolbarLayout.setCollapsedTitleTextColor(ContextCompat.getColor(this, R.color.white));

        tvTime.setText(getString(R.string.news_text_format, newsSource, newsTime));

        Glide.with(this).load(NewsImgSrc).asBitmap()
                .placeholder(R.drawable.ic_loading)
                .format(DecodeFormat.PREFER_ARGB_8888)
                .error(R.drawable.ic_load_fail)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(iv);
        setNewsDetailBodyTv(data, newsBody);
    }

    private void setNewsDetailBodyTv(final NewsDetail newsDetail, final String newsBody) {
        mSubscription = Observable.timer(500, TimeUnit.MILLISECONDS)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {
                        progressBar.setVisibility(View.GONE);
                        btnShare.setVisibility(View.VISIBLE);
                        YoYo.with(Techniques.RollIn).playOn(btnShare);
                    }

                    @Override
                    public void onError(Throwable e) {
                        progressBar.setVisibility(View.GONE);
                    }
                    @Override
                    public void onNext(Long aLong) {
                        setBody(newsDetail, newsBody);
                    }
                });
    }

    private void setBody(NewsDetail newsDetail, String newsBody) {
        int imgTotal = newsDetail.getImg().size();
        if (App.isHavePhoto() && imgTotal >= 2 && newsBody != null) {
            URLImageGetter   mUrlImageGetter = new URLImageGetter(tvBody, newsBody, imgTotal);
            tvBody.setText(Html.fromHtml(newsBody, mUrlImageGetter, null));
        } else {
            tvBody.setText(Html.fromHtml(newsBody));
        }
    }

    private String getImgSrcs(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        String imgSrc;
        if (imgSrcs != null && imgSrcs.size() > 0) {
            imgSrc = imgSrcs.get(0).getSrc();
        } else {
            imgSrc = getIntent().getStringExtra("imgSrc");
        }
        return imgSrc;
    }


    @OnClick(R.id.btn_share)
    public void onClick() {
        Intent intent=new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, "分享");
        intent.putExtra(Intent.EXTRA_TITLE, "Title");
        intent.putExtra(Intent.EXTRA_TEXT, getShareContents());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Intent.createChooser(intent, "请选择"));

    }
    private String getShareContents() {
        if (mShareLink == null) {
            mShareLink = "";
        }
        return getString(R.string.news_text_format, mNewsTitle, mShareLink);
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String string) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.action_web_view:
                Toast.makeText(this,"webview显示",Toast.LENGTH_SHORT).show();
                jumpWebViewAct();
                break;
            case R.id.action_browser:
                Toast.makeText(this,"浏览器显示",Toast.LENGTH_SHORT).show();
                jumpBrowserAct();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
    private void jumpWebViewAct() {
        Intent intent = new Intent(this, NewsListWebViewActivity.class);
        intent.putExtra("url", mShareLink);
        intent.putExtra("title", mNewsTitle);
        startActivity(intent);
    }
    private void jumpBrowserAct() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.VIEW");
        if (canBrowse(intent)) {
            Uri uri = Uri.parse(mShareLink);
            intent.setData(uri);
            startActivity(intent);
        }
    }
    private boolean canBrowse(Intent intent) {
        return intent.resolveActivity(getPackageManager()) != null && mShareLink != null;
    }
}
