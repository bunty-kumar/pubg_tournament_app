package com.bunty.pubgtournament.AdminApp.delete

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bunty.pubgtournament.AdminApp.DeleteMatchAdapter
import com.bunty.pubgtournament.R
import com.bunty.pubgtournament.AdminApp.match.DataClass
import com.google.firebase.database.*

class EveningActivity : AppCompatActivity() {

    lateinit var rvDelete: RecyclerView
    lateinit var deleteAdapter: DeleteMatchAdapter
    var deleteList = ArrayList<DataClass>()

    lateinit var reference: DatabaseReference
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_evening)
        supportActionBar?.setTitle("Evening Match")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progressBar)
        reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("Evening")
        rvDelete = findViewById(R.id.rvEvening)
        rvDelete.layoutManager = LinearLayoutManager(this)
        rvDelete.setHasFixedSize(true)

        getData()
    }

    private fun getData() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1: DataSnapshot in snapshot.children) {
                    var data = snapshot1.getValue(DataClass::class.java)
                    deleteList.add(0, data!!)

                }
                deleteAdapter = DeleteMatchAdapter(deleteList, this@EveningActivity)
                rvDelete.adapter = deleteAdapter
                progressBar.isVisible = false
                deleteAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.isVisible = false
                Toast.makeText(this@EveningActivity, "$error", Toast.LENGTH_SHORT).show()
            }

        })
    }
}