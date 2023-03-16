package com.example.whatsapp69.Adapters

import android.content.Intent
import android.media.ImageWriter.newInstance
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.whatsapp69.ChatActivity
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.Fragments.CallFragment.Companion.newInstance
import com.example.whatsapp69.Fragments.ChatFragment
import com.example.whatsapp69.Fragments.ShowClickedImageFragment
import com.example.whatsapp69.R
import com.example.whatsapp69.databinding.ChatUserItemLayoutBinding
import com.google.android.material.datepicker.MaterialCalendar.newInstance
import com.google.firebase.auth.FirebaseAuth
import javax.xml.datatype.DatatypeFactory.newInstance
import javax.xml.validation.SchemaFactory.newInstance

class ChatAdapter(var context: ChatFragment,var userList: List<UsersModel>): RecyclerView.Adapter<ChatAdapter.ChatViewHolder>(){

    class ChatViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        var binding : ChatUserItemLayoutBinding= ChatUserItemLayoutBinding.bind(view)
        var userImg : ImageView = itemView.findViewById(R.id.img_set_UserItem_Layout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        return ChatViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.chat_user_item_layout,parent , false))

    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        var user = userList[position]
        Glide.with(context).load(user.img).into(holder.binding.imgSetUserItemLayout)
        holder.binding.chatUserUserNameLayout.text = user.name

        holder.userImg.setOnClickListener {
            val showClickedImageFragment = ShowClickedImageFragment()
            val fragmentManager =(holder.itemView.context as AppCompatActivity).supportFragmentManager
            fragmentManager.beginTransaction().replace(R.id.main_container,showClickedImageFragment)
                .addToBackStack(null)
                .commit()

        }
        holder.itemView.setOnClickListener {
                val context = holder.itemView.context
                if (context != null) {
                    val intent = Intent(context, ChatActivity::class.java)
                    intent.putExtra("uid", user.userId)
                    context.startActivity(intent)
                }

        }
//        If upper Code not working than try this code .......
//
//        holder.itemView.setOnClickListener {
//            val context = holder.itemView.context.applicationContext
//            val intent = Intent(context, ChatActivity::class.java)
//            intent.putExtra("uid", user.userId)
//            context.startActivity(intent)
//        }

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}