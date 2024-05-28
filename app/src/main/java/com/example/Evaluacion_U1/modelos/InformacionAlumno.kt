package com.example.Evaluacion_U1.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Informacion")
data class InformacionAlumno (
    @PrimaryKey
    var matricula:String="",
    var fechaReins:String="",
    var modEducativo:String="",
    var adeudo:String="",
    var urlFoto:String="",
    var adeudoDescripcion:String="",
    var inscrito:Boolean=false,
    var estatus:String="",
    var semActual:String="",
    var cdtosAcumulados:Int=0,
    var cdtosActuales:Int=0,
    var especialidad:String="",
    var carrera:String="",
    var lineamiento:Int=0,
    var nombre:String=""
)