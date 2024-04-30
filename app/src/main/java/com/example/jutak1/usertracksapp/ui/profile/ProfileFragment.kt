package com.example.jutak1.usertracksapp.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.jutak1.usertracksapp.SignInActivity
import com.example.jutak1.usertracksapp.databinding.FragmentProfileBinding
import com.google.firebase.auth.FirebaseAuth

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private lateinit var firebaseAuth: FirebaseAuth

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        firebaseAuth = FirebaseAuth.getInstance()
        val user = firebaseAuth.currentUser

        val textEmail: TextView = binding.profileEmail
        textEmail.text = user?.email

        // edit password
        val editPasswordButton = binding.editPasswordButton
        editPasswordButton.setOnClickListener {
            val intent = Intent(requireContext(), EditPasswordActivity::class.java)
            startActivity(intent)
        }

        // delete account
        val deleteAccountButton = binding.deleteAccountButton
        deleteAccountButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Delete account ?")
            builder.setMessage("This action is irreversible !")
            builder.setIcon(android.R.drawable.ic_dialog_alert)
            builder.setPositiveButton("Delete") { _,_ ->
                firebaseAuth.currentUser?.delete()?.addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(requireContext(), "Account successfully deleted.", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireContext(), SignInActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                    } else {
                        Toast.makeText(requireContext(), "An error occurred, please try again.", Toast.LENGTH_SHORT).show()
                    }
                }
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            builder.setNegativeButton("Cancel") { _,_ ->
            }
            val dialog = builder.create()
            dialog.show()
        }

        // logout
        val logoutButton = binding.logoutButton
        logoutButton.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Logout ?")
            builder.setPositiveButton("Yes") { _, _ ->
                firebaseAuth.signOut()
                val intent = Intent(requireContext(), SignInActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                startActivity(intent)
            }
            builder.setNegativeButton("Cancel") { _, _ ->
            }
            val dialog = builder.create()
            dialog.show()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}