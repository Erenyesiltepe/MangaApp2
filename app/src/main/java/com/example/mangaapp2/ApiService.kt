package com.example.mangaapp2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("mangas/mangas")
    fun getMangas(): Call<List<Manga>>

    @GET("mangas/episodes/{manga_id}")
    fun getEpisodes(@Path("manga_id") manga_id: Int): Call<ApiResponse<List<Episode>>>

    @GET("mangas/pages/{episodeId}")
    fun getPages(@Path("episodeId") episodeId: Int): Call<ApiResponse<List<Page>>>
}
