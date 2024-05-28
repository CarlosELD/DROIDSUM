package com.example.Evaluacion_U1.workers.Request

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.Evaluacion_U1.AlumnosApplication

class WorkerRequestFinales (ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params){
    var contexto=(ctx.applicationContext as AlumnosApplication).container.alumnosRepository
    override suspend fun doWork(): Result {
        var modo=inputData.getString("modoEducativo")
        if(!modo.isNullOrEmpty()){
            var finales=contexto.getFinales(modo)
            var outputData = workDataOf("finales" to finales)
            return Result.success(outputData)
        }
        return Result.failure()
    }
}