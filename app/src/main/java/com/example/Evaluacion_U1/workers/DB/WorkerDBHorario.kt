package com.example.Evaluacion_U1.workers.DB

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.Evaluacion_U1.modelos.HorarioAlumno
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WorkerDBHorario (ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var contexto=(ctx.applicationContext as com.example.Evaluacion_U1.AlumnosApplication).container.alumnosRepositoryDB
    override suspend fun doWork(): Result {
        var jsonHorario=inputData.getString("horario")
        if(!jsonHorario.isNullOrEmpty()){
            var type = object : TypeToken<List<HorarioAlumno>>() {}.type
            var horario: List<HorarioAlumno> = Gson().fromJson(jsonHorario, type)
            contexto.deleteHorario()
            horario.forEach {
                contexto.insertHorario(it)
            }
            return Result.success()
        }
        return  Result.failure()
    }
}

