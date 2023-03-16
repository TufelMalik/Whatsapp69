package com.example.whatsapp69.DataClasses

import com.google.firebase.auth.FirebaseAuth

data class StatusModel(
    val uid : String? = "",
    val vUrl : String? = "",
    val expiryTime : Long = 0,
)
