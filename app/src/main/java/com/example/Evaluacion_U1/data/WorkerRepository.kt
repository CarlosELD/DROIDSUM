package com.example.Evaluacion_U1.data

import android.content.Context
import androidx.lifecycle.asFlow
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.example.Evaluacion_U1.workers.DB.WorkerDBFinales
import com.example.Evaluacion_U1.workers.DB.WorkerDBHorario
import com.example.Evaluacion_U1.workers.DB.WorkerDBKardex
import com.example.Evaluacion_U1.workers.DB.WorkerDBKardex2
import com.example.Evaluacion_U1.workers.DB.WorkerDBLogin
import com.example.Evaluacion_U1.workers.DB.WorkerDBParciales
import com.example.Evaluacion_U1.workers.DB.WorkerDBResumenKardex
import com.example.Evaluacion_U1.workers.Request.WorkerRequestFinales
import com.example.Evaluacion_U1.workers.Request.WorkerRequestHorario
import com.example.Evaluacion_U1.workers.Request.WorkerRequestKardex
import com.example.Evaluacion_U1.workers.Request.WorkerRequestKardex2
import com.example.Evaluacion_U1.workers.Request.WorkerRequestLogin
import com.example.Evaluacion_U1.workers.Request.WorkerRequestParciales
import com.example.Evaluacion_U1.workers.Request.WorkerRequestResumenKardex
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.mapNotNull



interface WorkerRepository {
    val outputWorkInfo: Flow<WorkInfo>
    val outputWorkFinales: Flow<WorkInfo>
    val outputWorkParciales: Flow<WorkInfo>
    val outputWorkHorario: Flow<WorkInfo>
    val outputWorkKardex: Flow<WorkInfo>
    val outputWorkKardex2: Flow<WorkInfo>
    val outputWorkKardexResumen: Flow<WorkInfo>
    fun getAccess(matricula: String, password: String, tipoUsuario: String)
    fun getFinales(modoEducativo:String)
    fun getParciales()
    fun getHorario()
    fun getKardex(lineamiento:String)
    fun getKardex2(lineamiento:String)
    fun getResumenKardex(lineamiento:String)
}

class WorkerManagement(context: Context): WorkerRepository {
    private val workManager = WorkManager.getInstance(context)
    override fun getAccess(matricula: String, password: String, tipoUsuario: String) {
        val data = workDataOf("matricula" to matricula, "password" to password, "tipo" to tipoUsuario)
        val workerLogin = OneTimeWorkRequestBuilder<WorkerRequestLogin>().setInputData(data).addTag("WorkerAccess").build()
        val workerDbLogin = OneTimeWorkRequestBuilder<WorkerDBLogin>().addTag("WorkerAccessInfo").build()
        workManager.beginUniqueWork("acceso", ExistingWorkPolicy.REPLACE,workerLogin).then(workerDbLogin).enqueue()
    }
    override fun getFinales(modoEducativo:String) {
        val data = workDataOf("modoEducativo" to modoEducativo)
        val workerFinales = OneTimeWorkRequestBuilder<WorkerRequestFinales>().setInputData(data).addTag("WorkerFinales").build()
        val workerDbFinales = OneTimeWorkRequestBuilder<WorkerDBFinales>().addTag("WorkerFinalesInfo").build()
        workManager.beginUniqueWork(
            "finalesWorker",
            ExistingWorkPolicy.REPLACE,
            workerFinales
        ).then(workerDbFinales).enqueue()
    }
    override fun getParciales() {
        val workerParciales = OneTimeWorkRequestBuilder<WorkerRequestParciales>()
            .addTag("WorkerParciales")
            .build()

        val workerDbParciales= OneTimeWorkRequestBuilder<WorkerDBParciales>()
            .addTag("WorkerParcialesInfo")
            .build()

        workManager.beginUniqueWork(
            "parcialesWorker",
            ExistingWorkPolicy.REPLACE,
            workerParciales
        ).then(workerDbParciales)
            .enqueue()
    }
    override fun getHorario() {
        val workerHorario = OneTimeWorkRequestBuilder<WorkerRequestHorario>()
            .addTag("WorkerHorario")
            .build()

        val workerDbHorario = OneTimeWorkRequestBuilder<WorkerDBHorario>()
            .addTag("WorkerHorarioInfo")
            .build()
        workManager.beginUniqueWork(
            "horarioWorker",
            ExistingWorkPolicy.REPLACE,
            workerHorario
        ).then(workerDbHorario)
            .enqueue()
    }

    override fun getKardex(lineamiento: String) {
        val data = workDataOf("lineamiento" to lineamiento)
        val workerKardex = OneTimeWorkRequestBuilder<WorkerRequestKardex>()
            .setInputData(data)
            .addTag("WorkerKardex")
            .build()

        val workerDbKardex = OneTimeWorkRequestBuilder<WorkerDBKardex>()
            .addTag("WorkerKardexInfo")
            .build()

        workManager.beginUniqueWork(
            "kardexWorker",
            ExistingWorkPolicy.REPLACE,
            workerKardex
        ).then(workerDbKardex)
            .enqueue()
    }
    override fun getKardex2(lineamiento: String) {
        val data = workDataOf("lineamiento" to lineamiento)
        val workerKardex2 = OneTimeWorkRequestBuilder<WorkerRequestKardex2>()
            .setInputData(data)
            .addTag("WorkerKardex2")
            .build()

        val workerDbKardex2 = OneTimeWorkRequestBuilder<WorkerDBKardex2>()
            .addTag("WorkerKardex2Info")
            .build()

        workManager.beginUniqueWork(
            "kardexWorker2",
            ExistingWorkPolicy.REPLACE,
            workerKardex2
        ).then(workerDbKardex2)
            .enqueue()
    }

    override fun getResumenKardex(lineamiento: String) {
        val data = workDataOf(
            "lineamiento" to lineamiento,
        )
        val workerRK = OneTimeWorkRequestBuilder<WorkerRequestResumenKardex>()
            .setInputData(data)
            .addTag("WorkerResumenKardex")
            .build()
        val workerDbRK = OneTimeWorkRequestBuilder<WorkerDBResumenKardex>()
            .addTag("WorkerResumenKardexInfo")
            .build()
        workManager.beginUniqueWork(
            "kardexResumenWorker",
            ExistingWorkPolicy.REPLACE,
            workerRK
        ).then(workerDbRK)
            .enqueue()

    }

    override val outputWorkInfo: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerAccess").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    override val outputWorkFinales: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerFinales").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    override val outputWorkParciales: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerParciales").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }
    override val outputWorkHorario: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerHorario").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }
    override val outputWorkKardex: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerKardex").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    override val outputWorkKardex2: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerKardex2").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }

    override val outputWorkKardexResumen: Flow<WorkInfo> =
        workManager.getWorkInfosByTagLiveData("WorkerResumenKardex").asFlow().mapNotNull {
            if (it.isNotEmpty()) it.first() else null
        }
}

//interfaz que especifica métodos para realizar diversas tareas relacionadas con la recuperación de datos de la base de datos.