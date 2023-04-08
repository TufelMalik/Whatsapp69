package com.example.whatsapp69.Classes

import android.app.ProgressDialog
import android.content.Context
import android.widget.Toast

class Tufel {
    fun showToast(context : Context,msg : String){
        Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
    }
    fun showProgressbar(context : Context,msg : String,title : String? = null) {
        val progress = ProgressDialog(context)
        progress.setMessage(msg)
        progress.setTitle(title)

    }
    fun hiderogressbar(progressBar : ProgressDialog){
        progressBar.dismiss()
    }
}