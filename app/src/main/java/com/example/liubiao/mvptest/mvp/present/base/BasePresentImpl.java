package com.example.liubiao.mvptest.mvp.present.base;

import com.example.liubiao.mvptest.listern.RequestCallBack;
import com.example.liubiao.mvptest.mvp.view.base.BaseView;

/**
 * Created by liubiao on 2016/12/5.
 */
//进行获取数据.并且要将数据回调给view
public class BasePresentImpl<T extends BaseView,E> implements RequestCallBack<E> {
    public T mView;
     public void onCreate() {}         //进行网络访问
    public void attchView(BaseView view)
    {
        //用于接口回调给v的。
        this.mView= (T) view;
    }
    public void destory() {

    }
    @Override
    public void RequsetBefore() {
        mView.showProgress();
    }

    @Override
    public void RequestSucc( E data) {
        mView.hideProgress();
    }

    @Override
    public void RequestError(String str) {
        mView.hideProgress();
        mView.showMessage(str);
    }
}
