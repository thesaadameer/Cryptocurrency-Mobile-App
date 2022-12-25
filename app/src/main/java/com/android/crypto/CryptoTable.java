package com.android.crypto;


import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

import java.util.ArrayList;

public class CryptoTable {

    public static String TABLE_NAME = "favorites";
    public static String NAME = "name";
    public static String PRICE = "price";
    public static String SYMBOL = "symbol";

    public static String CREATE_TABLE_SQL = "CREATE TABLE " + TABLE_NAME + " ( " + NAME + " TEXT, " + PRICE + " TEXT,"
            + " " + SYMBOL + " TEXT);";
    public static String DROP_TABLE_SQL = "DROP TABLE if exists " + TABLE_NAME;


    public static ArrayList<CurrencyRVModel> getAllFavorites(DatabaseHelper dbHelper) {
        CurrencyRVModel coin;
        ArrayList<CurrencyRVModel> coinList = new ArrayList<>();
        Cursor cursor = dbHelper.getAllRecords(TABLE_NAME, null);
        Log.d("DATABASE OPERATIONS", cursor.getCount() + ",  " + cursor.getColumnCount());
        while (cursor.moveToNext()) {
            String name = cursor.getString(0);
            String price = cursor.getString(1);
            String symbol = cursor.getString(2);
            double price2 = Double.parseDouble(price);
            coin = new CurrencyRVModel(name, symbol, price2);
            coinList.add(coin);

        }
        Log.d("DATABASE OPERATIONS", coinList.toString());
        return coinList;
    }


    public static boolean insert(DatabaseHelper dbHelper, CurrencyRVModel rvModel) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME, rvModel.getName());
        contentValues.put(PRICE, rvModel.getPrice());
        contentValues.put(SYMBOL, rvModel.getSymbol());
        boolean res = dbHelper.insert(TABLE_NAME, contentValues);
        return res;

    }


    public static boolean delete(DatabaseHelper dbHelper, CurrencyRVModel rvModel) {
        Log.d("DATABASE OPERATIONS", "DELETE DONE");
        String where = NAME + " = " + "'" + rvModel.getName() + "'";
        Log.d("Delete", where);
        boolean res = dbHelper.delete(TABLE_NAME, where);
        return res;
    }
}

