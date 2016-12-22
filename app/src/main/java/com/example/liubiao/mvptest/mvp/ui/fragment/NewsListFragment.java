package com.example.liubiao.mvptest.mvp.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.liubiao.mvptest.common.Constant;
import com.example.liubiao.mvptest.event.FabTopEvent;
import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.mvp.entity.NewsDetail;
import com.example.liubiao.mvptest.mvp.entity.NewsDetailPhotoBean;
import com.example.liubiao.mvptest.mvp.present.NewsListPresentImpl;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsDetailPhotoActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsListDetailsActivity;
import com.example.liubiao.mvptest.mvp.ui.adapter.base.BaseRecycleAdapter;
import com.example.liubiao.mvptest.mvp.ui.adapter.newsFragAdapter;
import com.example.liubiao.mvptest.mvp.ui.fragment.base.BaseFragment;
import com.example.liubiao.mvptest.mvp.view.NewsListView;
import com.example.liubiao.mvptest.utils.RxBus;
import com.example.liubiao.mvptext.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by liubiao on 2016/12/6.
 */
public class NewsListFragment extends BaseFragment<NewsListPresentImpl> implements NewsListView,
        SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.rv)
    RecyclerView mRecycleV;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @Inject
    NewsListPresentImpl listPresent;
    @Inject
    newsFragAdapter adapter;
    @Inject
    Activity activity;
    private LinearLayoutManager linearLayoutManager;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getRxBusInstace().toObservable(FabTopEvent.class)
                .subscribe(new Action1<FabTopEvent>() {
                    @Override
                    public void call(FabTopEvent fabTopEvent) {
                      //  mRecycleV.smoothScrollToPosition(0);
                        mRecycleV.scrollToPosition(0);
                    }
                });
    }

    @Override
    protected void initInject() {
        getFragmentComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news;
    }

    @Override
    protected void initView() {
        setViewProperty();
        initEvent();
        presentImpl = listPresent;
        presentImpl.attchView(this);
        initTypeName();
        mRecycleV.setAdapter(adapter);
    }

    private void setViewProperty() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        linearLayoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        mRecycleV.setLayoutManager(linearLayoutManager);
        mRecycleV.setItemAnimator(new DefaultItemAnimator());
        mRecycleV.setHasFixedSize(true);
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(this);
        mRecycleV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int lastItemPosition = linearLayoutManager.findLastVisibleItemPosition();
                int visibleItemCount = linearLayoutManager.getChildCount();
                int totalItemCount = linearLayoutManager.getItemCount();
                int itemCount = adapter.getItemCount();
                Log.e("account", "lastItemPosition=" + lastItemPosition + ";visibleItemCount=" + visibleItemCount + ";totalItemCount=" + totalItemCount + ";itemCount=" + itemCount);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastItemPosition + 1 == adapter.getItemCount()) {

                    adapter.showFootView();
                    presentImpl.loadNewsListData(false);
                    mRecycleV.smoothScrollToPosition(itemCount-1);

                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //button显示
                if (dy >= 0) {
                }
            }
        });
        adapter.setOnItemClickListern(new BaseRecycleAdapter.OnItemClickListern() {
            @Override
            public void onItemClick(View v, int layoutPosition, boolean isPhoto) {
                if (isPhoto) {
                    NewsBean newsBean = adapter.getList().get(layoutPosition);
                    Intent intent = new Intent(activity, NewsDetailPhotoActivity.class);
                    NewsDetailPhotoBean bean = getNewsDetailPhotoBean(newsBean);
                    intent.putExtra("bean", bean);
                    activity.startActivity(intent);
                } else {
                    NewsBean newsBean = adapter.getList().get(layoutPosition);
                    Intent intent = new Intent(activity, NewsListDetailsActivity.class);
                    intent.putExtra("postid", newsBean.getPostid());
                    intent.putExtra("imgSrc", newsBean.getImgsrc());
                    activity.startActivity(intent);
                }
            }
        });
    }

    public NewsDetailPhotoBean getNewsDetailPhotoBean(NewsBean newsBean) {
        NewsDetailPhotoBean bean = new NewsDetailPhotoBean();
        bean.setTitle(newsBean.getTitle());

        List<NewsDetailPhotoBean.Picture> pictureList = new ArrayList<>();
        if (newsBean.getAds() != null) {
            for (NewsBean.AdsBean entity : newsBean.getAds()) {
                setValuesAndAddToList(pictureList, entity.getTitle(), entity.getImgsrc());
            }
        } else if (newsBean.getImgextra() != null) {
            for (NewsBean.ImgextraEntity entity : newsBean.getImgextra()) {
                setValuesAndAddToList(pictureList, null, entity.getImgsrc());
            }
        } else {
            setValuesAndAddToList(pictureList, null, newsBean.getImgsrc());
        }
        bean.setPictures(pictureList);
        return bean;
    }

    private void setValuesAndAddToList(List<NewsDetailPhotoBean.Picture> pictureList, String title, String imgsrc) {
        NewsDetailPhotoBean.Picture picture = new NewsDetailPhotoBean.Picture();
        if (title != null) {
            picture.setTitle(title);
        }
        picture.setImgSrc(imgsrc);

        pictureList.add(picture);
    }

    private void initTypeName() {
        Bundle bundle = getArguments();
        String newsId = bundle.getString(Constant.NEWS_ID);
        String newsType = bundle.getString(Constant.NEWS_TYPE);
        presentImpl.setNewsListTypeId(newsId, newsType);
    }


    @Override
    public void onListenerData(List<NewsBean> data, boolean isRefresh) {
        if (isRefresh) {
            if (data.size() != 0) {
                adapter.setList(data);
                adapter.notifyDataSetChanged();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        } else {
            if (data.size() != 0) {
                adapter.addAllItem(data);
            }
            adapter.hideFootView();
        }
    }

    @Override

    public void onRefresh() {

        presentImpl.loadNewsListData(true);
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


}
