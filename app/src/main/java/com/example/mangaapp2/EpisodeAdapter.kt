package com.example.mangaapp2

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mangaapp2.databinding.ItemEpisodeBinding

class EpisodeAdapter(private val episodes: List<Episode>, private val clickListener: (Episode) -> Unit) :
    RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder>() {

    class EpisodeViewHolder(val binding: ItemEpisodeBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val binding = ItemEpisodeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val episode = episodes[position]
        holder.binding.episodeTextView.text = episode.title
        holder.itemView.setOnClickListener { clickListener(episode) }
    }

    override fun getItemCount(): Int = episodes.size
}
