package cn.edu.jumy.girls.http

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class GankService {
    companion object {
        val HOST = "http://gank.io/api/"
        var gankRetrofit: GankRetrofit
        get() {
            synchronized(monitor){
                if (gankRetrofit == null){
                    gankRetrofit = retrofit.create(GankRetrofit::class.java)
                }
            }
            return gankRetrofit
        }
        private val monitor = Any()
        var retrofit: Retrofit
        get() {
            synchronized(monitor){
                if (retrofit == null){
                    retrofit = Retrofit.Builder()
                            .baseUrl(HOST)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build()
                }
            }
            return retrofit
        }

        init {

            retrofit = Retrofit.Builder()
                    .baseUrl(HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build()

            gankRetrofit = retrofit.create(GankRetrofit::class.java)
        }
    }
}