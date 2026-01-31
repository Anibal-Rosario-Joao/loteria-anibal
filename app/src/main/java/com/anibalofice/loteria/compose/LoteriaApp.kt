package com.anibalofice.loteria.compose

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.anibalofice.loteria.App
import com.anibalofice.loteria.compose.detail.BetListDetailScreen
import com.anibalofice.loteria.compose.home.HomeScreen
import com.anibalofice.loteria.compose.megasena.MegaSenaScreen
import com.anibalofice.loteria.compose.quina.QuinaScreen

@Composable
fun LoteriaApp(context: Context= LocalContext.current){
    val navController = rememberNavController()
    LoteriaAppNavHost(navController)

}

enum class AppRouter(val rota: String){
    HOME("home"),
    MEGA_SENA("megasena"),
    QUINA("quina"),
    BET_LIST_DETAIL("betlistDetail")
}

enum class Type(val betType: String){
    MEGA_SENA("Mega Sena"),
    QUINA("Quina")
}

@Composable
fun LoteriaAppNavHost(navController: NavHostController,contexto: Context = LocalContext.current){
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
            MegaSenaScreen (
                clicado = {
                    navController.navigate(AppRouter.BET_LIST_DETAIL.rota + "/$it")
                },
                backClicado = {
                    navController.navigateUp()
                }
            )
        }
        composable(AppRouter.QUINA.rota) {
            QuinaScreen(
                clicado = {
                    navController.navigate(AppRouter.BET_LIST_DETAIL.rota + "/$it")
                },
                backClicado = {
                    navController.navigateUp()
                }
            )
        }
        // betListDetal / (dinamico)
        composable(
            route = AppRouter.BET_LIST_DETAIL.rota + "/{type}",
            arguments = listOf(navArgument("type") {
                type = NavType.StringType
            }
            )
        ) {
            val type = it.arguments?.getString("type")?: throw Exception("Tipo não encontrado")
            BetListDetailScreen(type = type, backClicado = {
                navController.navigateUp()
            })
        }


    }
}