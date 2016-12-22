package com.example.liubiao.mvptest.mvp.ui.activity.base;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.annotation.NavigatInject;
import com.example.liubiao.mvptest.di.component.ActivityComponent;
import com.example.liubiao.mvptest.di.component.DaggerActivityComponent;
import com.example.liubiao.mvptest.di.module.ActivityModule;
import com.example.liubiao.mvptest.mvp.present.base.BasePresentImpl;
import com.example.liubiao.mvptest.mvp.ui.activity.BeautyActivity;
import com.example.liubiao.mvptest.mvp.ui.activity.NewsActivity;
import com.example.liubiao.mvptest.utils.MyUtils;
import com.example.liubiao.mvptext.R;
import com.readystatesoftware.systembartint.SystemBarTintManager;

import butterknife.ButterKnife;
import rx.Subscription;


/**
 * Created by liubiao on 2016/12/2.
 */

public abstract class BaseActivity<T extends BasePresentImpl> extends AppCompatActivity {
    private boolean isShowNavigatView;
    private ActivityComponent activityComponent;
    public  SystemBarTintManager systemBarTintManager;

    public abstract void initInject();

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected T presentImpl;
    private Class curClass;
    public Subscription mSubscription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initAnnotation();//注解
        initActivityComponent();//dagger2初始化component

        setStatusStyle();
        int layoutId = getLayoutId();
        setContentView(layoutId);
        initInject();//注入
        ButterKnife.bind(this);

        initToolBar();
        initView();//初始化view
        if (isShowNavigatView) {
            initDrawLayout();
        }
        /**
         * 1.初始化数据，使用mvp模式解耦，不能直接在v中获取输出
         * 通过使用注解，注解对象中进行数据获取，然后回调（接口）给v中
         * 2.因为需要接口所以在每个v中定义接口view来进行回调。
         *      访问前，访问中，访问完成后。
         */
        if (presentImpl != null) {
            //访问网络或者数据库获取数据
            presentImpl.onCreate();
        }
    }


    private void initAnnotation() {
        /**注解：
         *  1.如果是字段注解
         *      通过字节码对象Class暴力反射获取到字段集合
         *      判断字段是否用注解，获取注解对象
         *      通过注解获取到字段对象的字段值（注解值）
         *      有了字段值（注解值）就可以获取到要求的对象。
         *      然后将对象赋予给字段
         *  2.如果是类，对象注解
         *      通过字节码对象，获取到注解对象
         *      通过注解对象获取到，获取到注解的值
         * */
        boolean annotationPresent = getClass().isAnnotationPresent(NavigatInject.class);
        if (annotationPresent)
            isShowNavigatView = getClass().getAnnotation(NavigatInject.class).value();
    }

    private void initActivityComponent() {
        activityComponent = DaggerActivityComponent.builder()
                .appComponent(((App) getApplication()).getAppComponent())
                .activityModule(new ActivityModule(this))
                .build();
    }


    private void setStatusStyle() {
        systemBarTintManager = new SystemBarTintManager(this);
        systemBarTintManager.setStatusBarTintEnabled(true);
        systemBarTintManager.setStatusBarTintResource(R.color.colorPrimary);
    }

    protected void initToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    private void initDrawLayout() {
        final DrawerLayout drawlay = (DrawerLayout) findViewById(R.id.draw_lay);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawlay, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawlay.addDrawerListener(toggle);
        toggle.syncState();
        //  curClass=this.getClass();
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_news:
                        curClass = NewsActivity.class;
                        break;
                    case R.id.nav_photo:
                        curClass = BeautyActivity.class;
                        break;
                    case R.id.nav_weather:
                        curClass = NewsActivity.class;
                        break;
                }
                drawlay.closeDrawers();
                return false;
            }
        });
        drawlay.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                if (curClass != null) {
                    Intent intent = new Intent(BaseActivity.this, curClass);
                    startActivity(intent);
                }
            }
        });
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(presentImpl!=null)
        {
            presentImpl.destory();
        }
        if(mSubscription!=null)
        {
            MyUtils.dismissSubScript(mSubscription);
        }
    }
}
