package pro.onlycode.testassignment.network

import io.reactivex.Single
import pro.onlycode.testassignment.data.models.Result
import retrofit2.http.GET
import retrofit2.http.Query

interface RetrofitInterface {
    @GET("search_by_date")
    fun getData(
        @Query("tags") tags: String,
        @Query("page") page: Int
    ): Single<Result>


}