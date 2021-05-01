package com.example.listadecontatos.helpers

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.listadecontatos.feature.listacontatos.model.ContactModel

class HelperDB(context: Context?):SQLiteOpenHelper(context, BANK_NAME,null, VERSION) {
    companion object {
        private val BANK_NAME = "contato.db"
        private val VERSION = 1
    }

    val TABLE_NAME = "contato"
    val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    val COLUMNS_ID = ""
    val COLUMNS_NAME = ""
    val COLUMNS_PHONE = ""
    val CREATE_TABLE = "CREATE TABLE $TABLE_NAME (" +
            "$COLUMNS_ID INTEGER NOT NULL,"+
            "$COLUMNS_NAME TEXT NOT NULL,"+
            "$COLUMNS_PHONE TEXT NOT NULL,"+
            ""+
            "PRIMARY KEY($COLUMNS_ID AUTOINCREMENT)"+
            ")"
    override fun onCreate(db: SQLiteDatabase?) {
       db?.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if(oldVersion != newVersion){
            db?.execSQL(DROP_TABLE)
               onCreate(db)
        }
    }

    fun onSelect(query: String): List<ContactModel>{
        val db = readableDatabase ?: return mutableListOf()
        var list = mutableListOf<ContactModel>()
        val sql = "SELECT * FROM $TABLE_NAME"
        var cursor = db.rawQuery(sql,null) ?: return mutableListOf()
        while (cursor.moveToNext()){
            var contact = ContactModel(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_PHONE)),
            )
            list.add(contact);
        }
        return list;
    }
}