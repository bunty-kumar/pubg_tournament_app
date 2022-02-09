package com.bunty.pubgtournament.UserApp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bunty.pubgtournament.AdminApp.DeleteMatchAdapter
import com.bunty.pubgtournament.AdminApp.match.DataClass
import com.bunty.pubgtournament.R
import com.google.firebase.database.*

class UserMorning : AppCompatActivity() {

    lateinit var rvDelete: RecyclerView
    lateinit var deleteAdapter: UserAdapter
    var deleteList = ArrayList<UserDataClass>()

    lateinit var reference: DatabaseReference
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_morning)

        supportActionBar?.setTitle("Morning Match")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        progressBar = findViewById(R.id.progressBar)
        reference = FirebaseDatabase.getInstance().getReference().child("Matches").child("Morning")
        rvDelete = findViewById(R.id.rvMorning)
        rvDelete.layoutManager = LinearLayoutManager(this)
        rvDelete.setHasFixedSize(true)
        /*var deleteData = DeleteMatchModel("abc","abc","abc","abc"
            ,"abc","abc","anc","abc","anc","abc",
            "abc","abc","anc")
        deleteList.add(deleteData)*/

        getData()
    }

    private fun getData() {
        reference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                for (snapshot1: DataSnapshot in snapshot.children) {
                    var data = snapshot1.getValue(UserDataClass::class.java)
                    deleteList.add(0, data!!)

                }
                deleteAdapter = UserAdapter(deleteList, this@UserMorning)
                rvDelete.adapter = deleteAdapter
                progressBar.isVisible = false
                deleteAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
                progressBar.isVisible = false
                Toast.makeText(this@UserMorning, "$error", Toast.LENGTH_SHORT).show()
            }

        })
    }

}