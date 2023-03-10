package com.example.whatsapp69

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import com.example.whatsapp69.DataClasses.Users
import com.example.whatsapp69.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var binding : ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()

        var loginProgressDialog = ProgressDialog(this)
        loginProgressDialog.setMessage("Login your account .....")

        binding.btnLogin.setOnClickListener {
            var email = binding.etEmailLogin.text.toString()
            var pass = binding.etPassLogin.text.toString()
            if(email.isNotEmpty() && pass.isNotEmpty()){
                    loginProgressDialog.show()         //starting Progress
                    auth.signInWithEmailAndPassword(email,pass)
                        .addOnCompleteListener { task->
                            if(task.isSuccessful){
                                loginProgressDialog.dismiss()
                                startActivity(Intent(this@LoginActivity,MainActivity::class.java))
                                Toast.makeText(this@LoginActivity,"Login Successfully...",
                                    Toast.LENGTH_SHORT).show()
                            }else{
                                loginProgressDialog.dismiss()
                                Toast.makeText(this@LoginActivity,"Could Not Found Your Account !\nPleas SignUp first...",
                                    Toast.LENGTH_SHORT).show()
                            }
                        }
                        .addOnFailureListener {
                            loginProgressDialog.dismiss()
                            Toast.makeText(this@LoginActivity,"Login Faild !\nPleas try again later...",
                                Toast.LENGTH_SHORT).show()
                        }
            }else{
                Toast.makeText(this@LoginActivity,"Invalid Email or Password !!!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.txtGotoRegistration.setOnClickListener {
            startActivity(Intent(this@LoginActivity,RegistrationActivity::class.java))
        }
    }
}