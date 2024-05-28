package com.example.Evaluacion_U1.data

import android.content.Context
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.example.Evaluacion_U1.AlumnosApplication
import com.example.Evaluacion_U1.network.AlumnoApiService
import com.google.gson.Gson
import okhttp3.RequestBody.Companion.toRequestBody


interface AlumnosRepository {
    suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):String
    suspend fun getInfo():String
    suspend fun getKardex(lineamiento: String):String
    suspend fun getHorario():String
    suspend fun getParciales():String
    suspend fun getFinales(modoEducativo:String):String
}
class NetworkAlumnosRepository(private val alumnoApiService: AlumnoApiService): AlumnosRepository{
    override suspend fun getAccess(matricula: String, password: String, tipoUsuario:String):String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <accesoLogin xmlns="http://tempuri.org/">
                  <strMatricula>${matricula}</strMatricula>
                  <strContrasenia>${password}</strContrasenia>
                  <tipoUsuario>ALUMNO</tipoUsuario>
                </accesoLogin>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        return try { alumnoApiService.getCokies()
            val credencial=alumnoApiService.getAcceso(requestBody).string().split("{","}")
            if(credencial.size>1){
               return "{"+credencial[1]+"}"
            }
            return  ""
        }catch (e:Exception){
            ""
        }
    }

    override suspend fun getInfo():String{
        val xml = """
           <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAlumnoAcademicoWithLineamiento xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>""".trimIndent()
        val requestBody=xml.toRequestBody()
        return try {
            val respuestaInfo=alumnoApiService.getInfo(requestBody).string().split("{","}")
            if(respuestaInfo.size>1){"{"+respuestaInfo[1]+"}"} else ""}catch (e:Exception){""}
    }

    override suspend fun getKardex(lineamiento:String):String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllKardexConPromedioByAlumno xmlns="http://tempuri.org/">
                  <aluLineamiento>${lineamiento}</aluLineamiento>
                </getAllKardexConPromedioByAlumno>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {return alumnoApiService.getKardex(requestBody).string()}catch (e:Exception){return ""}
    }
    override suspend fun getHorario():String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
             xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCargaAcademicaByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {return "["+alumnoApiService.getHorario(requestBody).string().split("[\r\n  ","]").get(1)+"]"
        }catch (e:Exception){return ""}
    }

    override suspend fun getParciales():String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema"
             xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getCalifUnidadesByAlumno xmlns="http://tempuri.org/" />
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {return "["+alumnoApiService.getParciales(requestBody).string().split("[\r\n  ","]").get(1)+"]"
        }catch (e:Exception){return ""}
    }

    override suspend fun getFinales(modoEducativo:String):String{
        val xml = """
            <soap:Envelope xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:xsd="http://www.w3.org/2001/XMLSchema" 
            xmlns:soap="http://schemas.xmlsoap.org/soap/envelope/">
              <soap:Body>
                <getAllCalifFinalByAlumnos xmlns="http://tempuri.org/">
                  <bytModEducativo>${modoEducativo}</bytModEducativo>
                </getAllCalifFinalByAlumnos>
              </soap:Body>
            </soap:Envelope>
            """.trimIndent()
        val requestBody=xml.toRequestBody()
        try {return "["+alumnoApiService.getFinales(requestBody).string().split("[\r\n  ","]").get(1)+"]"
        }catch (e:Exception){return ""}
    }
}

/*
*realiza la solicitud correspondiente a la API de alumnos utilizando Retrofit y procesa la respuesta
*  para devolver la información solicitada. Si ocurre alguna excepción durante el proceso, el método
*  devuelve una cadena vacía.
*
* */


