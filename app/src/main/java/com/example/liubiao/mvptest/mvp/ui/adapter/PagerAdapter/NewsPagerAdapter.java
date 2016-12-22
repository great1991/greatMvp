package com.example.liubiao.mvptest.mvp.ui.adapter.PagerAdapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;

import com.example.liubiao.mvptest.mvp.ui.fragment.base.BaseFragment;

import java.util.List;

/**
 * Created by liubiao on 2016/12/6.
 */

public class NewsPagerAdapter extends FragmentPagerAdapter {

    private final List<BaseFragment> fragmentLists;
    private List<String> titleList;

    public NewsPagerAdapter(FragmentManager supportFragmentManager, List<BaseFragment> fragmentLists,
                           List<String> titieLists) {
        super(supportFragmentManager);
        this.fragmentLists = fragmentLists;
        this.titleList = titieLists;
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentLists.get(position);
    }

    @Override
    public int getCount() {
        return fragmentLists.size();
    }
}
