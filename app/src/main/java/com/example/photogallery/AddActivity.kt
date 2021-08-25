package com.example.photogallery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.photogallery.databinding.AddImageActivityBinding
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var binding: AddImageActivityBinding
    private var db: DatabaseManager = DatabaseManager()
    private lateinit var imageURI: Uri
    private lateinit var fileName: String
    private lateinit var ImageManager: ImageManager

    companion object {
        const val IMAGE_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AddImageActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ImageManager = ImageManager()

        binding.addButton.setOnClickListener {
            val title = binding.addTitle.text.toString()
            val description = binding.addDescription.text.toString()
            fileName = ImageManager.uploadImage(binding,imageURI,this)
            db.addData(fileName, title, description, this)
            binding.addTitle.text.clear()
            binding.addDescription.text.clear()
        }

        binding.addPhotoButton.setOnClickListener {
            pickImageGallery()
        }
    }

    private fun pickImageGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == IMAGE_REQUEST_CODE && resultCode == RESULT_OK) {
            binding.imageView.setBackgroundResource(0)
            imageURI = data?.data!!
            binding.imageView.setImageURI(imageURI)

        }
    }
}