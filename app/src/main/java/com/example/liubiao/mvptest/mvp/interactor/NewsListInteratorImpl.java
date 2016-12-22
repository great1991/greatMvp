package com.example.liubiao.mvptest.mvp.interactor;

import com.example.liubiao.mvptest.common.ApiConstants;
import com.example.liubiao.mvptest.common.HostType;
import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.repository.net.RetrofitManager;
import com.example.liubiao.mvptest.utils.MyUtils;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.security.auth.callback.Callback;

import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;

/**
 * Created by liubiao on 2016/12/7.
 */

public class NewsListInteratorImpl {
    @Inject
    public NewsListInteratorImpl() {
    }

    public void loadNewsListData(final RequestCallBack callback, final String newsId, String newsType, int page) {
        RetrofitManager.getInstace(HostType.NETEASE_NEWS_VIDEO)
                .getNewsListData(newsId, newsType, page)
                .flatMap(new Func1<Map<String, List<NewsBean>>, Observable<NewsBean>>() {
                    @Override
                    public Observable<NewsBean> call(Map<String, List<NewsBean>> map) {
                        if (newsId.endsWith(ApiConstants.HOUSE_ID)) {
                            return Observable.from(map.get("北京"));
                        }
                        return Observable.from(map.get(newsId));
                    }

                }).map(new Func1<NewsBean, NewsBean>() {
            @Override
            public NewsBean call(NewsBean newsBean) {
                String ptime = newsBean.getPtime();
                String time = MyUtils.formatTime(ptime);
                newsBean.setPtime(time);
                return newsBean;
            }
        }).toSortedList(new Func2<NewsBean, NewsBean, Integer>() {
            @Override
            public Integer call(NewsBean newsBean, NewsBean newsBean2) {

                return newsBean2.getPtime().compareTo(newsBean.getPtime());
            }
        })
               // .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
             //   .subscribeOn(Schedulers.io())
              //  .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<NewsBean>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        callback.RequestError("无法获取数据");
                    }

                    @Override
                    public void onNext(List<NewsBean> newsBeen) {
                        callback.RequestSucc(newsBeen);
                    }
                });
    }


}
