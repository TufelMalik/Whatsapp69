package com.example.whatsapp69

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.whatsapp69.Classes.Tufel
import com.example.whatsapp69.DataClasses.StatusModel
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.ActivityStatusEditorBinding
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.io.File

class StatusEditorActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStatusEditorBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStatusEditorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val tufel = Tufel()
        var player = SimpleExoPlayer.Builder(this).build()
        val fPath = intent.getStringExtra("fPath")
        val userId  = intent.getStringExtra("uid")
        actionBar?.hide()


        //      Displaying the Selected Image or Video
        if (fPath!!.endsWith(".jpg") || fPath.endsWith(".jpeg") || fPath.endsWith(".png")) {
            binding.imgViewStatusLayout.setImageURI(Uri.fromFile(File(fPath)))
        } else if (fPath.endsWith(".mp4") || fPath.endsWith(".3gp") || fPath.endsWith(".avi")) {
            binding.videoViewStatusLayout.visibility = View.VISIBLE
            binding.imgViewStatusLayout.visibility = View.GONE

            // release the player if it's already playing
            binding.videoViewStatusLayout.player?.release()

            // initialize the ExoPlayer instance
            binding.videoViewStatusLayout.player = player
            player.setMediaItem(MediaItem.fromUri(Uri.parse(fPath)))
            player.prepare()
            player.play()
        }



        binding.backButtonStatusEditor.setOnClickListener {
            stopVideo(player)
            onBackPressed()
        }
        val database = FirebaseDatabase.getInstance().reference.child("Status").child(userId.toString())

        //   Getting Data
        database.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UsersModel::class.java)
                binding.nameStatusEditor.setText(user?.name)
                Glide.with(this@StatusEditorActivity).load(user?.img).into(binding.userImgStatusEditor)
                Glide.with(this@StatusEditorActivity).load(fPath).into(binding.imgViewStatusLayout)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        //  Saving user Status
        binding.sentBtnStatusEditor.setOnClickListener {
            if(fPath != null){
                 val storageRef = FirebaseStorage.getInstance().reference.child("Status").child(userId.toString())
                storageRef.putFile(Uri.parse(fPath)).addOnCompleteListener {
                    storageRef.downloadUrl.addOnSuccessListener { url ->
                        val status = StatusModel(userId,url.toString(),System.currentTimeMillis())
                        database.setValue(status)
                            .addOnSuccessListener {
                                tufel.showProgressbar(this@StatusEditorActivity,"Status Uploading....")
                                onBackPressed()

                                Toast.makeText(this@StatusEditorActivity,"Status Updated Successfullly...",Toast.LENGTH_SHORT).show()
                            }
                            .addOnFailureListener {
                                Toast.makeText(this@StatusEditorActivity,"Something Went Wrong !!!",Toast.LENGTH_SHORT).show()

                            }
                    }
                }
                    .addOnFailureListener {
                        Toast.makeText(this@StatusEditorActivity,"Status Updating Failed...",Toast.LENGTH_SHORT).show()

                    }

            }
        }
    }

    private fun stopVideo(player: SimpleExoPlayer) {
        if (player != null) {
            player.release()
            player .stop()
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}