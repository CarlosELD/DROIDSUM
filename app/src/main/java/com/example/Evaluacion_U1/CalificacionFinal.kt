package com.example.Evaluacion_U1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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

class Finales : ComponentActivity() {
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
fun Finales(navController: NavController, viewmodel: AppView){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically){
                        if(viewmodel.modoOffline){
                            Row (horizontalArrangement = Arrangement.Center, modifier=Modifier.fillMaxWidth()){
                                Box(modifier = Modifier) {
                                    Row (verticalAlignment = Alignment.CenterVertically){
                                        val formateador = DateTimeFormatter.ofPattern("dd/MM/yy")
                                        Text(text = "OFFLINE   ->"+LocalDate.now().format(formateador)+"<-",
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
                        onClick = {navController.popBackStack()}) {
                        Icon(painter = painterResource(id = R.drawable.back),
                            contentDescription = "")
                    }
                })
        }
    ) { CalificacionFinal(it, viewmodel) }
}

@Composable
private fun CalificacionFinal(paddingValues: PaddingValues, viewmodel: AppView) {
    Surface(color = Color.Cyan) {
        LazyColumn(verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(5.dp),
            contentPadding = paddingValues
        ) {
            items(viewmodel.finales) {
                Card(modifier = Modifier.padding(15.dp), colors = CardColors(containerColor = Color.Yellow, contentColor = Color.Black, disabledContainerColor = Color.Green, disabledContentColor = Color.Black)) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        Text(it.materia, textAlign = TextAlign.Start,modifier = Modifier.padding(horizontal = 5.dp))
                        Text(text = "Calificación: " + it.calif, textAlign = TextAlign.Center)
                        Text(text = "Acreditación: " + it.acred, textAlign = TextAlign.Center)
                        Text(text = "Grupo: " + it.grupo, textAlign = TextAlign.Center)
                        Text(text = "Observaciones: " + it.Observaciones, textAlign = TextAlign.Center)
                    }

                }
            }
        }
        if (viewmodel.sinFinales) {
            LazyColumn(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize(),
                contentPadding = paddingValues
            ) {
                item {
                    Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center){
                        Text(text = "No se descargo")
                    }
                }
            }
        }
    }
}
