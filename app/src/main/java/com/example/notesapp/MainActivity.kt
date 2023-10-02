package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    lateinit var dbRef: DatabaseReference
    lateinit var listNotes:ArrayList<Note>
    object DataHolder {
        var email: String = ""
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        DataHolder.email = intent.getStringExtra("email")!!
        DataHolder.email = DataHolder.email.subSequence(0,DataHolder.email.indexOf("@")).toString()
        binding.btnAdd.setOnClickListener {
            val intent = Intent(this,AddNoteActivity::class.java)
            startActivity(intent)
        }

        listNotes = arrayListOf()
        dbRef = FirebaseDatabase.getInstance().getReference(DataHolder.email)
        binding.rvListNotes.layoutManager = LinearLayoutManager(this)
        getAllNotes()
















    }

    fun getAllNotes() {
        dbRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                listNotes.clear()
                if(snapshot.exists()){
                    snapshot.children.forEach { data ->
                        var id = data.child("id").getValue().toString()
                        var title = data.child("title").getValue().toString()
                        var content = data.child("content").getValue().toString()
                        var note:Note = Note(id,title,content)
                        listNotes.add(note)
                    }

                }
                var adapter = NoteAdapter(listNotes)
                binding.rvListNotes.adapter = adapter

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }
}