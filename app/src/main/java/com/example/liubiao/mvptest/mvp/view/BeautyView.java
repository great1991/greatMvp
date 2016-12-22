package com.example.liubiao.mvptest.mvp.view;

import com.example.liubiao.mvptest.mvp.entity.BeautyBean;
import com.example.liubiao.mvptest.mvp.view.base.BaseView;

import java.util.List;

/**
 * Created by liubiao on 2016/12/21.
 */
public interface BeautyView extends BaseView {
    void onListenerData(List<BeautyBean.ResultsEntity> data);
}
