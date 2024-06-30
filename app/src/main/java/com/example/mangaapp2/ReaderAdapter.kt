package com.example.mangaapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangaapp2.databinding.ItemPageBinding

class ReaderAdapter(private val pages: List<Page>) :
    RecyclerView.Adapter<ReaderAdapter.PageViewHolder>() {

    class PageViewHolder(val binding: ItemPageBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PageViewHolder {
        val binding = ItemPageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PageViewHolder, position: Int) {
        val page = pages[position]
        Glide.with(holder.binding.imageView.context).load(page.image).into(holder.binding.imageView)
    }

    override fun getItemCount(): Int = pages.size
}
