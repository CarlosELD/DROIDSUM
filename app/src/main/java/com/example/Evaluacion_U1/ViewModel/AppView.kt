package com.example.Evaluacion_U1.ViewModel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.work.WorkInfo
import com.example.Evaluacion_U1.data.AlumnosRepositoryDB
import com.example.Evaluacion_U1.data.WorkerRepository
import com.example.Evaluacion_U1.modelos.CredencialesAlumno
import com.example.Evaluacion_U1.modelos.FinalesAlumno
import com.example.Evaluacion_U1.modelos.HorarioAlumno
import com.example.Evaluacion_U1.modelos.InformacionAlumno
import com.example.Evaluacion_U1.modelos.KardexMateria
import com.example.Evaluacion_U1.modelos.ParcialesAlumno
import com.example.Evaluacion_U1.modelos.ResumenKardex
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class AppView(private val workerRepository: WorkerRepository,private val alumnosRepositoryDB: AlumnosRepositoryDB):ViewModel(){
    var matricula by mutableStateOf("")
    var password by mutableStateOf("")
    var fechaDatos by mutableStateOf("")
    var errorLogin by mutableStateOf(false)
    var internetDisponible by mutableStateOf(false)
    var errorInternet by mutableStateOf(false)
    var expandido by mutableStateOf(false)
    var sinFinales by mutableStateOf(false)
    var sinKardex by mutableStateOf(false)
    var sinParciales by mutableStateOf(false)
    var sinHorario by mutableStateOf(false)
    var kardex1Finalizado by mutableStateOf(false)
    var kardex1Actualizado by mutableStateOf(false)
    var kardex2Finalizado by mutableStateOf(false)
    var kardex2Actualizado by mutableStateOf(false)
    var tipoUsuario by mutableStateOf("ALUMNO")
    var kardex: MutableList<KardexMateria> = mutableListOf()
    var horario: MutableList<HorarioAlumno> = mutableListOf()
    var parciales: MutableList<ParcialesAlumno> = mutableListOf()
    var finales: MutableList<FinalesAlumno> = mutableListOf()
    var lunes: MutableList<HorarioAlumno> = mutableListOf()
    var martes: MutableList<HorarioAlumno> = mutableListOf()
    var miercoles: MutableList<HorarioAlumno> = mutableListOf()
    var jueves: MutableList<HorarioAlumno> = mutableListOf()
    var viernes: MutableList<HorarioAlumno> = mutableListOf()
    var resumenKardex by mutableStateOf(ResumenKardex())
    var modoOffline by mutableStateOf(false)
    var credencialAlumno:CredencialesAlumno= CredencialesAlumno()
    var infoAlumno:InformacionAlumno= InformacionAlumno()
    var daClicBoton by mutableStateOf(false)
    var daClicFinales by mutableStateOf(false)
    var daClicKardex by mutableStateOf(false)
    var daClicParciales by mutableStateOf(false)
    var daClicHorario by mutableStateOf(false)
    var acceso by mutableStateOf(false)
    /*
     Se llama el estado del workerUiState utilizando StateFlow.
         Este flujo representa el estado de acceso al trabajador y su progreso estamos
         hablando que todas las val worker hacen lo mismo pero con diferentes elementos
    */
    val workerUiState: StateFlow<WorkerAccessState> = workerRepository.outputWorkInfo
        // Se realiza un mapeo del flujo de salida
        .map { info ->
            // Se extraen los datos de acceso y de información
            val acceso = info.outputData.getString("credencial")
            val informacion=info.outputData.getString("informacion")
            //entramos al flujo delos datos y determinamos si entra como tarea finalisada , fallo o que siga adelante
            when {
                info.state.isFinished  && !acceso.isNullOrEmpty() && !informacion.isNullOrEmpty()-> {
                    WorkerAccessState.Complete(acceso, informacion)
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default
                }
                else ->WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000,0),initialValue = WorkerAccessState.Default)
    val workerUiStateFinales: StateFlow<WorkerAccessState> = workerRepository.outputWorkFinales
        .map { info ->
            val finales = info.outputData.getString("finales")
            when {
                info.state.isFinished  && !finales.isNullOrEmpty()-> {
                    WorkerAccessState.Complete(finales,"")
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default
                }
                else ->WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000),initialValue = WorkerAccessState.Default)
    val workerUiStateParciales: StateFlow<WorkerAccessState> = workerRepository.outputWorkParciales
        .map { info ->
            val parciales = info.outputData.getString("parciales")
            when {
                info.state.isFinished  && !parciales.isNullOrEmpty()-> {
                    WorkerAccessState.Complete(parciales,"")
                }
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default
                }
                else ->WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000),initialValue = WorkerAccessState.Default)
    val workerUiStateHorario: StateFlow<WorkerAccessState> = workerRepository.outputWorkHorario.map { info ->
            val horario = info.outputData.getString("horario")
            when {info.state.isFinished  && !horario.isNullOrEmpty()-> {
                    WorkerAccessState.Complete(horario,"")}
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default }
                else -> WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope, started = SharingStarted.WhileSubscribed(5_000),initialValue = WorkerAccessState.Default)
    val workerUiStateKardexResumen: StateFlow<WorkerAccessState> = workerRepository.outputWorkKardexResumen.map { info ->
            val kardexR = info.outputData.getString("kardexResumen")
            when {info.state.isFinished  && !kardexR.isNullOrEmpty()-> {
                    WorkerAccessState.Complete(kardexR,"")}
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default}
                else ->WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000),initialValue = WorkerAccessState.Default)
    val workerUiStateKardex: StateFlow<WorkerAccessState> = workerRepository.outputWorkKardex.map { info ->
            val kardex1 = info.outputData.getString("kardex")
            when {info.state.isFinished  && !kardex1.isNullOrEmpty()-> {
                    kardex1Finalizado=true
                    WorkerAccessState.Complete(kardex1,"")}
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default }
                else -> WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000),initialValue = WorkerAccessState.Default)
    val workerUiStateKardex2: StateFlow<WorkerAccessState> = workerRepository.outputWorkKardex2.map { info ->
            val kardex2 = info.outputData.getString("kardex2")
            when {info.state.isFinished && !kardex2.isNullOrEmpty()-> {
                    kardex2Finalizado=true
                    WorkerAccessState.Complete(kardex2,"")}
                info.state == WorkInfo.State.FAILED -> {
                    WorkerAccessState.Default }
                else ->WorkerAccessState.Loading
            }
        }.stateIn(scope = viewModelScope,started = SharingStarted.WhileSubscribed(5_000),initialValue = WorkerAccessState.Default)
    fun updateInternetDisponible(boolean: Boolean){internetDisponible=boolean }
    fun updateDaClicBoton(boolean: Boolean){daClicBoton=boolean}
    fun updateDaClicFinales(boolean: Boolean){daClicFinales=boolean}
    fun updateDaClicHorario(boolean: Boolean){daClicHorario=boolean}
    fun updateDaClicParciales(boolean: Boolean){daClicParciales=boolean}
    fun updateDaClicKardex(boolean: Boolean){daClicKardex=boolean}
    fun updateAcceso(boolean: Boolean){acceso=boolean}
    fun updateErrorInternet(boolean: Boolean){errorInternet=boolean}
    fun updateExpandido(boolean: Boolean){expandido=boolean}
    fun updateOffline(boolean: Boolean){modoOffline=boolean}
    fun updateSinFinales(boolean: Boolean){sinFinales=boolean}
    fun updateSinParciales(boolean: Boolean){sinParciales=boolean}
    fun updateSinHorario(boolean: Boolean){sinHorario=boolean}
    fun updateSinKardex(boolean: Boolean){sinKardex=boolean}
    fun updateTipoUsuario(string: String){tipoUsuario=string}
    fun updateMatricula(string: String){matricula=string}
    fun updatePassword(string: String){password=string}
    fun updateFecha(string: String){fechaDatos=string}
    fun updateErrorLogin(boolean: Boolean){errorLogin=boolean}
    fun proporcionarAcceso(jsonCredencial:String,jsonInfo:String):Boolean{
        var credencial=Gson().fromJson(jsonCredencial, CredencialesAlumno::class.java)
        var informacion=Gson().fromJson(jsonInfo, InformacionAlumno::class.java)
        Log.d("worker",jsonCredencial)
        Log.d("worker",jsonInfo)
        if(credencial.acceso=="true" ){
            credencialAlumno=credencial
            infoAlumno=informacion
            return true
        }
        return false
    }
    fun actualizarFinales(json:String){ val type = object : TypeToken<List<FinalesAlumno>>() {}.type
        finales= Gson().fromJson(json, type)}
    fun actualizarHorario(json:String){
        val type = object : TypeToken<List<HorarioAlumno>>() {}.type
        horario= Gson().fromJson(json, type)
        limpiarListasDias()
        horario.forEach{
            if(it.Lunes.length>0)
               lunes.add(it)
            if(it.Martes.length>0)
               martes.add(it)
            if(it.Miercoles.length>0)
                miercoles.add(it)
            if(it.Jueves.length>0)
                jueves.add(it)
            if(it.Viernes.length>0)
                viernes.add(it)
        }
    }
    fun actualizarHorarioDB(horario:MutableList<HorarioAlumno>){
        limpiarListasDias()
        horario.forEach{
            if(it.Lunes.length>0)
                lunes.add(it)
            if(it.Martes.length>0)
                martes.add(it)
            if(it.Miercoles.length>0)
                miercoles.add(it)
            if(it.Jueves.length>0)
                jueves.add(it)
            if(it.Viernes.length>0)
                viernes.add(it)
        }
    }
    fun limpiarListasDias(){
        lunes.clear()
        martes.clear()
        miercoles.clear()
        jueves.clear()
        viernes.clear()
    }
    fun actualizarParciales(json:String){
        val type = object : TypeToken<List<ParcialesAlumno>>() {}.type
        var parcialesLista:MutableList<ParcialesAlumno> = Gson().fromJson(json, type)
        parcialesLista.forEach {
            if (it.C1 == null)
                it.C1 = ""
            if (it.C2 == null)
                it.C2 = ""
            if (it.C3 == null)
                it.C3 = ""
            if (it.C4 == null)
                it.C4 = ""
            if (it.C5 == null)
                it.C5 = ""
            if (it.C6 == null)
                it.C6 = ""
            if (it.C7 == null)
                it.C7 = ""
            if (it.C8 == null)
                it.C8 = ""
            if (it.C9 == null)
                it.C9 = ""
            if (it.C10 == null)
                it.C10 = ""
            if (it.C11 == null)
                it.C11 = ""
            if (it.C12 == null)
                it.C12 = ""
            if (it.C13 == null)
                it.C13 = ""
        }
        parciales=parcialesLista
    }
    fun actualizarResumenKardex(jsonKardexResumen:String){resumenKardex= Gson().fromJson(jsonKardexResumen, ResumenKardex::class.java) }
    fun actualizarKardex1(jsonKardex:String){
        var type = object : TypeToken<List<KardexMateria>>() {}.type
        var kardexL: MutableList<KardexMateria> = Gson().fromJson(jsonKardex, type)
        kardexL.forEach { kardex.add(it)}
        kardex1Actualizado=true
    }
    fun actualizarKardex2(jsonKardex:String){
        var type = object : TypeToken<List<KardexMateria>>() {}.type
        var kardexL: MutableList<KardexMateria> = Gson().fromJson(jsonKardex, type)
        kardexL.forEach {kardex.add(it)}
        kardex2Actualizado=true
    }
    fun limpiarKardex(){kardex.clear()}
    fun getAccess() {if(internetDisponible){workerRepository.getAccess(matricula, password, tipoUsuario)}}
    suspend fun getAccessDB():Boolean{ //acceso a los datos guardados
        var credencial=alumnosRepositoryDB.getAccessDB(matricula.uppercase())
        credencial?.let {
            if(tipoUsuario.equals("ALUMNO")){
                if (credencial.contrasenia==password && credencial.tipoUsuario==0){
                    infoAlumno=alumnosRepositoryDB.getInfoDB()
                    errorLogin=false
                    daClicBoton=false
                    modoOffline=true
                    return true
                }
                errorLogin=true
                return false
            }
        }
        errorInternet=true
        return false
    }
    suspend fun getFinalesDB(){
        var finalesDatos=alumnosRepositoryDB.getFinalesDB()
        if(!finalesDatos.isNullOrEmpty()) {
            sinFinales = false
            finales = finalesDatos
        }else{sinFinales=true}
    }
    suspend fun getParcialesDB(){
        var parcialesDatos=alumnosRepositoryDB.getParcialesDB()
        if(!parcialesDatos.isNullOrEmpty()) {
            sinParciales = false
            parciales = parcialesDatos
        }else{sinParciales=true}
    }
    suspend fun getHorarioDB(){
        var horarioDatos=alumnosRepositoryDB.getHorarioDB()
        if(!horarioDatos.isNullOrEmpty()) {
            sinHorario = false
            horario = horarioDatos
            actualizarHorarioDB(horario)
        }else{sinHorario=true}
    }
    suspend fun getKardexDB(){
        var kardexDatos=alumnosRepositoryDB.getKardexDB(infoAlumno.lineamiento.toString())
        var kardexResumen=alumnosRepositoryDB.getResumenKardexDB()
        kardexResumen?.let {
            sinKardex=false
            kardex= kardexDatos!!
            resumenKardex=kardexResumen
        }?:run{ sinKardex=true}
    }

     fun getKardex(){
         if(internetDisponible){
             workerRepository.getKardex(infoAlumno.lineamiento.toString())
             workerRepository.getKardex2(infoAlumno.lineamiento.toString())
             workerRepository.getResumenKardex(infoAlumno.lineamiento.toString())
         }
    }
    fun getHorario(){if(internetDisponible){workerRepository.getHorario()}}
    fun getParciales(){if(internetDisponible){workerRepository.getParciales()}}
    fun getFinales(){if(internetDisponible){workerRepository.getFinales(infoAlumno.modEducativo)}}
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as com.example.Evaluacion_U1.AlumnosApplication)
                val workerRepository=application.container.workerRepository
                val alumnosRepositoryDB=application.container.alumnosRepositoryDB
                AppView( workerRepository = workerRepository,alumnosRepositoryDB=alumnosRepositoryDB)
            }
        }
    }
}
sealed interface WorkerAccessState {// estados del work
    object Default : WorkerAccessState
    object Loading : WorkerAccessState
    data class Complete(val outputUno: String,val outputDos: String) : WorkerAccessState
}