package com.example.listadecontatos.feature.contato

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.listadecontatos.R
import com.example.listadecontatos.application.ContactApplication
import com.example.listadecontatos.bases.BaseActivity
import com.example.listadecontatos.feature.listacontatos.model.ContactModel
import com.example.listadecontatos.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.toolbar.*
import java.lang.Exception

class ContactActivity:BaseActivity() {
    private var index = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact)
        setupToolbar(toolbar, "Contato",true)
        setupContact()
        save()
        delete()

    }

    private fun setupContact(){
        var index = intent.getIntExtra("index",-1);
        if(index == -1){
            deleteContactBTN.visibility = View.GONE
            return
        }
        var list:List<ContactModel> = ContactApplication.instance.helperDB?.onSelect("$index",true) ?: return
        var contact = list.getOrNull(0) ?: return
        nameContactEDT.setText(contact.name);
        numberContactEDT.setText(contact.phone);
    }

    private fun save(){
        var index = intent.getIntExtra("index",-1)
        saveContactBTN.setOnClickListener{
            val name = nameContactEDT.text.toString()
            val phone = numberContactEDT.text.toString();
            val contact = ContactModel(
               index,
                name,
                phone
            )
            if(name.isNotEmpty()){
                if(index == -1){
                    try{
                        ContactApplication.instance.helperDB?.onSave(contact)
                        println("CONTACT: ${contact.name}");
                    }catch (ex:Exception){
                        ex.printStackTrace()
                    }

                }else{
                    ContactApplication.instance.helperDB?.onUpdate(contact);
                }

                finish()
            }else {
                Toast.makeText(this, "Enter some message", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun delete(){
        deleteContactBTN.setOnClickListener{
            var index = intent.getIntExtra("index",1);
            ContactApplication.instance.helperDB?.onDelete(index.toInt())
            finish()
        }
    }

}