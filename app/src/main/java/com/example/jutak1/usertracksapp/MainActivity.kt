package com.example.jutak1.usertracksapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {
    private lateinit var selectUser:TextView
    private lateinit var dialog: BottomSheetDialog


    private lateinit var itemAdaptor: UserAdaptor
    private lateinit var listView: ListView
    private lateinit var firebaseFirestore: FirebaseFirestore
    private lateinit var userlist : ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        selectUser = findViewById(R.id.tvSelectUser)
        selectUser.setOnClickListener{
            showBottonSheet()
        }

    }

    private fun showBottonSheet() {

        val dialogView = layoutInflater.inflate(R.layout.botton_sheet, null)
        dialog = BottomSheetDialog(this, R.style.BottomSheetDialogTheme)
        dialog.setContentView(dialogView)
        listView = dialogView.findViewById<ListView>(R.id.rvUser)
        val userList = ArrayList<String>()
        firebaseFirestore = FirebaseFirestore.getInstance()
        firebaseFirestore.collection("users").get().addOnSuccessListener {
            result -> for (document in result){
                var  userName : String = document.data["name"].toString()
                println(userName)
//            userList.add(userName)
            }
        }

        for (i in 1..5){
            userList.add("User $i")
        }

        val adapter: ArrayAdapter<String> =
            ArrayAdapter(this, android.R.layout.simple_list_item_1, userList)
        listView.adapter = adapter
        dialog.show()
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val selectedItem = userList[position]
            Toast.makeText(this, "Clicked: $selectedItem", Toast.LENGTH_SHORT).show()
        }


    }




}