package com.example.Evaluacion_U1.modelos

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "Credenciales")
data class CredencialesAlumno(
   @PrimaryKey
   var matricula:String="",

   var acceso:String="",
   var estatus:String="",
   var tipoUsuario:Int=0,
   var contrasenia:String=""

)
