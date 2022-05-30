package com.example.homework6.adapters

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.homework6.databinding.ItemBinding
import com.example.homework6.model.data.Person

class PersonViewHolder(
    private val binding: ItemBinding,
    private val longItemClick: (Person) -> Unit,
    private val itemClick: (Person) -> Unit
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Person) {
        binding.name.text = item.name
        binding.nickname.text = item.nickname
        binding.personImage.load(item.img)

        binding.root.setOnClickListener {
            itemClick(item)

        }

        binding.root.setOnLongClickListener {
            longItemClick(item)
            true
        }
    }
}