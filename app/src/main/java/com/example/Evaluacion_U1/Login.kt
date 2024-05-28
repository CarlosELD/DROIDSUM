package com.example.Evaluacion_U1

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.Evaluacion_U1.ViewModel.AppView
import com.example.Evaluacion_U1.ViewModel.WorkerAccessState
import com.example.Evaluacion_U1.navigation.AppNavigation
import com.example.Evaluacion_U1.navigation.AppScreens
import com.example.Evaluacion_U1.ui.theme.AutenticacionYConsultaTheme
import com.example.autenticacionyconsulta.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AutenticacionYConsultaTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val viewmodel: AppView = viewModel(factory = AppView.Factory)
                    AppNavigation(viewmodel)
                }
            }
        }

    }
}
fun showToastMsg(context: Context, msg: String) {
    val toast = Toast.makeText(context, msg, Toast.LENGTH_LONG)
    toast.setGravity(Gravity.CENTER, 0, 0)
    toast.show()
}

@Composable
fun loginApp(navController: NavController, viewmodel:AppView){
    val uiState by viewmodel.workerUiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope= rememberCoroutineScope()
    var passwordVisible by remember { mutableStateOf(false) }
    Surface(modifier = Modifier.fillMaxSize(), color = Color.Cyan) {
        Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center, modifier = Modifier.padding(40.dp)) {
            Text(text = "DROID SUM", fontSize = 30.sp)
            Spacer(modifier = Modifier.padding(15.dp))
            OutlinedTextField(
                value = viewmodel.matricula,
                label = { Text(text = "Matricula") },
                onValueChange = {
                    viewmodel.updateMatricula(it)
                    viewmodel.updateErrorLogin(false)
                    viewmodel.updateErrorInternet(false)
                },modifier = Modifier.fillMaxWidth().background(Color.White))
            Spacer(modifier = Modifier.height(25.dp))
            OutlinedTextField(
                value = viewmodel.password,
                label = { Text(text = "Contraseña") },
                onValueChange = {
                    viewmodel.updatePassword(it)
                    viewmodel.updateErrorLogin(false)
                    viewmodel.updateErrorInternet(false)
                },
                modifier = Modifier.fillMaxWidth().background(Color.White).padding(bottom = 16.dp),
                visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                trailingIcon = {
                    IconButton(onClick = { passwordVisible = !passwordVisible }) {
                        val icon: ImageVector = if (passwordVisible) Icons.Default.VisibilityOff
                        else Icons.Default.Visibility
                        Icon(icon,contentDescription = if (passwordVisible) "Ocultar" else "Mostrar")
                    }
                }
            )
            Spacer(modifier = Modifier.height(25.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    onClick = {
                        if (!viewmodel.matricula.equals("") && !viewmodel.password.equals("")) {
                            viewmodel.updateDaClicBoton(true)
                            ////////Estado del internet si el dispositivo tiene o no tiene acceso////////
                            scope.launch {
                                if (existeConexionInternet(context)) {
                                    val formateador = DateTimeFormatter.ofPattern("dd/MM/yy")
                                    showToastMsg(context,msg = "Se actualizaron los datos: " + LocalDate.now().format(formateador))
                                    viewmodel.updateInternetDisponible(true)
                                    viewmodel.getAccess()
                                } else { viewmodel.updateInternetDisponible(false)
                                    if (viewmodel.getAccessDB()) {
                                        showToastMsg(context, msg = "Base de datos local")
                                        navController.navigate(route = AppScreens.Info.route)
                                    }
                                }
                            }
                        } else { viewmodel.updateErrorLogin(true)}
                    },
                    ) {
                    Text(text = "Iniciar sesion",modifier = Modifier.width(120.dp),textAlign = TextAlign.Center)
                }
            }
            if (viewmodel.errorLogin) {
                Spacer(modifier = Modifier.height(45.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
                    Text(text = "Error en el Login\n son incorrectos",fontSize = 14.sp,color = Color(255, 158, 142))
                }
            }
            if (viewmodel.errorInternet) {
                Spacer(modifier = Modifier.height(45.dp))
                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Text(text = "No tiene Datos o Wifi",fontSize = 14.sp,color = Color(255, 158, 142))
                }
            }
            when (uiState) {
                is WorkerAccessState.Default -> {}
                is WorkerAccessState.Loading -> { CircularProgressIndicator(modifier = Modifier.padding(10.dp))}
                is WorkerAccessState.Complete -> {
                    if (viewmodel.internetDisponible) {
                        if (viewmodel.proporcionarAcceso(
                                (uiState as WorkerAccessState.Complete).outputUno,
                                (uiState as WorkerAccessState.Complete).outputDos)) {
                            viewmodel.updateAcceso(true)
                            viewmodel.updateFecha(LocalDate.now().toString())
                            pasarAventana(viewmodel, navController)
                        } else { viewmodel.updateErrorLogin(true)}
                    }
                }
            }
        }
    }
}

fun pasarAventana(viewmodel: AppView,navController: NavController){
    if(viewmodel.daClicBoton && viewmodel.acceso){
        viewmodel.updateDaClicBoton(false)
        viewmodel.updateAcceso(false)
        viewmodel.updateOffline(false)
        navController.navigate(route = AppScreens.Info.route)
    }
}

@Preview
@Composable
fun loginAppPreview(){
    Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center,modifier = Modifier.padding(40.dp)){
        OutlinedTextField(value = "", label = { Text(text = "Matricula") }, onValueChange = {}, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(25.dp))
        OutlinedTextField(value = "",label = { Text(text = "Contraseña") },onValueChange = {},modifier = Modifier.fillMaxWidth(),visualTransformation = PasswordVisualTransformation(),)
        Spacer(modifier = Modifier.height(35.dp))
        Spacer(modifier = Modifier.height(25.dp))
        Row(modifier=Modifier.fillMaxWidth(),horizontalArrangement = Arrangement.Center){
            Button(
                onClick = {}) {
                Text(text = "Iniciar sesion",modifier=Modifier.width(120.dp),textAlign = TextAlign.Center)}
        }
        Spacer(modifier = Modifier.height(45.dp))
        Column(horizontalAlignment = Alignment.CenterHorizontally,verticalArrangement = Arrangement.Center){
            Text(text = "no tiene Datos o Wifi",fontSize = 14.sp,color=Color(255,158,142))}
    }
}

suspend fun existeConexionInternet(context: Context): Boolean {
    val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkCapabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
    if (networkCapabilities != null) {
        // Verifica si hay conexión a través de Wi-Fi, datos móviles o Ethernet
        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
            // Verifica si realmente se puede acceder a Internet (por ejemplo, haciendo una solicitud HTTP)
            return verificaAccesoInternet()
        }
    }
    return false
}

suspend fun verificaAccesoInternet(): Boolean {
    return withContext(Dispatchers.IO) {
        try {
            // Verifica si se puede hacer una conexión a un servidor conocido (por ejemplo, google.com)
            val timeoutMs = 15000 // Tiempo de espera en milisegundos
            val socket = Socket()
            socket.connect(InetSocketAddress("google.com", 80), timeoutMs)
            socket.close()
            true // Hay acceso a Internet
        } catch (e: IOException) { false // No hay acceso a Internet
        }
    }
}

