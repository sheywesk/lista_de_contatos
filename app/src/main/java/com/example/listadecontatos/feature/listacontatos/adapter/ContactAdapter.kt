package com.example.listadecontatos.feature.listacontatos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView

import androidx.recyclerview.widget.RecyclerView
import com.example.listadecontatos.R
import com.example.listadecontatos.feature.listacontatos.model.ContactModel
import kotlinx.android.synthetic.main.item_contact.view.*

class ContactAdapter(private val context:Context, private val list: List<ContactModel>, private val onClick: ((Int)-> Unit)):
    RecyclerView.Adapter<ContactViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_contact,parent,false)
        return ContactViewHolder(view);
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
       val contact = list[position];
        with(holder.itemView){
            nameTXT.text = contact.name
            numberTXT.text = contact.phone
            llItem.setOnClickListener { onClick(contact.id) }
        }
    }

    override fun getItemCount () = list.size;

}


class ContactViewHolder(itemView: View): RecyclerView.ViewHolder(itemView);