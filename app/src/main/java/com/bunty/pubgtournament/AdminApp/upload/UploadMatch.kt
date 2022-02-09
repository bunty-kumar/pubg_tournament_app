package com.bunty.pubgtournament.AdminApp.upload

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.bunty.pubgtournament.R
import com.google.firebase.database.*

class UploadMatch : AppCompatActivity() {
    lateinit var refId:EditText
    lateinit var roomId:EditText
    lateinit var roomPass:EditText
    lateinit var matchCategory:Spinner
    lateinit var idPassSubmit:Button

    lateinit var selectCategory:String
    var matchDurationArray = arrayOf("Select Match Type", "Morning", "Afternoon", "Evening")
    lateinit var reference:DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload_match)
        supportActionBar?.setTitle("Upload Id Password")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        refId = findViewById(R.id.refId)
        roomId = findViewById(R.id.roomId)
        roomPass = findViewById(R.id.roomPass)
        matchCategory = findViewById(R.id.matchCategory)
        idPassSubmit = findViewById(R.id.idPasSubmit)

        reference = FirebaseDatabase.getInstance().getReference("Matches")
        var macthCategoryAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, matchDurationArray)
        matchCategory.adapter = macthCategoryAdapter

        matchCategory.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                selectCategory = matchDurationArray[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        idPassSubmit.setOnClickListener {
            checkValidation()
        }

    }

    private fun checkValidation() {
        if (refId.text.toString().trim().isEmpty()){
            Toast.makeText(this, "please enter ref id", Toast.LENGTH_SHORT).show()
            return
        }else if (roomId.text.toString().trim().isEmpty()){
            Toast.makeText(this, "please enter room id ", Toast.LENGTH_SHORT).show()
            return
        }else if (roomPass.text.toString().trim().isEmpty()){
            Toast.makeText(this, "please enter room password", Toast.LENGTH_SHORT).show()
            return
        }else if (selectCategory=="Select Match Type"){
            Toast.makeText(this, "please select category", Toast.LENGTH_SHORT).show()
            return
        }else{
            uploadData()
        }

    }

    private fun uploadData() {
        val hashMap = HashMap<String,String>()
        hashMap.put("roomId",roomId.text.toString().trim())
        hashMap.put("roomPass",roomPass.text.toString().trim())
        reference.child(selectCategory).child(refId.text.toString().trim()).updateChildren(hashMap as Map<String, Any>).addOnCompleteListener {
            Toast.makeText(this, "updated successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "error in updating", Toast.LENGTH_SHORT).show()
        }
    }
}