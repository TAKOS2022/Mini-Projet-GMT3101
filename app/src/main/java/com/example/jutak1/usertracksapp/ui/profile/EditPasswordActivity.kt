package com.example.jutak1.usertracksapp.ui.profile

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.jutak1.usertracksapp.databinding.ActivityEditPasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class EditPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditPasswordBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var user: FirebaseUser
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEditPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        user = firebaseAuth.currentUser!!

        val cancelButton = binding.cancelEditPasswordButton
        cancelButton.setOnClickListener {
            onBackPressed()
        }

        val oldPasswordInput = binding.oldPasswordInput
        val newPasswordInput = binding.newPasswordInput
        val confirmNewPasswordInput = binding.confirmNewPasswordInput

        val saveButton = binding.saveNewPassword
        saveButton.setOnClickListener {
            var errorMet = false

            // verify old password
            val oldPassword = oldPasswordInput.text.toString()
            val credential = user.email?.let { EmailAuthProvider.getCredential(it, oldPassword) }
            if (credential != null) {
                user.reauthenticate(credential)
                    .addOnCompleteListener { task ->
                        if (!task.isSuccessful) {
                            oldPasswordInput.error = "Invalid password"
                            errorMet = true
                        }
                    }
            } else {
                Toast.makeText(this, "Unable to verify, please try again.", Toast.LENGTH_SHORT).show()
                errorMet = true
            }

            // verify new password
            val newPassword = newPasswordInput.text.toString()
            val confirmNewPassword = confirmNewPasswordInput.text.toString()
            if(newPassword.isEmpty()) {
                newPasswordInput.error = "Password must not be empty."
                errorMet = true
            } else if(newPassword != confirmNewPassword) {
                confirmNewPasswordInput.error = "Passwords do not match."
                errorMet = true
            }

            // save
            if(!errorMet) {
                user.updatePassword(newPassword)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            Toast.makeText(this, "Password updated successfully.", Toast.LENGTH_SHORT).show()
                            onBackPressed()
                        } else {
                            Toast.makeText(this, "Unable to update password, please try again.", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }
    }
}