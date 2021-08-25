package com.example.photogallery

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.photogallery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainAdapter: FirebaseAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imageRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.imageRecyclerView.setHasFixedSize(true)

        mainAdapter = FirebaseAdapter(this)
        binding.imageRecyclerView.isClickable = true
        binding.imageRecyclerView.adapter = mainAdapter

        mainAdapter.setOnItemClickListener(object : FirebaseAdapter.OnItemClickListener {
            override fun onItemClick(position: Int) {
                // Toast.makeText(this@MainActivity, "You click on item $position", Toast.LENGTH_SHORT).show()

                val intent = Intent(this@MainActivity, PhotoItemActivity::class.java)
                intent.putExtra("title", mainAdapter.getItem(position).title)
                intent.putExtra("img", mainAdapter.getItem(position).img)
                intent.putExtra("description", mainAdapter.getItem(position).description)
             //  intent.putExtra("id",mainAdapter.)

                startActivity(intent)

            }

        })

        mainAdapter.notifyDataSetChanged()

        binding.floatingActionAddButton.setOnClickListener {
            val intent = Intent(this, AddActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        mainAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        mainAdapter.stopListening()
    }
}