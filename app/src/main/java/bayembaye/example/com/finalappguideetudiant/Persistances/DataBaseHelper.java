package bayembaye.example.com.finalappguideetudiant.Persistances;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by bayembaye on 31/12/2016.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private final static String DB = "dbguide.db";
    private final static String DataBase = "CREATE TABLE " +DataBaseFunctions.TABLENAME +
            " ("+ DataBaseFunctions.TEL + "  INTEGER PRIMARY KEY ," +
            DataBaseFunctions.NOM + " TEXT ," + DataBaseFunctions.TYPE + " TEXT ," +
            DataBaseFunctions.MAIL + " TEXT )";
    private final static String DROPTABLE = "DROP TABLE IF EXIST " + DataBaseFunctions.TABLENAME;
    private final static int VERSIONDB = 1;

    public DataBaseHelper(Context context) {
        super(context, DB, null, VERSIONDB);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
    sqLiteDatabase.execSQL(DataBase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    sqLiteDatabase.execSQL(DROPTABLE);
        onCreate(sqLiteDatabase);
    }
}
