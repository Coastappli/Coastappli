package osirisc.coastappli;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String TAG="DatabaseHelper";
    private static final String COL1="ID";
    private static final String COL2="hauteur";
    private static final String TABLE_NAME="listehauteurs";

    public DatabaseHelper(Context context){
        super(context,TABLE_NAME, null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable="CREATE TABLE "+ TABLE_NAME + "(ID INTEGER PRIMARY KEY AUTOINCREMENT,"+ COL2+"TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

        onCreate(db);

    }

    public boolean addData(String item){
        SQLiteDatabase db =this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put(COL2, item);
        Log.d(TAG, "addata: Adding" + item+ "to" +TABLE_NAME);
        long result = db.insert(TABLE_NAME, null, contentValues);
        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }
}