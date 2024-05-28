package com.example.Evaluacion_U1.workers.DB

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.Evaluacion_U1.AlumnosApplication
import com.example.Evaluacion_U1.modelos.KardexMateria
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class WorkerDBKardex2 (ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params) {
    var contexto=(ctx.applicationContext as AlumnosApplication).container.alumnosRepositoryDB
    override suspend fun doWork(): Result {
        var jsonKardex2=inputData.getString("kardex2")
        if(!jsonKardex2.isNullOrEmpty()){
            var type = object : TypeToken<List<KardexMateria>>() {}.type
            var kardex: List<KardexMateria> = Gson().fromJson(jsonKardex2, type)
            kardex.forEach {
                if(it.A1==null)
                    it.A1=""
                if(it.A2==null)
                    it.A2=""
                if(it.A3==null)
                    it.A3=""
                if(it.P1==null)
                    it.P1=""
                if(it.P2==null)
                    it.P2=""
                if(it.P3==null)
                    it.P3=""
                if(it.S1==null)
                    it.S1=""
                if(it.S2==null)
                    it.S2=""
                if(it.S3==null)
                    it.S3=""
                contexto.insertKardex(it)
            }
            return Result.success()
        }
        return  Result.failure()
    }
}