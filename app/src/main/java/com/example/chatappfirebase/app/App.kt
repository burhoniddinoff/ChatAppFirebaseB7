package com.example.chatappfirebase.app

import android.app.Application
import com.example.chatappfirebase.domain.MySharedPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MySharedPref.init(this)
    }

}