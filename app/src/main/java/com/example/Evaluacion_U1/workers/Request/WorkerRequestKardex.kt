package com.example.Evaluacion_U1.workers.Request

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class WorkerRequestKardex (ctx: Context, params: WorkerParameters): CoroutineWorker(ctx, params){
    var contexto=(ctx.applicationContext as com.example.Evaluacion_U1.AlumnosApplication).container.alumnosRepository
    override suspend fun doWork(): Result {
        var lineamiento=inputData.getString("lineamiento")
        if(!lineamiento.isNullOrEmpty()){
            var kardex=contexto.getKardex(lineamiento).split("[","]")
            var kardexM=kardex.get(1).split("},{")
            var jsonKardex1="["
            for (i in 0..kardexM.size/2-1){
                if(i==0){
                    jsonKardex1+=kardexM.get(i)+"},"
                }else{
                    if(i==((kardexM.size/2)-1)){
                        jsonKardex1+="{"+kardexM.get(i)+"}]"
                    }else{
                        jsonKardex1+="{"+kardexM.get(i)+"},"
                    }
                }
            }
            var outputData = workDataOf("kardex" to jsonKardex1)
            return Result.success(outputData)
        }
        return Result.failure()
    }
}