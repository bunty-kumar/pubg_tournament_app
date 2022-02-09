package com.bunty.pubgtournament

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import androidx.cardview.widget.CardView
import com.bunty.pubgtournament.UserApp.Login
import com.bunty.pubgtournament.UserApp.UserDashboard
import com.google.firebase.auth.FirebaseAuth

class ChooseWho : AppCompatActivity() {

    lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_who)

        auth = FirebaseAuth.getInstance()

        Handler().postDelayed({
            if (auth.currentUser!=null){
                var intent = Intent(this, UserDashboard::class.java)
                startActivity(intent)
                finish()
            }else{
                var intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        },3000)

    }

    /*override fun onStart() {
        super.onStart()
        if (auth.currentUser!=null){
            var intent = Intent(this, UserDashboard::class.java)
            startActivity(intent)
            finish()
        }
    }*/
}