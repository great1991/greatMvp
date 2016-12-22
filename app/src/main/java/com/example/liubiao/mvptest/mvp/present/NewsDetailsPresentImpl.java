package com.example.liubiao.mvptest.mvp.present;

import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.mvp.entity.NewsDetail;
import com.example.liubiao.mvptest.mvp.interactor.NewsDtailsInteractor;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;
import com.example.liubiao.mvptest.mvp.view.NewsDetailView;

import javax.inject.Inject;

/**
 * Created by liubiao on 2016/12/19.
 */

public class NewsDetailsPresentImpl extends BasePresentImpl<NewsDetailView,NewsDetail> {
    //访问网络获取数据回传给activity，
    private NewsDtailsInteractor interactor;
    private String postId;
    @Inject
    public NewsDetailsPresentImpl(NewsDtailsInteractor interactor)
    {
        this.interactor=interactor;
    }
    public void setPostId(String postId)
    {
        this.postId=postId;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        loadData();
    }

    public  void loadData() {
       interactor.loadData(postId,this);
    }

    @Override
    public void RequestSucc(NewsDetail data) {
        super.RequestSucc(data);
        mView.onListenerData(data);
    }

}
