package com.example.lab2playerhighscores;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MyDBHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "playerDB.db";
    public static final String TABLE_players = "players";
    public static final String COLUMN_ID = "playerID";
    public static final String COLUMN_NAME = "playerName";

    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_player_TABLE = "CREATE TABLE " +
                TABLE_players + "(" + COLUMN_ID + " INTEGER PRIMARY KEY," + COLUMN_NAME
                + " TEXT " + ")";
        db.execSQL(CREATE_player_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion,
                          int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_players);
        onCreate(db);

    }

    public void addHandler(Player player) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_ID, player.getID());
        values.put(COLUMN_NAME, player.getPlayerName());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE_players, null, values);
        db.close();
    }

    public Player findHandler(String playername) {
        String query = "Select * FROM " + TABLE_players + " WHERE " +
                COLUMN_NAME + " = '" + playername + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Player player = new Player();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            player.setID(Integer.parseInt(cursor.getString(0)));
            player.setPlayerName(cursor.getString(1));
            cursor.close();
        } else {
            player = null;
        }
        db.close();
        return player;
    }

    public String loadHandler() {
        String result = "";
        String query = "Select*FROM " + TABLE_players;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }

    public boolean deleteHandler(int ID) {
        boolean result = false;
        String query = "Select*FROM " + TABLE_players + " WHERE " + COLUMN_ID + " = '" + String.valueOf(ID) + "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Player player = new Player();
        if (cursor.moveToFirst()) {
            player.setID(Integer.parseInt(cursor.getString(0)));
            db.delete(TABLE_players, COLUMN_ID + "=?",
                    new String[] {
                            String.valueOf(player.getID())
                    });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public boolean updateHandler(int ID, String name) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues args = new ContentValues();
        args.put(COLUMN_ID, ID);
        args.put(COLUMN_NAME, name);
        return db.update(TABLE_players, args, COLUMN_ID + "=" + ID, null) > 0;
    }
}