package com.example.th2.dl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.th2.model.Item;

import java.util.ArrayList;
import java.util.List;

public class SQLiteHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "long.db";
    private static final int DATABASE_VERSION = 1;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table categories(" +
                "id integer primary key autoincrement," +
                "name text" +
                ")";
//        db.execSQL(sql);
        sql = "create table items(" +
                "id integer primary key autoincrement," +
                "title text not null," +
                "category text," +
                "price text," +
                "date text)";
//                +
//                "foreign key(cid) references categories(id))";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    public List<Item> getAll(){
        List<Item> items = new ArrayList<>();
        SQLiteDatabase st = getReadableDatabase();
        String order = "date DESC";
        Cursor rs = st.query("items", null,
                null,null,
                null,null,order);

        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            items.add(new Item(id, title, category, price, date));
        }
        return items;
    }

    public List<Item> getItemsByDate(String date){
        List<Item> items = new ArrayList<>();
        String whereClause = "date like ?";
        String[]  whereArg = {date};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            items.add(new Item(id, title, category, price, date));
        }
        return items;
    }

    public List<Item> getItemsByTitle(String key){
        List<Item> items = new ArrayList<>();
        String whereClause = "title like ?";
        String[]  whereArg = {"%" + key +"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            items.add(new Item(id, title, category, price, date));
        }
        return items;
    }

    public List<Item> getItemsByCategory(String key){
        List<Item> items = new ArrayList<>();
        String whereClause = "category like ?";
        String[]  whereArg = {"%" + key +"%"};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            items.add(new Item(id, title, category, price, date));
        }
        return items;
    }

    public List<Item> getItemsByDateRange(String from, String to){
        List<Item> items = new ArrayList<>();
        String whereClause = "date between ? and ?";
        String[]  whereArg = {from.trim(), to.trim()};
        SQLiteDatabase st = getReadableDatabase();
        Cursor rs = st.query("items", null, whereClause, whereArg, null, null, null);
        while (rs!=null && rs.moveToNext()){
            int id = rs.getInt(0);
            String title = rs.getString(1);
            String category = rs.getString(2);
            String price = rs.getString(3);
            String date = rs.getString(4);
            items.add(new Item(id, title, category, price, date));
        }
        return items;
    }
    //insert item
    public long insertItem(Item item){
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase st = getWritableDatabase();
        return st.insert("items", null, values);
    }

    //update
    public int updateItem(Item item ){
        ContentValues values = new ContentValues();
        values.put("title", item.getTitle());
        values.put("category", item.getCategory());
        values.put("price", item.getPrice());
        values.put("date", item.getDate());
        SQLiteDatabase st = getWritableDatabase();
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(item.getId())};
        return st.update("items", values, whereClause, whereArgs);
    }

    //delete
    public int deleteItem(int id){
        String whereClause = "id=?";
        String[] whereArgs = {Integer.toString(id)};
        SQLiteDatabase st = getWritableDatabase();
        return st.delete("items", whereClause, whereArgs);
    }


}
