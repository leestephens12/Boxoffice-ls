package com.stephensapps.boxoffice_ls

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)

        //get current instance of firebase
        auth = FirebaseAuth.getInstance()
        //Creating vals for fields in the view
        val submit = findViewById<Button>(R.id.submitButton)
        val register = findViewById<Button>(R.id.registerButton)
        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val errorText = findViewById<TextView>(R.id.errorTextView)
        val logo = findViewById<ImageView>(R.id.logoImage)
        val google = findViewById<ImageButton>(R.id.googleBtn)

        /**
         *Animations
         **/
        submit.alpha = 0f
        submit.translationY = 75f
        submit.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        google.alpha = 0f
        google.translationY = 75f
        google.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        register.alpha = 0f
        register.translationY = 75f
        register.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        email.alpha = 0f
        email.translationY = 75f
        email.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        password.alpha = 0f
        password.translationY = 75f
        password.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        logo.alpha = 0f
        logo.translationY = 75f
        logo.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        submit.setOnClickListener {

            //setting default colors to text
            /*email.setHintTextColor(Color.WHITE)
            password.setHintTextColor(Color.WHITE)
            email.setTextColor(Color.WHITE)
            password.setTextColor(Color.WHITE)*/
            errorText.setTextColor(Color.RED)

            if(email.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                email.setHintTextColor(Color.RED)
            }
            else if(password.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                password.setHintTextColor(Color.RED)
            }
            else {
                //using the firebase auth to sign in with email and password input by user
                auth.signInWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task: Task<AuthResult> ->
                        if (task.isSuccessful) {
                            Toast.makeText(baseContext, "Login successful", Toast.LENGTH_SHORT)
                                .show()

                            //after login is successful redirect to the main page
                            intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()

                        } else {
                            Toast.makeText(baseContext, "Login unsuccessful", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }.addOnFailureListener(this) { exception ->
                        errorText.text = exception.localizedMessage.toString()

                        //if the error text has password in it password will show in red or else email will show in red
                        if (errorText.text.contains("password")) {
                            password.setTextColor(Color.RED)
                        } else {
                            email.setTextColor(Color.RED)
                        }
                    }

            }
        }

        //if users don't have an account they can register here
        register.setOnClickListener {
            intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}