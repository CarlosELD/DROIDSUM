package com.example.Evaluacion_U1.workers.DB

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.Evaluacion_U1.AlumnosApplication
import com.example.Evaluacion_U1.modelos.CredencialesAlumno
import com.example.Evaluacion_U1.modelos.InformacionAlumno
import com.google.gson.Gson

class WorkerDBLogin(ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var contexto=(ctx.applicationContext as AlumnosApplication).container.alumnosRepositoryDB
    override suspend fun doWork(): Result {
        var jsonAcceso=inputData.getString("credencial")//obtener en base a la llave, si no hay se pone null
        var jsonInfo=inputData.getString("informacion")
        var password=inputData.getString("contra")
        if(!jsonAcceso.isNullOrEmpty() && !jsonInfo.isNullOrEmpty()){
                    var credencial= Gson().fromJson(jsonAcceso, CredencialesAlumno::class.java)
                    credencial.contrasenia=password.let { it }?:""
                    contexto.deleteCredenciales()
                    contexto.insertCredenciales(credencial)
                    var informacion= Gson().fromJson(jsonInfo, InformacionAlumno::class.java)
                    contexto.deleteInformacion()
                    contexto.insertInformacion(informacion)
                    return Result.success()
        }
        return  Result.failure()
    }
}