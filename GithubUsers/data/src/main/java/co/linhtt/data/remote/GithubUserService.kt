package co.linhtt.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface GithubUserService {
    @GET("users")
    suspend fun getUsers(@Query("since") query:Int, @Query("per_page") itemsPerPage:Int): List<GithubUser>
    companion object{
        private const val BASE_URL = "https://api.github.com/"

        fun create():GithubUserService{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubUserService::class.java)
        }
    }
}