package com.example.whatsapp69

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.whatsapp69.Fragments.ProfileFragment
import com.example.whatsapp69.Fragments.SettingFragment
import com.google.firebase.auth.FirebaseAuth

class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.profile_menu->{
                val profileFragement  = ProfileFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container, profileFragement)
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.setting_menu->{
                val settingFragment = SettingFragment()
                supportFragmentManager.beginTransaction()
                    .replace(R.id.main_container,settingFragment)
                    .addToBackStack(null)
                    .commit()
                return true
            }
            R.id.logOut_menu->{
                Toast.makeText(this@BaseActivity,"Log Out", Toast.LENGTH_SHORT).show()
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(this@BaseActivity,LoginActivity::class.java))
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }
}