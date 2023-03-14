package com.example.whatsapp69.Adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp69.DataClasses.MessageModel
import com.example.whatsapp69.R
import com.example.whatsapp69.databinding.ReceiverMessageLayoutBinding
import com.example.whatsapp69.databinding.SendMessageLayoutBinding
import com.google.firebase.auth.FirebaseAuth

class MessageAdapter(private val context: Context, private val list: ArrayList<MessageModel>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val ITEM_SEND = 1
    private val ITEM_RECEIVE = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ITEM_SEND) {
            SendViewHolder(LayoutInflater.from(context).inflate(R.layout.send_message_layout, parent, false))
        } else {
            ReceiverViewHolder(LayoutInflater.from(context).inflate(R.layout.receiver_message_layout, parent, false))
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (FirebaseAuth.getInstance().uid == list[position].senderId) ITEM_SEND else ITEM_RECEIVE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val message = list[position]
        when (getItemViewType(position)) {
            ITEM_SEND -> {
                (holder as SendViewHolder).binding.txtSentMessage.text = message.message

            }
            ITEM_RECEIVE -> {
                (holder as ReceiverViewHolder).binding.txtReceiveMessgae.text = message.message

            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class SendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = SendMessageLayoutBinding.bind(view)
    }

    inner class ReceiverViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ReceiverMessageLayoutBinding.bind(view)
    }
}
