package com.example.liubiao.mvptest.mvp.present;

import com.example.liubiao.mvptest.event.NewsRefreshChannelEvent;
import com.example.liubiao.mvptest.mvp.interactor.NewsAddInteratorImpl;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;
import com.example.liubiao.mvptest.mvp.view.NewsAddView;
import com.example.liubiao.mvptest.utils.RxBus;

import java.util.List;

import javax.inject.Inject;

import me.mvp.greendao.NewsChannelTable;

/**
 * Created by liubiao on 2016/12/12.
 */

public class NewsAddChannelImpl extends BasePresentImpl<NewsAddView, List<NewsChannelTable>> {
    private NewsAddInteratorImpl addInterator;
    private boolean flag;


    @Inject
    public NewsAddChannelImpl(NewsAddInteratorImpl addInterator) {
        this.addInterator = addInterator;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        addInterator.loadChannelData(this);
    }

    @Override
    public void RequestSucc(List<NewsChannelTable> data) {
        super.RequestSucc(data);
        mView.onLoadDatas(data);
    }

    public void doDb(int fromPosition, int toPosition) {
        flag=true;
        addInterator.doDb(fromPosition, toPosition);
    }

    public void doDb(NewsChannelTable table, boolean isMine) {
        flag=true;
        addInterator.doDb(table, isMine);
    }

    @Override
    public void destory() {
        super.destory();
        if (flag) {
            RxBus.getRxBusInstace().post(new NewsRefreshChannelEvent());
        }
    }
}
