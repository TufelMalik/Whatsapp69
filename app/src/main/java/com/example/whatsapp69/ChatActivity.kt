package com.example.whatsapp69

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.example.whatsapp69.DataClasses.MessageModel
import com.example.whatsapp69.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import java.util.*

class ChatActivity : AppCompatActivity() {
    lateinit var binding : ActivityChatBinding
    lateinit var database : FirebaseDatabase
    private lateinit var senderUid : String
    private lateinit var receiverUid : String
    private lateinit var senderRoom : String
    private lateinit var receiverRoom: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = FirebaseDatabase.getInstance()

        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!

        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid

        binding.btnSentMSGChat.setOnClickListener {
            if(!binding.etMessageChat.text.isEmpty()){
                val message = MessageModel(binding.etMessageChat.text.toString(),senderUid, Date().time)

                val randomKey = database.reference.push().key
                database.reference.child("Chats")
                    .child(senderRoom).child("Messages").child(randomKey!!).setValue(message)
                    .addOnSuccessListener {


                        database.reference.child("Chats").child(receiverRoom).child("Messages").child(randomKey!!).setValue(message)
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