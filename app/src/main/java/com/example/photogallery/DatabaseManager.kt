package com.example.photogallery

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.firebase.database.*


class DatabaseManager {

    private lateinit var database: DatabaseReference

    fun addData(img: String, title: String, description: String?, context: Context) {

        database = FirebaseDatabase.getInstance().getReference("Photo")
        val photo = Photo(img, title, description)
        database.push().setValue(photo).addOnSuccessListener {
            Toast.makeText(context, "Successfully Saved", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(context, "Failed database", Toast.LENGTH_SHORT).show()
        }
    }

    fun readData() {
        database = FirebaseDatabase.getInstance().getReference("Photo")
        val photoReadArray: ArrayList<Photo> = arrayListOf()

        val postListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (postSnapshot in dataSnapshot.children) {
                    val title = postSnapshot.child("title").value.toString()
                    val description = postSnapshot.child("description").value.toString()
                    val img = postSnapshot.child("img").value.toString()
                    photoReadArray.add(Photo(img, title, description))
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException())
            }
        }
        database.addValueEventListener(postListener)
    }

    fun deleteData(title: String) {
        // println("LOOOO$id")
        // database = FirebaseDatabase.getInstance().getReference("Photo").child(id)

        val ref = FirebaseDatabase.getInstance().reference
        val database = ref.child("Photo").orderByChild("title").equalTo(title)

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    appleSnapshot.ref.removeValue()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e(TAG, "onCancelled", databaseError.toException())
            }
        })
    }

    fun updateData(img: String, title: String, description: String?) {

        val ref = FirebaseDatabase.getInstance().reference
        val database = ref.child("Photo").orderByChild("title").equalTo(title)

        val photo = Photo(img, title, description)

        database.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (appleSnapshot in dataSnapshot.children) {
                    appleSnapshot.ref.push().setValue(photo)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e(TAG, "onCancelled", error.toException())
            }

        })
    }
}
