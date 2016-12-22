package com.example.liubiao.mvptest.mvp.present;

import com.example.liubiao.mvptest.mvp.interactor.NewsInteratorImpl;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;
import com.example.liubiao.mvptest.mvp.view.NewsView;
import com.example.liubiao.mvptest.mvp.view.base.BaseView;

import java.util.List;

import javax.inject.Inject;

import me.mvp.greendao.NewsChannelTable;

/**
 * Created by liubiao on 2016/12/5.
 */

public class NewsPresentImpl extends BasePresentImpl<NewsView,List<NewsChannelTable>> {
    private NewsInteratorImpl newsInterator;

    @Inject
    public NewsPresentImpl(NewsInteratorImpl newsInterator) {
        this.newsInterator = newsInterator;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        //获取数据
        provideNewsData();
    }

    public  void provideNewsData() {
        newsInterator.provideNewsData(this);
    }

    @Override
    public void RequestSucc(List<NewsChannelTable> data) {
        super.RequestSucc(data);
        mView.onListernData(data);
    }
}
