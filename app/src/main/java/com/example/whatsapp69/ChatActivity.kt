package com.example.whatsapp69

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.os.UserManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.bumptech.glide.Glide
import com.example.whatsapp69.DataClasses.MessageModel
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.ActivityChatBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
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

        supportActionBar?.hide()
        database = FirebaseDatabase.getInstance()
        senderUid = FirebaseAuth.getInstance().uid.toString()
        receiverUid = intent.getStringExtra("uid")!!
        senderRoom = senderUid + receiverUid
        receiverRoom = receiverUid + senderUid
        var userID = intent.getStringExtra("uid").toString()


        binding.backButtonChatActivity.setOnClickListener {
            onBackPressed()
        }

        database.reference.child("Users").child(userID)
            .addListenerForSingleValueEvent(object : ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user = snapshot.getValue(UsersModel::class.java)
                    val name = user!!.name!!
                    if (name.length >= 11) {
                        binding.userNameChatActiviy.text = name.substring(0,10) + "..."
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
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profile_menu -> {
               Toast.makeText(this@ChatActivity ,"Profile",Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.setting_menu -> {
                // Handle logout click
                return true
            }
            R.id.logOut_menu -> {
                // Handle logout click
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}