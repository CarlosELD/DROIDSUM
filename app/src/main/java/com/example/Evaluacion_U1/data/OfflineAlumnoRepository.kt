package com.example.Evaluacion_U1.data

import com.example.Evaluacion_U1.modelos.CredencialesAlumno
import com.example.Evaluacion_U1.modelos.FinalesAlumno
import com.example.Evaluacion_U1.modelos.HorarioAlumno
import com.example.Evaluacion_U1.modelos.InformacionAlumno
import com.example.Evaluacion_U1.modelos.KardexMateria
import com.example.Evaluacion_U1.modelos.ParcialesAlumno
import com.example.Evaluacion_U1.modelos.ResumenKardex

interface AlumnosRepositoryDB{
    suspend fun hayUsuario():Int
    suspend fun getAccessDB(matricula: String): CredencialesAlumno?
    suspend fun getInfoDB(): InformacionAlumno
    suspend fun getKardexDB(lineamiento: String):MutableList<KardexMateria>?
    suspend fun getResumenKardexDB(): ResumenKardex?
    suspend fun getHorarioDB():MutableList<HorarioAlumno>?
    suspend fun getParcialesDB():MutableList<ParcialesAlumno>?
    suspend fun getFinalesDB():MutableList<FinalesAlumno>?
    suspend  fun insertCredenciales(credencialesAlumno: CredencialesAlumno)
    suspend  fun insertFinal(finalesAlumno: FinalesAlumno)
    suspend  fun insertHorario(horarioAlumno: HorarioAlumno)
    suspend  fun insertInformacion(informacionAlumno: InformacionAlumno)
    suspend  fun insertKardex(kardexMateria: KardexMateria)
    suspend  fun insertResumenKardex(resumenKardex: ResumenKardex)
    suspend  fun insertParciales(parcialesAlumno: ParcialesAlumno)
    suspend  fun insertResumen(resumenKardex: ResumenKardex)
    suspend fun deleteCredenciales()
    suspend fun deleteFinales()
    suspend fun deleteHorario()
    suspend fun deleteKardex()
    suspend fun deleteParciales()
    suspend fun deleteResumenKardex()
    suspend fun deleteInformacion()
}

class OfflineAlumnoRepository(private val alumnoDAO: AlumnoDAO):AlumnosRepositoryDB {
     override suspend  fun insertCredenciales(credencialesAlumno: CredencialesAlumno)=alumnoDAO.insertMatricula(credencialesAlumno)
     override suspend  fun insertFinal(finalesAlumno: FinalesAlumno)=alumnoDAO.insertCalificacionesFinal(finalesAlumno)
     override suspend  fun insertHorario(horarioAlumno: HorarioAlumno)=alumnoDAO.insertCargaAcademica(horarioAlumno)
     override suspend  fun insertInformacion(informacionAlumno: InformacionAlumno)=alumnoDAO.insertInformacionAlumno(informacionAlumno)
     override suspend  fun insertKardex(kardexMateria: KardexMateria)=alumnoDAO.insertKardex(kardexMateria)
     override suspend  fun insertResumenKardex(resumenKardex: ResumenKardex)=alumnoDAO.insertResumenKardex(resumenKardex)
     override suspend  fun insertParciales(parcialesAlumno: ParcialesAlumno)=alumnoDAO.insertCalificacionesPUnidad(parcialesAlumno)
     override suspend  fun insertResumen(resumenKardex: ResumenKardex)=alumnoDAO.insertResumenKardex(resumenKardex)


    override suspend fun hayUsuario():Int=alumnoDAO.hayUsuario()
    override suspend fun getAccessDB(matricula: String):CredencialesAlumno?=alumnoDAO.getAccess(matricula)
     override suspend fun getInfoDB():InformacionAlumno=alumnoDAO.getInfo()
     override suspend fun getKardexDB(lineamiento: String): MutableList<KardexMateria>? =alumnoDAO.getKardex()
     override suspend fun getHorarioDB(): MutableList<HorarioAlumno>? =alumnoDAO.getCarcaAcademica()
     override suspend fun getParcialesDB(): MutableList<ParcialesAlumno>? =alumnoDAO.getCalificacionesPUnidad()
     override suspend fun getFinalesDB(): MutableList<FinalesAlumno>? =alumnoDAO.getCalificacionFinal()
     override suspend fun getResumenKardexDB():ResumenKardex? = alumnoDAO.getResumenKardex()


     override suspend fun deleteCredenciales()=alumnoDAO.deleteMatricula()
     override suspend fun deleteFinales()=alumnoDAO.deleteCalificacionFinal()
     override suspend fun deleteHorario()=alumnoDAO.deleteCargaAcademica()
     override suspend fun deleteKardex()=alumnoDAO.deleteKardex()
     override suspend fun deleteParciales()=alumnoDAO.deleteCalificacionesPUnidad()
     override suspend fun deleteResumenKardex()=alumnoDAO.deleteResumen()
     override suspend fun deleteInformacion()=alumnoDAO.deleteInformacion()
}

//es una implementación concreta de este repositorio para trabajar offline, es decir, sin necesidad de una conexión a una base de datos.