package com.example.chatappfirebase.presentation.screens

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.chatappfirebase.R
import com.example.chatappfirebase.data.ChatData
import com.example.chatappfirebase.databinding.ScreenChatBinding
import com.example.chatappfirebase.presentation.adapter.MessageAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.AndroidEntryPoint
import java.util.Calendar

@AndroidEntryPoint
class ChatScreen : Fragment(R.layout.screen_chat) {

    private val binding by viewBinding(ScreenChatBinding::bind)
    private val navArgs: ChatScreenArgs by navArgs()
    private val adapter = MessageAdapter()
    private var data = ArrayList<ChatData>()

    private var receiverRoom = ""
    private var senderRoom = ""

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        binding.textName.text = navArgs.data.name

        val dbRef = FirebaseDatabase.getInstance().getReference()
        val receiverUid = navArgs.data.uid
        val senderUid = FirebaseAuth.getInstance().currentUser?.uid

        senderRoom = receiverUid + senderUid
        receiverRoom = senderUid + receiverUid

        data = ArrayList()
        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        dbRef.child("chats").child(senderRoom).child("messages").addValueEventListener(object : ValueEventListener {
            @SuppressLint("NotifyDataSetChanged")
            override fun onDataChange(snapshot: DataSnapshot) {
                data.clear()

                for (post in snapshot.children) {
                    val messages = post.getValue(ChatData::class.java)
                    data.add(messages!!)
                }

                adapter.submitList(data)
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(requireContext(), error.message, Toast.LENGTH_SHORT).show()
            }

        })

        binding.btnSend.setOnClickListener {

            val textSend = binding.editText.text.toString()
            val messageData = senderUid?.let { it1 ->
                ChatData(textSend, getCurrentTime(), it1)
            }

            dbRef.child("chats").child(senderRoom).child("messages").push().setValue(messageData).addOnSuccessListener {
                dbRef.child("chats").child(receiverRoom).child("messages").push().setValue(messageData)
            }

            binding.editText.setText("")

        }

    }

    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)

        // Format hours, minutes, and seconds with leadin zeros if needed
        val formattedHour = String.format("%02d", hour)
        val formattedMinute = String.format("%02d", minute)
        val formattedSecond = String.format("%02d", second)

        return "$formattedHour:$formattedMinute:$formattedSecond"
    }


}