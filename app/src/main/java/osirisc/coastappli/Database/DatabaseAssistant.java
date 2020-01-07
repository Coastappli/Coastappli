package osirisc.coastappli.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import androidx.core.content.ContextCompat;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import osirisc.coastappli.R;

import static com.mapbox.mapboxsdk.Mapbox.getApplicationContext;


public class DatabaseAssistant extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CoastappliDB.db";

    public DatabaseAssistant(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL("CREATE TABLE Marker (_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude DOUBLE, longitude DOUBLE, namebeach TEXT, nameTown TEXT, coastType TEXT, INEC TEXT, erosionDistanceMesure BOOL,  photo BLOB);");
        db.execSQL("CREATE TABLE MesureErosionDistance (_id INTEGER PRIMARY KEY AUTOINCREMENT, markerLatitude DOUBLE, markerLongitude DOUBLE, date DATE, time DATE, user TEXT, note TEXT, photo BLOB);");
        db.execSQL("CREATE TABLE MethodErosionDistance (_id INTEGER PRIMARY KEY AUTOINCREMENT, markerLatitude DOUBLE, markerLongitude DOUBLE, photo BLOB, photoPerson BLOB, clue1 TEXT, clue2 TEXT, clue3 TEXT);");

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.le_dellec)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        Marker marker = new Marker(48.3549,-4.5671,  "Le Dellec", "Plouzané", "Type de côte", "INEC", 1, byteArray);
        Marker marker1 = new Marker(47.3549, -5.671, "Test2", "Test2", "Test2", "Test2", 0);
        addInitMarker(marker, db);
        addInitMarker(marker1, db);

        //Attention: somme des deux photos de la méthode limitée à 1MB
        ByteArrayOutputStream streamPhoto = new ByteArrayOutputStream();
        ((BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.photo_dellec)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, streamPhoto);
        byte[] byteArrayPhoto = streamPhoto.toByteArray();
        ByteArrayOutputStream streamPhotoPerson = new ByteArrayOutputStream();
        ((BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.photo_dellec_person)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, streamPhotoPerson);
        byte[] byteArrayPhotoPerson = streamPhotoPerson.toByteArray();
        MethodErosionPhotoCapture method = new MethodErosionPhotoCapture(48.3549,-4.5671, byteArrayPhoto, byteArrayPhotoPerson, "Clocher dans le coin gauche", "Arbre aligné avec le centre", "Ne pas trop voir le ciel");
        addInitMethodErosionDistance(method, db);
    }

    // A quoi ça sert ?????
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
            marker.setPhoto(cursor.getBlob(8));
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
            marker.setPhoto(cursor.getBlob(8));
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
        values.put("erosionDistanceMesure", marker.getErosionDistanceMesureBool());
        values.put("photo", marker.getPhoto());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("Marker", null, values);
        db.close();
    }

    public void addInitMarker(Marker marker, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("latitude", marker.getLatitude());
        values.put("longitude", marker.getLongitude());
        values.put("nameBeach", marker.getNameBeach());
        values.put("nameTown", marker.getNameTown());
        values.put("coastType", marker.getCoastType());
        values.put("INEC", marker.getINEC());
        values.put("erosionDistanceMesure", marker.getErosionDistanceMesureBool());
        values.put("photo", marker.getPhoto());
        db.insert("Marker", null, values);
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

    public boolean deleteMarker(Marker marker) {
        boolean result = false;
        String query = "Select*FROM Marker WHERE latitude =" + "'" + marker.getLatitude() +  "'" + "AND longitude =" + "'" + marker.getLongitude() +  "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        if (cursor.moveToFirst()) {
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

    public void addMesureErosionDistance(MeasureErosionPhotoCapture mesure){
        ContentValues values = new ContentValues();
        values.put("markerLatitude", mesure.getMarkerLatitude());
        values.put("markerLongitude", mesure.getMarkerLongitude());
        values.put("date", mesure.getDate());
        values.put("time", mesure.getTime());
        values.put("user", mesure.getUser());
        values.put("note", mesure.getNotes());
        values.put("photo", mesure.getPhoto());
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert("MeasureErosionPhotoCapture", null, values);
        db.close();
    }

    public MeasureErosionPhotoCapture findMesureErosionDistance(double latitude, double longitude){
        String query = "Select*FROM MesureErosionDistance WHERE markerLatitude =" + "'" + latitude +  "'" + "AND markerLongitude =" + "'" + longitude +  "' ORDER BY _id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MeasureErosionPhotoCapture mesure = new MeasureErosionPhotoCapture();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            mesure.setMarkerLatitude(Double.parseDouble(cursor.getString(1)));
            mesure.setMarkerLongitude(Double.parseDouble(cursor.getString(2)));
            mesure.setDate(cursor.getString(3));
            mesure.setTime(cursor.getString(4));
            mesure.setUser(cursor.getString(5));
            mesure.setNotes(cursor.getString(6));
            mesure.setPhoto(cursor.getBlob(7));
            cursor.close();
        } else {
            mesure = null;
        }
        db.close();
        return mesure;
    }

    public void deleteAllMesureErosionDistance() {
        boolean result = false;
        String query = "Select*FROM MesureErosionDistance";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            MeasureErosionPhotoCapture measureErosionPhotoCapture = new MeasureErosionPhotoCapture();
            measureErosionPhotoCapture.setMarkerLatitude(Double.parseDouble(cursor.getString(1)));
            measureErosionPhotoCapture.setMarkerLongitude(Double.parseDouble(cursor.getString(2)));
            db.delete("MeasureErosionPhotoCapture", "markerLatitude =? and markerLongitude =?",
                    new String[] {
                            String.valueOf(measureErosionPhotoCapture.getMarkerLatitude()), String.valueOf(measureErosionPhotoCapture.getMarkerLongitude())
                    });
        }
        cursor.close();
        db.close();
    }

    public void addInitMethodErosionDistance(MethodErosionPhotoCapture method, SQLiteDatabase db){
        ContentValues values = new ContentValues();
        values.put("markerLatitude", method.getMarkerLatitude());
        values.put("markerLongitude", method.getMarkerLongitude());
        values.put("photo", method.getPhoto());
        values.put("photoPerson", method.getPhotoPerson());
        values.put("clue1", method.getClue1());
        values.put("clue2", method.getClue2());
        values.put("clue3", method.getClue3());
        db.insert("MethodErosionPhotoCapture", null, values);
    }

    public MethodErosionPhotoCapture findMethodErosionDistance(double latitude, double longitude){
        String query = "Select*FROM MethodErosionDistance WHERE markerLatitude =" + "'" + latitude +  "'" + "AND markerLongitude =" + "'" + longitude +  "' ORDER BY _id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MethodErosionPhotoCapture method = new MethodErosionPhotoCapture();
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            method.setMarkerLatitude(Double.parseDouble(cursor.getString(1)));
            method.setMarkerLongitude(Double.parseDouble(cursor.getString(2)));
            method.setPhoto(cursor.getBlob(3));
            method.setPhotoPerson(cursor.getBlob(4));
            method.setClue1(cursor.getString(5));
            method.setClue2(cursor.getString(6));
            method.setClue3(cursor.getString(7));
            cursor.close();
        } else {
            method = null;
        }
        db.close();
        return method;
    }
}
