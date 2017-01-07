package bayembaye.example.com.finalappguideetudiant.Persistances;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import bayembaye.example.com.finalappguideetudiant.Drivers.UtilsContacts;

/**
 * Created by bayembaye on 31/12/2016.
 */

public class DataBaseFunctions {
    public static String
                        NOM = "name",
                        TYPE = "type",
                        MAIL = "mail",
                        TEL = "tel",
                        TABLENAME = "util";
    private SQLiteDatabase db = null;
    public DataBaseFunctions(Context context){
        db  = new DataBaseHelper(context).getWritableDatabase();
    }
    public boolean delectUtil(int tel){
       return db.delete(TABLENAME, TEL + " = ? ",new String[]{String.valueOf(tel)}) > 0;
    }
    public boolean editUtils(UtilsContacts contacts){
        String key = String.valueOf(contacts.getTel());
        ContentValues cv = new ContentValues();
        cv.put(TEL,contacts.getTel());
        cv.put(MAIL,contacts.getMail());
        cv.put(TYPE,contacts.getTypecase());
        cv.put(NOM,contacts.getName());
        return db.update(TABLENAME,cv, contacts.getTel() +" = ? ",new String[]{key}) > 0;
    }
    public List<UtilsContacts> seletAll(){
        Cursor cr  = db.rawQuery("SELECT * FROM " + TABLENAME,null);
        List<UtilsContacts> listAmical = new ArrayList<>();
        if (cr.getCount() > 0 ){
            if (cr.moveToFirst()){
                while(!cr.isAfterLast()){
                    int tel = cr.getInt(cr.getColumnIndex(TEL));
                    String name = cr.getString(cr.getColumnIndex(NOM));
                    String mail = cr.getString(cr.getColumnIndex(MAIL));
                    String type = cr.getString(cr.getColumnIndex(TYPE));
                    listAmical.add(new UtilsContacts(name,tel,type,mail));
                    cr.moveToNext();
                }
            }
            cr.close();
        }else return null;
        return (listAmical == null) ? null: listAmical;
    }

    public boolean addUtil(UtilsContacts contacts){
        ContentValues cv = new ContentValues();
        cv.put(TEL,contacts.getTel());
        cv.put(MAIL,contacts.getMail());
        cv.put(NOM,contacts.getName());
        cv.put(TYPE,contacts.getTypecase());
        return db.insert(TABLENAME,null,cv) > 0 ;
    }
    public boolean exist(int tel){
        Cursor cr = db.rawQuery("SELECT "+TEL + " FROM "+TABLENAME +"WHERE id = "+String.valueOf(tel),null);
        return cr.getCount() >0;
    }
    public boolean isNull(){
        Cursor cr = db.rawQuery("SELECT * FROM "+TABLENAME,null);
        return !(cr.getCount()>0);
    }
    public void close(){
        db.close();
        db = null;
    }
}
