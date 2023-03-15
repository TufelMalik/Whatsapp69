package com.example.whatsapp69.Fragments

import android.os.Bundle
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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

       var userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        var storageRef = FirebaseStorage.getInstance().reference.child("Users").child(userId)
        storageRef.downloadUrl.addOnSuccessListener {
            val imgUrl = it.toString()
            Glide.with(this@StatusFragment).load(imgUrl).into(binding.userStstusImageStatusFragment)
        }


        return  binding.root
        return inflater.inflate(R.layout.fragment_status, container, false)
    }



}