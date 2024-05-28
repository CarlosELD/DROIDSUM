package com.example.Evaluacion_U1.workers.DB

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.Evaluacion_U1.modelos.FinalesAlumno
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
//procesamos los datos del work en segundo plano
class WorkerDBFinales (ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var contexto=(ctx.applicationContext as com.example.Evaluacion_U1.AlumnosApplication).container.alumnosRepositoryDB
    override suspend fun doWork(): Result {
        var jsonFinales=inputData.getString("finales")
        if(!jsonFinales.isNullOrEmpty()){
            var type = object : TypeToken<List<FinalesAlumno>>() {}.type
            var finales: List<FinalesAlumno> = Gson().fromJson(jsonFinales, type)
            contexto.deleteFinales()
            finales.forEach {
                contexto.insertFinal(it)
            }
            return Result.success()
        }
        return  Result.failure()
    }
}