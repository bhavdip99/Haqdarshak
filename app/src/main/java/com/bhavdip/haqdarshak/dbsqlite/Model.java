package com.bhavdip.haqdarshak.dbsqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Abstract class for a sample Model related to a table in a SQLite Database.
 *
 * @author Aakash Kejriwal
 */
public abstract class Model {

    protected SQLiteDatabase db;
    private Context context;

    /**
     * Default constructor
     */
    public Model(Context ctx) {
        context = ctx;
    }

    /**
     * Get a readable SQLiteDatabase object from the DBHelper singleton
     *
     * @return readable SQLiteDatabase instance
     */
    protected SQLiteDatabase getReadableDB() {
        return DBHelper.getInstance(context).getReadableDatabase();
    }

    /**
     * Get a writable SQLiteDatabase object from the DBHelper singleton
     *
     * @return writable SQLiteDatabase instance
     */
    protected SQLiteDatabase getWritableDB() {
        return DBHelper.getInstance(context).getWritableDatabase();

    }

    /**
     * Returns the context
     *
     * @return Context context
     */
    public Context getContext() {
        return context;
    }

    /**
     * Opens the db
     */
    public void open() {
        db = getWritableDB();
    }

    /**
     * Closes the DB if necessary
     */
    public void close() {
        // db.close();
    }
}
