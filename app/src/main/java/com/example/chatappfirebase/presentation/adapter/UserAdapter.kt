package com.example.chatappfirebase.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.chatappfirebase.data.UserData
import com.example.chatappfirebase.databinding.ItemUserBinding

class UserAdapter : ListAdapter<UserData, UserAdapter.Holder>(UserDiffUtil) {

    var onClickItem: ((sms: UserData) -> Unit)? = null

    object UserDiffUtil : DiffUtil.ItemCallback<UserData>() {
        override fun areItemsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem.uid == newItem.uid
        override fun areContentsTheSame(oldItem: UserData, newItem: UserData): Boolean = oldItem == newItem
    }

    inner class Holder(private var binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {

            binding.textName.text = getItem(adapterPosition).name

            val uid = getItem(adapterPosition).uid
            val name = getItem(adapterPosition).name

            binding.root.setOnClickListener {
                onClickItem?.invoke(UserData(name, "asd", uid))
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder = Holder(
        ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
    )

    override fun onBindViewHolder(holder: Holder, position: Int) = holder.bind()

}