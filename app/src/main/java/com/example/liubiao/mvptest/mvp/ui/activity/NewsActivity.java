package com.example.liubiao.mvptest.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.liubiao.mvptest.annotation.NavigatInject;
import com.example.liubiao.mvptest.common.Constant;

import com.example.liubiao.mvptest.event.FabTopEvent;
import com.example.liubiao.mvptest.event.NewsRefreshChannelEvent;
import com.example.liubiao.mvptest.mvp.present.NewsPresentImpl;
import com.example.liubiao.mvptest.mvp.ui.activity.base.BaseActivity;

import com.example.liubiao.mvptest.mvp.ui.adapter.PagerAdapter.NewsPagerAdapter;
import com.example.liubiao.mvptest.mvp.ui.fragment.NewsListFragment;
import com.example.liubiao.mvptest.mvp.ui.fragment.base.BaseFragment;
import com.example.liubiao.mvptest.mvp.view.NewsView;
import com.example.liubiao.mvptest.utils.MyUtils;
import com.example.liubiao.mvptest.utils.RxBus;
import com.example.liubiao.mvptext.R;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import me.mvp.greendao.NewsChannelTable;
import rx.functions.Action1;


/**
 * Created by liubiao on 2016/12/2.
 */
@NavigatInject(true)
public class NewsActivity extends BaseActivity<NewsPresentImpl> implements NewsView, ViewPager.OnPageChangeListener {

    /**
     * 获取数据
     * 解耦，通过presentimpl获取数据，
     * 数据需要回调回来，需要定义接口
     */
    @Inject
    NewsPresentImpl newsListPresent;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tabs)
    TabLayout tabs;
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.draw_lay)
    DrawerLayout drawLay;
    @BindView(R.id.add_channel_iv)
    ImageView addChannelIv;
    @BindView(R.id.fab)
    FloatingActionButton fab;
    private List<BaseFragment> fragmentLists = new ArrayList<>();
    private List<String> titieLists = new ArrayList<>();
    private NewsPagerAdapter adapter;
    private int currentItem = 0;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //使用rxBus接受事件,刷新频道
        RxBus.getRxBusInstace().toObservable(NewsRefreshChannelEvent.class).subscribe(new Action1<NewsRefreshChannelEvent>() {
            @Override
            public void call(NewsRefreshChannelEvent newsRefreshChannelEvent) {
                newsListPresent.provideNewsData();
            }
        });

    }

    @Override
    public void initInject() {
        getActivityComponent().inject(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_news;
    }

    @Override
    protected void initView() {
        presentImpl = newsListPresent;
        presentImpl.attchView(this);
    }

    //回调获取到数据
    @Override
    public void onListernData(List<NewsChannelTable> data) {
        if (data == null) {
            Toast.makeText(this, "没数据", Toast.LENGTH_SHORT).show();
            return;
        } else {
            initFragment(data);
            initViewpager();
        }
    }

    private void initFragment(List<NewsChannelTable> data) {
        fragmentLists.clear();
        titieLists.clear();
        for (NewsChannelTable newsChannel : data) {
            NewsListFragment fragment = new NewsListFragment();
            Bundle bundle = new Bundle();
            bundle.putString(Constant.NEWS_ID, newsChannel.getNewsChannelId());
            bundle.putString(Constant.NEWS_TYPE, newsChannel.getNewsChannelType());
            bundle.putInt(Constant.CHANNEL_POSITION, newsChannel.getNewsChannelIndex());
            fragment.setArguments(bundle);
            fragmentLists.add(fragment);
            titieLists.add(newsChannel.getNewsChannelName());
        }
    }

    private void initViewpager() {
        if (adapter == null) {
            adapter = new NewsPagerAdapter(getSupportFragmentManager(), fragmentLists, titieLists);
            viewPager.setAdapter(adapter);
        } else {
            adapter.notifyDataSetChanged();
        }
        viewPager.setCurrentItem(currentItem);
        tabs.setupWithViewPager(viewPager);
        //设置tab模式
        MyUtils.setTabsMode(this, tabs);
        viewPager.addOnPageChangeListener(this);
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

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        currentItem = position;
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @OnClick({R.id.add_channel_iv, R.id.fab})
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.add_channel_iv:
                Toast.makeText(this,"跳转到更多界面",Toast.LENGTH_SHORT).show();
                intent= new Intent(this,NewsAddActivity.class);
                startActivity(intent);
            case R.id.fab:
                Toast.makeText(this,"跳转顶部",Toast.LENGTH_SHORT).show();
                RxBus.getRxBusInstace().post(new FabTopEvent());

                break;
        }
    }
}
