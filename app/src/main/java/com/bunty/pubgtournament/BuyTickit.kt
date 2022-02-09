package com.bunty.pubgtournament

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.bumptech.glide.Glide
import com.razorpay.Checkout
import de.hdodenhof.circleimageview.CircleImageView
import org.json.JSONObject
import android.content.ContentValues.TAG
import android.provider.ContactsContract
import android.util.Log
import android.widget.Toast
import com.bunty.pubgtournament.UserApp.ChooseSquadData
import com.bunty.pubgtournament.UserApp.UserDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception
import com.razorpay.PaymentResultListener


class BuyTickit : AppCompatActivity(), PaymentResultListener {

    lateinit var auth: FirebaseAuth
    lateinit var user: FirebaseUser

    lateinit var ref: DatabaseReference
    lateinit var ref2: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_buy_tickit)
        Checkout.preload(applicationContext)

        ref = FirebaseDatabase.getInstance().getReference().child("Orders")
        ref2 = FirebaseDatabase.getInstance().getReference().child("TotalOrders")
        auth = FirebaseAuth.getInstance()
        user = auth.currentUser!!

        val uploadDate: TextView = findViewById(R.id.uploadDate)
        val uploadTime: TextView = findViewById(R.id.uploadTime)
        val referId: TextView = findViewById(R.id.referenceId)
        val matchCharge: TextView = findViewById(R.id.matchCharge)
        val reward: TextView = findViewById(R.id.matchReward)
        val matchDuration: TextView = findViewById(R.id.matchType)
        val matchCategory: TextView = findViewById(R.id.matchCategory)
        val roomId: TextView = findViewById(R.id.roomId)
        val roomPass: TextView = findViewById(R.id.roomPass)
        val matchDate: TextView = findViewById(R.id.matchDate)
        val matchTime: TextView = findViewById(R.id.matchTime)
        val deleteImage: CircleImageView = findViewById(R.id.deleImage)
        val payBtn = findViewById<Button>(R.id.payBtn)

        matchDate.text = intent.getStringExtra("dt")
        matchTime.text = intent.getStringExtra("ti")
        uploadDate.text = intent.getStringExtra("updt")
        uploadTime.text = intent.getStringExtra("uptm")
        roomId.text = intent.getStringExtra("rid")
        roomPass.text = intent.getStringExtra("rpas")
        matchCharge.text = intent.getStringExtra("charge")
        reward.text = intent.getStringExtra("reward")
        referId.text = intent.getStringExtra("refid")
        matchCategory.text = intent.getStringExtra("category")
        matchDuration.text = intent.getStringExtra("duration")
        Glide.with(this).load(intent.getStringExtra("image"))
            .into(deleteImage)

        payBtn.setText("Pay " + intent.getStringExtra("charge"))
        payBtn.setOnClickListener {
            makePayment()
        }

    }

    private fun makePayment() {
        val checkout = Checkout()
        checkout.setKeyID("rzp_live_EUjsej3S05WTIS")
        checkout.setImage(R.drawable.cart)
        val activity: Activity = this

        try {
            val options = JSONObject()
            options.put("name", "Bgmi User")
            options.put(
                "description",
                intent.getStringExtra("refid") + intent.getStringExtra("category")
            )
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png")
            /* options.put("order_id", "order_DBJOWzybf0sJbb") //from response of step 3.*/
            options.put("theme.color", "#3399cc")
            options.put("currency", "INR")
            options.put(
                "amount",
                intent.getStringExtra("charge")!!.toInt() * 100
            ) //pass amount in currency subunits
            options.put("prefill.email", user.email)
            /*options.put("prefill.contact", "9988776655")*/
            val retryObj = JSONObject()
            retryObj.put("enabled", true)
            retryObj.put("max_count", 4)
            options.put("retry", retryObj)
            checkout.open(activity, options)
        } catch (e: Exception) {
            Log.e(TAG, "Error in starting Razorpay Checkout", e)
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        saveOrder()
        Toast.makeText(this, "successful", Toast.LENGTH_SHORT).show()
    }

    private fun saveOrder() {
        var dataClass = UserDataClass(
            intent.getStringExtra("updt")!!,
            intent.getStringExtra("uptm")!!, intent.getStringExtra("refid")!!,
            intent.getStringExtra("charge")!!, "",
            intent.getStringExtra("dt")!!, intent.getStringExtra("ti")!!,
            intent.getStringExtra("image")!!, intent.getStringExtra("duration")!!,
            intent.getStringExtra("category")!!, intent.getStringExtra("rid")!!,
            intent.getStringExtra("rpas")!!, intent.getStringExtra("reward")!!
        )
        ref.child(user.uid).child(intent.getStringExtra("refid")!!).setValue(dataClass)

        var chooseSquad = ChooseSquadData(user.uid, user.displayName!!, "",
            "", "", "", "", "",
            "", "", intent.getStringExtra("refid")!!
        )
        ref2.child(intent.getStringExtra("refid")!!).child(user.uid).setValue(chooseSquad)
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "failed", Toast.LENGTH_SHORT).show()
    }
}