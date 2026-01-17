package com.anibalofice.loteria

import android.app.Application
import com.anibalofice.loteria.data.AppDatabase

class App: Application() {

    //inicializar depois
    lateinit var db: AppDatabase

    override fun onCreate() {
        super.onCreate()
        db = AppDatabase.getInstance(this)

    }
}