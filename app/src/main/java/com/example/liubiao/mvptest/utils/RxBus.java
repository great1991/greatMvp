package com.example.liubiao.mvptest.utils;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;

/**
 * Created by liubiao on 2016/12/6.
 */

public class RxBus {

    private final SerializedSubject<Object, Object> mBus;
    public static RxBus bus;

    private RxBus()
    {
        mBus = new SerializedSubject<>(PublishSubject.create());
    }
    public static RxBus getRxBusInstace()
    {

        if(bus==null)
        {
            synchronized (RxBus.class)
            {
                if(bus==null)
                {
                    bus = new RxBus();
                }
            }
        }
        return bus;
    }
    // 提供了一个新的事件
    public void post(Object o) {
        mBus.onNext(o);
    }
    // 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
    public <T> Observable<T> toObservable(Class<T> eventType) {
        return mBus.ofType(eventType);
    }

}
