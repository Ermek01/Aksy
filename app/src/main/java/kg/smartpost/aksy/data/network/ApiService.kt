package kg.smartpost.aksy.data.network

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kg.smartpost.aksy.data.network.category.model.ModelCategory
import kg.smartpost.aksy.data.network.category.model.ModelCategoryItem
import kg.smartpost.aksy.data.network.category.model.ModelSendKey
import kg.smartpost.aksy.data.network.items.model.ModelItemDetail
import kg.smartpost.aksy.data.network.items.model.ModelItems
import kg.smartpost.aksy.data.network.items.model.ModelSearchItems
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.*
import java.util.ArrayList

interface ApiService {

    @FormUrlEncoded
    @POST("api/category/list")
    suspend fun getCategories(
        @Field ("secret_key") secret_key: String,
    ): ModelCategory

    @FormUrlEncoded
    @POST("api/blog/list")
    suspend fun getItems(
        @Field ("secret_key") secret_key: String,
        @Field ("page") page: Int,
    ): ModelItems

    @FormUrlEncoded
    @POST("api/blog/show")
    suspend fun getItemsById(
        @Field ("secret_key") secret_key: String,
        @Field ("blogId") blogId: Int,
    ): ModelItemDetail

    @FormUrlEncoded
    @POST("api/blog/search")
    suspend fun searchItems(
        @Field ("secret_key") secret_key: String,
        @Field ("search") search: String?,
    ): ModelSearchItems

    companion object {

        private const val BASE_URL = "http://api-777.aio.kg/"

        operator fun invoke(): ApiService {
            val requestInterceptor = Interceptor { chain ->
                val url = chain.request()
                    .url
                    .newBuilder()
                    .build()

                val request = chain.request()
                    .newBuilder()
                    .url(url)
                    .build()

                return@Interceptor chain.proceed(request)
            }

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)

            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(requestInterceptor)
                .addInterceptor(logging)
                .build()

            return Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService::class.java)
        }
    }

}