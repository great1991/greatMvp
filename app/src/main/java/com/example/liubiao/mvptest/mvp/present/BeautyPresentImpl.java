package com.example.liubiao.mvptest.mvp.present;

import com.example.liubiao.mvptest.mvp.entity.BeautyBean;

import com.example.liubiao.mvptest.mvp.interactor.BeautyInteractorImpl;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;
import com.example.liubiao.mvptest.mvp.view.BeautyView;


import java.util.List;

import javax.inject.Inject;

/**
 * Created by liubiao on 2016/12/21.
 */
public class BeautyPresentImpl extends BasePresentImpl<BeautyView, List<BeautyBean.ResultsEntity>> {
    @Inject
    BeautyInteractorImpl interactor;
    private int size = 20;
    private int page = 1;
    @Inject
    public BeautyPresentImpl(BeautyInteractorImpl interactor)
    {
        this.interactor=interactor;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        loadData(true);
    }

    private void loadData() {
        interactor.loadData(this, size, page);
    }

    @Override
    public void RequestSucc(List<BeautyBean.ResultsEntity> data) {
        super.RequestSucc(data);
        if (data.size() != 0 && data != null) {
            size += 20;
            page++;
            mView.onListenerData(data);
        } else {
            mView.onListenerData(null);
        }
    }

    public void loadData(boolean isfresh) {
        if (isfresh) {
            size = 20;
            page = 1;
        }
        loadData();
    }
}
