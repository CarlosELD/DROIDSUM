package com.example.Evaluacion_U1

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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.Evaluacion_U1.ViewModel.AppView
import com.example.Evaluacion_U1.ui.theme.AutenticacionYConsultaTheme
import com.example.autenticacionyconsulta.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Horario : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Horario(navController: NavController, viewmodel: AppView){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(viewmodel.modoOffline) {
                            Row(
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Box(modifier = Modifier) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        val formateador = DateTimeFormatter.ofPattern("dd/MM/yy")
                                        Text(text = "OFFLINE ->"+LocalDate.now().format(formateador)+"<-",
                                            fontSize = 20.sp
                                        )
                                    }
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(
                            painter = painterResource(id = R.drawable.back),
                            contentDescription = "")
                    }
                })
        }
    ) {presentacion(viewmodel = viewmodel,it)}
}

@Composable
fun presentacion(viewmodel: AppView, paddingValues: PaddingValues) {
    Surface(color = Color.Cyan){
        if (!(viewmodel.sinHorario.equals(true))) {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                items(viewmodel.lunes) { horario ->
                    Card(
                        modifier = Modifier.fillMaxWidth(.6f),
                        colors = CardColors(contentColor = Color.Black, containerColor = Color.Yellow, disabledContentColor = Color.Green, disabledContainerColor = Color.Black)
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            Text(
                                text = "Materia: ${horario.Materia}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Maestro: ${horario.Docente}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Grupo: ${horario.Grupo}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Lunes: ${horario.Lunes}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Martes: ${horario.Martes}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Miércoles: ${horario.Miercoles}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Jueves: ${horario.Jueves}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Viernes: ${horario.Viernes}",
                                fontSize = 16.sp,
                                modifier = Modifier.padding(8.dp),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(7.dp))
                }
            }
        } else {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                item {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        Text(text = "No se descargo")
                    }
                }
            }
        }
    }
}


