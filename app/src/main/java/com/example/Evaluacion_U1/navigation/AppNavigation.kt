package com.example.Evaluacion_U1.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavArgument
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.Evaluacion_U1.Finales
import com.example.Evaluacion_U1.Horario
import com.example.Evaluacion_U1.Kardex
import com.example.Evaluacion_U1.Parciales
import com.example.Evaluacion_U1.ViewModel.AppView
import com.example.Evaluacion_U1.loginApp
import com.example.Evaluacion_U1.principalHome

@Composable
fun AppNavigation(viewmodel: AppView){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = AppScreens.Login.route,
    ){
        composable(route =AppScreens.Login.route){
            loginApp(navController,viewmodel)
        }
        composable(route =AppScreens.Info.route){
            principalHome(navController,viewmodel)
        }
        composable(route =AppScreens.Kardex.route){
            Kardex(navController, viewmodel)
        }
        composable(route =AppScreens.Horario.route){
            Horario(navController, viewmodel)
        }
        composable(route =AppScreens.Finales.route){
            Finales(navController, viewmodel)
        }
        composable(route =AppScreens.Parciales.route){
            Parciales(navController, viewmodel)
        }
    }
}