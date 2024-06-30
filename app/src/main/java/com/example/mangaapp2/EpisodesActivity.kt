package com.example.mangaapp2

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.mangaapp2.databinding.ActivityEpisodesBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EpisodesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEpisodesBinding
    private lateinit var adapter: EpisodeAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEpisodesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val manga: Manga? = intent.getParcelableExtra<Parcelable>("manga") as? Manga

        supportActionBar?.title = manga?.title
        manga?.thumbnail?.let { imageUrl ->
            Glide.with(this).load(imageUrl).into(binding.coverImageView)
        }

        binding.episodesRecyclerView.layoutManager = LinearLayoutManager(this)

        manga?.let {
            Log.d("Episode",it.toString())
            fetchEpisodes(it)
        }

    }

    private fun fetchEpisodes(manga : Manga) {
        val mangaId = manga.id
        RetrofitClient.apiService.getEpisodes(mangaId).enqueue(object : Callback<ApiResponse<List<Episode>>> {
            override fun onResponse(call: Call<ApiResponse<List<Episode>>>, response: Response<ApiResponse<List<Episode>>>) {
                if (response.isSuccessful) {
                    val apiResponse = response.body()
                    val episodes = apiResponse?.results ?: emptyList()
                    // Process episodes here
                        adapter = EpisodeAdapter(episodes) { episode ->
                            val intent = Intent(this@EpisodesActivity, ReaderActivity::class.java)
                            intent.putExtra("episode", episode)
                            intent.putExtra("manga",manga)
                            startActivity(intent)
                        }
                        binding.episodesRecyclerView.adapter = adapter

                    for (episode in episodes) {
                        Log.d("Episode", "${episode.id} - ${episode.title}")
                    }
                } else {
                    Log.e("MainActivity", "Error fetching episodes: ${response.code()} - ${response.message()}")
                    // Handle error cases here
                }
            }

            override fun onFailure(call: Call<ApiResponse<List<Episode>>>, t: Throwable) {
                Log.e("MainActivity", "Network error: ${t.message}", t)
                // Handle network failures here
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
