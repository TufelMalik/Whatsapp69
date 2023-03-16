package com.example.whatsapp69.Fragments

import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.R
import com.example.whatsapp69.databinding.FragmentStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage

class StatusFragment : Fragment() {
    private lateinit var binding : FragmentStatusBinding
    private lateinit var auth : FirebaseAuth
    val database = FirebaseDatabase.getInstance().reference.child("Users")


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = FragmentStatusBinding.inflate(layoutInflater)

       val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("Users").child(userId)

        //  Getting User Data ...
        database.child(userId).addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UsersModel::class.java)
                Glide.with(this@StatusFragment).load(user?.img).into(binding.userStstusImageStatusFragment)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        binding.statusLayout.setOnClickListener {
            val videoIntent = Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(videoIntent,1)
        }


        return  binding.root
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

}