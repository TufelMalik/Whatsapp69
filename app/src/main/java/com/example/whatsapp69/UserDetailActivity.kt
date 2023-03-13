package com.example.whatsapp69

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsapp69.databinding.ActivityUserDetailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class UserDetailActivity : AppCompatActivity() {

    lateinit var imgUri: Uri
    lateinit var database: FirebaseDatabase
    lateinit var auth: FirebaseAuth
    lateinit var storage: FirebaseStorage
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (data.data != null) {
                imgUri = data.data!!
                binding.imgSetUserDetail.setImageURI(imgUri)

            }
        }
    }

    lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storage = FirebaseStorage.getInstance()
        database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()
        var progress = ProgressDialog(this)
        progress.setMessage("Uploading Pleas wait....")
        try {
            binding.btnSubmiteUserDetails.setOnClickListener {
                var bio = binding.etBioUserDetailed.text.toString()
                if (bio.isNotEmpty()) {
                    progress.show()
                    var userId = auth.uid.toString()
                    var imgRef = storage.reference.child("Users").child(userId)
                    imgRef.putFile(imgUri)
                        .addOnCompleteListener {
                            if (it.isSuccessful) {
                                imgRef.downloadUrl
                                    .addOnSuccessListener {
                                        val childUpdates = HashMap<String, Any>()
                                        childUpdates["name"] = binding.etNmaeUSerDetailed.text.toString()

                                        childUpdates["bio"] = bio
                                        childUpdates["img"] = it.toString()
                                             database.reference.child("Users").child(userId).updateChildren(childUpdates)
                                             .addOnSuccessListener {
                                                progress.dismiss()
                                                 startActivity(Intent(this@UserDetailActivity,MainActivity::class.java))
                                                Toast.makeText(
                                                    this@UserDetailActivity,
                                                    "Saved Successfully...",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                    }
                            }

                        }
                } else {
                    Toast.makeText(this@UserDetailActivity, "Pleas fill bio", Toast.LENGTH_SHORT)
                        .show()
                }


            }
        }catch (e: java.lang.Exception){
            Toast.makeText(this@UserDetailActivity,"Eroor is : $e",Toast.LENGTH_LONG).show()
        }
        binding.imgSetUserDetail.setOnClickListener {
            var intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.type = "image/*"
            startActivityForResult(intent, 1)


        }


    }

}
