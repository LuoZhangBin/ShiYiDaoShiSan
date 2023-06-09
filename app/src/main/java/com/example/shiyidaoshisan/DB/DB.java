package com.example.shiyidaoshisan.DB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DB extends SQLiteOpenHelper {
    SQLiteDatabase db ;
    static String db_name = "shuju.db";
    static int db_version = 1;

    public DB(@Nullable Context context ) {
        super(context, db_name, null, db_version);
        db=getWritableDatabase();
    }
    //打开数据库
    public SQLiteDatabase openDb(){
        return db=getWritableDatabase();
    }
    //关闭数据库
    public void closeDb(){
        db.close();
    }
    public boolean createTable(String sp1){
        openDb();
        try{
            db.execSQL(sp1);
            closeDb();
        }
        catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }
    public boolean isExist(String tableName){
        String sp1=" select * from "+tableName;
        openDb();
        try {
            db.rawQuery(sp1,null);
            closeDb();
        }
        catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }
    //添加
    public boolean insertDB(String table, ContentValues cv){
        openDb();
        try {
            db.insert(table,null,cv);
            closeDb();
        }
        catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }
    //删除
    public boolean deleteDB(String table,String a,String c){
        openDb();
        try {
            db.delete(table,null,null );
            closeDb();
        }
        catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }
    //更新数据
    public boolean updateDB(String table,ContentValues cv,String name,String shoji){
        openDb();
        try {
            db.update(table,cv,"name=? and shoji=?",new String[]{name,shoji});
            closeDb();
        }
        catch (Exception ex){
            closeDb();
            return false;
        }
        return true;
    }
    public Cursor cursorDB(String tablename,String[] cols,String argwhere,String[] args,String group,String having,String order,String litmit){
        Cursor c;
        openDb();
        try {
            c=db.query(tablename,cols,argwhere,args,group,having,order,litmit);
        }
        catch (Exception ex){
            return null;
        }
        return c;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
