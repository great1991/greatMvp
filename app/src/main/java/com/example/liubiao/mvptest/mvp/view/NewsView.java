package com.example.liubiao.mvptest.mvp.view;

import com.example.liubiao.mvptest.mvp.view.base.BaseView;

import java.util.List;

import me.mvp.greendao.NewsChannelTable;

/**
 * Created by liubiao on 2016/12/6.
 */

public interface NewsView extends BaseView {
   void onListernData(List<NewsChannelTable>data);

}
