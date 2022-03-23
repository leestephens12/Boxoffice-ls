package com.stephensapps.boxoffice_ls

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.SignInMethodQueryResult
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class RegisterActivity : AppCompatActivity() {
    //setting up val for the firebase authentication
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register_activity)

        //Initializing variable to get the current instance to create the new account
        auth = FirebaseAuth.getInstance()
        val db = Firebase.firestore
        //Initializing vals to get the user inputted text
        val email = findViewById<EditText>(R.id.emailEditText)
        val password = findViewById<EditText>(R.id.passwordEditText)
        val confirmPassword = findViewById<EditText>(R.id.confirmPasswordEditText)
        val errorText = findViewById<TextView>(R.id.errorTextView)
        val firstName = findViewById<EditText>(R.id.firstNameEditText)
        val lastName = findViewById<EditText>(R.id.lastNameEditText)
        val submit = findViewById<Button>(R.id.submitButton)
        val signIn = findViewById<Button>(R.id.signInButton)

        /**
         * Animations
         */

        email.alpha = 0f
        email.translationY = 75f
        email.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        password.alpha = 0f
        password.translationY = 75f
        password.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        confirmPassword.alpha = 0f
        confirmPassword.translationY = 75f
        confirmPassword.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        firstName.alpha = 0f
        firstName.translationY = 75f
        firstName.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        lastName.alpha = 0f
        lastName.translationY = 75f
        lastName.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        submit.alpha = 0f
        submit.translationY = 75f
        submit.animate().alpha(1f).translationYBy(-75f).setDuration(1500)

        signIn.alpha = 0f
        signIn.translationY = 75f
        signIn.animate().alpha(1f).translationYBy(-75f).setDuration(1500)


        //when the submit button is clicked it will pass the email and password to firebase to create a new user account
        submit.setOnClickListener {

            //Resetting Text colors to their original values
            email.setTextColor(Color.WHITE)
            password.setTextColor(Color.WHITE)
            confirmPassword.setTextColor(Color.WHITE)
            firstName.setTextColor(Color.WHITE)
            lastName.setTextColor(Color.WHITE)
            errorText.setTextColor(Color.RED)

            //Setting hints back to original color
            email.setHintTextColor(Color.WHITE)
            password.setHintTextColor(Color.WHITE)
            confirmPassword.setHintTextColor(Color.WHITE)
            firstName.setHintTextColor(Color.WHITE)
            lastName.setHintTextColor(Color.WHITE)

            //Error checking to make it more user friendly to show which fields are not filled out
            if(email.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                email.setHintTextColor(Color.RED)
            }
            else if(password.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                password.setHintTextColor(Color.RED)
            }
            else if(firstName.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                firstName.setHintTextColor(Color.RED)
            }
            else if(lastName.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                lastName.setHintTextColor(Color.RED)
            }
            else if(confirmPassword.text.toString().isEmpty()) {
                errorText.text = "Field must be complete"
                confirmPassword.setHintTextColor(Color.RED)
            }
            else if(password.text.toString() == confirmPassword.text.toString()) {
                //Checks all query results to see if email is already being used
                auth.fetchSignInMethodsForEmail(email.text.toString())
                    .addOnCompleteListener(this) { task: Task<SignInMethodQueryResult> ->
                        if(task.result?.signInMethods.isNullOrEmpty()) {
                            auth.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                                .addOnCompleteListener(this) { task: Task<AuthResult> ->
                                    if (task.isSuccessful) {
                                        Toast.makeText(baseContext, "Sign-up successful", Toast.LENGTH_SHORT)
                                            .show()

                                        val userID = FirebaseAuth.getInstance().currentUser.uid

                                        val user = hashMapOf(
                                            "firstName" to firstName.text.toString(),
                                            "lastName" to lastName.text.toString(),
                                            "email" to email.text.toString()
                                        )

                                        db.collection("users").document(userID)
                                            .set(user)
                                            .addOnSuccessListener { documentReference ->
                                                Toast.makeText(baseContext, "User added to firestore", Toast.LENGTH_SHORT)
                                                    .show()
                                            }
                                            .addOnFailureListener { e ->
                                                Toast.makeText(baseContext, e.toString(), Toast.LENGTH_SHORT)
                                                    .show()
                                            }

                                        //Bring you the the main activity once the registration is successful
                                        intent = Intent(this, MainActivity::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Toast.makeText(baseContext, "Sign-up unsuccessful", Toast.LENGTH_SHORT)
                                            .show()
                                    }
                                }.addOnFailureListener(this) { exception ->

                                    //If the registration fails it will output to exception directly from firebase and display it for the user
                                    errorText.text = exception.localizedMessage.toString()

                                    if(errorText.text.toString() == "The email address is badly formatted.") {
                                        email.setTextColor(Color.RED)
                                    }
                                }
                        }
                        else {
                            //if the email is already being used it will throw an error to the user
                            email.setTextColor(Color.RED)
                            errorText.text = "This Email is Already in Use"
                        }
                    }
            }
            else {
                //if the confirm password field does not match with the password field it will throw an error and display it
                errorText.text = "Passwords do Not Match"
                password.setTextColor(Color.RED)
                confirmPassword.setTextColor(Color.RED)
            }
        }

        //if user already has an account they can sign up here
        signIn.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}