package com.example.Evaluacion_U1.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Horario")
data class HorarioAlumno(
    @PrimaryKey
    var clvOficial: String="",
    var CreditosMateria: String="",
    var Docente: String="",
    var EstadoMateria: String="",
    var Grupo: String="",
    var Jueves: String="",
    var Lunes: String="",
    var Martes: String="",
    var Materia: String="",
    var Miercoles: String="",
    var Observaciones: String="",
    var Sabado: String="",
    var Semipresencial: String="",
    var Viernes: String=""
)