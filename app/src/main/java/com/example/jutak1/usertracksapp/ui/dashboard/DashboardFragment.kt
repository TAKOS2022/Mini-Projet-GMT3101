package com.example.jutak1.usertracksapp.ui.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.jutak1.usertracksapp.CameraActivity
import com.example.jutak1.usertracksapp.StepCountActivity
import com.example.jutak1.usertracksapp.databinding.FragmentDashboardBinding

class DashboardFragment : Fragment() {

    private var _binding: FragmentDashboardBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.cameraButtonDashboard.setOnClickListener{
            val intent = Intent(getActivity(), CameraActivity::class.java)
            startActivity(intent)
        }
        binding.stepsButtonDashboard.setOnClickListener{
            val intent = Intent(activity, StepCountActivity::class.java)
            startActivity(intent)
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}