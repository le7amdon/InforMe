package a7amdon.enis.tn.testiot.db;

/**
 * Created by 7amdon on 12/12/2016.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import a7amdon.enis.tn.testiot.model.Position;


public class DatabaseHandler extends SQLiteOpenHelper{



    private static final int DATA_BASE_VERSION=1;
    private static final String DATA_BASE_NAME="positions";
    private static final String TABLE_POSITION="position";
    private static final String Id="id";
    private static final String X="x";
    private static final String Y="y";
    private static final String Z="z";
    private static final String Date="date";

    public DatabaseHandler(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_POSITION + "(" + Id + " INTEGER PRIMARY KEY," + X + " FLOAT," + Y + " FLOAT,"+ Z + " FLOAT," + Date + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_POSITION);

        onCreate(db);
    }

    public void updatePosition(Position position) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(X,position.getX());
        cv.put(Y,position.getY());
        cv.put(Z,position.getZ());
        cv.put(Date,position.getDate());
        db.update(TABLE_POSITION, cv, "id="+1, null);
        db.close();
    }


    public void addPosition(Position position) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(Id, 1);
        values.put(X, position.getX());
        values.put(Y, position.getY());
        values.put(Z, position.getZ());
        values.put(Date, position.getDate());

        db.insert(TABLE_POSITION, null, values);
        db.close();
    }

    public Position getPositionById(int id) {
        Position position=null;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_POSITION+" WHERE id = '"+id+"' ", null);

        if (cursor.moveToFirst()) {
            do {
                position = new Position(Float.parseFloat(cursor.getString(1)),Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)) ,cursor.getString(4));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return position;
    }



    public int getPositionsCount() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_POSITION, null);
        int count = cursor.getCount();
        db.close();
        cursor.close();

        return count;
    }


    public List<Position> getAllPositions() {
        List<Position> positions = new ArrayList<Position>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_POSITION, null);

        if (cursor.moveToFirst()) {
            do {
                positions.add( new Position(Float.parseFloat(cursor.getString(1)),Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)) ,cursor.getString(4)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return positions;
    }
    public List<Position> getPositionsByZ(float z) {
        List<Position> positions = new ArrayList<Position>();

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM "+TABLE_POSITION+" WHERE z = '"+z+"' ", null);

        if (cursor.moveToFirst()) {
            do {
                positions.add( new Position(Float.parseFloat(cursor.getString(1)),Float.parseFloat(cursor.getString(2)), Float.parseFloat(cursor.getString(3)) ,cursor.getString(4)));
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return positions;
    }
}

