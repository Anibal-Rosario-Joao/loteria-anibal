package com.anibalofice.loteria.compose

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.anibalofice.loteria.compose.home.HomeScreen
import com.anibalofice.loteria.compose.megasena.MegaSenaScreen
import com.anibalofice.loteria.compose.quina.QuinaScreen

@Composable
fun LoteriaApp(){
    val navController = rememberNavController()
    LoteriaAppNavHost(navController)

}

enum class AppRouter(val rota: String){
    HOME("home"),
    MEGA_SENA("megasena"),
    QUINA("quina")
}

@Composable
fun LoteriaAppNavHost(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = AppRouter.HOME.rota
    ) {
        composable(AppRouter.HOME.rota) {
            HomeScreen { item ->
                val router = when (item.id) {
                    1 -> AppRouter.MEGA_SENA
                    2 -> AppRouter.QUINA
                    else -> AppRouter.HOME
                }
                navController.navigate(router.rota)
            }
        }
        composable(AppRouter.MEGA_SENA.rota) {
            MegaSenaScreen()
        }
        composable(AppRouter.QUINA.rota) {
            QuinaScreen()
        }
    }
}