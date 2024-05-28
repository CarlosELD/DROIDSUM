package com.example.Evaluacion_U1.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.Evaluacion_U1.modelos.CredencialesAlumno
import com.example.Evaluacion_U1.modelos.FinalesAlumno
import com.example.Evaluacion_U1.modelos.HorarioAlumno
import com.example.Evaluacion_U1.modelos.InformacionAlumno
import com.example.Evaluacion_U1.modelos.KardexMateria
import com.example.Evaluacion_U1.modelos.ParcialesAlumno
import com.example.Evaluacion_U1.modelos.ResumenKardex

@Dao
interface AlumnoDAO {
    @Insert
    suspend fun insertMatricula(credencialesAlumno: CredencialesAlumno)
    @Query("SELECT * from Credenciales where matricula=:mat")
    suspend fun getAccess(mat: String): CredencialesAlumno?
    @Query("SELECT COUNT(*) from Credenciales")
    suspend fun hayUsuario(): Int
    @Query("delete from Credenciales")
    suspend fun deleteMatricula()


    @Insert
    suspend fun insertCalificacionesFinal(finalesAlumno: FinalesAlumno)
    @Query("SELECT * from Finales")
    suspend fun getCalificacionFinal(): MutableList<FinalesAlumno>?
    @Query("delete from Finales")
    suspend fun deleteCalificacionFinal()


    @Insert
    suspend fun insertCargaAcademica(horarioAlumno: HorarioAlumno)
    @Query("SELECT * from Horario")
    suspend fun getCarcaAcademica(): MutableList<HorarioAlumno>?
    @Query("delete from Horario")
    suspend fun deleteCargaAcademica()

    @Insert
    suspend fun insertInformacionAlumno(informacionAlumno: InformacionAlumno)
    @Query("SELECT * from Informacion")
    suspend fun getInfo(): InformacionAlumno
    @Query("delete from Informacion")
    suspend fun deleteInformacion()


    @Insert
    suspend fun insertKardex(kardexMateria: KardexMateria)
    @Query("SELECT * from Kardex")
    suspend fun getKardex(): MutableList<KardexMateria>?
    @Query("delete from Kardex")
    suspend fun deleteKardex()

    @Insert
    suspend fun insertCalificacionesPUnidad(parcialesAlumno: ParcialesAlumno)
    @Query("SELECT * from Parciales")
    suspend fun getCalificacionesPUnidad(): MutableList<ParcialesAlumno>?
    @Query("delete from Parciales")
    suspend fun deleteCalificacionesPUnidad()

    @Insert
    suspend fun insertResumenKardex(resumenKardex: ResumenKardex)
    @Query("SELECT * from ResumenKardex")
    suspend fun getResumenKardex(): ResumenKardex?
    @Query("delete from ResumenKardex")
    suspend fun deleteResumen()





}