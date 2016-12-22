package com.example.liubiao.mvptest.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.Toast;

import com.example.liubiao.mvptest.annotation.NavigatInject;
import com.example.liubiao.mvptest.mvp.entity.BeautyBean;
import com.example.liubiao.mvptest.mvp.present.BeautyPresentImpl;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;
import com.example.liubiao.mvptest.mvp.ui.adapter.BeautyAdapter;
import com.example.liubiao.mvptest.mvp.view.BeautyView;
import com.example.liubiao.mvptext.R;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by liubiao on 2016/12/21.
 */
@NavigatInject(true)
public class BeautyActivity extends BaseActivity implements BeautyView {
    @Inject
    BeautyPresentImpl present;
    @Inject
    BeautyAdapter adapter;
    @Inject
    Activity activity;
    @BindView(R.id.recycyle_view)
    RecyclerView mRecycleV;
    @BindView(R.id.swipe_container)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.draw_lay)
    DrawerLayout drawLay;
    private StaggeredGridLayoutManager layoutManager;
    private boolean isRefresh = true;

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_beauty;
    }

    @Override
    protected void initView() {
        setViewProperty();
        initEvent();
        presentImpl = present;
        presentImpl.attchView(this);
        mRecycleV.setAdapter(adapter);
    }


    private void setViewProperty() {
        mSwipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.holo_orange_light,
                android.R.color.holo_green_light);
        layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        mRecycleV.setLayoutManager(layoutManager);
        mRecycleV.setItemAnimator(new DefaultItemAnimator());
        mRecycleV.setHasFixedSize(true);
    }

    private void initEvent() {
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isRefresh = true;
                present.loadData(isRefresh);
            }
        });
        mRecycleV.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int[] lastVisibleItemPositions = layoutManager.findLastVisibleItemPositions(null);
                int allCount = layoutManager.getItemCount();
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPositions[1] == allCount-1) {
                    isRefresh = false;
                    adapter.showFootView();
                    present.loadData(isRefresh);
                }
            }
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
        });
        adapter.setOnItemClickListern(new BeautyAdapter.OnItemClickListern() {
            @Override
            public void onItemClick(View v, int position) {

                Intent intent=new Intent(activity,BeautyDetailActivity.class);
                intent.putExtra("url",adapter.getList().get(position).getUrl());
                startActivity(intent);
            }
        });
    }

    @Override
    public void onListenerData(List<BeautyBean.ResultsEntity> data) {
        if (isRefresh) {
            if (data != null || data.size() != 0) {
                adapter.setList(data);
                adapter.notifyDataSetChanged();
            }
            mSwipeRefreshLayout.setRefreshing(false);
        }
        else {
            if (data != null || data.size() != 0) {
                adapter.addAllItem(data);
            }
            adapter.hideFootView();
        }
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
