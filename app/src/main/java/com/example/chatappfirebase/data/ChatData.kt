package com.example.chatappfirebase.data

data class ChatData(
    val message: String,
    val time: String,
    val senderID: String,
) {
    // Add a no-argument constructor
    constructor() : this("", "", "")
}