package com.example.chatappfirebase.domain

import android.content.Context
import android.content.SharedPreferences

object MySharedPref {
    private lateinit var sharedPreferences: SharedPreferences

    fun init(context: Context) {
        sharedPreferences = context.getSharedPreferences("Contact", Context.MODE_PRIVATE)
    }

    fun setPhone(phone: String) {
        sharedPreferences.edit().putString("phone", phone).apply()
    }

    fun setToken(token: String) {
        sharedPreferences.edit().putString("token", token).apply()
    }

    fun getPhone(): String = sharedPreferences.getString("phone", "^^")!!

    fun getToken(): String = sharedPreferences.getString("token", "%%")!!

}