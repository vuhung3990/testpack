package com.example.hungvu.testpack;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;

/**
 * Created by hung on 1/17/16.
 */
public class ContentProviderSample extends ContentProvider {

    private Database db;
    private static UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    private static final int CODE_DATA = 11;
    private static final int CODE_DATAS = 12;

    private static final String BASE_PATH = "data";
    private static final String AUTHORITY = "com.example.hungvu.testpack.provider";
    public static Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/" + BASE_PATH);

    static {
        sUriMatcher.addURI(AUTHORITY, BASE_PATH, CODE_DATA);
        sUriMatcher.addURI(AUTHORITY, BASE_PATH + "/#", CODE_DATAS);
    }

    @Override
    public boolean onCreate() {
        // init db
        db = new Database(getContext(), "dbname", null, 1);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        try {
            Thread.sleep(5000);
            SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
            queryBuilder.setTables(Database.tableName);
            if (sUriMatcher.match(uri) == CODE_DATAS) {
                long id = ContentUris.parseId(uri);
                queryBuilder.appendWhere("_id=" + id);
            }
            return queryBuilder.query(db.getWritableDatabase(), projection, selection, selectionArgs, null, null, sortOrder);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        long rowId = sqlite.insert(Database.tableName, null, values);
        return ContentUris.withAppendedId(CONTENT_URI, rowId);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String whereClause = null;
        if (sUriMatcher.match(uri) == CODE_DATAS) {
            whereClause = "_id=" + ContentUris.parseId(uri);
        }
        return sqlite.delete(Database.tableName, whereClause, null);
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        SQLiteDatabase sqlite = db.getWritableDatabase();
        String whereClause = null;
        if (sUriMatcher.match(uri) == CODE_DATAS) {
            whereClause = "_id=" + ContentUris.parseId(uri);
        }
        return sqlite.update(Database.tableName, values, whereClause, null);
    }
}
