package com.example.liubiao.mvptest.widget;


import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Html;
import android.widget.TextView;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.common.HostType;
import com.example.liubiao.mvptest.repository.net.RetrofitManager;
import com.example.liubiao.mvptext.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by liubiao on 2016/12/12.
 */
public class URLImageGetter implements Html.ImageGetter {
    private TextView mTextView;
    private int mPicWidth;
    private String mNewsBody;
    private int mPicCount;
    private int mPicTotal;
    private static final String mFilePath = App.getAppContext().getCacheDir().getAbsolutePath();
    public Subscription mSubscription;

    public URLImageGetter(TextView textView, String newsBody, int picTotal) {
        mTextView = textView;
        mPicWidth = mTextView.getWidth();
        mNewsBody = newsBody;
        mPicTotal = picTotal;
    }

    @Override
    public Drawable getDrawable(final String source) {
        Drawable drawable;
        File file = new File(mFilePath, source.hashCode() + "");
        if (file.exists()) {
            mPicCount++;
            drawable = getDrawableFromDisk(file);
        } else {
            drawable = getDrawableFromNet(source);
        }
        return drawable;
    }

    @Nullable
    private Drawable getDrawableFromDisk(File file) {
        Drawable drawable = Drawable.createFromPath(file.getAbsolutePath());
        if (drawable != null) {
            int picHeight = calculatePicHeight(drawable);
            drawable.setBounds(0, 0, mPicWidth, picHeight);
        }
        return drawable;
    }

    private int calculatePicHeight(Drawable drawable) {
        float imgWidth = drawable.getIntrinsicWidth();
        float imgHeight = drawable.getIntrinsicHeight();
        float rate = imgHeight / imgWidth;
        return (int) (mPicWidth * rate);
    }

    @NonNull
    private Drawable getDrawableFromNet(final String source) {
        mSubscription = RetrofitManager.getInstace(HostType.NEWS_DETAIL_HTML_PHOTO)
                .getNewsBodyHtmlPhoto(source)
                .unsubscribeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Func1<ResponseBody, Boolean>() {
                    @Override
                    public Boolean call(ResponseBody response) {
                        return WritePicToDisk(response, source);
                    }
                }).subscribe(new Subscriber<Boolean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(Boolean isLoadSuccess) {

                        mPicCount++;
                        if (isLoadSuccess && (mPicCount == mPicTotal - 1)) {
                            mTextView.setText(Html.fromHtml(mNewsBody, URLImageGetter.this, null));
                        }
                    }
                });

        return createPicPlaceholder();
    }

    @NonNull
    private Boolean WritePicToDisk(ResponseBody response, String source) {
        File file = new File(mFilePath, source.hashCode() + "");
        InputStream in = null;
        FileOutputStream out = null;
        try {
            in = response.byteStream();
            out = new FileOutputStream(file);
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
            return true;
        } catch (Exception e) {

            return false;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {

            }
        }
    }

    @SuppressWarnings("deprecation")
    @NonNull
    private Drawable createPicPlaceholder() {
        Drawable drawable;
        drawable = new ColorDrawable(App.getAppContext().getResources().getColor(R.color.day));
        drawable.setBounds(0, 0, mPicWidth, mPicWidth / 3);
        return drawable;
    }

}