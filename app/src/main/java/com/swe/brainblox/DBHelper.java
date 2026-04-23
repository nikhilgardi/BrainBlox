package com.swe.brainblox;

import android.content.*;
import android.database.sqlite.*;
import android.database.Cursor;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BrainBloxDB";
    private static final int DB_VERSION = 2; // IMPORTANT (incremented)

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE results (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "sequence TEXT," +
                "user_input TEXT," +
                "is_correct INTEGER," +
                "time_taken INTEGER)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Simple approach (for now)
        db.execSQL("DROP TABLE IF EXISTS results");
        onCreate(db);
    }

    // ✅ Insert with time
    public void insert(String seq, String input, int correct, int time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put("sequence", seq);
        cv.put("user_input", input);
        cv.put("is_correct", correct);
        cv.put("time_taken", time);

        db.insert("results", null, cv);
    }

    // ✅ Get all data
    public Cursor getAllResults() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM results", null);
    }

    // ✅ Total count
    public int getTotalGames() {
        Cursor c = getAllResults();
        return c.getCount();
    }

    // ✅ Correct count
    public int getCorrectGames() {
        Cursor c = getAllResults();
        int correct = 0;

        while (c.moveToNext()) {
            if (c.getInt(3) == 1) correct++;
        }
        return correct;
    }

    // ✅ Wrong count
    public int getWrongGames() {
        return getTotalGames() - getCorrectGames();
    }
}