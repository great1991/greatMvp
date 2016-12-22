package com.example.liubiao.mvptest.repository.net;

import com.example.liubiao.mvptest.mvp.entity.BeautyBean;
import com.example.liubiao.mvptest.mvp.entity.NewsBean;
import com.example.liubiao.mvptest.mvp.entity.NewsDetail;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import retrofit2.http.Url;
import rx.Observable;

/**
 * Created by liubiao on 2016/12/8.
 */

public interface ApiService {
    /**
     * retrofit的网络接口
     */

    @GET("nc/article/{type}/{id}/{startPage}-20.html")
    Observable<Map<String, List<NewsBean>>> getNewsListData(
            @Header("Cache-Control") String cache_control,
            @Path("type") String type, @Path("id") String id, @Path("startPage") int startPage
    );

    // PHOT23PT9000100A
    @GET("nc/article/{postId}/full.html")
    Observable<Map<String, NewsDetail>> getNewsDetails(
            @Header("Cache-Control") String cache_control,
            @Path("postId") String postId
    );

    @GET
    Observable<ResponseBody> getNewsBodyHtmlPhoto(
            @Url String photoPath);

    @GET("data/福利/{size}/{page}")
    Observable<BeautyBean> getBeautyListData(
            @Header("Cache-Control") String cache_control,
            @Path("size") int  postId,
            @Path("page") int page
    );
}
