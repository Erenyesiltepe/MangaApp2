package com.example.mangaapp2

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mangaapp2.databinding.ActivityReaderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReaderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityReaderBinding
    private lateinit var adapter: ReaderAdapter
    private lateinit var manga: Manga

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityReaderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val episode: Episode? = intent.getParcelableExtra("episode")
        // Retrieve manga object
        manga = intent.getParcelableExtra("manga") ?: throw IllegalArgumentException("Manga must not be null")

        // Set up toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = episode?.title

        episode?.id?.let { fetchPages(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // Handle back button (up navigation)
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun fetchPages(episodeId: Int) {
        RetrofitClient.apiService.getPages(episodeId)
            .enqueue(object : Callback<ApiResponse<List<Page>>> {
                override fun onResponse(
                    call: Call<ApiResponse<List<Page>>>,
                    response: Response<ApiResponse<List<Page>>>
                ) {
                    if (response.isSuccessful) {
                        val apiResponse = response.body()
                        val pages = apiResponse?.results ?: emptyList()

                        // Process pages here
                        adapter = ReaderAdapter(pages)
                        binding.recyclerView.layoutManager =
                            LinearLayoutManager(this@ReaderActivity)
                        binding.recyclerView.adapter = adapter
                    } else {
                        Log.e(
                            "ReaderActivity",
                            "Error fetching pages: ${response.code()} - ${response.message()}"
                        )
                        // Handle error cases here
                    }
                }

                override fun onFailure(call: Call<ApiResponse<List<Page>>>, t: Throwable) {
                    Log.e("ReaderActivity", "Network error: ${t.message}", t)
                    // Handle network failures here
                }
            })
    }}
