package com.example.liubiao.mvptest.mvp.view;

import com.example.liubiao.mvptest.mvp.view.base.BaseView;

import java.util.List;

import me.mvp.greendao.NewsChannelTable;

/**
 * Created by liubiao on 2016/12/12.
 */

public interface NewsAddView extends BaseView {
    void onLoadDatas(List<NewsChannelTable> list);
}
