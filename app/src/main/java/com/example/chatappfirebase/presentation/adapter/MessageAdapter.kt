package com.example.chatappfirebase.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.chatappfirebase.data.ChatData
import com.example.chatappfirebase.databinding.FromLayoutBinding
import com.example.chatappfirebase.databinding.ToLayoutBinding
import com.google.firebase.auth.FirebaseAuth
import java.util.Calendar

class MessageAdapter : ListAdapter<ChatData, MessageAdapter.Holder>(ChatDiffUtil) {

    var onClickItem: ((sms: ChatData) -> Unit)? = null
    var onLongClickItem: ((sms: ChatData) -> Unit)? = null


    object ChatDiffUtil : DiffUtil.ItemCallback<ChatData>() {
        override fun areItemsTheSame(oldItem: ChatData, newItem: ChatData): Boolean = oldItem.senderID == newItem.senderID
        override fun areContentsTheSame(oldItem: ChatData, newItem: ChatData): Boolean = oldItem == newItem
    }

    inner class Holder1(private var binding: FromLayoutBinding) : Holder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        override fun bind() {

            binding.textMessage.text = getItem(adapterPosition).message
            binding.textTime.text = getItem(adapterPosition).time

            binding.root.setOnLongClickListener {
                onLongClickItem?.invoke(getItem(adapterPosition))
                return@setOnLongClickListener true
            }
        }
    }

    inner class Holder2(private var binding: ToLayoutBinding) : Holder(binding.root) {

        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        override fun bind() {

            binding.textMessage.text = getItem(adapterPosition).message
            binding.textTime.text = getItem(adapterPosition).time

            binding.root.setOnLongClickListener {
                onLongClickItem?.invoke(getItem(adapterPosition))
                return@setOnLongClickListener true
            }
        }

    }

    open class Holder(view: View) : ViewHolder(view) {
        open fun bind() {}
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder =
        when (viewType) {
            0 -> Holder1(FromLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            else -> Holder2(ToLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind()

    override fun getItemViewType(position: Int): Int {
        super.getItemViewType(position)

        return if (getItem(position).senderID == FirebaseAuth.getInstance().currentUser?.uid) 0 else 1

    }


    private fun getCurrentTime(): String {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)
        val second = calendar.get(Calendar.SECOND)
        return "$hour:$minute:$second"
    }

    fun getEndPos(): Int {
        return itemCount - 1
    }


}