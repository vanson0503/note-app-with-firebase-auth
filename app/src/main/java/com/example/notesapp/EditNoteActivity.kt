package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityEditNoteBinding
import com.example.notesapp.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class EditNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityEditNoteBinding
    lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        dbRef = FirebaseDatabase.getInstance().getReference(MainActivity.DataHolder.email)
        val note:Note = Note(intent.getStringExtra("id").toString(),
            intent.getStringExtra("title").toString(),
            intent.getStringExtra("content").toString())
        binding.edtEditContent.setText(note.content)
        binding.edtEditTitle.setText(note.title)

        binding.btnEditNote.setOnClickListener {
            val newNote = Note(note.id,
                binding.edtEditTitle.text.toString(),
                binding.edtEditContent.text.toString())

            dbRef.child(note.id).setValue(newNote)
            var intent = Intent(this,ActivityMainBinding::class.java)
            startActivity(intent)
            finish()
        }
    }
}