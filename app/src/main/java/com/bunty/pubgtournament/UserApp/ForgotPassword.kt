package com.bunty.pubgtournament.UserApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.bunty.pubgtournament.R
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {

    lateinit var forgotEmail:EditText
    lateinit var forgotSubmit:Button
    lateinit var tvRegister:TextView
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        forgotEmail = findViewById(R.id.forgotEmail)
        forgotSubmit = findViewById(R.id.forgotSubmit)
        tvRegister = findViewById(R.id.tvRegister)
        auth = FirebaseAuth.getInstance()

        tvRegister.setOnClickListener {
            var intent = Intent(this,SignUp::class.java)
            startActivity(intent)
        }

        forgotSubmit.setOnClickListener {
            if (forgotEmail.text.toString().trim().isEmpty()){
                Toast.makeText(this, "please enter email", Toast.LENGTH_SHORT).show()
            }else{
                sendEmail()
            }
        }
    }

    private fun sendEmail() {
        auth.sendPasswordResetEmail(forgotEmail.text.toString().trim())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    var intent = Intent(this,Login::class.java)
                    startActivity(intent)
                    finish()
                    Toast.makeText(this, "send email successful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "error"+it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

}