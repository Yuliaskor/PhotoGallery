package com.example.photogallery

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.photogallery.databinding.ItemViewBinding
import com.google.firebase.storage.FirebaseStorage
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

class PhotoItemActivity : AppCompatActivity() {

    private lateinit var binding: ItemViewBinding
    private var db: DatabaseManager = DatabaseManager()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ItemViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle: Bundle? = intent.extras
        val title = bundle!!.getString("title")
        val img = bundle.getString("img")
        val description = bundle.getString("description")
        //  val id = bundle.getString("id")

        binding.titleItem.text = title
        binding.imageViewItem.setBackgroundResource(0)
        FirebaseStorage.getInstance()
            .getReference("images/${img}").downloadUrl.addOnSuccessListener {
                Glide.with(this)
                    .load(it) // Uri of the picture
                    .into(binding.imageViewItem)
            }

        val formatter = DateTimeFormatter.ofPattern("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
        val date = LocalDate.parse(img, formatter)

        binding.date.text = date.toString()
        binding.description.text = description

        //  println("LOOOOL" + id)

        binding.deleteButton.setOnClickListener {
            if (title != null) {
                db.deleteData(title)
            }
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        binding.editButton.setOnClickListener {
            if (title != null) {
                db.deleteData(title)
            }
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }
}
