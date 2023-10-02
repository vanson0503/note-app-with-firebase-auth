package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding
    lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtSignUp.setOnClickListener {
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignUp.setOnClickListener {
            var email = binding.edtLoginEmail.text.toString()
            var password = binding.edtLoginPassword.text.toString()

            if(email.isNotEmpty()&&password.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,MainActivity::class.java)
                                .apply {
                                    putExtra("email",email)
                                }
                            startActivity(intent)
                        }
                        else{
                            Toast.makeText(this, "Email or password incorrect", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}