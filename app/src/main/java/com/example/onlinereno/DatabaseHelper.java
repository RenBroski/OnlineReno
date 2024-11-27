package com.example.onlinereno;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "orderHistory.db";
    private static final int DATABASE_VERSION = 1;

    // Nama tabel dan kolom
    private static final String TABLE_NAME = "order_history";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_FRUIT_NAME = "fruit_name";
    private static final String COLUMN_PRICE_PER_KG = "price_per_kg";
    private static final String COLUMN_QUANTITY = "quantity";
    private static final String COLUMN_TOTAL_PRICE = "total_price";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FRUIT_NAME + " TEXT, " +
                COLUMN_PRICE_PER_KG + " REAL, " +
                COLUMN_QUANTITY + " REAL, " +
                COLUMN_TOTAL_PRICE + " REAL)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    /**
     * Menambahkan data riwayat pesanan ke database.
     *
     * @param fruitName  Nama buah.
     * @param pricePerKg Harga per kg.
     * @param quantity   Jumlah (kg).
     * @param totalPrice Total harga.
     * @return True jika berhasil, false jika gagal.
     */
    public boolean addHistory(String fruitName, double pricePerKg, double quantity, double totalPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_FRUIT_NAME, fruitName);
        contentValues.put(COLUMN_PRICE_PER_KG, pricePerKg);
        contentValues.put(COLUMN_QUANTITY, quantity);
        contentValues.put(COLUMN_TOTAL_PRICE, totalPrice);

        long result = db.insert(TABLE_NAME, null, contentValues);
        db.close(); // Tutup database setelah operasi selesai
        return result != -1;
    }

    public boolean deleteOrderById(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int rowsDeleted = db.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close(); // Tutup database
        return rowsDeleted > 0; // Return true jika ada data yang dihapus
    }


    /**
     * Mengambil semua data dari tabel order_history dalam bentuk Cursor.
     *
     * @return Cursor dengan semua data order.
     */
    public Cursor getAllOrders() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    /**
     * Mengonversi data dari database ke dalam bentuk List<OrderHistory>.
     *
     * @return List<OrderHistory> berisi semua riwayat pesanan.
     */
    public List<OrderHistory> getOrderHistoryList() {
        List<OrderHistory> historyList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = getAllOrders();

        if (cursor != null) {
            while (cursor.moveToNext()) {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String fruitName = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_FRUIT_NAME));
                double pricePerKg = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_PRICE_PER_KG));
                double quantity = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_QUANTITY));
                double totalPrice = cursor.getDouble(cursor.getColumnIndexOrThrow(COLUMN_TOTAL_PRICE));

                // Tambahkan item baru ke daftar
                historyList.add(new OrderHistory(id, fruitName, pricePerKg, quantity, totalPrice));
            }
            cursor.close(); // Pastikan Cursor ditutup
        }
        db.close(); // Tutup database setelah operasi selesai
        return historyList;
    }
}
