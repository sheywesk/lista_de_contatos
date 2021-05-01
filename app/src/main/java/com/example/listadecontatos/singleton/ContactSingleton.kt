package com.example.listadecontatos.singleton

import com.example.listadecontatos.feature.listacontatos.model.ContactModel

object ContactSingleton {
   var list:MutableList<ContactModel> = mutableListOf();
}