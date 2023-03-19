package com.example.whatsapp69.DataClasses

import com.google.firebase.auth.FirebaseAuth

data class StatusModel(
    val uid : String? = "",
    val vUrl : String? = "",
    val timeStamp : Long = 0,
){
    constructor() : this("","",0)


}
