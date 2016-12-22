package com.example.liubiao.mvptest.mvp.view;

import com.example.liubiao.mvptest.mvp.entity.NewsDetail;
import com.example.liubiao.mvptest.mvp.view.base.BaseView;

/**
 * Created by liubiao on 2016/12/19.
 */

public interface NewsDetailView extends BaseView {
    void onListenerData(NewsDetail data);
}
