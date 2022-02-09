package com.bunty.pubgtournament.UserApp

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import com.bunty.pubgtournament.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream

class SignUp : AppCompatActivity() {

    lateinit var registerName:EditText
    lateinit var registerEmail:EditText
    lateinit var registerPassword:EditText
    lateinit var registerImage:ImageView
    lateinit var registerSubmit:Button
    lateinit var tvLogin:TextView

    private var req: Int = 1
    private lateinit var bitMap: Bitmap

    lateinit var auth:FirebaseAuth
    lateinit var storageReference:StorageReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        registerName = findViewById(R.id.registerName)
        registerEmail = findViewById(R.id.registerEmail)
        registerPassword = findViewById(R.id.registerPassword)
        registerImage = findViewById(R.id.profile_image)
        registerSubmit = findViewById(R.id.registerSubmit)
        tvLogin = findViewById(R.id.tvLogin)
        auth = FirebaseAuth.getInstance()
        storageReference = FirebaseStorage.getInstance().getReference().child("UserImages")

        registerImage.setOnClickListener {
            openGallery()
        }
        registerSubmit.setOnClickListener {
            checkValidation()
        }
        tvLogin.setOnClickListener {
            var intent = Intent(this,Login::class.java)
            startActivity(intent)
        }

    }


    private fun checkValidation() {
        if (registerName.text.toString().trim().isEmpty()||registerEmail.text.toString().trim().isEmpty()||
                registerPassword.text.toString().trim().isEmpty()){
            Toast.makeText(this, "please enter all fields", Toast.LENGTH_SHORT).show()
        }else{
           registerUser()
        }
    }

    private fun registerUser() {
        auth.createUserWithEmailAndPassword(registerEmail.text.toString().trim(),registerPassword.text.toString().trim())
            .addOnCompleteListener {
                if (it.isSuccessful){
                    uploadImage()
                    Toast.makeText(this, "register successful", Toast.LENGTH_SHORT).show()
                }else{
                    Toast.makeText(this, "error"+it.exception?.message, Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun uploadImage() {
        var uid = FirebaseAuth.getInstance().currentUser?.uid

        var baos = ByteArrayOutputStream()
        bitMap.compress(Bitmap.CompressFormat.JPEG, 50, baos)
        var finalImage = baos.toByteArray()

        val filePath: StorageReference
        filePath = storageReference.child(uid + "jpg")
        val uploadTask: UploadTask = filePath.putBytes(finalImage)
        uploadTask.addOnCompleteListener(OnCompleteListener {
            filePath.downloadUrl.addOnSuccessListener {
                updateUser(it)
                Toast.makeText(this, "Data Uploaded successfully", Toast.LENGTH_SHORT)
                    .show()
               // pd.dismiss()
            }.addOnFailureListener {
               // pd.dismiss()
                Toast.makeText(this, "something went wrong", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun updateUser(it: Uri?) {
        var changeRequest = UserProfileChangeRequest.Builder()
            .setDisplayName(registerName.text.toString().trim())
            .setPhotoUri(it)
            .build()
        auth.currentUser?.updateProfile(changeRequest)
        auth.signOut()
        var intent = Intent(this,Login::class.java)
        startActivity(intent)
        finish()
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
            registerImage.setImageBitmap(bitMap)
        }else{
            Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show()
        }
    }

}