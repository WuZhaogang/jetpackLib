package com.wzg.jetpacklib.http

import android.content.Context
import android.os.Environment
import com.google.gson.*
import com.wzg.jetpacklib.BaseApp
import com.wzg.jetpacklib.utils.NetWorkUtils
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.reflect.Type
import java.text.DateFormat
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.TimeUnit

/**
 * 类描述:
 * 创建人:    wzg
 * 创建时间:  2020/3/23
 * 修改备注:  说明本次修改内容
 */
class RetrofitClient {
    var mRetrofit: Retrofit? = null

    private var builder: GsonBuilder? = null

    private var mGson: Gson? = null

    private val serviceMap = ConcurrentHashMap<String, Any>()

    //缓存配置
    private val mCacheInterceptor: Interceptor = object : Interceptor {
        @Throws(IOException::class)
        override fun intercept(chain: Interceptor.Chain): Response {
            val cacheBuilder = CacheControl.Builder()
            cacheBuilder.maxAge(0, TimeUnit.SECONDS)
            cacheBuilder.maxStale(365, TimeUnit.DAYS)
            val cacheControl = cacheBuilder.build()
            var request = chain.request()
            if (!NetWorkUtils.isNetworkAvailable(BaseApp.getInstance())) {
                request = request.newBuilder()
                        .cacheControl(cacheControl)
                        .build()
            }
            val originalResponse = chain.proceed(request)
            return if (NetWorkUtils.isNetworkAvailable(BaseApp.getInstance())) {
                val maxAge = 0 // read from cache
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public ,max-age=$maxAge")
                        .build()
            } else {
                val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale
                originalResponse.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                        .build()
            }
        }
    }

    private fun createRetrofit(url: String): Retrofit {
        val builder = OkHttpClient.Builder()
                .readTimeout(READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(CONNECT_TIMEOUT, TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
        val headerInterceptor: Interceptor = object : Interceptor {
            @Throws(IOException::class)
            override fun intercept(chain: Interceptor.Chain): Response {
                val builder = chain.request().newBuilder()

                return chain.proceed(builder.build())
            }
        }

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(httpLoggingInterceptor)
        builder.addInterceptor(headerInterceptor)

        val client = builder.build()
        return Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    fun <T : Any> getService(clz: Class<T>, host: String): T {
        if (serviceMap.containsKey(host)) {
            val obj = serviceMap[host] as? T
            obj?.let {
                return obj
            }
        }
        val value = createRetrofit(host).create(clz)
        serviceMap[host] = value
        return value
    }

    private fun initGsonBuilder() {
        builder = GsonBuilder()
        builder!!.setPrettyPrinting()
        builder!!.registerTypeAdapter(Date::class.java, DateSerializer()).setDateFormat(DateFormat.LONG)
        builder!!.registerTypeAdapter(Date::class.java, DateDeserializer()).setDateFormat(DateFormat.LONG)
        builder!!.registerTypeAdapter(Date::class.java, DoubleSerializer())
    }

    private fun getDiskCachePath(context: Context): String? {
        return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
            context.externalCacheDir!!.path
        } else {
            context.cacheDir.path
        }
    }

    companion object {
        private const val READ_TIMEOUT: Long = 10000

        private const val WRITE_TIMEOUT: Long = 10000

        private const val CONNECT_TIMEOUT: Long = 10000

        private var mApiRetrofit: RetrofitClient? = null
        fun getInstance(): RetrofitClient {
            if (mApiRetrofit == null) {
                synchronized(Any::class.java) {
                    if (mApiRetrofit == null) {
                        mApiRetrofit = RetrofitClient()
                    }
                }
            }
            return mApiRetrofit!!
        }
    }

    fun getGson(): Gson? {
        if (mGson == null) {
            if (builder == null) {
                initGsonBuilder()
            }
            mGson = builder!!.create()
        }
        return mGson
    }

    private class DoubleSerializer : JsonSerializer<Double> {
        override fun serialize(
                src: Double,
                typeOfSrc: Type,
                context: JsonSerializationContext
        ): JsonElement {
            return if (src == src.toLong()
                            .toDouble()
            ) JsonPrimitive(src.toLong()) else JsonPrimitive(src)
        }
    }

    private class DateSerializer : JsonSerializer<Date> {
        override fun serialize(
                src: Date,
                typeOfSrc: Type,
                context: JsonSerializationContext
        ): JsonElement {
            return JsonPrimitive(src.time)
        }
    }

    private class DateDeserializer : JsonDeserializer<Date> {
        @Throws(JsonParseException::class)
        override fun deserialize(
                json: JsonElement,
                typeOfT: Type,
                context: JsonDeserializationContext
        ): Date {
            return Date(json.asJsonPrimitive.asLong)
        }
    }
}