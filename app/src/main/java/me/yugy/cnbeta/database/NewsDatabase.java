package me.yugy.cnbeta.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import java.util.List;

import me.yugy.app.common.database.Column.Constraint;
import me.yugy.app.common.database.Column.DataType;
import me.yugy.app.common.database.SQLiteTable;
import me.yugy.app.common.network.GsonHelper;
import me.yugy.cnbeta.Application;
import me.yugy.cnbeta.model.News;

public class NewsDatabase {

    private static final String DB_NAME = "news";
    private static final int DB_VERSION = 1;
    private static final String TB_LIST = "list";

    private static class NewsListColumn implements BaseColumns {
        public static final String COLUMN_COMMENT_COUNT = "comment_count";
        public static final String COLUMN_VIEW_COUNT = "view_count";
        public static final String COLUMN_TIMESTAMP = "timestamp";
        public static final String COLUMN_SID = "sid";
        public static final String COLUMN_THUMB = "thumb";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_TOPIC_ID = "topic_id";
        public static final String COLUMN_TOPIC_LOGO = "topic_logo";
        public static final String COLUMN_READED = "readed";
        public static final String COLUMN_JSON = "json";
    }

    private static final SQLiteTable TABLE_LIST = new SQLiteTable(TB_LIST)
            .addColumn(NewsListColumn.COLUMN_COMMENT_COUNT, DataType.INTEGER)
            .addColumn(NewsListColumn.COLUMN_VIEW_COUNT, DataType.INTEGER)
            .addColumn(NewsListColumn.COLUMN_TIMESTAMP, DataType.INTEGER)
            .addColumn(NewsListColumn.COLUMN_SID, Constraint.UNIQUE, DataType.INTEGER)
            .addColumn(NewsListColumn.COLUMN_THUMB, DataType.TEXT)
            .addColumn(NewsListColumn.COLUMN_TITLE, DataType.TEXT)
            .addColumn(NewsListColumn.COLUMN_TOPIC_ID, DataType.INTEGER)
            .addColumn(NewsListColumn.COLUMN_TOPIC_LOGO, DataType.TEXT)
            .addColumn(NewsListColumn.COLUMN_READED, DataType.INTEGER)
            .addColumn(NewsListColumn.COLUMN_JSON, DataType.TEXT);

    private static NewsDatabase sInstance;

    public static NewsDatabase getInstance() {
        if (sInstance == null) {
            synchronized (NewsDatabase.class) {
                if (sInstance == null) {
                    sInstance = new NewsDatabase();
                }
            }
        }
        return sInstance;
    }

    private SQLiteDatabase mDatabase;
    private GsonHelper mGsonHelper;

    private NewsDatabase() {
        mDatabase = new DatabaseHelper(Application.getContext()).getWritableDatabase();
        mGsonHelper = new GsonHelper();
    }

    public void saveNewsList(List<News> newsList) {
        synchronized (NewsDatabase.class) {
            mDatabase.beginTransaction();
            try {
                for (News news : newsList) {
                    ContentValues values = new ContentValues();
                    values.put(NewsListColumn.COLUMN_COMMENT_COUNT, news.commentCount);
                    values.put(NewsListColumn.COLUMN_VIEW_COUNT, news.viewCount);
                    values.put(NewsListColumn.COLUMN_TIMESTAMP, news.timestamp);
                    values.put(NewsListColumn.COLUMN_SID, news.sid);
                    values.put(NewsListColumn.COLUMN_THUMB, news.thumb);
                    values.put(NewsListColumn.COLUMN_TITLE, news.title);
                    values.put(NewsListColumn.COLUMN_TOPIC_ID, news.topicId);
                    values.put(NewsListColumn.COLUMN_TOPIC_LOGO, news.topicLogo);
                    values.put(NewsListColumn.COLUMN_READED, news.readed);
                    values.put(NewsListColumn.COLUMN_JSON, mGsonHelper.dump(news));
                    mDatabase.insertWithOnConflict(TB_LIST, null, values, SQLiteDatabase.CONFLICT_REPLACE);
                }
                mDatabase.setTransactionSuccessful();
                mDatabase.endTransaction();
                return;
            } catch (Exception e) {
                mDatabase.endTransaction();
                e.printStackTrace();
            }
            throw new SQLException("Fail to insert news list.");
        }
    }

    public class DatabaseHelper extends SQLiteOpenHelper {

        public DatabaseHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            TABLE_LIST.create(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
}
