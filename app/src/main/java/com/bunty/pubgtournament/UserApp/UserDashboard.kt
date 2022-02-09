package com.bunty.pubgtournament.UserApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.cardview.widget.CardView
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.bunty.pubgtournament.AdminApp.delete.AfternoonActivity
import com.bunty.pubgtournament.AdminApp.delete.EveningActivity
import com.bunty.pubgtournament.AdminApp.delete.MorningActivity
import com.bunty.pubgtournament.R
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class UserDashboard : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var actionBarToggle: ActionBarDrawerToggle
    private lateinit var navView: NavigationView
    lateinit var header: View
    lateinit var auth:FirebaseAuth
    lateinit var user:FirebaseUser

    lateinit var morning: LinearLayout
    lateinit var evening: LinearLayout
    lateinit var afternoon: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        drawerLayout = findViewById(R.id.maindrawer)

        // Pass the ActionBarToggle action into the drawerListener
        actionBarToggle = ActionBarDrawerToggle(this, drawerLayout, 0, 0)
        drawerLayout.addDrawerListener(actionBarToggle)

        // Display the hamburger icon to launch the drawer
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Call syncState() on the action bar so it'll automatically change to the back button when the drawer layout is open
        actionBarToggle.syncState()


        // Call findViewById on the NavigationView
        navView = findViewById(R.id.navigationView)

        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.myProfile -> {
                    Toast.makeText(this, "My Profile", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.people -> {
                    Toast.makeText(this, "People", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.settings -> {
                    Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> {
                    false
                }
            }
        }

        header = navView.getHeaderView(0)
        if (user!= null){
            var image = header.findViewById<ImageView>(R.id.userImage)

            Glide.with(this).load(R.drawable.user)
                .into(image)

            val name = header.findViewById<TextView>(R.id.userName)
            name.setText(user.displayName)
            val email = header.findViewById<TextView>(R.id.userEmail)
            email.setText(user.email)

            val logoutBtn = header.findViewById<Button>(R.id.logoutBtn)

            logoutBtn.setOnClickListener {
                auth.signOut()
                startActivity(Intent(this,Login::class.java))
                finish()
            }

        }


        morning = findViewById(R.id.morningMatch)
        evening = findViewById(R.id.eveningMatch)
        afternoon = findViewById(R.id.afternoonMatch)

        morning.setOnClickListener {
            var intent = Intent(this, UserMorning::class.java)
            startActivity(intent)
        }

        evening.setOnClickListener {
            var intent = Intent(this, UserEvening::class.java)
            startActivity(intent)
        }

        afternoon.setOnClickListener {
            var intent = Intent(this, UserAfternoon::class.java)
            startActivity(intent)
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        drawerLayout.openDrawer(navView)
        return true
    }
    // override the onBackPressed() function to close the Drawer when the back button is clicked
    override fun onBackPressed() {
        if (this.drawerLayout.isDrawerOpen(GravityCompat.START)) {
            this.drawerLayout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }


}