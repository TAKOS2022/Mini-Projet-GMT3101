package com.example.jutak1.usertracksapp

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jutak1.usertracksapp.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

class SignInActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textViewSignIn.setOnClickListener{
            val intent = Intent(this,SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.btnSignIn.setOnClickListener{
            val email = binding.emailEtSignIn.text.toString()
            val pass = binding.passwordEtSignIn.text.toString()

            firebaseAuth = FirebaseAuth.getInstance()
            if (email.isNotEmpty() && pass.isNotEmpty() ){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener{
                    if (it.isSuccessful){
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }else{
                        Toast.makeText(this, it.exception.toString(), Toast.LENGTH_SHORT).show()

                    }
                }

            }else{
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()

            }
        }
    }
 //Verifie si l'utilisateur courant est deja authentifie et lance le main activity directement
//    override fun onStart() {
//        super.onStart()
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
//        }
//    }
}