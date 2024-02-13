package com.example.chatappfirebase.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chatappfirebase.R
import com.example.chatappfirebase.data.UserData
import com.example.chatappfirebase.databinding.ScreenUserBinding
import com.example.chatappfirebase.presentation.adapter.UserAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserScreen : Fragment(R.layout.screen_user) {

    private val binding by viewBinding(ScreenUserBinding::bind)
    private val adapter = UserAdapter()

    private lateinit var auth: FirebaseAuth
    private lateinit var dbRef: DatabaseReference
    private lateinit var data: ArrayList<UserData>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        data = ArrayList()
        auth = FirebaseAuth.getInstance()
        dbRef = FirebaseDatabase.getInstance().getReference()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        binding.logOut.setOnClickListener {
            auth.signOut()
            findNavController().navigateUp()
        }

        adapter.onClickItem = {
            findNavController().navigate(UserScreenDirections.actionUserScreenToChatScreen(it))
        }

        dbRef.child("users").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {

                data.clear()

                for (post in snapshot.children) {

                    val currentUser = post.getValue(UserData::class.java)

                    if (auth.currentUser?.uid != currentUser?.uid)
                        data.add(currentUser!!)

                }

                adapter.notifyDataSetChanged()
                adapter.submitList(data)

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


}