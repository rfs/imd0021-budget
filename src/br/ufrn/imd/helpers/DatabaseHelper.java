package br.ufrn.imd.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
	 
    private static final int DATABASE_VERSION = 1;
 
    public DatabaseHelper(Context context) {
        super(context, "budget", null, DATABASE_VERSION);
    }

	@Override
	public void onCreate(SQLiteDatabase db) {
	    db.execSQL("CREATE TABLE incomes (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value REAL, dueDate TEXT, done INTEGER);");
	    db.execSQL("CREATE TABLE expenses (id INTEGER PRIMARY KEY AUTOINCREMENT, title TEXT, value REAL, dueDate TEXT, done INTEGER);");
    }

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
