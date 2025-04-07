package com.example.lab_3;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "flowerOrders.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_ORDERS = "orders";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FLOWER = "flower";
    private static final String COLUMN_COLOR = "color";
    private static final String COLUMN_PRICE = "price";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ORDERS_TABLE = "CREATE TABLE " + TABLE_ORDERS + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_FLOWER + " TEXT,"
                + COLUMN_COLOR + " TEXT,"
                + COLUMN_PRICE + " TEXT" + ")";
        db.execSQL(CREATE_ORDERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ORDERS);
        onCreate(db);
    }

    public long addOrder(String flower, String color, String price) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FLOWER, flower);
        values.put(COLUMN_COLOR, color);
        values.put(COLUMN_PRICE, price);

        long id = db.insert(TABLE_ORDERS, null, values);
        db.close();
        return id;
    }

    public List<Order> getAllOrders() {
        List<Order> orderList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ORDERS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Order order = new Order();
                order.setId(cursor.getInt(0));
                order.setFlower(cursor.getString(1));
                order.setColor(cursor.getString(2));
                order.setPrice(cursor.getString(3));
                orderList.add(order);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return orderList;
    }

    public int updateOrder(Order order) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_FLOWER, order.getFlower());
        values.put(COLUMN_COLOR, order.getColor());
        values.put(COLUMN_PRICE, order.getPrice());

        return db.update(TABLE_ORDERS, values, COLUMN_ID + " = ?",
                new String[]{String.valueOf(order.getId())});
    }

    public int deleteOrder(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ORDERS, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
        return id;
    }
}