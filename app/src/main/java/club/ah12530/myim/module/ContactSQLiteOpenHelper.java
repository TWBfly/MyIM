package club.ah12530.myim.module;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class ContactSQLiteOpenHelper extends SQLiteOpenHelper {
    public static final String CONTACT = "contact";
    private static final String DB_NAME = "contacts.db";
    private static final int VERSION = 1;
    public static final String T_CONTACT = "t_contact";
    public static final String USERNAME = "username";
    public ContactSQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public ContactSQLiteOpenHelper(Context context){
        super(context,DB_NAME,null,VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + T_CONTACT + "(_id integer primary key," + USERNAME + " varchar(20)," + CONTACT + " varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
