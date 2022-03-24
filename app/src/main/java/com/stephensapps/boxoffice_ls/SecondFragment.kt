package com.stephensapps.boxoffice_ls

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SecondFragment:Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val post = view.findViewById<Button>(R.id.postBtn)
        val movieName = view.findViewById<EditText>(R.id.editTextMovieName)
        val rating = view.findViewById<RadioGroup>(R.id.rdoMovieRating)
        val description = view.findViewById<EditText>(R.id.editTextDescription)

        var auth: FirebaseAuth = FirebaseAuth.getInstance()
        val db = Firebase.firestore

        post.setOnClickListener {

            val userID = FirebaseAuth.getInstance().currentUser.uid

            val post = hashMapOf(
                "MovieName" to movieName.text.toString(),
                "description" to description.text.toString()
            )

            db.collection("posts").get()
                .addOnSuccessListener { document ->
                    if(document != null) {
                        val counter = document.size() + 1
                        db.collection("posts").document(userID + counter.toString())
                            .set(post)
                            .addOnSuccessListener { documentReference ->
                                Toast.makeText(context, "post added to firestore", Toast.LENGTH_SHORT)
                                    .show()
                            }
                            .addOnFailureListener { e ->
                                Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT)
                                    .show()
                            }
                    }else {
                        Toast.makeText(context, "No documents available", Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener{e->
                    Toast.makeText(context, e.toString(), Toast.LENGTH_SHORT).show()
                }
        }
    }

}