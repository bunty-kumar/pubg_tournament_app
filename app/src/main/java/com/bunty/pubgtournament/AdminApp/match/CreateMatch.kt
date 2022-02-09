package com.bunty.pubgtournament.AdminApp.match

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import com.bunty.pubgtournament.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class CreateMatch : AppCompatActivity() {

    private lateinit var matchDate: EditText
    private lateinit var matchTime: EditText
    private lateinit var matchReferenceId: EditText
    private lateinit var matchSlot: EditText
    private lateinit var matchCharge: EditText
    private lateinit var matchReward: EditText
    private lateinit var image: ImageView
    private lateinit var uploadImage: Button
    private lateinit var uploadData: Button
    lateinit var matchCategorySpin: Spinner
    lateinit var matchTypeSpin: Spinner

    var matchCategory = arrayOf("Select Match Category", "Erangle", "Livik", "Tdm")
    var matchType = arrayOf("Select Match Type", "Morning", "Afternoon", "Evening")
    lateinit var pd: ProgressDialog

    lateinit var reference: DatabaseReference
    lateinit var reference2: DatabaseReference
    lateinit var query: DatabaseReference
    lateinit var storagereference: StorageReference

    lateinit var matchDuration: String
    lateinit var matchCat: String

    private var req: Int = 1
    private lateinit var bitMap: Bitmap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_match)
        supportActionBar?.setTitle("Create Match")
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        matchDate = findViewById(R.id.matchDate)
        matchTime = findViewById(R.id.matchTime)
        matchReferenceId = findViewById(R.id.matchReferenceId)
        matchSlot = findViewById(R.id.matchSlots)
        matchCharge = findViewById(R.id.matchCharge)
        matchReward = findViewById(R.id.matchReward)
        image = findViewById(R.id.imageview)
        uploadImage = findViewById(R.id.uploadImage)
        uploadData = findViewById(R.id.uploadData)
        matchCategorySpin = findViewById(R.id.dropdown1)
        matchTypeSpin = findViewById(R.id.dropdown2)
        pd = ProgressDialog(this)

        reference = FirebaseDatabase.getInstance().reference.child("Matches")
        reference2 = FirebaseDatabase.getInstance().reference.child("Reference Number")
        query = FirebaseDatabase.getInstance().reference.child("Reference Number")
        storagereference = FirebaseStorage.getInstance().reference.child("Matches")

        var macthCategoryAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, matchCategory)
        matchCategorySpin.adapter = macthCategoryAdapter

        var macthTypeAdapter =
            ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, matchType)
        matchTypeSpin.adapter = macthTypeAdapter

        matchCategorySpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                matchCat = matchCategory[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
        matchTypeSpin.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                matchDuration = matchType[position]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }

        uploadImage.setOnClickListener {
            openGallery()
        }

        uploadData.setOnClickListener {
            checkValidation()
        }


    }

    private fun checkValidation() {
        var id = matchReferenceId.text.toString()
        query.addListenerForSingleValueEvent(object :ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (matchDate.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateMatch, "please enter match date", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchTime.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateMatch, "please enter match time", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchReferenceId.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateMatch, "please enter match refrenceid", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchSlot.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateMatch, "please enter match slot", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchCharge.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateMatch, "please enter match charge", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchReward.text.toString().trim().isEmpty()) {
                    Toast.makeText(this@CreateMatch, "please enter match reward", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchDuration=="Select Match Type") {
                    Toast.makeText(this@CreateMatch, "please select match duration", Toast.LENGTH_SHORT).show()
                    return
                } else if (matchCat=="Select Match Category") {
                    Toast.makeText(this@CreateMatch, "please select match category", Toast.LENGTH_SHORT).show()
                    return
                }else if (bitMap == null) {
                    Toast.makeText(this@CreateMatch, "please select image", Toast.LENGTH_SHORT).show()
                    return
                }else if (snapshot.hasChild(id)){
                    AlertDialog.Builder(this@CreateMatch)
                        .setMessage("Reference Id Already Exists")
                        .setCancelable(false)
                        .setNegativeButton("Cancle", null)
                        .show()
                }
                else {
                    uploadImage()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@CreateMatch, "$error", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun uploadImage() {
        pd.setMessage("Uploading...")
        pd.setCancelable(false)
        pd.show()

        var baos = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        var finalImage = baos.toByteArray()

        val filePath: StorageReference
        filePath = storagereference.child(matchReferenceId.text.toString().trim() + "jpg")

        val uploadTask: UploadTask = filePath.putBytes(finalImage)
        uploadTask.addOnCompleteListener(OnCompleteListener {
            if (it.isSuccessful) {
                uploadTask.addOnSuccessListener {
                    filePath.downloadUrl.addOnSuccessListener {
                        uploadData(it)
                        Toast.makeText(this, "Data Uploaded successfully", Toast.LENGTH_SHORT)
                            .show()
                        pd.dismiss()
                    }.addOnFailureListener {
                        pd.dismiss()
                        Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }

    private fun uploadData(it: Uri?) {
        var matchDate = matchDate.text.toString().trim()
        var matchTime = matchTime.text.toString().trim()
        var refId = matchReferenceId.text.toString().trim()
        var slots = matchSlot.text.toString().trim()
        var matchCharge = matchCharge.text.toString().trim()
        var roomId = "not available"
        var roomPass = "not available"
        var reward = matchReward.text.toString().trim()

        var calDate = Calendar.getInstance()
        var currentDate = SimpleDateFormat("dd-MM-yy")
        var date = currentDate.format(calDate.time)

        var calTime = Calendar.getInstance()
        var currentTime = SimpleDateFormat("hh:mm a")
        var time = currentTime.format(calTime.time)

        var matchData =
            DataClass(
                date,
                time,
                refId,
                matchCharge,
                slots,
                matchDate,
                matchTime,
                it.toString(),
                matchDuration,
                matchCat, roomId, roomPass, reward
            )
        var referenceData = ReferenceModel(refId)
        reference.child(matchDuration).child(refId).setValue(matchData)
        reference2.child(refId).setValue(referenceData)

    }

    private fun openGallery() {
        val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
        startActivityForResult(gallery, req)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == req) {
            val imageUri = data?.data
            try {
                bitMap = MediaStore.Images.Media.getBitmap(this.contentResolver, imageUri)
            } catch (e: Exception) {
            }
            image.setImageBitmap(bitMap)
        }else{
            Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show()
        }
    }

}