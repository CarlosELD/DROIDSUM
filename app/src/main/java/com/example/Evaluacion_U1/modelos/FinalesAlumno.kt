package com.example.Evaluacion_U1.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Finales")
data class FinalesAlumno(
    @PrimaryKey
    var materia: String="",
    var Observaciones: String="",
    var acred: String="",
    var calif: Int=0,
    var grupo: String=""
)