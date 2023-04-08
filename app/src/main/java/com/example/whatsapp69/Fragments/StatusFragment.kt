package com.example.whatsapp69.Fragments

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.R
import com.example.whatsapp69.StatusEditorActivity
import com.example.whatsapp69.databinding.FragmentStatusBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import java.util.*


class StatusFragment : Fragment() {
    private lateinit var binding: FragmentStatusBinding
    private lateinit var auth: FirebaseAuth
    val CAMERA_REQ_CODE = 100
    val GALARY_REQ_CODE = 101
    private var fileUri: Uri? = null
    private val userID = FirebaseAuth.getInstance().currentUser?.uid.toString()
    val database = FirebaseDatabase.getInstance().reference.child("Users").child(userID)
    val storyStorageRef = FirebaseStorage.getInstance().reference.child("Status").child(userID)

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            fileUri = data!!.data
            if (fileUri != null) {
                try {

                     navigateToActivityWithData(userID,fileUri.toString())
//                    val bundle = Bundle()
//                    bundle.putString("uid",userID)
//                    bundle.putString("status", fileUri.toString())
//                    val statusMediaFragment = StatusMediaFragment()
//                    statusMediaFragment.arguments = bundle
//                    val transaction = fragmentManager?.beginTransaction()
//                    transaction?.replace(R.id.status_container, statusMediaFragment)
//                    transaction?.addToBackStack(null)
//                    transaction?.commit()
                }catch (e : Exception){
                    Toast.makeText(context,"Error : $e",Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        auth = FirebaseAuth.getInstance()
        binding = FragmentStatusBinding.inflate(layoutInflater)
        val userId = FirebaseAuth.getInstance().currentUser?.uid.toString()
        val storageRef = FirebaseStorage.getInstance().reference.child("Status").child(userId)
        val fileRef = storageRef.child("status/${UUID.randomUUID()}")


        //  Getting User Data ...
        database.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(UsersModel::class.java)
                Glide.with(this@StatusFragment).load(user?.img)
                    .into(binding.userStstusImageStatusFragment)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })


        //  Add Story.......
        binding.statusLayout.setOnClickListener {
            addStory()

        }


        return binding.root
        return inflater.inflate(R.layout.fragment_status, container, false)

    }

    private fun addStory() {
        val videoIntent = Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI)
        videoIntent.type = "video/*,image/*"
        startActivityForResult(videoIntent, 1)
    }

    private fun navigateToActivityWithData(data: String, fPath : String) {
        val intent = Intent(requireContext(), StatusEditorActivity::class.java)
        intent.putExtra("uid", data)
        intent.putExtra("fPath",fPath)
        startActivity(intent)
    }
}
