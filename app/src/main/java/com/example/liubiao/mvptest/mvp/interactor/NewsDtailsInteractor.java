package com.example.liubiao.mvptest.mvp.interactor;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.common.HostType;
import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.mvp.entity.NewsDetail;
import com.example.liubiao.mvptest.repository.net.RetrofitManager;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liubiao on 2016/12/19.
 */

public class NewsDtailsInteractor {
    @Inject
    public NewsDtailsInteractor() {
    }

    public void loadData(final String postId, final RequestCallBack callBack) {
        callBack.RequsetBefore();
        RetrofitManager.getInstace(HostType.NETEASE_NEWS_VIDEO).getNewsDetail(postId)
                .map(new Func1<Map<String, NewsDetail>, NewsDetail>() {
                    @Override
                    public NewsDetail call(Map<String, NewsDetail> map) {
                        NewsDetail newsDetail = map.get(postId);
                        changeNewsDetail(newsDetail);  //需要调整newsDetail，textview显示网页的文本
                        return newsDetail;
                    }
                })
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<NewsDetail>() {
                    @Override
                    public void onCompleted() {
                    }
                    @Override
                    public void onError(Throwable e) {
                        callBack.RequestError(e.toString());
                    }

                    @Override
                    public void onNext(NewsDetail newsDetail) {
                        callBack.RequestSucc(newsDetail);
                    }
                });
    }
    private void changeNewsDetail(NewsDetail newsDetail) {
        List<NewsDetail.ImgBean> imgSrcs = newsDetail.getImg();
        if (imgSrcs != null && imgSrcs.size() >= 2 && App.isHavePhoto()) {
            String newsBody = newsDetail.getBody();
            newsBody = changeNewsBody(imgSrcs, newsBody);
            newsDetail.setBody(newsBody);
        }
    }
    private String changeNewsBody( List<NewsDetail.ImgBean> imgSrcs,String newsBody)
    {
        for (int i = 0; i < imgSrcs.size(); i++) {
            String oldChars = "<!--IMG#" + i + "-->";
            String newChars;
            if (i == 0) {
                newChars = "";
            } else {
                newChars = "<img src=\"" + imgSrcs.get(i).getSrc() + "\" />";
            }
            newsBody = newsBody.replace(oldChars, newChars);

        }
        return newsBody;
    }
}
