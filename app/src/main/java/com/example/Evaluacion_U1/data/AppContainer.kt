package com.example.Evaluacion_U1.data

import android.app.Application
import android.content.Context
import com.example.Evaluacion_U1.network.AlumnoApiService
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.simplexml.SimpleXmlConverterFactory

interface AppContainer{
    val alumnosRepository:AlumnosRepository
    val alumnosRepositoryDB:AlumnosRepositoryDB
    val workerRepository:WorkerRepository
}

class DefaultAppContainer(private val context: Context): AppContainer {
    private val BASE_URL ="https://sicenet.surguanajuato.tecnm.mx/"
    private val interceptor= CookiesInterceptor()
    private val cliente = OkHttpClient.Builder().addInterceptor(interceptor).build()
    private val retrofit = Retrofit.Builder().addConverterFactory(SimpleXmlConverterFactory.create()).baseUrl(BASE_URL).client(cliente).build()
    private val retrofitService : AlumnoApiService by lazy {retrofit.create(AlumnoApiService::class.java)}
    override val alumnosRepositoryDB: AlumnosRepositoryDB by lazy {OfflineAlumnoRepository(AlumnoDatabase.getDatabase(context).alumnoDAO())}
    override val workerRepository: WorkerRepository by lazy {WorkerManagement(context)}
    override val alumnosRepository: AlumnosRepository by lazy {NetworkAlumnosRepository(retrofitService)}
}

class CookiesInterceptor : Interceptor {
    private val cookieStore = mutableMapOf<String, String>()
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val cookiesHeader = StringBuilder()
        for ((name, value) in cookieStore) {if (cookiesHeader.isNotEmpty()) {cookiesHeader.append("; ")}
            cookiesHeader.append("$name=$value")
        }
        if (cookiesHeader.isNotEmpty()) {request = request.newBuilder().header("Cookie", cookiesHeader.toString()).build()}
        val response = chain.proceed(request)
        val receivedCookies = response.headers("Set-Cookie")
        for (cookie in receivedCookies) {
            val parts = cookie.split(";")[0].split("=")
            if (parts.size == 2) {
                val name = parts[0]
                val value = parts[1]
                cookieStore[name] = value
            }
        }
        return response
    }
}

/*
* la infraestructura es para manejar la comunicación con el servidor a través de Retrofit,
* administrar el almacenamiento de cookies y proporcionar acceso a los componentes clave de la
* aplicación a través del contenedor de aplicación.
* */
