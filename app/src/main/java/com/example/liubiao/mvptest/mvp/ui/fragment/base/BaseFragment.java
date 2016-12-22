package com.example.liubiao.mvptest.mvp.ui.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.di.component.DaggerFragmentComponent;
import com.example.liubiao.mvptest.di.component.FragmentComponent;
import com.example.liubiao.mvptest.di.module.FragmentModule;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;

import butterknife.ButterKnife;

/**
 * Created by liubiao on 2016/12/2.
 */

public abstract class BaseFragment<T extends BasePresentImpl> extends Fragment {

    private FragmentComponent fragmentComponent;
    public  T presentImpl;

    protected abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initView();

    /**
     * 1.依赖注入
     * 2.布局view.初始化view
     * 3.获取数据data
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initFragmentComponent();
        initInject();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getLayoutId();
        //TODO 为什么添加频道后可以复用view
        View fragmentView = inflater.inflate(getLayoutId(), null);
        ButterKnife.bind(this, fragmentView);
        initView();
        if (presentImpl != null) {
            presentImpl.onCreate();
        }
        return fragmentView;
    }


    private void initFragmentComponent() {
        fragmentComponent = DaggerFragmentComponent.builder().appComponent(App.getAppComponent())
                .fragmentModule(new FragmentModule(this)).build();
    }

    public FragmentComponent getFragmentComponent() {
        return fragmentComponent;
    }

}
