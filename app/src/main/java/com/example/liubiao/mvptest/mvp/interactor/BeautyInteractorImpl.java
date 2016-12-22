package com.example.liubiao.mvptest.mvp.interactor;

import android.support.annotation.MainThread;

import com.example.liubiao.mvptest.common.HostType;
import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.mvp.entity.BeautyBean;
import com.example.liubiao.mvptest.repository.net.RetrofitManager;

import java.util.List;

import javax.inject.Inject;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liubiao on 2016/12/21.
 */
public class BeautyInteractorImpl {
    @Inject
    public BeautyInteractorImpl() {
    }

    public void loadData(final RequestCallBack callBack, int size, int page) {
        RetrofitManager.getInstace(HostType.GANK_GIRL_PHOTO).getBeautyListData(size, page)
                .map(new Func1<BeautyBean, List<BeautyBean.ResultsEntity>>() {
                    @Override
                    public List<BeautyBean.ResultsEntity> call(BeautyBean beautyBean) {
                        return beautyBean.getResults();
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<BeautyBean.ResultsEntity>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.RequestError(e.toString());
                    }

                    @Override
                    public void onNext(List<BeautyBean.ResultsEntity> resultsEntities) {
                        callBack.RequestSucc(resultsEntities);
                    }
                });


    }
}
