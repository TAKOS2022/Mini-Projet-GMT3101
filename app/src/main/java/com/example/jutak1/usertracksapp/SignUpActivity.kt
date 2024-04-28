package com.example.jutak1.usertracksapp

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.jutak1.usertracksapp.databinding.ActivitySignUpBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private  lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        binding.textViewSignUp.setOnClickListener{
            val intent = Intent(this, SignInActiviity::class.java)
            startActivity(intent)
        }
        binding.btnSignUp.setOnClickListener{
            val name = binding.nomEtSignUp.text.toString()
            val email = binding.emailEtSignUp.text.toString()
            val pass = binding.passwordEtSignUp.text.toString()
            val conFirmPass = binding.passwordConEtSignUp.text.toString()

            if (name.isNotEmpty() && email.isNotEmpty() && pass.isNotEmpty() && conFirmPass.isNotEmpty()){
                if (pass == conFirmPass){
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener{
                        if (it.isSuccessful){
                            var userID = firebaseAuth.currentUser?.uid.toString()
//                            var documentReference = firestore.collection("users").document(userID)
                            val user = hashMapOf(
                                "name" to name
                            )
                            firestore.collection("users").add(user).addOnSuccessListener {
                                documentReference -> Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
                            }.addOnFailureListener{
                                e -> Log.w(TAG, "Error adding document", e)
                            }
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