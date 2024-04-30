package com.example.jutak1.usertracksapp

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import com.example.jutak1.usertracksapp.databinding.ActivityCameraBinding
import com.example.jutak1.usertracksapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth
import java.io.File

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding

    private lateinit var captureIV : ImageView
    private lateinit var imageUrl : Uri

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()){

        captureIV.setImageURI(null)
        captureIV.setImageURI(imageUrl)

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.captureImgBtn.setOnClickListener {
            contract.launch(imageUrl)
        }

        //setContentView(R.layout.activity_main)

        imageUrl = createImageUri()
        captureIV = findViewById(R.id.captureImageView)
        val captureImgBtn = findViewById<Button>(R.id.captureImgBtn)
    }

    private fun createImageUri(): Uri {
        val image = File(filesDir,"camera_photos.png")
        return FileProvider.getUriForFile(this,
            "com.coding.captureimage.FileProvider",
            image)
    }
}