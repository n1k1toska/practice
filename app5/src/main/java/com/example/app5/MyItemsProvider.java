package com.example.app5;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MyItemsProvider extends ContentProvider {

    private static final String AUTHORITY = "com.example.app5.itemsprovider";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/items");

    private static final int ITEMS = 100;
    private static final int ITEM_ID = 101;

    private static final UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        uriMatcher.addURI(AUTHORITY, "items", ITEMS);
        uriMatcher.addURI(AUTHORITY, "items/#", ITEM_ID);
    }

    private ItemsDbHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    public boolean onCreate() {
        dbHelper = new ItemsDbHelper(getContext());
        database = dbHelper.getWritableDatabase();
        return database != null;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        if (database == null) {
            throw new IllegalStateException("Database not initialized");
        }
        return database.query("items", projection, selection, selectionArgs, null, null, sortOrder);
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return "vnd.android.cursor.dir/vnd.practice6.items";
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        if (database == null) {
            throw new IllegalStateException("Database not initialized");
        }

        long id = database.insert("items", null, values);
        if (id > 0) {
            Uri newUri = Uri.parse(CONTENT_URI + "/" + id);
            getContext().getContentResolver().notifyChange(newUri, null);
            return newUri;
        }
        return null;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        if (database == null) {
            throw new IllegalStateException("Database not initialized");
        }
        int count = database.delete("items", selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        if (database == null) {
            throw new IllegalStateException("Database not initialized");
        }
        int count = database.update("items", values, selection, selectionArgs);
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}