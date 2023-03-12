package com.example.whatsapp69.Fragments

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp69.Adapters.ChatAdapter
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.FragmentChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class ChatFragment : Fragment() {
    lateinit var binding : FragmentChatBinding
    lateinit var database : FirebaseDatabase
    lateinit var auth : FirebaseAuth
    var userList = ArrayList<UsersModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        auth = FirebaseAuth.getInstance()
        binding = FragmentChatBinding.inflate(layoutInflater)
        val database = FirebaseDatabase.getInstance().reference.child("Users")

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(context)
        binding.userListRecyclerView.setLayoutManager(layoutManager)


        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var userList = mutableListOf<UsersModel>()
                for (userSnapshot in snapshot.children) {
                    val user = userSnapshot.getValue(UsersModel::class.java)
                    if (user != null) {
                        userList.add(user)
                    }
                }
                binding.userListRecyclerView.adapter = ChatAdapter(this@ChatFragment,userList)
            }

            override fun onCancelled(error: DatabaseError) {

                Log.e(TAG, "Error retrieving user data", error.toException())
            }
        })
        val adapter = ChatAdapter(this@ChatFragment, userList)
        binding.userListRecyclerView.setAdapter(adapter)

        return binding.root
    }
}