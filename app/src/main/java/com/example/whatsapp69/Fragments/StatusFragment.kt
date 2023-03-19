package com.example.whatsapp69.Fragments

import android.app.Activity
import android.content.Intent
import android.media.browse.MediaBrowser.MediaItem
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.whatsapp69.DataClasses.StatusModel
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.R
import com.example.whatsapp69.databinding.FragmentStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding
    private lateinit var auth: FirebaseAuth
    val CAMERA_REQ_CODE = 100
    val GALARY_REQ_CODE = 101
    var imgUri: Uri? = null
    var videoUri: Uri? = null
    private val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val database = FirebaseDatabase.getInstance().reference.child("Users").child(userID)
    val storyStorageRef = FirebaseStorage.getInstance().reference.child("Status").child(userID)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when(resultCode){
                CAMERA_REQ_CODE ->{
                    imgUri = data?.data
                    if(imgUri != null){
                        val imgRef = storyStorageRef.child("Image/${UUID.randomUUID()}")
                        imgRef.putFile(imgUri!!).addOnSuccessListener { uri->
                            database.child("Status").push().setValue(StatusModel(userID,imgUri.toString(),System.currentTimeMillis()))
                        }
                    }
                }
                GALARY_REQ_CODE ->{
                    videoUri = data?.data
                    if(videoUri != null){
                        val videoRef = if(videoUri.toString().contains("image")) {
                            storyStorageRef.child("image/${UUID.randomUUID()}.jpg")
                        }else{
                            storyStorageRef.child("video/${UUID.randomUUID()}.mp4")
                        }
                        videoRef.putFile(videoUri!!).addOnSuccessListener { uri->
                            val videoType = if(videoUri.toString().contains("image")) "image" else "video"
                            database.child("Status").push().setValue(StatusModel(userID,videoUri.toString(),System.currentTimeMillis()))
                            //Toast.makeText(this@StatusFragment,"Video Done",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        binding = FragmentStatusBinding.inflate(layoutInflater)
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storageRef =
            FirebaseStorage.getInstance().reference.child("Status").child(userId)
        val videoRef = storageRef.child("video/${UUID.randomUUID()}mp4")
        val imageRef = storageRef.child("image/${UUID.randomUUID()}jpg")


        //  Getting User Data ...
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UsersModel::class.java)
                Glide.with(this@StatusFragment).load(user?.img).into(binding.userStstusImageStatusFragment)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //  Save User Status on Firebase


        //  Add Story.......
        binding.statusLayout.setOnClickListener {
           addStory()
        }


        return binding.root
        return inflater.inflate(R.layout.fragment_status, container, false)
    }


    private fun addStory() {
        val videoIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        videoIntent.type = "video/*,image/*"
        startActivityForResult(videoIntent, 1)
    }
}