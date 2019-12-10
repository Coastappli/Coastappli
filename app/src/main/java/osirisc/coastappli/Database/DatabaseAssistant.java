package osirisc.coastappli.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseAssistant extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CoastappliDB.db";

    public DatabaseAssistant(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Marker (_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude DOUBLE, longitude DOUBLE, namebeach TEXT, nameTown TEXT, coastType TEXT, INEC TEXT, erosionDistanceMesure BOOL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        for(int indexVersion=oldVersion ; indexVersion <newVersion; indexVersion ++){
            int nextVersion = indexVersion+1;
            switch (nextVersion){
                case 2:
                    break;
                case 3:
                    break;
            }
        }
    }

    public ArrayList<Marker> loadMarker() {
        ArrayList<Marker> listMarker = new ArrayList<Marker>();
        String query = "Select * FROM Marker";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Marker marker = new Marker();
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            marker.setNameBeach(cursor.getString(3));
            marker.setNameTown(cursor.getString(4));
            marker.setCoastType(cursor.getString(5));
            marker.setINEC(cursor.getString(6));
            marker.setErosionDistanceMesure(Integer.parseInt(cursor.getString(7)));
            listMarker.add(marker);
        }
        cursor.close();
        db.close();
        return listMarker;
    }

    public Marker findMarker(double latitude, double longitude) {
        String query = "Select*FROM Marker WHERE latitude =" + "'" + latitude +  "'" + "AND longitude =" + "'" + longitude +  "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Marker marker = new Marker();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            marker.setNameBeach(cursor.getString(3));
            marker.setNameTown(cursor.getString(4));
            marker.setCoastType(cursor.getString(5));
            marker.setINEC(cursor.getString(6));
            marker.setErosionDistanceMesure(Integer.parseInt(cursor.getString(7)));
            cursor.close();
        } else {
            marker = null;
        }
        db.close();
        return marker;
    }

    public void addMarker(Marker marker){
        ContentValues values = new ContentValues();
        values.put("latitude", marker.getLatitude());
        values.put("longitude", marker.getLongitude());
        values.put("nameBeach", marker.getNameBeach());
        values.put("nameTown", marker.getNameTown());
        values.put("coastType", marker.getCoastType());
        values.put("INEC", marker.getINEC());
        values.put("erosionDistanceMesure", marker.getErosionDistanceMesure());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Marker", null, values);
        db.close();
    }

    public boolean deleteMarker(Double latitude, Double longitude) {
        boolean result = false;
        String query = "Select*FROM Marker WHERE latitude =" + "'" + latitude +  "'" + "AND longitude =" + "'" + longitude +  "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        Marker marker = new Marker();
        if (cursor.moveToFirst()) {
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            db.delete("Marker", "latitude =? and longitude =?",
                    new String[] {
                String.valueOf(marker.getLatitude()), String.valueOf(marker.getLongitude())
            });
            cursor.close();
            result = true;
        }
        db.close();
        return result;
    }

    public void deleteAllMarker() {
        boolean result = false;
        String query = "Select*FROM Marker";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            Marker marker = new Marker();
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            db.delete("Marker", "latitude =? and longitude =?",
                    new String[] {
                            String.valueOf(marker.getLatitude()), String.valueOf(marker.getLongitude())
                    });
        }
        cursor.close();
        db.close();
    }
}
