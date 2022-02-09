package com.bunty.pubgtournament

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.bunty.pubgtournament.AdminApp.delete.DeleteMatch
import com.bunty.pubgtournament.AdminApp.match.AssignedTickit
import com.bunty.pubgtournament.AdminApp.match.CreateMatch
import com.bunty.pubgtournament.AdminApp.upload.UploadMatch

class MainActivity : AppCompatActivity() {

    private lateinit var deleteMatch: CardView
    private lateinit var uploadMatch: CardView
    private lateinit var createMatch: CardView
    private lateinit var assignedTickets: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Admin Dashboard"

        deleteMatch = findViewById(R.id.deleteMatch)
        uploadMatch = findViewById(R.id.uploadMatch)
        createMatch = findViewById(R.id.createMatch)
        assignedTickets = findViewById(R.id.assignedTickit)

        deleteMatch.setOnClickListener {
            val intent = Intent(this, DeleteMatch::class.java)
            startActivity(intent)
        }

        uploadMatch.setOnClickListener {
            val intent = Intent(this, UploadMatch::class.java)
            startActivity(intent)
        }

        createMatch.setOnClickListener {
            val intent = Intent(this, CreateMatch::class.java)
            startActivity(intent)
        }

        assignedTickets.setOnClickListener {
            val intent = Intent(this, AssignedTickit::class.java)
            startActivity(intent)
        }

    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setMessage("Are you sure you want to exit?")
            .setCancelable(false)
            .setPositiveButton(
                "Yes"
            ) { _, _ -> super.onBackPressed() }
            .setNegativeButton("No", null)
            .show()
    }

}