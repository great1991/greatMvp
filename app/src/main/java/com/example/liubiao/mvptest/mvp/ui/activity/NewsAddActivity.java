package com.example.liubiao.mvptest.mvp.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.liubiao.mvptest.di.component.ActivityComponent;
import com.example.liubiao.mvptest.event.NewsAddDBEvent;
import com.example.liubiao.mvptest.mvp.present.NewsAddChannelImpl;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptest.mvp.ui.adapter.NewsAddAdapter;
import com.example.liubiao.mvptest.mvp.view.NewsAddView;
import com.example.liubiao.mvptest.repository.db.NewsChannelManager;
import com.example.liubiao.mvptest.utils.RxBus;
import com.example.liubiao.mvptest.widget.ItemDragHelperCallback;
import com.example.liubiao.mvptext.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.mvp.greendao.NewsChannelTable;
import rx.Subscriber;
import rx.functions.Action1;

/**
 * Created by liubiao on 2016/12/12.
 */

public class NewsAddActivity extends BaseActivity<NewsAddChannelImpl> implements NewsAddView {
    @BindView(R.id.rv_my_channel)
    RecyclerView rvMineChannel;
    @BindView(R.id.rv_more_channel)
    RecyclerView rvMoreChannel;
    private List<NewsChannelTable> mineList = new ArrayList<>();
    private List<NewsChannelTable> moreList = new ArrayList<>();
    @Inject
    NewsAddChannelImpl addChannelImpl;
    @Inject
    NewsAddAdapter mineAdapter;
    @Inject
    NewsAddAdapter moreAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        RxBus.getRxBusInstace().toObservable(NewsAddDBEvent.class).subscribe(new Action1<NewsAddDBEvent>() {
            @Override
            public void call(NewsAddDBEvent newsAddDBEvent) {
                presentImpl.doDb(newsAddDBEvent.getFromPosition(), newsAddDBEvent.getToPosition());
            }
        });
    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_add_news;
    }

    @Override
    protected void initView() {
        presentImpl = addChannelImpl;
        presentImpl.attchView(this);
        setRecycleView();
        initEvent();
    }


    private void setRecycleView() {
        rvMineChannel.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        rvMoreChannel.setLayoutManager(new GridLayoutManager(this, 4, GridLayoutManager.VERTICAL, false));
        rvMineChannel.setAdapter(mineAdapter);
        rvMoreChannel.setAdapter(moreAdapter);
    }

    private void initEvent() {
        //设置点击事件
        mineAdapter.setOnItemclickListener(new NewsAddAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (mineAdapter.getList().size() != 0) {
                    NewsChannelTable newsChannelTable = mineAdapter.getList().get(position);
                    mineAdapter.removeItem(position);
                    moreAdapter.addItem(newsChannelTable);
                    //修改数据
                    presentImpl.doDb(newsChannelTable, true);
                }
            }
        });
        moreAdapter.setOnItemclickListener(new NewsAddAdapter.ItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                if (moreAdapter.getList().size() != 0) {
                    NewsChannelTable newsChannelTable = moreAdapter.getList().get(position);
                    mineAdapter.addItem(newsChannelTable);
                    moreAdapter.removeItem(position);
                    //修改数据库
                    presentImpl.doDb(newsChannelTable, false);
                }
            }
        });
        //设置拖动排序
        ItemDragHelperCallback itemDragHelperCallback = new ItemDragHelperCallback(mineAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragHelperCallback);
        itemTouchHelper.attachToRecyclerView(rvMineChannel);
        mineAdapter.setOnItemDragHelperCallback(itemDragHelperCallback);
    }

    @Override
    public void onLoadDatas(List<NewsChannelTable> list) {
        if (list.size() == 0)
            return;
        mineList.clear();
        moreList.clear();
        for (NewsChannelTable news : list) {
            if (news.getNewsChannelSelect()) {
                mineList.add(news);
            } else {
                moreList.add(news);
            }
        }
        loadDataForAdapter(mineAdapter, mineList);
        loadDataForAdapter(moreAdapter, moreList);

    }

    private void loadDataForAdapter(NewsAddAdapter adapter, List<NewsChannelTable> list) {
        adapter.setList(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showProgress() {

    }

    @Override
    public void hideProgress() {

    }

    @Override
    public void showMessage(String string) {

    }


}
