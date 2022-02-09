package com.bunty.pubgtournament.UserApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.bunty.pubgtournament.R
import com.google.firebase.auth.FirebaseAuth

class Login : AppCompatActivity() {

    lateinit var loginEmail: EditText
    lateinit var loginPassword: EditText
    lateinit var loginSubmit: Button
    lateinit var tvRegister: TextView
    lateinit var tvForgot: TextView

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginSubmit = findViewById(R.id.loginSubmit)
        tvRegister = findViewById(R.id.tvRegister)
        tvForgot = findViewById(R.id.tvForgot)
        auth = FirebaseAuth.getInstance()

        tvRegister.setOnClickListener {
            var intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        tvForgot.setOnClickListener {
            var intent = Intent(this,ForgotPassword::class.java)
            startActivity(intent)
        }

        loginSubmit.setOnClickListener {
            checkValidation()
        }

    }

    private fun checkValidation() {
        if (loginEmail.text.toString().trim().isEmpty()||loginPassword.text.toString().trim().isEmpty()){
            Toast.makeText(this, "please enter all fields", Toast.LENGTH_SHORT).show()
        }else{
            loginUser()
        }
    }

    private fun loginUser() {
        auth.signInWithEmailAndPassword(loginEmail.text.toString().trim(),loginPassword.text.toString().trim())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                    var intent = Intent(this,UserDashboard::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    Toast.makeText(this, "error"+it.exception?.message, Toast.LENGTH_SHORT).show()
                }

            }

    }



}