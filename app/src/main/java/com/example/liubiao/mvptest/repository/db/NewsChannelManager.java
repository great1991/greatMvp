package com.example.liubiao.mvptest.repository.db;

import com.example.liubiao.mvptest.App;
import com.example.liubiao.mvptest.common.ApiConstants;
import com.example.liubiao.mvptest.common.Constant;
import com.example.liubiao.mvptest.utils.MyUtils;
import com.example.liubiao.mvptext.R;

import java.util.List;

import de.greenrobot.dao.Property;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.QueryBuilder;
import me.mvp.greendao.NewsChannelTable;
import me.mvp.greendao.NewsChannelTableDao;

/**
 * Created by liubiao on 2016/12/6.
 */
//数据库工具
public class NewsChannelManager {

    public static void initDB() {
        if (MyUtils.getSharePreference().getBoolean(Constant.First_Open, true)) {
            NewsChannelTableDao tableDao = App.getTableDao();
            String[] newsName = App.getAppContext().getResources().getStringArray(R.array.news_channel_name);
            String[] newsId = App.getAppContext().getResources().getStringArray(R.array.news_channel_id);
            for (int i = 0; i < newsId.length; i++) {
                //名字，id,类型，是否加载，索引，默认显示那个
                NewsChannelTable entity = new NewsChannelTable(newsName[i], newsId[i], ApiConstants.getType(newsId[i]), i < 5, i, i == 0);
                tableDao.insert(entity);
            }
            MyUtils.getSharePreference().edit().putBoolean(Constant.First_Open, false).commit();
        }
    }

    //获取已加载的频道
    public static List<NewsChannelTable> getSelectedChannel() {
        Query<NewsChannelTable> query = App.getTableDao().queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true))
                .orderAsc(NewsChannelTableDao.Properties.NewsChannelIndex).build();
        List<NewsChannelTable> list = query.list();
        return list;
    }

    //获取更多
    public static List<NewsChannelTable> getMoreChannel() {
        Query<NewsChannelTable> query = App.getTableDao().queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(false))
                .orderAsc(NewsChannelTableDao.Properties.NewsChannelId).build();
        return query.list();
    }

    //获取指定索引的频道对象
    public static NewsChannelTable getIndexChannel(int index) {
        return App.getTableDao().queryBuilder().where(NewsChannelTableDao.Properties.NewsChannelIndex.eq(index)).unique();
    }

    //数据库更新
    public static void update(NewsChannelTable newsChannelTable) {
        App.getTableDao().update(newsChannelTable);
    }

    //获取指定索引范围的频道list
    public static List<NewsChannelTable> loadNewsChannelsWithin(int from, int to) {
        Query<NewsChannelTable> newsChannelTableQuery = App.getTableDao().queryBuilder()
                .where(NewsChannelTableDao.Properties.NewsChannelIndex
                        .between(from, to)).build();
        return newsChannelTableQuery.list();
    }

    //获取大于指定索引值得频道list
    public static List<NewsChannelTable> loadNewsChannelsIndexGt(int channelIndex) {
        Query<NewsChannelTable> newsChannelTableQuery = App.getTableDao().queryBuilder()
                .where(NewsChannelTableDao.Properties.NewsChannelIndex
                        .gt(channelIndex)).build();
        return newsChannelTableQuery.list();
    }

    //获取小于等于指定索引值的频道list
    public static List<NewsChannelTable> loadNewsChannelsIndexLtAndIsUnselect(int channelIndex) {
        Query<NewsChannelTable> newsChannelTableQuery = App.getTableDao().queryBuilder()
                .where(NewsChannelTableDao.Properties.NewsChannelIndex.ge(channelIndex)
                        , NewsChannelTableDao.Properties.NewsChannelSelect.eq(false)).build();
        return newsChannelTableQuery.list();
    }

    //获取表中总条目数
    public static int getAllSize() {
        return App.getTableDao().loadAll().size();
    }

    //获取表中可显示的条目数
    public static int getNewsChannelSelectSize() {
        return (int) App.getTableDao().queryBuilder()
                .where(NewsChannelTableDao.Properties.NewsChannelSelect.eq(true))
                .buildCount().count();
    }

}
