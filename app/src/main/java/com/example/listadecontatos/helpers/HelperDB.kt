package com.example.listadecontatos.helpers

import android.content.ContentValues
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
    val COLUMNS_ID = "id"
    val COLUMNS_NAME = "name"
    val COLUMNS_PHONE = "phone"
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

    fun onSelect(query: String,isById:Boolean=false): List<ContactModel>{

        val db = readableDatabase ?: return mutableListOf()
        var list = mutableListOf<ContactModel>()
        val sql:String;
        if(isById){
            sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMNS_ID LIKE $query"
        }else{
            sql = "SELECT * FROM $TABLE_NAME WHERE $COLUMNS_NAME LIKE '%$query%'"
        }
        var cursor = db.rawQuery(sql,null)
       if(cursor == null){
           return mutableListOf()
       }
        while (cursor.moveToNext()){
            var contact = ContactModel(
                    cursor.getInt(cursor.getColumnIndex(COLUMNS_ID)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_NAME)),
                    cursor.getString(cursor.getColumnIndex(COLUMNS_PHONE)),
            )
            list.add(contact);
        }
        db.close()
        return list;
    }

    fun onSave(contact:ContactModel){
        val db = writableDatabase?: return
        val content = ContentValues()
        content.put(COLUMNS_NAME,contact.name)
        content.put(COLUMNS_PHONE,contact.phone)
        db.insert(TABLE_NAME,null,content)
        db.close()
    }

    fun onDelete(id: Int) {
        val db = writableDatabase ?: return;
        val where = "id = ?"
        val arg = arrayOf("$id")
        db.delete(TABLE_NAME,where,arg)
        db.close()
    }
    fun onUpdate(contato: ContactModel){
        val db = writableDatabase?:return
        val content = ContentValues()
        content.put(COLUMNS_NAME,contato.name)
        content.put(COLUMNS_PHONE,contato.phone)
        val where = "id = ?"
        val arg = arrayOf("${contato.id}")
        db.update(TABLE_NAME,content,where,arg)
        db.close()
    }
}