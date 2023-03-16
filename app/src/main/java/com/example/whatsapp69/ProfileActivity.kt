package com.example.whatsapp69

import android.content.ContentValues
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.widget.EditText
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.ActivityProfileBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class ProfileActivity : AppCompatActivity() {

    val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private lateinit var binding : ActivityProfileBinding
    private lateinit var imgUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Whatsapp69")


        val auth = FirebaseAuth.getInstance().currentUser!!
        val database = FirebaseDatabase.getInstance().reference.child("Users")

        // Selecting or Changing the Image by clicking the Image...
        binding.btnSelectIMGProfile.setOnClickListener {
            var intent = Intent()
            intent.type = "image/*"
            intent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(intent, 1)
        }


        val imgRef = FirebaseStorage.getInstance().reference.child("Users").child(userID)

        //  Getting Current User Data From Friebase Database..
        database.child(userID).addValueEventListener(object  : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UsersModel::class.java)
                binding.etUSerNameProfile.text = user?.name.toString()
                binding.etUserBioProfile.text = user?.bio.toString()
                imgRef.downloadUrl.addOnSuccessListener {
                    try {
                        Glide.with(this@ProfileActivity).load(user?.img)
                            .into(binding.userIMGProfile)
                    }catch (e : Exception){
                        TODO("Erroo Profile Activity Line Number : 59  == "+e.toString())
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




        binding.nameEditButtonProfile.setOnClickListener {

        }
        binding.bioEditButtonProfile.setOnClickListener {

        }
        binding.btnSaveProfile.setOnClickListener {
            imgRef.putFile(imgUri).addOnCompleteListener {
                imgRef.downloadUrl.addOnSuccessListener {
                    val updatedUserData = HashMap<String,Any>()
                    updatedUserData["name"] = binding.etUSerNameProfile.text.toString()
                    updatedUserData["bio"] = binding.etUserBioProfile.text.toString()
                    updatedUserData["img"] = it.toString()
                    database.child(userID).updateChildren(updatedUserData).addOnSuccessListener {
                        Toast.makeText(this,"Data Updated Successfully...",Toast.LENGTH_SHORT).show()
                    }.addOnFailureListener {
                        Toast.makeText(this,"Getting Error...",Toast.LENGTH_SHORT).show()

                    }
                    Toast.makeText(this,"Save",Toast.LENGTH_SHORT).show()
                }
            }

                }
            }

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