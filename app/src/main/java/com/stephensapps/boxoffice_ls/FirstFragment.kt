package com.stephensapps.boxoffice_ls

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.*
import com.google.firebase.ktx.Firebase
import org.w3c.dom.Document

class FirstFragment:Fragment(R.layout.fragment_first) {

    private lateinit var recyclerView: RecyclerView
    private lateinit var postArrayList: ArrayList<Post>
    private lateinit var adapter: Adapter
    private lateinit var db: FirebaseFirestore


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = view.findViewById(R.id.recyclerViewFeed)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.setHasFixedSize(true)

        postArrayList = arrayListOf()

        adapter = Adapter(postArrayList)

        recyclerView.adapter = adapter

        EventChangeListener()
    }

    private fun EventChangeListener() {
        db = FirebaseFirestore.getInstance()
        db.collection("Posts").addSnapshotListener(object : EventListener<QuerySnapshot>{
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