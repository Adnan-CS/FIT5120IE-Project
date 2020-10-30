package com.workingsafe.safetyapp.sos;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import com.workingsafe.safetyapp.model.ContactPerson;

public class ContactHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MyContactName.db";
    public static final String CONTACTS_TABLE_NAME = "contacts";
    public static final String CONTACTS_COLUMN_ID = "id";
    public static final String CONTACTS_COLUMN_NAME = "name";
    public static final String CONTACTS_COLUMN_MESSAGE = "message";
    public static final String CONTACTS_COLUMN_PHONE = "phone";
    public static final String CONTACTS_COLUMN_LOCSHARE = "share";
    private HashMap hp;
    private Context currentContext;

    public ContactHelper(Context context) {
        super(context, DATABASE_NAME, null, 8);
        currentContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // TODO Auto-generated method stub
        db.execSQL(
                "create table contacts " +
                        "(id integer primary key, name text,phone text,message text,share text)"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS contacts");
        onCreate(db);
    }

    public boolean insertContact(String name, String phone, String message, String share) {
        if(!isNumberExist(phone)){
            Toast.makeText(currentContext,"Phone number already exist",Toast.LENGTH_SHORT).show();
            return false;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("message", message);
        contentValues.put("share", share);
        db.insert("contacts", null, contentValues);
        return true;
    }
    public boolean isNumberExist(String phone){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where phone=?", new String [] {phone});
        int count = res.getCount();
        res.close();
        return count == 0;
    }

    public Cursor getData(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts where id=" + id + "", null);
        return res;
    }

    public int numberOfRows() {
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, CONTACTS_TABLE_NAME);
        return numRows;
    }

    public boolean updateContact(Integer id, String name, String phone, String email, String street, String place) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("phone", phone);
        contentValues.put("email", email);
        contentValues.put("street", street);
        contentValues.put("place", place);
        db.update("contacts", contentValues, "id = ? ", new String[]{Integer.toString(id)});
        return true;
    }

    public Integer deleteContact(String contactNumber) {
        SQLiteDatabase db = this.getWritableDatabase();
/*        return db.delete("contacts",
                "id = ? ",
                new String[]{});*/
        String where="phone=?";
        int numberOFEntriesDeleted= db.delete("contacts", where, new String[]{contactNumber}) ;
        return numberOFEntriesDeleted;
    }

    public ArrayList<ContactPerson> getAllCotacts() {
        ArrayList<ContactPerson> array_list = new ArrayList<ContactPerson>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res = db.rawQuery("select * from contacts", null);
        res.moveToFirst();

        while (res.isAfterLast() == false) {
            array_list.add(new ContactPerson(res.getColumnIndex(CONTACTS_COLUMN_ID),res.getString(res.getColumnIndex(CONTACTS_COLUMN_NAME)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_PHONE)),
                    res.getString(res.getColumnIndex(CONTACTS_COLUMN_MESSAGE)),
                    Boolean.valueOf(res.getString(res.getColumnIndex(CONTACTS_COLUMN_LOCSHARE)))));
            res.moveToNext();
        }
        return array_list;
    }
}