package com.example.whatsapp69

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.whatsapp69.Adapters.ChatAdapter
import com.example.whatsapp69.DataClasses.MessageModel
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.net.URI

class ProfileActivity : AppCompatActivity() {

    val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private lateinit var binding : ActivityProfileBinding
    private lateinit var auth : FirebaseAuth
    private lateinit var imgUri : Uri
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val database = FirebaseDatabase.getInstance().reference.child("Users")
        val storage = FirebaseStorage.getInstance().reference.child("Users").child(userID)
        binding.btnSelectIMGProfile.setOnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent,1)
        }

        val imgRef = FirebaseStorage.getInstance().reference.child("Users").child(userID)

        database.child(userID).addListenerForSingleValueEvent(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {

        }

        override fun onCancelled(error: DatabaseError) {

            Log.e(ContentValues.TAG, "Error retrieving user data", error.toException())
        }
    })




    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(data != null){
            if(data.data != null){
                imgUri = data.data!!
                binding.userIMGProfile.setImageURI(imgUri)
            }
        }
        super.onActivityResult(requestCode, resultCode, data)

    }
}