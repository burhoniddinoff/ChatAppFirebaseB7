package com.example.chatappfirebase.data

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class UserData(
    var name: String? = null,
    var email: String? = null,
    var uid: String? = null,
) : Parcelable {
    // Add a no-argument constructor
    constructor() : this("", "", "")
}