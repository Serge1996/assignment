package pro.onlycode.testassignment.network

import android.util.Base64
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import pro.onlycode.testassignment.BuildConfig
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object NetworkUtil {
    val retrofit: RetrofitInterface
        get() {

            val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

            return Retrofit.Builder()
                .baseUrl(BuildConfig.BASE_URL)
                .addCallAdapterFactory(rxAdapter)
                .addConverterFactory(NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(RetrofitInterface::class.java)

        }
    fun getRetrofitPath(path:String):RetrofitInterface{
        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
            .baseUrl(path)
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitInterface::class.java)
    }

    fun getRetrofit(email: String, password: String): RetrofitInterface {

        val credentials = "$email:$password"
        val basic = "Basic " + Base64.encodeToString(credentials.toByteArray(), Base64.NO_WRAP)
        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->

            val original = chain.request()
            val builder = original.newBuilder()
                .addHeader("Authorization", basic)
                .method(original.method(), original.body())
            chain.proceed(builder.build())

        }

        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient.build())
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitInterface::class.java)
    }

    fun getRetrofit(token: String): RetrofitInterface {

        val httpClient = OkHttpClient.Builder()

        httpClient.addInterceptor { chain ->

            val original = chain.request()
            val builder = original.newBuilder()
                .addHeader("x-access-token", token)
                .method(original.method(), original.body())
            chain.proceed(builder.build())

        }

        val rxAdapter = RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())

        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(httpClient.build())
            .addCallAdapterFactory(rxAdapter)
            .addConverterFactory(NullOnEmptyConverterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(RetrofitInterface::class.java)
    }
}

