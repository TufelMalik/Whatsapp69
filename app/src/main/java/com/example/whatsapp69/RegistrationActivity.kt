package com.example.whatsapp69

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.whatsapp69.DataClasses.UsersModel
import com.example.whatsapp69.databinding.ActivityRegistrationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistrationActivity : AppCompatActivity() {
    lateinit var binding : ActivityRegistrationBinding
    lateinit var auth : FirebaseAuth
    lateinit var database : FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        var progressDialog = ProgressDialog(this)
        progressDialog.setMessage("We are creating your account.\nPleas wait...")
        binding.txtGotoLogin.setOnClickListener {
            startActivity(Intent(this@RegistrationActivity,LoginActivity::class.java))
        }

        binding.btnReg.setOnClickListener {
            var email = binding.etEmailReg.text.toString()
            var pass = binding.etPassReg.text.toString()
            var conPass = binding.etConfirmPassReg.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty() && conPass.isNotEmpty()){
                if(pass == conPass){
                    progressDialog.show()         //starting Progress
                    auth.createUserWithEmailAndPassword(email,pass)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful){
                                progressDialog.dismiss()
                                database.reference.child("Users").child(auth.uid.toString()).setValue(
                                    UsersModel(
                                       email,pass
                                    )
                                )
                                startActivity(Intent(this@RegistrationActivity,UserDetailActivity::class.java))
                                Toast.makeText(this@RegistrationActivity,"Registration Completed...",Toast.LENGTH_SHORT).show()
                            }else{
                                progressDialog.dismiss()
                                Toast.makeText(this@RegistrationActivity,"Registration Faild !",Toast.LENGTH_SHORT).show()
                            }
                        }
                }else{
                    Toast.makeText(this@RegistrationActivity,"Password Does Not Match !",Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this@RegistrationActivity,"Pleas fill all crateria !!!",Toast.LENGTH_SHORT).show()
            }
        }

        if(auth.currentUser != null){
            startActivity(Intent(this@RegistrationActivity,MainActivity::class.java))
        }





    }
}