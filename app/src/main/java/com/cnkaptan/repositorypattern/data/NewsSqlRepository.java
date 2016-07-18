package com.cnkaptan.repositorypattern.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cihankaptan on 18/07/16.
 */
public class NewsSqlRepository implements Repostiory<News> {

    private final SQLiteOpenHelper openHelper;
    private final Mapper<News, ContentValues> toContentValuesMapper;
    private final Mapper<Cursor, News> toNewsMapper;

    public NewsSqlRepository(SQLiteOpenHelper openHelper) {
        this.openHelper = openHelper;
        this.toContentValuesMapper = new NewsToContentValuesMapper();
        this.toNewsMapper = new CursorToNewsMapper();
    }

    @Override
    public void add(News item) {
        add(Collections.singletonList(item).iterator());
    }

    @Override
    public void add(Iterator<News> items) {
        final SQLiteDatabase database = openHelper.getWritableDatabase();
        database.beginTransaction();

        try {

            while(items.hasNext()){
                final ContentValues contentValues = toContentValuesMapper.map(items.next());
                database.insert(NewsTable.TABLE_NAME, null, contentValues);
            }


            database.setTransactionSuccessful();
        } finally {
            database.endTransaction();
            database.close();
        }
    }

    @Override
    public void update(News item) {

    }

    @Override
    public void remove(News item) {

    }

    @Override
    public void remove(Specification specification) {

    }

    @Override
    public List<News> query(Specification specification) {
        final SqlSpecification sqlSpecification = (SqlSpecification) specification;

        final SQLiteDatabase database = openHelper.getReadableDatabase();
        final List<News> newses = new ArrayList<>();

        try {
            final Cursor cursor = database.rawQuery(sqlSpecification.toSqlQuery(), new String[]{});

            for (int i = 0, size = cursor.getCount(); i < size; i++) {
                cursor.moveToPosition(i);

                newses.add(toNewsMapper.map(cursor));
            }

            cursor.close();

            return newses;
        } finally {
            database.close();
        }
    }
}
