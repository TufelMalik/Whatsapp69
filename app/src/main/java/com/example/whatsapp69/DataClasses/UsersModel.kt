package com.example.whatsapp69.DataClasses

data class UsersModel(
    var userId : String?,
    var name : String?,
    var email : String? ,
    var pass : String?,
    var img : String?,
    var status : String?,
    var bio : String?
    )
{
    constructor() :this(null, "", "", "", "", "", "")
}