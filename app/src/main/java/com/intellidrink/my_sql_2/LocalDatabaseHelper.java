package com.intellidrink.my_sql_2;

/**
 * Created by David on 2/8/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseHelper extends SQLiteOpenHelper {

    // Logcat tag
    private static final String LOG = "DatabaseHelper";

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "IntelliDrink";

    // Table Names
    public static final String TABLE_DRINK_LIST = "Drink_List";


    // Table Create Statements
    // COURSES table create statement
    public static final String CREATE_TABLE_DRINK_LIST = "CREATE TABLE "
            + TABLE_DRINK_LIST + "("
            + Kiosk.TAG_ID + " INTEGER PRIMARY KEY,"
            + Kiosk.TAG_RECIPE_NAME + " TEXT,"
            + Kiosk.TAG_DESCRIPTION + " TEXT, "
            + Kiosk.TAG_INGREDIENT_ID + "INTEGER, "
            + Kiosk.TAG_UNITS + " INTEGER"
            + Kiosk.TAG_AVAILABLE + " BOOLEAN)";

    public LocalDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_DRINK_LIST);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_DRINK_LIST);
        // create new tables
        onCreate(db);
    }

    protected void resetTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DRINK_LIST);
        db.execSQL(CREATE_TABLE_DRINK_LIST);
    }

    /*
        Add Methods
     */
    protected void addDrink(String RecipeName, String Description, int IngredientID, int Units, Boolean Available) {
        SQLiteDatabase db = this.getWritableDatabase();
        String SQLString = "INSERT INTO " + TABLE_DRINK_LIST +
                " (" + Kiosk.TAG_RECIPE_NAME + ", " + Kiosk.TAG_DESCRIPTION + ", " + Kiosk.TAG_INGREDIENT_ID + ", " + Kiosk.TAG_UNITS + ", " + Kiosk.TAG_LITERAL_NAME + ") " +
                "VALUES ('" + RecipeName + "', '" + Description + "', " + IngredientID + ", " + Units + ", " + Available + "')";
        db.execSQL(SQLString);
    }

    protected void updateAvailability(int IngredientID, boolean availability) {
        SQLiteDatabase WRITE = this.getReadableDatabase();
        String SQLQuery = "UPDATE " + TABLE_DRINK_LIST + " " +
                "SET " + Kiosk.TAG_AVAILABLE + "=" + availability +
                "WHERE " + Kiosk.TAG_INGREDIENT_ID + "=" + IngredientID;
        WRITE.execSQL(SQLQuery);
    }

/*
    Get Methods
 */


}


