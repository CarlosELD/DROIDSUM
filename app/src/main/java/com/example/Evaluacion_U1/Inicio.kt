package com.example.Evaluacion_U1

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.ListAlt
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.Evaluacion_U1.ViewModel.AppView
import com.example.Evaluacion_U1.ViewModel.WorkerAccessState
import com.example.Evaluacion_U1.navigation.AppScreens
import com.example.Evaluacion_U1.ui.theme.AutenticacionYConsultaTheme
import com.example.autenticacionyconsulta.R
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class InfoStudent : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = Color.Cyan){}
            }
        }
    }
}

@Composable
fun dataStudent(viewmodel: AppView) {
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Cyan) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.Center,){
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 60.dp, horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                Text(text = "DROID SUM", fontSize = 30.sp)
                Spacer(modifier = Modifier.padding(15.dp))
                Card(modifier = Modifier.padding(horizontal = 5.dp).size(320.dp, 320.dp),colors = CardColors(containerColor = Color.Yellow, contentColor = Color.Black, disabledContainerColor = Color.Green, disabledContentColor = Color.Red))
                {
                    Text(text = "Nombre Completo: \n" + viewmodel.infoAlumno.nombre,modifier = Modifier.weight(1f))
                    Text(text = "Número de control: \n" + viewmodel.infoAlumno.matricula,modifier = Modifier.weight(1f))
                    Text(text = "Especialidad: \n" + viewmodel.infoAlumno.especialidad,modifier = Modifier.weight(1f))
                    Text(text = "Nombre de la carrera: \n" + viewmodel.infoAlumno.carrera,modifier = Modifier.weight(1f))
                    Text(text = "Semestre: \n" + viewmodel.infoAlumno.semActual,modifier = Modifier.weight(1f))
                    Text(text = "Estatus academico:\n" + viewmodel.infoAlumno.estatus,modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun principalHome(navController: NavController, viewmodel: AppView){
    val uiStateFinales by viewmodel.workerUiStateFinales.collectAsStateWithLifecycle()
    val uiStateParciales by viewmodel.workerUiStateParciales.collectAsStateWithLifecycle()
    val uiStateHorario by viewmodel.workerUiStateHorario.collectAsStateWithLifecycle()
    val uiStateKardex by viewmodel.workerUiStateKardex.collectAsStateWithLifecycle()
    val uiStateKardex2 by viewmodel.workerUiStateKardex2.collectAsStateWithLifecycle()
    val uiStateKardexR by viewmodel.workerUiStateKardexResumen.collectAsStateWithLifecycle()
    val scope= rememberCoroutineScope()
    val context= LocalContext.current
    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(viewmodel.modoOffline){
                            Row (horizontalArrangement = Arrangement.Center,modifier=Modifier.fillMaxWidth()){
                                Box(modifier = Modifier){
                                    Row (verticalAlignment = Alignment.CenterVertically){
                                       val formateador = DateTimeFormatter.ofPattern("dd/MM/yy")
                                        Text(text = "Last update ->"+LocalDate.now().format(formateador)+"<-",fontSize = 20.sp)
                                        /*
                                        val currentDateInMillis = Calendar.getInstance().timeInMillis
                                        SharedPreferencesHelper.saveLastAccessDate(context, currentDateInMillis)
                                    */
                                    }
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = {navController.popBackStack()}) {
                        Icon(painter = painterResource(id = R.drawable.back),contentDescription = "Atras")}
                }
            )
        },
        bottomBar = {
                BottomAppBar(modifier = Modifier.fillMaxWidth(),contentPadding = PaddingValues(16.dp)){
                    Row(horizontalArrangement = Arrangement.SpaceEvenly,modifier = Modifier.fillMaxWidth()){
                        // Botón para Kardex
                        IconButton(
                            onClick = {
                                if (viewmodel.internetDisponible) {
                                    viewmodel.getKardex()
                                    viewmodel.updateDaClicKardex(true)
                                } else { scope.launch {viewmodel.getKardexDB()}
                                    navController.navigate(route = AppScreens.Horario.route)
                                }
                            },modifier = Modifier.weight(1f)){Icon(Icons.Default.Home, contentDescription = "Kardex")}
                        // Botón para Horario
                        IconButton(
                            onClick = {
                                if (viewmodel.internetDisponible) {
                                    viewmodel.getHorario()
                                    viewmodel.updateDaClicHorario(true)
                                } else { scope.launch {viewmodel.getHorarioDB()}
                                    navController.navigate(route = AppScreens.Horario.route)
                                }
                            },
                            modifier = Modifier.weight(1f)){ Icon(Icons.Default.CalendarToday, contentDescription = "Horario")}

                        // Botón para Parciales
                        IconButton(
                            onClick = {
                                if (viewmodel.internetDisponible) {
                                    viewmodel.getParciales()
                                    viewmodel.updateDaClicParciales(true)
                                } else {scope.launch {viewmodel.getParcialesDB()}
                                    navController.navigate(route = AppScreens.Parciales.route)
                                }
                            },modifier = Modifier.weight(1f)){Icon(Icons.Default.List, contentDescription = "Parciales")}

                        // Botón para Finales
                        IconButton(
                            onClick = {
                                if (viewmodel.internetDisponible) {
                                    viewmodel.getFinales()
                                    viewmodel.updateDaClicFinales(true)
                                } else {scope.launch {viewmodel.getFinalesDB()}
                                    navController.navigate(route = AppScreens.Finales.route)
                                }
                            },
                            modifier = Modifier.weight(1f)){Icon(Icons.Default.ListAlt, contentDescription = "Finales")}
                    }
                }
            }
    ){
        dataStudent(viewmodel)
        when (uiStateFinales) {
            is WorkerAccessState.Default -> {}
            is WorkerAccessState.Loading -> {}
            is WorkerAccessState.Complete -> {
                if(viewmodel.daClicFinales){
                    viewmodel.updateDaClicFinales(false)
                    viewmodel.actualizarFinales((uiStateFinales as WorkerAccessState.Complete).outputUno)
                    viewmodel.updateSinFinales(false)
                    navController.navigate(route = AppScreens.Finales.route)
                }
            }
        }
        when (uiStateParciales) {
            is WorkerAccessState.Default -> {}
            is WorkerAccessState.Loading -> {}
            is WorkerAccessState.Complete -> {
                if(viewmodel.daClicParciales){
                    viewmodel.updateDaClicParciales(false)
                    viewmodel.actualizarParciales((uiStateParciales as WorkerAccessState.Complete).outputUno)
                    viewmodel.updateSinParciales(false)
                    navController.navigate(route = AppScreens.Parciales.route) } } }
        when (uiStateHorario) {
            is WorkerAccessState.Default -> {}
            is WorkerAccessState.Loading -> {}
            is WorkerAccessState.Complete -> {
                if(viewmodel.daClicHorario){
                    viewmodel.updateDaClicHorario(false)
                    viewmodel.actualizarHorario((uiStateHorario as WorkerAccessState.Complete).outputUno)
                    viewmodel.updateSinHorario(false)
                    navController.navigate(route = AppScreens.Horario.route)
                }
            }
        }
        when (uiStateKardex) {
            is WorkerAccessState.Default -> {}
            is WorkerAccessState.Loading -> {}
            is WorkerAccessState.Complete -> {
                if(viewmodel.daClicKardex){
                    if(viewmodel.kardex1Actualizado==false) {
                        viewmodel.limpiarKardex()
                        viewmodel.actualizarKardex1((uiStateKardex as WorkerAccessState.Complete).outputUno)
                        viewmodel.updateSinKardex(false) } } } }
        when (uiStateKardex2) {
            is WorkerAccessState.Default -> {}
            is WorkerAccessState.Loading -> {}
            is WorkerAccessState.Complete -> {
                if(viewmodel.kardex1Finalizado){
                    if(viewmodel.kardex2Actualizado==false){ viewmodel.actualizarKardex2((uiStateKardex2 as WorkerAccessState.Complete).outputUno) } } } }
        when (uiStateKardexR) {
            is WorkerAccessState.Default -> {}
            is WorkerAccessState.Loading -> {}
            is WorkerAccessState.Complete -> {
                viewmodel.actualizarResumenKardex((uiStateKardexR as WorkerAccessState.Complete).outputUno)
                if(viewmodel.kardex1Finalizado && viewmodel.kardex2Finalizado && viewmodel.daClicKardex){
                    viewmodel.updateDaClicKardex(false)
                    navController.navigate(route = AppScreens.Kardex.route) } } } }
}

fun validarCampos(dato:String):String{
    if(dato.equals("")){
        return "Ninguno"
    }else if(dato.equals("true")){
        return "Si"
    }else if(dato.equals("false")){
        return "No"
    }else{
        return dato
    }
}