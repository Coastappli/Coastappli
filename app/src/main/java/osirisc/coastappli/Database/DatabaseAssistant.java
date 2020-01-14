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

/**
 * This class manages the database in which we store the data about markers and measures.
 * The database is currently stored on the device locally. Future updates will change this and put the database online.
 * The corresponding tables are created and managed by this activity's methods.
 */

public class DatabaseAssistant extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "CoastappliDB.db";

    /**
     * Constructor of the class.
      * @param context
     */

    public DatabaseAssistant(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /**
     * This functions creates the 3 main tables used in the app at the moment :
     * Marker table collects the data of each registered location.
     * MesureErosionDistance table indexises every measure of the ErosionDistance indicator
     * MethodErosionDistance table contains the recquired informations to present the measurement protocol of
     * the indicator ErosionDistance
     * We here add all the markers we need in to local database.
     * @param db
     */

    @Override
    public void onCreate(SQLiteDatabase db){
        //We create all 3 databases described above
        db.execSQL("CREATE TABLE Marker (_id INTEGER PRIMARY KEY AUTOINCREMENT, latitude DOUBLE, longitude DOUBLE, namebeach TEXT, nameTown TEXT, coastType TEXT, INEC TEXT, erosionDistanceMesure BOOL,  photo BLOB);");
        db.execSQL("CREATE TABLE MesureErosionDistance (_id INTEGER PRIMARY KEY AUTOINCREMENT, markerLatitude DOUBLE, markerLongitude DOUBLE, date DATE, time DATE, user TEXT, note TEXT, photo BLOB);");
        db.execSQL("CREATE TABLE MethodErosionDistance (_id INTEGER PRIMARY KEY AUTOINCREMENT, markerLatitude DOUBLE, markerLongitude DOUBLE, photo BLOB, photoPerson BLOB, clue1 TEXT, clue2 TEXT, clue3 TEXT);");

        //We get the bite array out of a picture of our first marker's location
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        ((BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.le_dellec)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        //We now create this marker
        Marker marker = new Marker(48.3549,-4.5671,  "Le Dellec", "Plouzané", "Type de côte", "INEC", 1, byteArray);
        //We can create as many markers as we want :
        Marker marker1 = new Marker(47.3549, -5.671, "Test2", "Test2", "Test2", "Test2", 0);
        //And we add them to the marker table
        addInitMarker(marker, db);
        addInitMarker(marker1, db);

        //By the same process, we extract the byte arrays from our 2 pictures that we'll store in the MethodErosionDistance database
        ByteArrayOutputStream streamPhoto = new ByteArrayOutputStream();
        ((BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.photo_dellec)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, streamPhoto);
        byte[] byteArrayPhoto = streamPhoto.toByteArray();
        ByteArrayOutputStream streamPhotoPerson = new ByteArrayOutputStream();
        ((BitmapDrawable)ContextCompat.getDrawable(getApplicationContext(), R.drawable.photo_dellec_person)).getBitmap().compress(Bitmap.CompressFormat.PNG, 100, streamPhotoPerson);
        byte[] byteArrayPhotoPerson = streamPhotoPerson.toByteArray();
        //Thanks to these 2 byte arrays, we create the corresponding method
        MethodErosionPhotoCapture method = new MethodErosionPhotoCapture(48.3549,-4.5671, byteArrayPhoto, byteArrayPhotoPerson, "Clocher dans le coin gauche", "Arbre aligné avec le centre", "Ne pas trop voir le ciel");
        //And we add it to the table
        addInitMethodErosionDistance(method, db);
    }

    /**
     * This function is currently not really used.
     * If different versions of the database are used, it will become useful.
     * @param db
     * @param oldVersion
     * @param newVersion
     */
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

    /**
     * This function is used to retrieve all the markers from the database.
     * @return
     */

    public ArrayList<Marker> loadMarker() {
        //We create an empty list of markers
        ArrayList<Marker> listMarker = new ArrayList<Marker>();
        String query = "Select * FROM Marker";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //we extract all the markers from the database and add them to our new list
        while (cursor.moveToNext()) {
            Marker marker = new Marker();
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            marker.setNameBeach(cursor.getString(3));
            marker.setNameTown(cursor.getString(4));
            marker.setCoastType(cursor.getString(5));
            marker.setINEC(cursor.getString(6));
            marker.setErosionMeasurePhotoCapture(Integer.parseInt(cursor.getString(7)));
            marker.setPhoto(cursor.getBlob(8));
            listMarker.add(marker);
        }
        cursor.close();
        db.close();
        return listMarker;
    }

    /**
     * Function finding a marker into a database thanks to its latitude and longitude
     * @param latitude
     * @param longitude
     * @return
     */

    public Marker findMarker(double latitude, double longitude) {
        //We identify the corresponding marker in the database via SQL request
        String query = "Select*FROM Marker WHERE latitude =" + "'" + latitude +  "'" + "AND longitude =" + "'" + longitude +  "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //Then we create a new marker and set its attributes to the right values
        Marker marker = new Marker();
        //We check if the query is not empty, and if it's not, we pick values from the first element
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            marker.setNameBeach(cursor.getString(3));
            marker.setNameTown(cursor.getString(4));
            marker.setCoastType(cursor.getString(5));
            marker.setINEC(cursor.getString(6));
            marker.setErosionMeasurePhotoCapture(Integer.parseInt(cursor.getString(7)));
            marker.setPhoto(cursor.getBlob(8));
            cursor.close();
        //If query is empty, there's no corresponding marker so we set i to null
        } else {
            marker = null;
        }
        db.close();
        return marker;
    }

    /**
     * Function that adds a marker to the database
     * This function is currently not used, but it can become useful if a button "add marker" is added on the app
     * @param marker
     */

    public void addMarker(Marker marker){
        //We will store all the attribute values in a ContentValues object
        ContentValues values = new ContentValues();
        //We put inside our ContentValues all the information required about the marker
        values.put("latitude", marker.getLatitude());
        values.put("longitude", marker.getLongitude());
        values.put("nameBeach", marker.getNameBeach());
        values.put("nameTown", marker.getNameTown());
        values.put("coastType", marker.getCoastType());
        values.put("INEC", marker.getINEC());
        values.put("erosionDistanceMesure", marker.getErosionDistanceMesureBool());
        values.put("photo", marker.getPhoto());
        SQLiteDatabase db = this.getWritableDatabase();
        //We then add this marker to the database
        db.insert("Marker", null, values);
        db.close();
    }

    /**
     * This functions allows us to add initial markers in the OnCreate method.
     * At this stage, we need to give the database as a parameter because we don't want to close it at the end of the function
     * @param marker
     * @param db
     */

    public void addInitMarker(Marker marker, SQLiteDatabase db){
        //We will store all the attreibutes values in a ContentValues object
        ContentValues values = new ContentValues();
        //We put inside our ContentValues all the informations recquired about the marker
        values.put("latitude", marker.getLatitude());
        values.put("longitude", marker.getLongitude());
        values.put("nameBeach", marker.getNameBeach());
        values.put("nameTown", marker.getNameTown());
        values.put("coastType", marker.getCoastType());
        values.put("INEC", marker.getINEC());
        values.put("erosionDistanceMesure", marker.getErosionDistanceMesureBool());
        values.put("photo", marker.getPhoto());
        //We then add this marker to the database
        db.insert("Marker", null, values);
    }

    /**
     * Function that deletes a marker from a database. The marker is identified thanks to it's latitude and longitude
     * This functions returns a boolean that tells if one marker has been deleted.
     * This function is currently not used, but it can become useful if a button "delete marker" is added on the app
     * @param latitude
     * @param longitude
     * @return
     */

    public boolean deleteMarker(Double latitude, Double longitude) {
        //We set a boolean to "false"
        boolean result = false;
        //We look for corresponding markers in the dataset
        String query = "Select*FROM Marker WHERE latitude =" + "'" + latitude +  "'" + "AND longitude =" + "'" + longitude +  "'";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //We create a new marker
        Marker marker = new Marker();
        //If there is a marker matching the location in the dabase, we delete it
        if (cursor.moveToFirst()) {
            //We assign its latitude and longitude to the new marker we created
            marker.setLatitude(Double.parseDouble(cursor.getString(1)));
            marker.setLongitude(Double.parseDouble(cursor.getString(2)));
            //We deleted marker in the database corresponding to the one we created
            db.delete("Marker", "latitude =? and longitude =?",
                    new String[] {
                String.valueOf(marker.getLatitude()), String.valueOf(marker.getLongitude())
            });
            cursor.close();
            //We set the boolean to "true"
            result = true;
        }
        db.close();
        //We return the boolean to show if a marker was deleted
        return result;
    }

    /**
     * Functions that deletes the marker in the database corresponding to the one given as a parameter.
     * This function is currently not used, but it can become useful if a button "delete marker" is added on the app
     * @param marker
     * @return
     */

    public boolean deleteMarker(Marker marker) {
        //The process is the same as in the previous function (deleteMarker(Double latitude, Double longitude)
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

    /**
     * Functions that deletes all markers from a database
     * This function is currently not used
     */

    public void deleteAllMarker() {
        boolean result = false;
        //We select every marker from the marker table
        String query = "Select*FROM Marker";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //We delete the markers one by one
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

    /**
     * Functions adding a measure of the ErosionDistance indicator to the database
     * @param mesure
     */

    public void addMeasureErosionDistance(MeasureErosionPhotoCapture mesure){
        //We put all the measures values in a ContentValues object to then add the totality to the database
        ContentValues values = new ContentValues();
        values.put("markerLatitude", mesure.getMarkerLatitude());
        values.put("markerLongitude", mesure.getMarkerLongitude());
        values.put("date", mesure.getDate());
        values.put("time", mesure.getTime());
        values.put("user", mesure.getUser());
        values.put("note", mesure.getNotes());
        values.put("photo", mesure.getPhoto());
        SQLiteDatabase db = this.getWritableDatabase();
        //We add the ContentValues object to the database : it contains every information about the measure
        db.insert("MeasureErosionPhotoCapture", null, values);
        db.close();
    }

    /**
     * This function is used to get MeasureErosionPhotoCapture object corresponding to a measure of ErosionDistance indicator
     * @param latitude
     * @param longitude
     * @return
     */

    public MeasureErosionPhotoCapture findMeasureErosionDistance(double latitude, double longitude){
        //We select the measures corresponding to the marker's given latitude and longitude
        String query = "Select*FROM MesureErosionDistance WHERE markerLatitude =" + "'" + latitude +  "'" + "AND markerLongitude =" + "'" + longitude +  "' ORDER BY _id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //We create a new measure of ErosionPhotoCapture
        MeasureErosionPhotoCapture measure = new MeasureErosionPhotoCapture();
        //If the query is not empty, we set this measure's attributes to match the first element from query
        if (cursor.moveToFirst()) {
            cursor.moveToFirst();
            measure.setMarkerLatitude(Double.parseDouble(cursor.getString(1)));
            measure.setMarkerLongitude(Double.parseDouble(cursor.getString(2)));
            measure.setDate(cursor.getString(3));
            measure.setTime(cursor.getString(4));
            measure.setUser(cursor.getString(5));
            measure.setNotes(cursor.getString(6));
            measure.setPhoto(cursor.getBlob(7));
            cursor.close();
        //If query is empty, then there is no measure corresponding, and the return will be null
        } else {
            measure = null;
        }
        db.close();
        return measure;
    }

    /**
     * Functions that delete all MesureErosionDistance from the database
     */

    public void deleteAllMeasureErosionDistance() {
        //Boolean is used to check if measures where deleted
        boolean result = false;
        //We select every element from the database
        String query = "Select*FROM MesureErosionDistance";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        //We pick elements one by one to delete them, as long as there are left
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

    /**
     * This functions allows us to add initial measures in the OnCreate method.
     * At this stage, we need to give the database as a parameter as we don't want to close it at the end of the function
     * @param method
     * @param db
     */

    public void addInitMethodErosionDistance(MethodErosionPhotoCapture method, SQLiteDatabase db){
        //Storing the measure's information in a ContentValues object
        ContentValues values = new ContentValues();
        values.put("markerLatitude", method.getMarkerLatitude());
        values.put("markerLongitude", method.getMarkerLongitude());
        values.put("photo", method.getPhoto());
        values.put("photoPerson", method.getPhotoPerson());
        values.put("clue1", method.getClue1());
        values.put("clue2", method.getClue2());
        values.put("clue3", method.getClue3());
        //Inserting the ContentValues object into th database
        db.insert("MethodErosionPhotoCapture", null, values);
    }

    /**
     * Function that returns the MethodErosionPhotoCapture object corresponding to the given latitude and longitude
     * @param latitude
     * @param longitude
     * @return
     */

    public MethodErosionPhotoCapture findMethodPhotoCaptureErosion(double latitude, double longitude){
        //We select the methods corresponding to the location
        String query = "Select*FROM MethodErosionDistance WHERE markerLatitude =" + "'" + latitude +  "'" + "AND markerLongitude =" + "'" + longitude +  "' ORDER BY _id DESC";
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        MethodErosionPhotoCapture method = new MethodErosionPhotoCapture();
        //if we found one method, we provide its information do the MethodErosionPhotoCapture object we just created
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
        //If there is no method in this location, the function will return "null"
        } else {
            method = null;
        }
        db.close();
        return method;
    }
}
