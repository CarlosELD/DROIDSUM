package com.example.Evaluacion_U1.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ResumenKardex")
data class ResumenKardex(
    @PrimaryKey
    val PromedioGral: Double=0.0,
    val AvanceCdts: Double=0.0,
    val CdtsAcum: Int=0,
    val CdtsPlan: Int=0,
    val MatAprobadas: Int=0,
    val MatCursadas: Int=0
)