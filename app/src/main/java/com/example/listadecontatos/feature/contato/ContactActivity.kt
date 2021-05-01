package com.example.listadecontatos.feature.contato

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import com.example.listadecontatos.R
import com.example.listadecontatos.bases.BaseActivity
import com.example.listadecontatos.feature.listacontatos.model.ContactModel
import com.example.listadecontatos.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_contact.*
import kotlinx.android.synthetic.main.toolbar.*

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
        nameContactEDT.setText(ContactSingleton.list[index].name);
        numberContactEDT.setText(ContactSingleton.list[index].phone);
    }

    private fun save(){
        val contact = ContactModel(
                0,
                nameContactEDT.text.toString(),
                numberContactEDT.text.toString()
        )

        saveContactBTN.setOnClickListener{
            if(index == -1){

                ContactSingleton.list.add(contact)
            }else{
                ContactSingleton.list[index] = contact
            }
            finish()
        }

    }

    private fun delete(){
        deleteContactBTN.setOnClickListener{
            var index = intent.getIntExtra("index",1);
            ContactSingleton.list.removeAt(index)
            finish()
        }
    }

}