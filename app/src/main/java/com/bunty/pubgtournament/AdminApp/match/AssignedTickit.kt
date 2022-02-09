package com.bunty.pubgtournament.AdminApp.match

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bunty.pubgtournament.R
import com.google.firebase.database.*

class AssignedTickit : AppCompatActivity() {

    lateinit var rvRefer: RecyclerView
    lateinit var referAdapter: ReferenceAdapter
    var referList = ArrayList<ReferenceModel>()

    lateinit var reference: DatabaseReference
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_assigned_tickit)

        progressBar = findViewById(R.id.progressBar)
        reference = FirebaseDatabase.getInstance().getReference().child("Reference Number")
        rvRefer = findViewById(R.id.rvReference)
        rvRefer.layoutManager = GridLayoutManager(this,2)
        rvRefer.setHasFixedSize(true)

        getData()

    }

    private fun getData() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1: DataSnapshot in snapshot.children) {
                    var data = snapshot1.getValue(ReferenceModel::class.java)
                    referList.add(0, data!!)

                }
                referAdapter = ReferenceAdapter(referList)
                rvRefer.adapter = referAdapter
                progressBar.isVisible = false
                referAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.isVisible = false
                Toast.makeText(this@AssignedTickit, "$error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}