package com.example.photogallery

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.photogallery.databinding.AddImageActivityBinding
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class ImageManager {

    fun uploadImage(binding: AddImageActivityBinding, imageURI: Uri, context: Context ): String {
        val fileName: String
        val progressDialog = ProgressDialog(context)
        progressDialog.setMessage("Uploading file ...")
        progressDialog.setCancelable(false)
        progressDialog.show()

        val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val now = Date()
        fileName = formatter.format(now)
        val storageReference = FirebaseStorage.getInstance().getReference("images/$fileName")

        storageReference.putFile(imageURI).addOnSuccessListener {
            binding.imageView.setImageURI(null)

            Toast.makeText(context, "Successfully", Toast.LENGTH_SHORT).show()

            if (progressDialog.isShowing) progressDialog.dismiss()

        }.addOnFailureListener {
            // Log.d("XDDD", it.message!!)
            Toast.makeText(context, "Failed Image", Toast.LENGTH_SHORT).show()
            if (progressDialog.isShowing) progressDialog.dismiss()
        }

        return fileName
    }
}
