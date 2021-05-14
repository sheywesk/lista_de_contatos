package com.example.listadecontatos

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadecontatos.application.ContactApplication
import com.example.listadecontatos.bases.BaseActivity
import com.example.listadecontatos.feature.contato.ContactActivity
import com.example.listadecontatos.feature.listacontatos.adapter.ContactAdapter
import com.example.listadecontatos.feature.listacontatos.model.ContactModel
import com.example.listadecontatos.singleton.ContactSingleton
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.toolbar.*

class MainActivity : BaseActivity() {
    private var adapter:ContactAdapter? = null

    var listFilter:MutableList<ContactModel> = mutableListOf();

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        generateContactList();
        setupToolbar(toolbar,"Lista de Contatos",false)
        setupRecyclerView();
        setupOnClicks()
        onChange()
    }

    override fun onResume() {
        super.onResume()
        generateContactList()
    }
    private fun setupOnClicks(){
        fab.setOnClickListener{onClickAdd()}
    }
    private fun generateContactList(){
        try {
            listFilter = (ContactApplication.instance.helperDB?.onSelect("") ?: mutableListOf<ContactModel>()) as MutableList<ContactModel>
            adapter = ContactAdapter(this,listFilter){ onClickItemRecyclerView(it) }
            recyclerView.adapter = adapter;
        }catch (ex:Exception){
            ex.printStackTrace();
        }

    }

    private fun setupRecyclerView(){
        recyclerView.layoutManager = LinearLayoutManager(this);
    }

    private fun onClickItemRecyclerView(item:Int){
        val intent = Intent( this, ContactActivity::class.java)
        intent.putExtra("index",item)
        startActivity(intent)

    }

    private fun onClickAdd(){
        val intent = Intent(this, ContactActivity::class.java)
        startActivity(intent)
    }

    private fun onChange() {

        val context = this
        val contactSingleton = ContactApplication.instance;
        searchEDT.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                try {
                    if(query != null){
                        listFilter = (contactSingleton.helperDB?.onSelect(query) ?: mutableListOf<ContactModel>()) as MutableList<ContactModel>

                    }
                }   catch (ex:Exception){

                    ex.printStackTrace()
                }
                adapter = ContactAdapter(context,listFilter){onClickItemRecyclerView(it)}
                recyclerView.adapter = adapter;
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
//                try {
//                    if(newText != null){
//                        listFilter = (contactInstance.helperDB?.onSelect(newText) ?: mutableListOf<ContactModel>()) as MutableList<ContactModel>
//                    }
//                }   catch (ex:Exception){
//                    ex.printStackTrace()
//                }
//                listFilter = ContactSingleton.list.filter{
//                    if (newText != null) {
//                        if(it.name.toLowerCase().contains(newText.toLowerCase())){
//                            return@filter true;
//                        }
//                    }
//                    return@filter false
//                }as MutableList<ContactModel>
//                adapter = ContactAdapter(context,listFilter){onClickItemRecyclerView(it)}
//                recyclerView.adapter = adapter;
                return false
            }
        })

    }

}