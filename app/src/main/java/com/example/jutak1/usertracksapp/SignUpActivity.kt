package com.example.jutak1.usertracksapp

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.jutak1.usertracksapp.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private  lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.textViewSignUp.setOnClickListener{
            val intent = Intent(this, SignInActiviity::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener{
            val email = binding.emailEtSignUp.text.toString()
            val pass = binding.passwordEtSignUp.text.toString()
            val conFirmPass = binding.passwordConEtSignUp.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && conFirmPass.isNotEmpty()){
                if (pass == conFirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            val intent = Intent(this, SignInActiviity::class.java)
                            startActivity(intent)
                        }else{
                            Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                        }
                    }
                }else{
                    Toast.makeText(this, "Password is not working", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
}