package com.stephensapps.boxoffice_ls

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        val firstFragment = FirstFragment();
        val secondFragment = SecondFragment();
        val thirdFragment = ThirdFragment();
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        setCurrentFragment(thirdFragment);

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navIcon->setCurrentFragment(firstFragment)
                R.id.addIcon->setCurrentFragment(secondFragment)
                R.id.profileIcon->setCurrentFragment(thirdFragment)
            }
            true
        }
    }

    private fun setCurrentFragment(fragment:Fragment)=
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment,fragment)
            commit()
        }
}