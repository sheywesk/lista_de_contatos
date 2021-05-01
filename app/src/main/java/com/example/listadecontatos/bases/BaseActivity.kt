package com.example.listadecontatos.bases

import android.R
import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import com.example.listadecontatos.feature.contato.ContactActivity

open class BaseActivity: AppCompatActivity() {

    protected fun setupToolbar(toolbar: Toolbar, title:String, navigationBack:Boolean){
        toolbar.title = title;
        setSupportActionBar(toolbar);
        supportActionBar?.setDisplayHomeAsUpEnabled(navigationBack);
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.home ->{
                this.onBackPressed();
                return true;
            }
        }
        return super.onOptionsItemSelected(item)
    }
}