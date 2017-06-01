package com.bhavdip.haqdarshak.dbsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by aakash on 05/09/14.
 */
public class UsersModel extends Model {
    private static final String TABLE_USERS = "hqd_users";

    //Fields
    private static final String KEY_USERS_ID = "user_id";

    private static final String KEY_MOBILE = "mobile";

    private static final String KEY_PASSWORD = "password";

    private static final String KEY_NAME = "name";

    private static final String KEY_EMAIL = "email";

    private static final String KEY_GENDER = "gender";

    private static final String CREATE_TABLE_USERS = "create table if not exists "
            + TABLE_USERS
            + " ( "
            + KEY_USERS_ID
            + " integer primary key autoincrement, "
            + KEY_MOBILE
            + " text not null unique,"
            + KEY_PASSWORD
            + " text,"
            + KEY_NAME
            + " text,"
            + KEY_EMAIL
            + " text,"
            + KEY_GENDER
            + " text);";

    public UsersModel(Context ctx) {
        super(ctx);
    }

    /**
     * Drop Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void dropTable(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS " + UsersModel.TABLE_USERS);
    }

    /**
     * Create the Requests table
     *
     * @param db SQLiteDatabase instance
     */
    public static void createTable(SQLiteDatabase db) {
        db.execSQL(UsersModel.CREATE_TABLE_USERS);
    }

    /**
     * Clears the tables in the db
     *
     * @param db
     */
    public static void clearTable(SQLiteDatabase db) {
        db.execSQL("DELETE FROM " + UsersModel.TABLE_USERS);
    }

    public void insertEntry(String name, String email, String gender, String mobile, String password) {

        open();
        ContentValues newValues = new ContentValues();
        // Assign values for each row.
        newValues.put(KEY_NAME, name);
        newValues.put(KEY_EMAIL, email);
        newValues.put(KEY_GENDER, gender);
        newValues.put(KEY_MOBILE, mobile);
        newValues.put(KEY_PASSWORD, password);

        // Insert the row into your table
        db.insert(UsersModel.TABLE_USERS, null, newValues);
    }

    public String getSinlgeEntry(String mobile) {
        open();
        Cursor cursor = db.query(UsersModel.TABLE_USERS, null, " mobile=?", new String[]{mobile}, null, null, null);
        if (cursor.getCount() < 1) // UserName Not Exist
        {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex(UsersModel.KEY_PASSWORD));
        cursor.close();
        return password;
    }

}
