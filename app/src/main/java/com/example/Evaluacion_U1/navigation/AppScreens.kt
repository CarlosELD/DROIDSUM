package com.example.Evaluacion_U1.navigation

sealed class AppScreens(val route:String) {
    object Login:AppScreens("Login")
    object Info:AppScreens("Info")
    object Kardex:AppScreens("Kardex")
    object Horario:AppScreens("Horario")
    object Finales:AppScreens("Finales")
    object Parciales:AppScreens("Parciales")
}