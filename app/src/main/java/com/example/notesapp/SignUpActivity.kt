package com.example.notesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    lateinit var firebaseAuth:FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.txtLogin.setOnClickListener {
            val intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.btnSignUp.setOnClickListener {
            var email = binding.edtSignUpEmail.text.toString()
            var password = binding.edtSignUpPassword.text.toString()
            if(email.isNotEmpty()&&password.isNotEmpty()){
                firebaseAuth.createUserWithEmailAndPassword(email,password)
                    .addOnCompleteListener(this) { task->
                        if(task.isSuccessful){
                            Toast.makeText(this, "SignUp successful", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this,LoginActivity::class.java)
                            startActivity(intent)
                            finish()
                        }
                        else{
                            Toast.makeText(this, "SignUp unsuccessful", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
            else{
                Toast.makeText(this, "Enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }
}