package com.stephensapps.boxoffice_ls

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity);

        val user = FirebaseAuth.getInstance().currentUser;
        Toast.makeText(baseContext, user.toString(), Toast.LENGTH_SHORT)
            .show()
        if(user != null) {

        }
    }
}