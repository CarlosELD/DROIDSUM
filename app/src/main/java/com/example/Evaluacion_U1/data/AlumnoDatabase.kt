package com.example.Evaluacion_U1.data

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.Evaluacion_U1.modelos.CredencialesAlumno
import com.example.Evaluacion_U1.modelos.FinalesAlumno
import com.example.Evaluacion_U1.modelos.HorarioAlumno
import com.example.Evaluacion_U1.modelos.InformacionAlumno
import com.example.Evaluacion_U1.modelos.KardexMateria
import com.example.Evaluacion_U1.modelos.ParcialesAlumno
import com.example.Evaluacion_U1.modelos.ResumenKardex

@Database(entities = [CredencialesAlumno::class, FinalesAlumno::class, HorarioAlumno::class,
    InformacionAlumno::class, KardexMateria::class, ParcialesAlumno::class, ResumenKardex::class],
    version = 1, exportSchema = false)
abstract class AlumnoDatabase : RoomDatabase(){
    abstract fun alumnoDAO(): AlumnoDAO
    companion object {
        private const val PREF_NAME = "DatabaseLastAccessPrefs"
        private const val KEY_LAST_ACCESS_DATE = "lastAccessDate"
        @Volatile
        private var Instance: AlumnoDatabase? = null

        fun getDatabase(context: Context): AlumnoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AlumnoDatabase::class.java, "Alumno_database")
                    .build().also { Instance = it }
            }
        }
        fun getLastAccessDate(context: Context): Long {
            val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
            return sharedPreferences.getLong(KEY_LAST_ACCESS_DATE, 0L) // 0L es el valor predeterminado si no se encuentra ninguna fecha guardada
        }
    }
}
