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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.Evaluacion_U1.ViewModel.AppView
import com.example.Evaluacion_U1.ui.theme.AutenticacionYConsultaTheme
import com.example.autenticacionyconsulta.R
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Parciales : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background){}
            }
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Parciales(navController: NavController, viewmodel: AppView){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if(viewmodel.modoOffline) {
                            Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()){
                                Box(modifier = Modifier) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        val formateador = DateTimeFormatter.ofPattern("dd/MM/yy")
                                        Text(text = "OFFLINE   ->"+LocalDate.now().format(formateador)+"<-",fontSize = 20.sp)
                                    }
                                }
                            }
                        }
                    }
                },
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }) {
                        Icon(painter = painterResource(id = R.drawable.back), contentDescription = "")
                    }
                })
        }
    ) { ParcialesP(viewmodel)}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ParcialesP(viewmodel: AppView){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {}) {
                        Icon(painter = painterResource(id = R.drawable.back),contentDescription = "")
                    }
                })
        }
    ) {presentacionParciales(viewmodel,it)}
}

@Composable
fun presentacionParciales(viewmodel: AppView, paddingValues: PaddingValues) {
    Surface(modifier = Modifier.fillMaxSize(),color = Color.Cyan) {
        if (viewmodel.sinHorario.equals(false)) {
            LazyColumn(
                contentPadding = paddingValues,
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(viewmodel.parciales) { materia ->
                    Card(modifier = Modifier.fillMaxWidth(.3f),
                        colors = CardColors(
                            containerColor = Color.Yellow,
                            contentColor = Color.Black,
                            disabledContainerColor = Color.Green,
                            disabledContentColor = Color.Red)
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxSize()
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            Text(text = materia.Materia)
                            Spacer(modifier = Modifier.height(5.dp))
                            if (materia.C1.isNotBlank()) {
                                Text(text = "Unidad 1: ${materia.C1}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C2.isNotBlank()) {
                                Text(text = "Unidad 2: ${materia.C2}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C3.isNotBlank()) {
                                Text(text = "Unidad 3: ${materia.C3}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C4.isNotBlank()) {
                                Text(text = "Unidad 4: ${materia.C4}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C5.isNotBlank()) {
                                Text(text = "Unidad 5: ${materia.C5}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C6.isNotBlank()) {
                                Text(text = "Unidad 6: ${materia.C6}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C7.isNotBlank()) {
                                Text(text = "Unidad 7: ${materia.C7}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C8.isNotBlank()) {
                                Text(text = "Unidad 8: ${materia.C8}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C9.isNotBlank()) {
                                Text(text = "Unidad 9: ${materia.C9}", modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C10.isNotBlank()) {
                                Text(text = "Unidad 10: ${materia.C10}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C11.isNotBlank()) {
                                Text(text = "Unidad 11: ${materia.C11}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C12.isNotBlank()) {
                                Text(text = "Unidad 12: ${materia.C12}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                            if (materia.C13.isNotBlank()) {
                                Text(text = "Unidad 13: ${materia.C13}",modifier = Modifier.padding(start = 5.dp))
                                Spacer(modifier = Modifier.height(7.dp))
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                }
            }
            if (viewmodel.sinParciales) {
                LazyColumn(
                    contentPadding = paddingValues,
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    item {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center
                        ) {
                            Text(text = "No se descargo")
                        }
                    }
                }
            }
        }
    }
}