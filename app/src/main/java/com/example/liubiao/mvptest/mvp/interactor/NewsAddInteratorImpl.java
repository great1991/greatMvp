package com.example.liubiao.mvptest.mvp.interactor;

import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.repository.db.NewsChannelManager;
import com.example.liubiao.mvptest.repository.net.RetrofitManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import javax.inject.Inject;

import me.mvp.greendao.NewsChannelTable;
import rx.Observable;
import rx.Subscriber;

/**
 * Created by liubiao on 2016/12/12.
 */

public class NewsAddInteratorImpl {
    private List<NewsChannelTable> list = new ArrayList<>();
    private ExecutorService executor;

    @Inject
    public NewsAddInteratorImpl() {
    }

    public void loadChannelData(final RequestCallBack callBack) {
        //获取数据，回调回去
        Observable.create(new Observable.OnSubscribe<List<NewsChannelTable>>() {

            @Override
            public void call(Subscriber<? super List<NewsChannelTable>> subscriber) {
                List<NewsChannelTable> list = getDataFromDB();
                subscriber.onNext(list);
            }
        }).subscribe(new Subscriber<List<NewsChannelTable>>() {
            @Override
            public void onCompleted() {}
            @Override
            public void onError(Throwable e) {
                callBack.RequestError("c");
            }
            @Override
            public void onNext(List<NewsChannelTable> newsChannelTables) {
                callBack.RequestSucc(newsChannelTables);
            }
        });
    }

    public List<NewsChannelTable> getDataFromDB() {
        list.clear();
        NewsChannelManager.initDB();
        List<NewsChannelTable> mineChannel = NewsChannelManager.getSelectedChannel();
        List<NewsChannelTable> moreChannel = NewsChannelManager.getMoreChannel();
        list.addAll(mineChannel);
        list.addAll(moreChannel);
        return list;
    }
    public void doDb(final NewsChannelTable table, final boolean isMine) {
        createExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {

                if (isMine) {
                    //点击的是我的频道
                    int index = table.getNewsChannelIndex();
                    int allSize = NewsChannelManager.getAllSize();
                    List<NewsChannelTable> list = NewsChannelManager.loadNewsChannelsIndexGt(index);
                    for (NewsChannelTable table : list) {
                        table.setNewsChannelIndex(table.getNewsChannelIndex() - 1);
                        NewsChannelManager.update(table);
                    }
                    table.setNewsChannelSelect(false);
                    table.setNewsChannelIndex(allSize);
                    NewsChannelManager.update(table);
                } else {
                    int index = table.getNewsChannelIndex();
                    int newsChannelSelectSize = NewsChannelManager.getNewsChannelSelectSize();
                    List<NewsChannelTable> list = NewsChannelManager.loadNewsChannelsWithin(newsChannelSelectSize, index);
                    for (NewsChannelTable table : list) {
                        table.setNewsChannelIndex(table.getNewsChannelIndex() + 1);
                        NewsChannelManager.update(table);
                    }

                    table.setNewsChannelSelect(true);
                    table.setNewsChannelIndex(newsChannelSelectSize);
                    NewsChannelManager.update(table);
                }

            }
        });


    }

    public void doDb(final int fromPosition, final int toPosition) {
        createExecutor();
        executor.execute(new Runnable() {
            @Override
            public void run() {
                if (Math.abs(fromPosition - toPosition) == 1) {
                    NewsChannelTable fromChannel = NewsChannelManager.getIndexChannel(fromPosition);
                    NewsChannelTable toChannel = NewsChannelManager.getIndexChannel(toPosition);
                    int newsChannelIndex = toChannel.getNewsChannelIndex();
                    toChannel.setNewsChannelIndex(fromChannel.getNewsChannelIndex());
                    fromChannel.setNewsChannelIndex(newsChannelIndex);
                } else if (fromPosition - toPosition > 0) {
                    List<NewsChannelTable> list = NewsChannelManager.loadNewsChannelsWithin(fromPosition, toPosition);
                    NewsChannelTable table = NewsChannelManager.getIndexChannel(fromPosition);
                    table.setNewsChannelIndex(toPosition);
                    for (NewsChannelTable ta : list) {
                        table.setNewsChannelIndex(ta.getNewsChannelIndex() + 1);
                    }
                } else {
                    List<NewsChannelTable> list = NewsChannelManager.loadNewsChannelsWithin(fromPosition, toPosition);
                    NewsChannelTable table = NewsChannelManager.getIndexChannel(fromPosition);
                    table.setNewsChannelIndex(toPosition);
                    for (NewsChannelTable ta : list) {
                        table.setNewsChannelIndex(ta.getNewsChannelIndex() - 1);
                    }
                }
            }
        });

    }

    private void createExecutor() {
        if (executor == null) {
            executor = Executors.newSingleThreadExecutor();
        }
    }


}
