package com.example.liubiao.mvptest.mvp.ui.adapter.PagerAdapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.liubiao.mvptest.event.NewsPhotoEvent;
import com.example.liubiao.mvptest.utils.RxBus;
import com.example.liubiao.mvptext.R;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.zip.Inflater;

import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;

/**
 * Created by liubiao on 2016/12/21.
 */
public class NewsPhotoPagerAdapter extends PagerAdapter {

    private List<String> list;
    private Context context;

    public NewsPhotoPagerAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        if (list == null || list.size() == 0)
            return 0;
        return list.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        View view = LayoutInflater.from(context).inflate(R.layout.news_photo, null);

        final PhotoView photoView = (PhotoView) view.findViewById(R.id.photo_view);
        initEvent(photoView);
        final ProgressBar pb = (ProgressBar) view.findViewById(R.id.pb);
        Observable.timer(100, TimeUnit.MILLISECONDS)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onCompleted() {
                        pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        pb.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        pb.setVisibility(View.GONE);
                        Glide.with(context).load(list.get(position)).asBitmap().diskCacheStrategy(DiskCacheStrategy.ALL)
                                .into(photoView);
                    }
                });
        container.addView(view);
        return view;
    }

    private void initEvent(PhotoView photoView) {
        photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {
            @Override
            public void onPhotoTap(View view, float v, float v1) {
                startEvent();
            }
            @Override
            public void onOutsidePhotoTap() {
                startEvent();
            }
        });
    }

    @Override
    public void destroyItem(ViewGroup view, int position, Object object) {
        view.removeView((View) object);
    }

    private void startEvent() {
        RxBus.getRxBusInstace().post(new NewsPhotoEvent());
    }
}
