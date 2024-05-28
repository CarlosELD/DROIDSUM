package com.example.Evaluacion_U1.workers.Request

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class WorkerRequestLogin (ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params){
    var contexto=(ctx.applicationContext as com.example.Evaluacion_U1.AlumnosApplication).container.alumnosRepository
    override suspend fun doWork(): Result {
        var matricula=inputData.getString("matricula")
        var password=inputData.getString("password")
        var tipo=inputData.getString("tipo")
        if(!matricula.isNullOrEmpty() && !password.isNullOrEmpty() && !tipo.isNullOrEmpty()){
            var credencial=contexto.getAccess(matricula,password,tipo)
            Log.d("worker",credencial)
            var informacion=contexto.getInfo()
            Log.d("worker",informacion)
            var outputData = workDataOf("credencial" to credencial,"informacion" to informacion,"contra" to password)
            return Result.success(outputData)
        }
        return Result.failure()
    }
}