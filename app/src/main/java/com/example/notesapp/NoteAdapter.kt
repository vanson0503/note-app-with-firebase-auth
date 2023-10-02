package com.example.notesapp

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.FirebaseDatabase

class NoteAdapter(var list:ArrayList<Note>):RecyclerView.Adapter<NoteAdapter.ViewHolder>() {
    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var itemTitle = itemView.findViewById<TextView>(R.id.txtTitle)
        var itemContent = itemView.findViewById<TextView>(R.id.txtContent)
        var itemBtnDel = itemView.findViewById<ImageView>(R.id.btnDelNote)
        var itemBtnEdit = itemView.findViewById<ImageView>(R.id.btnEditNote)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(parent.context).inflate(R.layout.note_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.apply {
            holder.itemTitle.setText(list[position].title)
            holder.itemContent.setText(list[position].content)

            holder.itemBtnDel.setOnClickListener {
                var dbRef = FirebaseDatabase.getInstance().getReference(MainActivity.DataHolder.email).child(list[position].id)
                dbRef.removeValue()
                Toast.makeText(context, "Remove data", Toast.LENGTH_SHORT).show()
            }

            holder.itemBtnEdit.setOnClickListener {
                val intent = Intent(context,EditNoteActivity::class.java)
                    .apply {
                        putExtra("id",list[position].id)
                        putExtra("title",list[position].title)
                        putExtra("content",list[position].content)
                    }
                holder.itemView.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}