package com.example.notesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityAddNoteBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class AddNoteActivity : AppCompatActivity() {
    lateinit var binding: ActivityAddNoteBinding
    lateinit var dbRef: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbRef = FirebaseDatabase.getInstance().getReference(MainActivity.DataHolder.email)

        binding.btnAddNote.setOnClickListener {
            if(binding.edtContent.text.toString()==""||binding.edtTitle.text.toString()==""){
                Toast.makeText(this, "Enter data", Toast.LENGTH_SHORT).show()
            }
            else{
                addNote()
            }
        }
    }

    private fun addNote() {
        var title = binding.edtTitle.text.toString()
        var content = binding.edtContent.text.toString()

        var id = dbRef.push().key!!
        var note = Note(id, title, content)
        dbRef.child(id).setValue(note).addOnCompleteListener { task->
            if(task.isSuccessful){
                binding.edtContent.setText("")
                binding.edtTitle.setText("")
                binding.edtTitle.requestFocus()
                Toast.makeText(this, "Add successful", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(this, "Add fail", Toast.LENGTH_SHORT).show()
            }
        }
    }
}