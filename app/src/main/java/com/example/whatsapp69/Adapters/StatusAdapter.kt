package com.example.whatsapp69.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.whatsapp69.R
import javax.net.ssl.SSLEngineResult.Status

class StatusAdapter(statusList : List<Status>) : RecyclerView.Adapter<StatusAdapter.StatusViewHolder>() {
    class StatusViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
         private val statusImg : ImageView = itemView.findViewById(R.id.userIMG_Status)
        private val statusName : TextView = itemView.findViewById(R.id.userName_Status)
        private val statusTime : TextView = itemView.findViewById(R.id.statusTime_Status)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatusViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.status_layout_drawable,parent,false)
        return StatusViewHolder(view)
    }



    override fun onBindViewHolder(holder: StatusViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


}