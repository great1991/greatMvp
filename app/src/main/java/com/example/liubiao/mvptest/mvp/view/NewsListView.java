package com.example.liubiao.mvptest.mvp.view;

import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by liubiao on 2016/12/7.
 */

public interface NewsListView extends BaseView {
    void onListenerData(List<NewsBean> data,boolean isRefresh);

}
