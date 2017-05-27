package com.kurekhub.rssfinancialreader.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.kurekhub.rssfinancialreader.RssItem;

public class RssFeederDbHelper extends SQLiteOpenHelper {
    public static final String TAG = "RssFeederDbHelper";

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "rss_feeder";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_PUB_DATE = "pub_date";
    }

    private static RssFeederDbHelper instance;

    public static synchronized RssFeederDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new RssFeederDbHelper(context.getApplicationContext());
        }

        return instance;
    }

    public static final String DATABASE_NAME = "RssFeeder.db";
    public static final int DATABASE_VERSION = 1;

    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT, " +
                    FeedEntry.COLUMN_NAME_LINK + " TEXT, " +
                    FeedEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                    FeedEntry.COLUMN_NAME_PUB_DATE + " TEXT)";


    private RssFeederDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void addItem(RssItem item) {
        SQLiteDatabase db = getWritableDatabase();

        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(FeedEntry.COLUMN_NAME_TITLE, item.getTitle());
            values.put(FeedEntry.COLUMN_NAME_LINK, item.getLink());
            values.put(FeedEntry.COLUMN_NAME_PUB_DATE, item.getPubDate());
            values.put(FeedEntry.COLUMN_NAME_DESCRIPTION, item.getDescription());

            int rows = db.update(FeedEntry.TABLE_NAME, values, FeedEntry.COLUMN_NAME_TITLE + "= ?", new String[]{item.getTitle()});
            if(rows == 0) {
                db.insertOrThrow(FeedEntry.TABLE_NAME, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add item to database");
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}
