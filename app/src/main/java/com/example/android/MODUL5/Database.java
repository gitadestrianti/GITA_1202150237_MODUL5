package com.example.android.MODUL5;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class Database extends SQLiteOpenHelper {
    Context cntx;
    SQLiteDatabase db;

    public static final String nama_db = "listtodo.db";
    public static final String nama_tabel = "daftartodo";
    public static final String kolom_1 = "todo";
    public static final String kolom_2 = "description";
    public static final String kolom_3 = "priority";

    public Database(Context context) {//Constructyor
        super(context, nama_db, null, 1);
        this.cntx = context;
        db = this.getWritableDatabase();
        db.execSQL("create table if not exists "+nama_tabel+" (todo varchar(35) primary key, description varchar(50), priority varchar(3))");
    }

    //Waktu database dibuat
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists "+nama_tabel+" (todo varchar(35) primary key, description varchar(50), priority varchar(3))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("drop table if exists "+nama_tabel);
        onCreate(sqLiteDatabase);
    }

    public boolean inputdata(AddData list) {

        //Apakah kolom dna nilai sudah cocok?
        ContentValues val = new ContentValues();
        val.put(kolom_1, list.getTodo());
        val.put(kolom_2, list.getDesc());
        val.put(kolom_3, list.getPrior());
        long hasil = db.insert(nama_tabel, null, val);
        if (hasil==-1) {
            return false;
        }else {
            return true;
        }
    }

    //method removeData pada Database
    public boolean removedata(String ToDo) {
        return db.delete(nama_tabel, kolom_1+"=\""+ToDo+"\"", null)>0;
    }

    //method readData dari database
    public void readdata(ArrayList<AddData> daftar){
        Cursor cursor = this.getReadableDatabase().rawQuery("select todo, description, priority from "+nama_tabel, null);
        while (cursor.moveToNext()){
            daftar.add(new AddData(cursor.getString(0), cursor.getString(1), cursor.getString(2)));
        }
    }
}


