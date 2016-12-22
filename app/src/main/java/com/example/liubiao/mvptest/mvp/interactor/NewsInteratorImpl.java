package com.example.liubiao.mvptest.mvp.interactor;

import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.repository.db.NewsChannelManager;

import java.util.List;

import javax.inject.Inject;

import me.mvp.greendao.NewsChannelTable;
import rx.Observable;
import rx.Scheduler;
import rx.Subscriber;

/**
 * Created by liubiao on 2016/12/6.
 */

public class NewsInteratorImpl {
    @Inject
    public NewsInteratorImpl(){}
    public void provideNewsData(final RequestCallBack callBack)
    {
        Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {
            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                NewsChannelManager.initDB();
                List<NewsChannelTable> selectedChannel = NewsChannelManager.getSelectedChannel();
                subscriber.onNext(selectedChannel);
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<List<NewsChannelTable>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

                callBack.RequestError("从数据库获取数据有误");
            }

            @Override
            public void onNext(List<NewsChannelTable> newsChannelTables) {
                callBack.RequestSucc(newsChannelTables);
            }
        });



    }
}
