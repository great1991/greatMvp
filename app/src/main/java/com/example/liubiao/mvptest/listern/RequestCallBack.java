package com.example.liubiao.mvptest.listern;

/**
 * Created by liubiao on 2016/12/5.
 */

public interface RequestCallBack <E> {
    void RequsetBefore();
    void RequestSucc(E data);
    void RequestError(String str);
}
