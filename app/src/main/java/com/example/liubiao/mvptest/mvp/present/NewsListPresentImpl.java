package com.example.liubiao.mvptest.mvp.present;

import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.mvp.interactor.NewsListInteratorImpl;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;
import com.example.liubiao.mvptest.mvp.view.NewsListView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.http.Path;

/**
 * Created by liubiao on 2016/12/7.
 */
public class NewsListPresentImpl extends BasePresentImpl<NewsListView, List<NewsBean>> {
    private NewsListInteratorImpl NewsListInterator;
    private String newsType;
    private String newsId;
    private boolean isRefresh;
    private int startPage = 0;

    @Inject
    public NewsListPresentImpl(NewsListInteratorImpl NewsListInterator) {
        this.NewsListInterator = NewsListInterator;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadNewsListData(true, startPage);
    }

    private void loadNewsListData(boolean isRefresh, int startPage) {
        this.isRefresh = isRefresh;
        NewsListInterator.loadNewsListData(this, newsId, newsType, startPage);
    }

    @Override
    public void RequestSucc(List<NewsBean> data) {
        super.RequestSucc(data);
        if(data.size()!=0)
        {
            startPage+=20;
        }
        mView.onListenerData(data, isRefresh);
    }

    public void setNewsListTypeId(String newsId, String newsType) {
        this.newsId = newsId;
        this.newsType = newsType;
    }

    public void loadNewsListData(boolean isRefresh) {
        if (isRefresh) {
            startPage = 0;
        }
        loadNewsListData(isRefresh, startPage);
    }
}
