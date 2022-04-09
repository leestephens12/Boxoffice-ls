package com.stephensapps.boxoffice_ls

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SecondFragment:Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val post = view.findViewById<Button>(R.id.postBtn)
        val movieName = view.findViewById<EditText>(R.id.editTextMovieName)
        val rating = view.findViewById<EditText>(R.id.editTextRating)
        val description = view.findViewById<EditText>(R.id.editTextDescription)
        val errorText = view.findViewById<TextView>(R.id.errorText)

        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        post.setOnClickListener {
            //Toast.makeText(context, rating, Toast.LENGTH_LONG)
            if (movieName.text.isEmpty()) {
                errorText.text = "Field must be complete"
                movieName.setHintTextColor(Color.RED)
            } else if (rating.text.isEmpty()) {
                errorText.text = "Field must be complete"
                rating.setHintTextColor(Color.RED)
            } else if(Integer.parseInt(rating.text.toString()) > 100 || Integer.parseInt( rating.text.toString()) < 1) {
                errorText.text = "Rating must be between 1-100"
                rating.setHintTextColor(Color.RED)
            } else if (description.text.isEmpty()) {
                errorText.text = "Field must be complete"
                description.setHintTextColor(Color.RED)
            } else {
                val userID = FirebaseAuth.getInstance().currentUser!!.uid

                val post = hashMapOf(
                    "movieName" to movieName.text.toString(),
                    "movieRating" to rating.text.toString(),
                    "description" to description.text.toString()
                )


                /*
                Uploads users post to the db in a subclass collection
             */
                db.collection("posts").document()
                    .set(post)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(context, "post added to firestore", Toast.LENGTH_SHORT)
                            .show()
                        movieName.setText("")
                        description.setText("")
                        rating.setText("")


                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }


                /*
                    Adds the post to the individual user post section to view them on profile
                 */
                db.collection("users").document(userID).collection("posts").document()
                    .set(post)
                    .addOnSuccessListener { documentReference ->
                        Toast.makeText(context, "post added to firestore", Toast.LENGTH_SHORT)
                            .show()
                        movieName.setText("")
                        description.setText("")
                        rating.setText("")


                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT)
                            .show()
                    }

            }
        }
    }

}