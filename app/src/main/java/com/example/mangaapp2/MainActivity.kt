package com.example.mangaapp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.mangaapp2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: MangaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = GridLayoutManager(this, 2)

        // Set up toolbar
        setSupportActionBar(binding.toolbar)

        fetchMangas()
    }

    private fun fetchMangas() {
        RetrofitClient.apiService.getMangas().enqueue(object : Callback<List<Manga>> {
            override fun onResponse(call: Call<List<Manga>>, response: Response<List<Manga>>) {
                if (response.isSuccessful) {
                    val mangas = response.body() ?: emptyList()
                    adapter = MangaAdapter(mangas) { manga ->
                        val intent = Intent(this@MainActivity, EpisodesActivity::class.java)
                        intent.putExtra("manga", manga)
                        startActivity(intent)
                    }
                    binding.recyclerView.adapter = adapter
                } else {
                    Toast.makeText(this@MainActivity, "Failed to load mangas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Manga>>, t: Throwable) {
                Log.e("MainActivity", "Error fetching mangas", t)
                Toast.makeText(this@MainActivity, "Error fetching mangas", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
