package com.example.mangaapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mangaapp2.databinding.ItemMangaBinding

class MangaAdapter(private val mangas: List<Manga>, private val clickListener: (Manga) -> Unit) :
    RecyclerView.Adapter<MangaAdapter.MangaViewHolder>() {

    class MangaViewHolder(val binding: ItemMangaBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MangaViewHolder {
        val binding = ItemMangaBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MangaViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MangaViewHolder, position: Int) {
        val manga = mangas[position]
        holder.binding.titleView.text = manga.title
        Glide.with(holder.binding.imageView.context).load(manga.thumbnail).into(holder.binding.imageView)
        holder.itemView.setOnClickListener { clickListener(manga) }
    }

    override fun getItemCount(): Int = mangas.size
}
