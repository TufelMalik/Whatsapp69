package com.example.whatsapp69

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.whatsapp69.Adapters.MessageAdapter
import com.example.whatsapp69.DataClasses.MessageModel
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.util.*
import kotlin.collections.ArrayList

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var database : FirebaseDatabase
    private lateinit var senderUid : String
    private lateinit var receiverUid : String
    private lateinit var senderRoom : String
    private lateinit var receiverRoom: String

    private lateinit var list : ArrayList<MessageModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)


        supportActionBar?.hide()
        database = FirebaseDatabase.getInstance()
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!
        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        val userID = intent.getStringExtra("uid").toString()

        val layoutManager = LinearLayoutManager(this)
        binding.chatRecyclerView.setLayoutManager(layoutManager)
        list = ArrayList()

        database.reference.child("Chats").child(senderRoom).child("Message")
            .addValueEventListener(object  : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    list.clear()
                    for(snapshot1 in snapshot.children ){
                        val data = snapshot1.getValue(MessageModel::class.java)
                        list.add(data!!)

                    }
                    binding.chatRecyclerView.adapter = MessageAdapter(this@ChatActivity, list)

                }
                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        binding.chatRecyclerView.adapter?.notifyDataSetChanged()



        binding.backButtonChatActivity.setOnClickListener {
            onBackPressed()
        }

        list = ArrayList()
        database.reference.child("Users").child(userID)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue(UsersModel::class.java)
                    val name = user!!.name!!
                    if (name.length >= 10) {
                        binding.userNameChatActiviy.text = name.substring(0,7) + "..."
                    } else {
                        binding.userNameChatActiviy.text = name
                    }

                    Glide.with(this@ChatActivity).load(user.img).into(binding.userImgChatActivity)

                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })



        binding.btnSentMSGChat.setOnClickListener {
            if(!binding.etMessageChat.text.isEmpty()){
                val message = MessageModel(binding.etMessageChat.text.toString(),senderUid, Date().time)

                val randomKey = database.reference.push().key
                database.reference.child("Chats")
                    .child(senderRoom).child("Message").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {
                        database.reference.child("Chats").child(receiverRoom).child("Message").child(randomKey!!).setValue(message)
                            .addOnSuccessListener {
                                binding.etMessageChat.text = null
                                Toast.makeText(this@ChatActivity,"Message Sent.",Toast.LENGTH_SHORT).show()
                            }
                    }

            }else{
                binding.etMessageChat.error = "Type a Message first !"
            }
        }


    }

}