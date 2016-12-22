package com.example.liubiao.mvptest.event;

/**
 * Created by liubiao on 2016/12/13.
 */
public class NewsAddDBEvent {
    private int fromPosition;
    private int toPosition;

    public NewsAddDBEvent(int fromPosition, int toPosition) {
        this.toPosition=toPosition;
        this.fromPosition=fromPosition;
    }
    public int getFromPosition()
    {
        return fromPosition;
    }
    public int getToPosition()
    {
        return toPosition;
    }
}
