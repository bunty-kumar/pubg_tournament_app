package com.bunty.pubgtournament.AdminApp.delete


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.cardview.widget.CardView
import com.bunty.pubgtournament.R

class DeleteMatch : AppCompatActivity() {

    lateinit var morning: CardView
    lateinit var evening: CardView
    lateinit var afternoon: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delete_match)
        supportActionBar?.setTitle("Delete Match")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        morning = findViewById(R.id.morning)
        evening = findViewById(R.id.evening)
        afternoon = findViewById(R.id.afternoon)

        morning.setOnClickListener {
            var intent = Intent(this, MorningActivity::class.java)
            startActivity(intent)
        }

        evening.setOnClickListener {
            var intent = Intent(this, EveningActivity::class.java)
            startActivity(intent)
        }

        afternoon.setOnClickListener {
            var intent = Intent(this, AfternoonActivity::class.java)
            startActivity(intent)
        }

    }

}