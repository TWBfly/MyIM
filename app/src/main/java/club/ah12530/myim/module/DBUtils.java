package club.ah12530.myim.module;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import club.ah12530.myim.MyApplication;

public class DBUtils {

    //通过username获取到联系人
    public static List<String> getContacts(String username){
        ContactSQLiteOpenHelper openHelper = new ContactSQLiteOpenHelper(MyApplication.getContext());
        SQLiteDatabase database = openHelper.getReadableDatabase();
        Cursor cursor = database.query(ContactSQLiteOpenHelper.T_CONTACT, new String[]{ContactSQLiteOpenHelper.CONTACT}, ContactSQLiteOpenHelper.USERNAME + "=?", new String[]{username}, null, null, ContactSQLiteOpenHelper.CONTACT);
        List<String> contactsList = new ArrayList<>();
        while (cursor.moveToNext()){
            String contact = cursor.getString(0);
            contactsList.add(contact);
        }
        cursor.close();
        database.close();
        return contactsList;
    }

    //先删除username的所有的联系人
    //再添加contactsList添加进去
    public static void updateContacts(String username,List<String> contactsList){
        ContactSQLiteOpenHelper openHelper = new ContactSQLiteOpenHelper(MyApplication.getContext());
        SQLiteDatabase database = openHelper.getWritableDatabase();
        database.beginTransaction();//开启事物

        database.delete(ContactSQLiteOpenHelper.T_CONTACT,ContactSQLiteOpenHelper.USERNAME+"=?",new String[]{username});
        ContentValues values = new ContentValues();
        values.put(ContactSQLiteOpenHelper.USERNAME,username);
        for (int i = 0; i < contactsList.size(); i++) {
            String contact = contactsList.get(i);
            values.put(ContactSQLiteOpenHelper.CONTACT,contact);
            database.insert(ContactSQLiteOpenHelper.T_CONTACT,null,values);
        }

        database.setTransactionSuccessful();//关闭事物
        database.endTransaction();
        database.close();
    }



}
