package com.example.liubiao.mvptest.repository.net;

import android.os.Environment;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.common.ApiConstants;
import com.example.liubiao.mvptest.common.HostType;
import com.example.liubiao.mvptest.mvp.entity.BeautyBean;
import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.mvp.entity.NewsDetail;
import com.example.liubiao.mvptest.utils.MyUtils;
import com.example.liubiao.mvptest.utils.NetUtils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;

import static android.R.attr.id;
import static android.R.attr.path;
import static android.R.attr.type;

/**
 * Created by liubiao on 2016/12/7.
 */

public class RetrofitManager {

    private static Map<Integer, RetrofitManager> map = new HashMap<>();
    private static OkHttpClient okHttpClient;
    private final ApiService apiService;
    private static String MaxStale = "only-if-cached, max-stale=60 * 60 * 24 * 2";
    private static String MaxAge = "max-age=0";

    /**
     * 根据type创建对应的retrofitManager.
     * okhttpclient单例模式
     */
    private RetrofitManager(int hostType) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ApiConstants.getHost(hostType)).
                client(getOkhttpClient()).
                addCallAdapterFactory(RxJavaCallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        apiService = retrofit.create(ApiService.class);

    }

    private OkHttpClient getOkhttpClient() {
        if (okHttpClient == null) {
            synchronized (RetrofitManager.class) {
                if (okHttpClient == null) {
                    //开启缓存，设置缓存的文件及大小
                    Cache cache = new Cache(new File(Environment.getExternalStorageDirectory(), "respone"), 10 * 1024 * 1024);
                    okHttpClient = new OkHttpClient.Builder()
                            .cache(cache)
                            //添加缓存拦截器
                            .addNetworkInterceptor(getInterceptor())
                            .addInterceptor(getInterceptor())
                            .build();
                    return okHttpClient;
                }
            }
        }
        return okHttpClient;
    }

    //拦截器设置缓存策略
    private Interceptor getInterceptor() {
        Interceptor interceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!NetUtils.isNetworkReachable()) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (NetUtils.isNetworkReachable()) {
                    String cacheControl = request.cacheControl().toString();
                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", cacheControl)
                            .build();
                } else {

                    response.newBuilder()
                            .removeHeader("Pragma")
                            .header("Cache-Control", MaxStale)
                            .build();
                }
                return response;
            }
        };
        return interceptor;
    }

    public static RetrofitManager getInstace(int hostType) {
        if (map.get(hostType) == null) {
            RetrofitManager retrofitManager = new RetrofitManager(hostType);
            map.put(hostType, retrofitManager);
            return retrofitManager;
        } else {
            return map.get(hostType);
        }
    }

    public static String getCacheControl() {
        if (NetUtils.isNetworkReachable()) {
            return MaxAge;
        }
        return MaxStale;
    }


    /**
     * 网络获取数据的接口
     */
    public Observable<Map<String,List<NewsBean>>> getNewsListData(String id, String type, int page) {
        return apiService.getNewsListData(getCacheControl(), type, id, page);
    }
    public Observable<Map<String,NewsDetail>> getNewsDetail(String postId)
    {
        return apiService.getNewsDetails(getCacheControl(),postId);
    }
    public Observable<ResponseBody> getNewsBodyHtmlPhoto(String photoPath) {
        return apiService.getNewsBodyHtmlPhoto(photoPath);
    }
    public Observable<BeautyBean> getBeautyListData(int size, int page) {
        return apiService.getBeautyListData(getCacheControl(), size, page);
    }


}
