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
import androidx.compose.foundation.layout.size
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

class kardex : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                Surface(modifier = Modifier.fillMaxSize(),color = MaterialTheme.colorScheme.background){}
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Kardex(navController: NavController, viewmodel: AppView){
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        if (viewmodel.modoOffline) { // verificacion del modo offline con el view
                            Row(horizontalArrangement = Arrangement.Center,modifier = Modifier.fillMaxWidth()){
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
                        Icon(painter = painterResource(id = R.drawable.back),contentDescription = "")}
                })
        }) {KardexMateria(viewmodel = viewmodel,it) }}

@Composable
private fun KardexMateria(viewmodel: AppView, paddingValues:PaddingValues) {
    Surface(modifier = Modifier.fillMaxSize(),color = Color.Cyan) {
        Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically){
            Column(
                modifier = Modifier.fillMaxWidth().padding(vertical = 60.dp, horizontal = 5.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(modifier = Modifier.padding(5.dp))
                Text(text = "KARDEX", fontSize = 30.sp)
                Spacer(modifier = Modifier.padding(5.dp))
                Row {
                    Text(text = "Creditos acumulados: ${viewmodel.resumenKardex.CdtsAcum}",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(text = "Materias cursadas: ${viewmodel.resumenKardex.MatCursadas}",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(text = "Materias aprobadas: ${viewmodel.resumenKardex.MatAprobadas}",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                    Text(text = "PROMEDIO GENERAL: ${viewmodel.resumenKardex.PromedioGral}",
                        modifier = Modifier.padding(5.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(modifier = Modifier.padding(3.dp))
                LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 60.dp)) {
                    items(viewmodel.kardex) { materia ->
                        Spacer(modifier = Modifier.height(5.dp))
                        Card(modifier = Modifier.padding(horizontal = 6.dp).size(750.dp, 300.dp),
                            colors = CardColors(containerColor = Color.Yellow, contentColor = Color.Black, disabledContainerColor = Color.Green, disabledContentColor = Color.Red)) {
                            Text(text = materia.Materia, textAlign = TextAlign.Center, fontSize = 14.sp, modifier = Modifier.fillMaxWidth())
                            Text(text = "Calif: ${materia.Calif}", modifier = Modifier.weight(.1f), textAlign = TextAlign.Center)
                            Text(text = "Clave: ${materia.ClvMat}", modifier = Modifier.weight(.1f), textAlign = TextAlign.Center)
                            Text(text = "Oficial: ${materia.ClvOfiMat}", modifier = Modifier.weight(.1f), textAlign = TextAlign.Center)
                            Text(text = "Cdts: ${materia.Cdts}", modifier = Modifier.weight(.1f), textAlign = TextAlign.Center)
                        }
                    }
                }           
            }
        }
    }
}