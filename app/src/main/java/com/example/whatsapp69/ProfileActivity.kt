package com.example.whatsapp69

import android.app.Dialog
import android.app.ProgressDialog
import android.app.WallpaperManager
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
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

class ProfileActivity : AppCompatActivity()
{

    val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
    private lateinit var binding : ActivityProfileBinding
    lateinit var imgUri : Uri
    lateinit var oldImgUri : Uri

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setTitle("Profile")
        val dialog =Dialog(this@ProfileActivity)
        dialog.setContentView(R.layout.edit_profile_dialog)
        var etName : EditText = dialog.findViewById(R.id.etName_Dialog)
        var etBio : EditText = dialog.findViewById(R.id.etBio_Dialog)

        val progress = ProgressDialog(this@ProfileActivity)
        progress.setMessage("Updating Your Profile...")

        val saveBtn : Button = dialog.findViewById(R.id.btnSave_Dialog)
        saveBtn.setOnClickListener {
            val userNewName = etName.text.toString()
            binding.txtUSerNameProfile.text = userNewName
            val userNewBio = etBio.text.toString()
            binding.txtUserBioProfile.text = userNewBio
            dialog.dismiss()
        }

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
        database.child(userID).addValueEventListener(object  : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UsersModel::class.java)
                binding.txtUSerNameProfile.text = user?.name.toString()
                binding.txtUserBioProfile.text = user?.bio.toString()
                binding.txtUserEmailProfile.text = user?.email.toString()

                imgUri = Uri.parse(user!!.img)
                Glide.with(this@ProfileActivity).load(user?.img).into(binding.userIMGProfile)

            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })




        binding.txtEditProfile.setOnClickListener {
            dialog.show()
            etName.requestFocus()
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)

        }

        binding.btnSaveProfile.setOnClickListener {
            progress.show()
            if(imgUri.equals(null)) {
                imgUri = oldImgUri
                imgRef.putFile(imgUri).addOnCompleteListener {
                    imgRef.downloadUrl.addOnSuccessListener {
                        val updatedUserData = HashMap<String, Any>()
                        updatedUserData["name"] = binding.txtUSerNameProfile.text.toString()
                        updatedUserData["bio"] = binding.txtUserBioProfile.text.toString()
                        updatedUserData["img"] = it.toString()
                        database.child(userID).updateChildren(updatedUserData)
                            .addOnSuccessListener {
                                progress.dismiss()
                                Toast.makeText(
                                    this,
                                    "Data Updated Successfully...",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }.addOnFailureListener {
                                progress.dismiss()
                                Toast.makeText(this, "Getting Error...", Toast.LENGTH_SHORT).show()
                            }
                    }
                        .addOnFailureListener {
                            Toast.makeText(this, "No Data Updated !", Toast.LENGTH_SHORT).show()
                        }
                }
                    .addOnFailureListener {
                        Toast.makeText(this, "No Data Updated !", Toast.LENGTH_SHORT).show()
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