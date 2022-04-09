package com.stephensapps.boxoffice_ls

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Document

class FirstFragment:Fragment(R.layout.fragment_first) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var adapter: Adapter
    private lateinit var db: FirebaseFirestore
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?{
        val view = inflater.inflate(R.layout.fragment_first,container,false)

        recyclerView = view.findViewById(R.id.recyclerViewFeed)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.setHasFixedSize(true)

        postArrayList = arrayListOf()

        adapter = Adapter(postArrayList)

        recyclerView.adapter = adapter

        EventChangeListener()

        return view;
    }

    private fun EventChangeListener() {
        auth = FirebaseAuth.getInstance()
        val email = FirebaseAuth.getInstance().currentUser?.email
        val userID = FirebaseAuth.getInstance().currentUser!!.uid
        db = FirebaseFirestore.getInstance()
        db.collection("posts").orderBy("movieName").addSnapshotListener(object : EventListener<QuerySnapshot>{
            override fun onEvent(value: QuerySnapshot?, error: FirebaseFirestoreException?) {

                if(error != null) {

                    Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT)
                    return
                }

                for(i : DocumentChange in value?.documentChanges!!) {

                    if(i.type == DocumentChange.Type.ADDED) {
                        postArrayList.add(i.document.toObject(Post::class.java))
                    }
                }

                adapter.notifyDataSetChanged()

            }

        })
    }
}
